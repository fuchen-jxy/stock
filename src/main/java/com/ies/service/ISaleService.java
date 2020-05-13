package com.ies.service;

import com.ies.utils.DataGridView;
import com.ies.vo.SaleVo;

import java.util.Map;

public interface ISaleService {
    DataGridView loadAllSale(SaleVo saleVo);

    Integer addSale(SaleVo saleVo);

    void updateSale(SaleVo saleVo);

    void deleteBatchSale(Long[] ids);

    void deleteSale(Long id);

    Map<String, Object> totalMoneyAndCount(SaleVo saleVo);
}
