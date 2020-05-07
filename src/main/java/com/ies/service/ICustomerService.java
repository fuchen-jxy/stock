package com.ies.service;

import com.ies.domain.Customer;
import com.ies.utils.DataGridView;
import com.ies.vo.CustomerVo;

import java.util.List;

public interface ICustomerService {

    DataGridView loadAllCustomer(CustomerVo customerVo);

    Integer addCustomer(CustomerVo customerVo);

    void updateCustomer(CustomerVo customerVo);

    void deleteBatchCustomer(Long[] ids);

    void deleteCustomer(Long id);

    List<Customer> loadAllCustomerJson();

    Customer getCustomer(Long customerId);
}
