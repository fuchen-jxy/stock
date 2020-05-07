package com.ies.service.impl;

import cn.hutool.core.lang.Assert;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ies.constast.SysConstast;
import com.ies.domain.*;
import com.ies.mapper.*;
import com.ies.service.ICaigouService;
import com.ies.utils.DataGridView;
import com.ies.utils.WebUtils;
import com.ies.vo.CaigouVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-06 00:21
 **/
@Service
public class CaigouServiceImpl implements ICaigouService {

    @Resource
    private CaigouMapper caigouMapper;
    @Resource
    private IncomeMapper incomeMapper;
    @Resource
    private StockMapper stockMapper;
    @Resource
    private StockInOutRecordMapper stockInOutRecordMapper;
    @Resource
    private ItemMapper itemMapper;

    @Override
    public DataGridView loadAllCaigou(CaigouVo caigouVo) {
        Page<Object> page = PageHelper.startPage(caigouVo.getPage(), caigouVo.getLimit());
        List<Caigou> caigouList = caigouMapper.loadAllCaigou(caigouVo);
        return new DataGridView(page.getTotal(), caigouList);
    }

    /**
     * 新增采购单  更新商品库存  新增财务收入支持  新增库存   新增库存出入库
     *
     * @param caigouVo
     * @return
     */
    @Override
    @Transactional
    public Integer addCaigou(CaigouVo caigouVo) {
        Assert.isTrue(caigouVo.getCount() != null && caigouVo.getCount() > 0, "采购数量不能小于0");
        User user = (User) WebUtils.getHttpSession().getAttribute("user");

        caigouVo.setCreated(new Date());
        caigouVo.setUserId(user.getId());
        caigouMapper.insert(caigouVo);

        Item item = itemMapper.selectByPrimaryKey(caigouVo.getItemId());
        Integer remain = SysConstast.CODE_ONE.equals(caigouVo.getType()) ? (item.getStock() + caigouVo.getCount()) : (item.getStock() - caigouVo.getCount());
        item.setStock(remain);
        itemMapper.updateByPrimaryKey(item);

        Income income = new Income();
        income.setCreated(new Date());
        income.setUserId(user.getId());
        income.setType(SysConstast.CODE_ONE);
        income.setSource(SysConstast.CODE_ONE.equals(caigouVo.getType()) ? "新建采购单" : "新建采退单");
        income.setRemark("系统生成");
        income.setMoneyOut(caigouVo.getPrice());
        income.setMoneyIn(BigDecimal.ZERO);
        income.setRelId(caigouVo.getId());
        incomeMapper.insert(income);

        if (SysConstast.CODE_ONE.equals(caigouVo.getType())) {
            for (int i = 1; i <= caigouVo.getCount(); i++) {
                Stock stock = new Stock();
                stock.setCreated(new Date());
                stock.setItemId(caigouVo.getItemId());
                stock.setRelId(caigouVo.getId());
                stock.setIndexs(i);
                stock.setStatus(SysConstast.STOCK_INIT);
                stockMapper.insert(stock);
            }
        } else {
            StockCriteria stockCriteria = new StockCriteria();
            stockCriteria.createCriteria().andItemIdEqualTo(caigouVo.getItemId()).andStatusEqualTo(SysConstast.STOCK_INIT);
            List<Stock> stocks = stockMapper.selectByExampleWithRowbounds(stockCriteria, new RowBounds(0, caigouVo.getCount()));
            for (Stock stock : stocks) {
                stockMapper.deleteByPrimaryKey(stock.getId());
            }
        }

        StockInOutRecord stockInOutRecord = new StockInOutRecord();
        stockInOutRecord.setCount(caigouVo.getCount());
        stockInOutRecord.setCreated(new Date());
        stockInOutRecord.setItemId(caigouVo.getItemId());
        stockInOutRecord.setRelId(caigouVo.getId());
        stockInOutRecord.setRemain(remain);
        stockInOutRecord.setType(caigouVo.getType());
        stockInOutRecord.setUserId(user.getId());
        stockInOutRecordMapper.insert(stockInOutRecord);

        return SysConstast.CODE_SUCCESS;
    }

    @Override
    @Transactional
    public void deleteBatchCaigou(Long[] ids) {
        for (Long id : ids) {
            deleteCaigou(id);
        }
    }

    /**
     * 删除采购单，需要删除财务-收入支持，删除库存，删除库存出入库，商品库存数相应减少
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteCaigou(Long id) {
        StockCriteria stockCriteria = new StockCriteria();
        stockCriteria.createCriteria().andRelIdEqualTo(id).andStatusEqualTo(SysConstast.STOCK_USED);
        int countStockUsed = stockMapper.countByExample(stockCriteria);
        if (countStockUsed > 0) {
            throw new IllegalArgumentException("当前采购单库存已经出库无法删除");
        }

        Caigou caigou = caigouMapper.selectByPrimaryKey(id);
        Integer count = caigou.getCount();
        Long itemId = caigou.getItemId();
        caigouMapper.deleteByPrimaryKey(id);

        IncomeCriteria incomeCriteria = new IncomeCriteria();
        incomeCriteria.createCriteria().andRelIdEqualTo(id);
        incomeMapper.deleteByExample(incomeCriteria);

        StockCriteria stockCriteria4Delete = new StockCriteria();
        stockCriteria4Delete.createCriteria().andRelIdEqualTo(id);
        stockMapper.deleteByExample(stockCriteria4Delete);

        StockInOutRecordCriteria stockInOutRecordCriteria = new StockInOutRecordCriteria();
        stockInOutRecordCriteria.createCriteria().andRelIdEqualTo(id);
        stockInOutRecordMapper.deleteByExample(stockInOutRecordCriteria);

        Item item = itemMapper.selectByPrimaryKey(itemId);
        item.setStock(item.getStock() - count);
        itemMapper.updateByPrimaryKey(item);

    }


}
