package com.ies.service;

import com.ies.utils.DataGridView;
import com.ies.vo.CaigouVo;

public interface ICaigouService {
    DataGridView loadAllCaigou(CaigouVo caigouVo);

    Integer addCaigou(CaigouVo caigouVo);

    void deleteBatchCaigou(Long[] ids);

    void deleteCaigou(Long id);
}
