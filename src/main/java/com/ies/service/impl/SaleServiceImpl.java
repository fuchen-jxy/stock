package com.ies.service.impl;

import cn.hutool.core.lang.Assert;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ies.constast.SysConstast;
import com.ies.domain.*;
import com.ies.mapper.*;
import com.ies.service.ISaleService;
import com.ies.utils.DataGridView;
import com.ies.utils.WebUtils;
import com.ies.vo.SaleVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-07 02:01
 **/
@Service
public class SaleServiceImpl implements ISaleService {

    @Resource
    private SaleMapper saleMapper;
    @Resource
    private ItemMapper itemMapper;
    @Resource
    private StockMapper stockMapper;
    @Resource
    private StockInOutRecordMapper stockInOutRecordMapper;
    @Resource
    private IncomeMapper incomeMapper;

    @Override
    public DataGridView loadAllSale(SaleVo saleVo) {
        Page<Object> page = PageHelper.startPage(saleVo.getPage(), saleVo.getLimit());
        List<Sale> saleList = saleMapper.loadAllSale(saleVo);
        return new DataGridView(page.getTotal(), saleList);
    }

    @Override
    @Transactional
    public Integer addSale(SaleVo saleVo) {
        Item item = itemMapper.selectByPrimaryKey(saleVo.getItemId());
        Assert.isTrue(item != null, "商品不存在");
        if (SysConstast.CODE_ONE.equals(saleVo.getType()) && item.getStock() < saleVo.getCount()) {
            throw new IllegalArgumentException("销售商品库存不足");
        }
        int remain = SysConstast.CODE_ONE.equals(saleVo.getType()) ? (item.getStock() - saleVo.getCount()) : (item.getStock() + saleVo.getCount());
        item.setStock(remain);
        itemMapper.updateByPrimaryKey(item);

        User user = (User) WebUtils.getHttpSession().getAttribute("user");
        saleVo.setUserId(user.getId());
        saleVo.setCreated(new Date());
        saleMapper.insert(saleVo);
        if (SysConstast.CODE_ONE.equals(saleVo.getType())) {
            StockCriteria stockCriteria = new StockCriteria();
            stockCriteria.createCriteria().andItemIdEqualTo(saleVo.getItemId()).andStatusEqualTo(SysConstast.STOCK_INIT);
            List<Stock> stocks = stockMapper.selectByExampleWithRowbounds(stockCriteria, new RowBounds(0, saleVo.getCount()));
            for (Stock stock : stocks) {
                stock.setStatus(SysConstast.STOCK_USED);
                stockMapper.updateByPrimaryKey(stock);
            }
        } else {
            StockCriteria stockCriteria = new StockCriteria();
            stockCriteria.createCriteria().andItemIdEqualTo(saleVo.getItemId()).andStatusEqualTo(SysConstast.STOCK_USED);
            List<Stock> stocks = stockMapper.selectByExampleWithRowbounds(stockCriteria, new RowBounds(0, saleVo.getCount()));
            for (Stock stock : stocks) {
                stock.setStatus(SysConstast.STOCK_INIT);
                stockMapper.updateByPrimaryKey(stock);
            }
        }

        StockInOutRecord stockInOutRecord = new StockInOutRecord();
        stockInOutRecord.setCount(saleVo.getCount());
        stockInOutRecord.setCreated(new Date());
        stockInOutRecord.setItemId(saleVo.getItemId());
        stockInOutRecord.setRelId(saleVo.getId());
        stockInOutRecord.setRemain(remain);
        stockInOutRecord.setType(saleVo.getType());
        stockInOutRecord.setUserId(user.getId());
        stockInOutRecordMapper.insert(stockInOutRecord);

        Income income = new Income();
        income.setCreated(new Date());
        income.setUserId(user.getId());
        income.setType(SysConstast.CODE_TWO);
        income.setSource(SysConstast.CODE_ONE.equals(saleVo.getType()) ? "新建销售单" : "新建销退单");
        income.setRemark("系统生成");
        income.setMoneyOut(BigDecimal.ZERO);
        BigDecimal money = saleVo.getPrice().multiply(BigDecimal.valueOf(saleVo.getCount()));
        income.setMoneyIn(money);
        income.setRelId(saleVo.getId());
        incomeMapper.insert(income);

        return SysConstast.CODE_ONE;
    }

    @Override
    public void updateSale(SaleVo saleVo) {

    }

    @Override
    @Transactional
    public void deleteBatchSale(Long[] ids) {
        for (Long id : ids) {
            deleteSale(id);
        }
    }

    @Override
    @Transactional
    public void deleteSale(Long id) {
        Sale sale = saleMapper.selectByPrimaryKey(id);
        if (SysConstast.CODE_TWO.equals(sale.getType())) {
            throw new IllegalArgumentException("已经销退了");
        }
        int count = sale.getCount();

        Item item = itemMapper.selectByPrimaryKey(sale.getItemId());
        Assert.isTrue(item != null, "商品不存在");
        int remain = item.getStock() + count;
        item.setStock(item.getStock() + count);
        itemMapper.updateByPrimaryKey(item);

        User user = (User) WebUtils.getHttpSession().getAttribute("user");
        StockCriteria stockCriteria = new StockCriteria();
        stockCriteria.createCriteria().andItemIdEqualTo(sale.getItemId()).andStatusEqualTo(SysConstast.STOCK_USED);
        List<Stock> stocks = stockMapper.selectByExampleWithRowbounds(stockCriteria, new RowBounds(0, count));
        for (Stock stock : stocks) {
            stock.setStatus(SysConstast.STOCK_INIT);
            stockMapper.updateByPrimaryKey(stock);
        }

        StockInOutRecord stockInOutRecord = new StockInOutRecord();
        stockInOutRecord.setCount(count);
        stockInOutRecord.setCreated(new Date());
        stockInOutRecord.setItemId(sale.getItemId());
        stockInOutRecord.setRelId(sale.getId());
        stockInOutRecord.setRemain(remain);
        stockInOutRecord.setType(SysConstast.CODE_TWO);
        stockInOutRecord.setUserId(user.getId());
        stockInOutRecordMapper.insert(stockInOutRecord);

        Income income = new Income();
        income.setCreated(new Date());
        income.setUserId(user.getId());
        income.setType(SysConstast.CODE_ONE);
        income.setSource("生成销退单");
        income.setRemark("系统生成");
        BigDecimal money = sale.getPrice().multiply(BigDecimal.valueOf(sale.getCount()));
        income.setMoneyOut(money);
        income.setMoneyIn(BigDecimal.ZERO);
        income.setRelId(sale.getId());
        incomeMapper.insert(income);

        sale.setType(SysConstast.CODE_TWO);
        saleMapper.updateByPrimaryKey(sale);
    }

}
