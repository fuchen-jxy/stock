package com.ies.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ies.domain.Stock;
import com.ies.domain.StockInOutRecord;
import com.ies.mapper.StockInOutRecordMapper;
import com.ies.mapper.StockMapper;
import com.ies.service.IStockService;
import com.ies.utils.DataGridView;
import com.ies.vo.StockInOutRecordVo;
import com.ies.vo.StockVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-08 00:28
 **/
@Service
public class StockServiceImpl implements IStockService {

    @Resource
    private StockMapper stockMapper;
    @Resource
    private StockInOutRecordMapper stockInOutRecordMapper;

    @Override
    public DataGridView loadAllStockInOutRecord(StockInOutRecordVo stockInOutRecordVo) {
        Page<Object> page = PageHelper.startPage(stockInOutRecordVo.getPage(), stockInOutRecordVo.getLimit());
        List<StockInOutRecord> stockInOutRecordList = stockInOutRecordMapper.loadAllStockInOutRecord(stockInOutRecordVo);
        return new DataGridView(page.getTotal(), stockInOutRecordList);
    }

    @Override
    public DataGridView loadAllItemYear(StockVo stockVo) {
        Page<Object> page = PageHelper.startPage(stockVo.getPage(), stockVo.getLimit());
        List<Stock> stockList = stockMapper.loadAllItemYear(stockVo);
        return new DataGridView(page.getTotal(), stockList);
    }
}
