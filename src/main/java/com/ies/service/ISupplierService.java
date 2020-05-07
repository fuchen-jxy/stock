package com.ies.service;

import com.ies.domain.Supplier;
import com.ies.utils.DataGridView;
import com.ies.vo.SupplierVo;

import java.util.List;


public interface ISupplierService {

    DataGridView loadAllSupplier(SupplierVo supplierVo);

    Integer addSupplier(SupplierVo supplierVo);

    void updateSupplier(SupplierVo supplierVo);

    void deleteBatchSupplier(Long[] ids);

    void deleteSupplier(Long id);

    Supplier getSupplier(Long id);

    List<Supplier> loadAllSupplierJson();
}
