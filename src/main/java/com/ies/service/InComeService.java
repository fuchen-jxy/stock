package com.ies.service;

import com.ies.utils.DataGridView;
import com.ies.vo.IncomeVo;

import java.util.Map;

public interface InComeService {
    DataGridView loadAllIncome(IncomeVo incomeVo);

    Integer addIncome(IncomeVo incomeVo);

    void updateIncome(IncomeVo incomeVo);

    void deleteBatchIncome(Long[] ids);

    void deleteIncome(Long id);

    Map<String, Object> incomeTotalIncome(IncomeVo incomeVo);
}
