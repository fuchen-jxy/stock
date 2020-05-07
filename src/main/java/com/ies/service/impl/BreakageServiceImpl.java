package com.ies.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ies.constast.SysConstast;
import com.ies.domain.*;
import com.ies.mapper.*;
import com.ies.service.IBreakageService;
import com.ies.utils.DataGridView;
import com.ies.utils.WebUtils;
import com.ies.vo.BreakageVo;
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
 * @create: 2020-05-07 00:46
 **/
@Service
public class BreakageServiceImpl implements IBreakageService {

    @Resource
    private BreakageMapper breakageMapper;
    @Resource
    private ItemMapper itemMapper;
    @Resource
    private StockMapper stockMapper;
    @Resource
    private StockInOutRecordMapper stockInOutRecordMapper;
    @Resource
    private IncomeMapper incomeMapper;

    @Override
    public DataGridView loadAllBreakage(BreakageVo breakageVo) {
        Page<Object> page = PageHelper.startPage(breakageVo.getPage(), breakageVo.getLimit());
        List<Breakage> breakages = breakageMapper.loadAllBreakage(breakageVo);
        return new DataGridView(page.getTotal(), breakages);
    }

    @Override
    @Transactional
    public Integer addBreakage(BreakageVo breakageVo) {
        User user = (User) WebUtils.getHttpSession().getAttribute("user");
        breakageVo.setUserId(user.getId());
        breakageVo.setCreated(new Date());
        breakageMapper.insert(breakageVo);

        Item item = itemMapper.selectByPrimaryKey(breakageVo.getItemId());
        if (item.getStock() < breakageVo.getCount()) {
            throw new IllegalArgumentException("该商品的库存少于可报损数");
        }
        Integer remainCount = item.getStock() - breakageVo.getCount();
        item.setStock(remainCount);
        itemMapper.updateByPrimaryKey(item);

        StockCriteria stockCriteria = new StockCriteria();
        stockCriteria.createCriteria().andItemIdEqualTo(breakageVo.getItemId()).andStatusEqualTo(SysConstast.STOCK_INIT);
        List<Stock> stocks = stockMapper.selectByExampleWithRowbounds(stockCriteria, new RowBounds(0, breakageVo.getCount()));
        for (Stock stock : stocks) {
            stock.setStatus(SysConstast.STOCK_RETURN);
            stockMapper.updateByPrimaryKey(stock);
        }

        StockInOutRecord stockInOutRecord = new StockInOutRecord();
        stockInOutRecord.setCount(breakageVo.getCount());
        stockInOutRecord.setCreated(new Date());
        stockInOutRecord.setItemId(breakageVo.getItemId());
        stockInOutRecord.setRelId(breakageVo.getId());
        stockInOutRecord.setRemain(remainCount);
        stockInOutRecord.setType(SysConstast.CODE_ONE);
        stockInOutRecord.setUserId(user.getId());
        stockInOutRecordMapper.insert(stockInOutRecord);

        Income income = new Income();
        income.setCreated(new Date());
        income.setUserId(user.getId());
        BigDecimal totalMoney = breakageVo.getPrice().multiply(BigDecimal.valueOf(breakageVo.getCount()));
        income.setMoneyOut(totalMoney);
        income.setMoneyIn(BigDecimal.ZERO);
        income.setType(SysConstast.CODE_ONE);
        income.setRelId(breakageVo.getId());
        income.setRemark("系统生成");
        income.setSource("商品报损");
        incomeMapper.insert(income);

        return SysConstast.CODE_ONE;
    }

    @Override
    public void updateBreakage(BreakageVo breakageVo) {

    }

    @Override
    @Transactional
    public void deleteBatchBreakage(Long[] ids) {
        for (Long id : ids) {
            deleteBreakage(id);
        }
    }

    @Override
    @Transactional
    public void deleteBreakage(Long id) {
        User user = (User) WebUtils.getHttpSession().getAttribute("user");
        Breakage breakage = breakageMapper.selectByPrimaryKey(id);
        int count = breakage.getCount();

        Item item = itemMapper.selectByPrimaryKey(breakage.getItemId());
        item.setStock(item.getStock() + count);
        itemMapper.updateByPrimaryKey(item);

        StockCriteria stockCriteria = new StockCriteria();
        stockCriteria.createCriteria().andItemIdEqualTo(item.getId()).andStatusEqualTo(SysConstast.STOCK_RETURN);
        List<Stock> stocks = stockMapper.selectByExampleWithRowbounds(stockCriteria, new RowBounds(0, count));
        for (Stock stock : stocks) {
            stock.setStatus(SysConstast.STOCK_INIT);
            stockMapper.updateByPrimaryKey(stock);
        }

        StockInOutRecordCriteria stockInOutRecordCriteria = new StockInOutRecordCriteria();
        stockInOutRecordCriteria.createCriteria().andRelIdEqualTo(breakage.getId());
        stockInOutRecordMapper.deleteByExample(stockInOutRecordCriteria);

        IncomeCriteria incomeCriteria = new IncomeCriteria();
        incomeCriteria.createCriteria().andRelIdEqualTo(breakage.getId());
        incomeMapper.deleteByExample(incomeCriteria);

        breakageMapper.deleteByPrimaryKey(breakage.getId());
    }

}
