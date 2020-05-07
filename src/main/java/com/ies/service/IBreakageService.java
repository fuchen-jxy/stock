package com.ies.service;

import com.ies.utils.DataGridView;
import com.ies.vo.BreakageVo;

public interface IBreakageService {
    DataGridView loadAllBreakage(BreakageVo breakageVo);

    Integer addBreakage(BreakageVo breakageVo);

    void updateBreakage(BreakageVo breakageVo);

    void deleteBatchBreakage(Long[] ids);

    void deleteBreakage(Long id);
}
