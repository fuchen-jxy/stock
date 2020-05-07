package com.ies.controller;

import com.ies.domain.Supplier;
import com.ies.service.ISupplierService;
import com.ies.utils.DataGridView;
import com.ies.utils.ResultObj;
import com.ies.vo.SupplierVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-02 21:31
 **/
@RestController
@RequestMapping("supplier")
public class SupplierController {

    @Resource
    private ISupplierService supplierService;

    @RequestMapping("loadAllSupplier")
    public DataGridView loadAllSupplier(SupplierVo supplierVo) {
        return supplierService.loadAllSupplier(supplierVo);
    }

    @RequestMapping("addSupplier")
    public ResultObj addSupplier(SupplierVo supplierVo) {
        supplierService.addSupplier(supplierVo);
        return ResultObj.ADD_SUCCESS;
    }

    @RequestMapping("updateSupplier")
    public ResultObj updateSupplier(SupplierVo supplierVo) {
        supplierService.updateSupplier(supplierVo);
        return ResultObj.UPDATE_SUCCESS;
    }

    @RequestMapping("deleteBatchSupplier")
    public ResultObj deleteBatchSupplier(SupplierVo supplierVo) {
        supplierService.deleteBatchSupplier(supplierVo.getIds());
        return ResultObj.DELETE_SUCCESS;
    }

    @RequestMapping("deleteSupplier")
    public ResultObj deleteSupplier(SupplierVo supplierVo) {
        supplierService.deleteSupplier(supplierVo.getId());
        return ResultObj.DELETE_SUCCESS;
    }

    @RequestMapping("loadAllSupplierJson")
    public Object loadAllSupplierJson() {
        List<Supplier> supplierVoList = supplierService.loadAllSupplierJson();
        return supplierVoList;
    }

}
