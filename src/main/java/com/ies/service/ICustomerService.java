package com.ies.service;

import com.ies.utils.DataGridView;
import com.ies.vo.CustomerVo;

public interface ICustomerService {

    DataGridView loadAllCustomer(CustomerVo customerVo);

    Integer addCustomer(CustomerVo customerVo);

    void updateCustomer(CustomerVo customerVo);

    void deleteBatchCustomer(Long[] ids);

    void deleteCustomer(Long id);
}
