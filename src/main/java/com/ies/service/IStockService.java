package com.ies.service;

import com.ies.utils.DataGridView;
import com.ies.vo.StockInOutRecordVo;
import com.ies.vo.StockVo;

public interface IStockService {

    DataGridView loadAllStockInOutRecord(StockInOutRecordVo stockInOutRecordVo);

    DataGridView loadAllItemYear(StockVo stockVo);
}
