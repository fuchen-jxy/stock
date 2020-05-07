package com.ies.controller;

import com.ies.service.ICustomerService;
import com.ies.utils.DataGridView;
import com.ies.utils.ResultObj;
import com.ies.vo.CustomerVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-02 22:29
 **/
@RestController
@RequestMapping("customer")
public class CustomerController {

    @Resource
    private ICustomerService customerService;

    @RequestMapping("loadAllCustomer")
    public DataGridView loadAllCustomer(CustomerVo customerVo) {
        return customerService.loadAllCustomer(customerVo);
    }

    @RequestMapping("addCustomer")
    public ResultObj addCustomer(CustomerVo customerVo) {
        customerService.addCustomer(customerVo);
        return ResultObj.ADD_SUCCESS;
    }

    @RequestMapping("updateCustomer")
    public ResultObj updateCustomer(CustomerVo customerVo) {
        customerService.updateCustomer(customerVo);
        return ResultObj.UPDATE_SUCCESS;
    }

    @RequestMapping("deleteBatchCustomer")
    public ResultObj deleteBatchCustomer(CustomerVo customerVo) {
        customerService.deleteBatchCustomer(customerVo.getIds());
        return ResultObj.DELETE_SUCCESS;
    }

    @RequestMapping("deleteCustomer")
    public ResultObj deleteCustomer(CustomerVo customerVo) {
        customerService.deleteCustomer(customerVo.getId());
        return ResultObj.DELETE_SUCCESS;
    }

    @RequestMapping("loadAllCustomerJson")
    public Object loadAllCustomerJson() {
        return customerService.loadAllCustomerJson();
    }

}
