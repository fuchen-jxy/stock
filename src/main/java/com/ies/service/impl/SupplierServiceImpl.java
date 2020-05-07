package com.ies.service.impl;

import com.github.pagehelper.Constant;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ies.constast.SysConstast;
import com.ies.domain.CaigouCriteria;
import com.ies.domain.ItemCriteria;
import com.ies.domain.Supplier;
import com.ies.domain.SupplierCriteria;
import com.ies.mapper.ItemMapper;
import com.ies.mapper.SupplierMapper;
import com.ies.service.ISupplierService;
import com.ies.utils.DataGridView;
import com.ies.vo.SupplierVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import java.util.List;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-02 21:32
 **/
@Service
public class SupplierServiceImpl implements ISupplierService {

    @Resource
    private SupplierMapper supplierMapper;
    @Resource
    private ItemMapper itemMapper;

    @Override
    public DataGridView loadAllSupplier(SupplierVo supplierVo) {
        SupplierCriteria supplierCriteria = new SupplierCriteria();
        SupplierCriteria.Criteria criteria = supplierCriteria.createCriteria();
        if (!StringUtils.isEmpty(supplierVo.getUsername())) {
            criteria.andUsernameLike("%" + supplierVo.getUsername() + "%");
        }
        if (supplierVo.getStatus() != null) {
            criteria.andStatusEqualTo(supplierVo.getStatus());
        }
        Page<Object> page = PageHelper.startPage(supplierVo.getPage(), supplierVo.getLimit());
        List<Supplier> supplierList = supplierMapper.selectByExample(supplierCriteria);
        return new DataGridView(page.getTotal(), supplierList);
    }

    @Override
    public Integer addSupplier(SupplierVo supplierVo) {
        if (checkTheSameName(supplierVo.getUsername())) {
            throw new IllegalArgumentException("用户名已经存在");
        }
        return supplierMapper.insert(supplierVo);
    }

    @Override
    public void updateSupplier(SupplierVo supplierVo) {
        supplierMapper.updateByPrimaryKey(supplierVo);
    }

    @Override
    @Transactional
    public void deleteBatchSupplier(Long[] ids) {
        for (Long id : ids) {
            this.deleteSupplier(id);
        }
    }

    @Override
    public void deleteSupplier(Long id) {
        checkDeleteSupplier(id);
        supplierMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Supplier getSupplier(Long id) {
        return supplierMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Supplier> loadAllSupplierJson() {
        SupplierCriteria supplierCriteria = new SupplierCriteria();
        supplierCriteria.createCriteria().andStatusEqualTo(SysConstast.CODE_ONE);
        return supplierMapper.selectByExample(supplierCriteria);
    }

    private boolean checkTheSameName(String name) {
        SupplierCriteria supplierCriteria = new SupplierCriteria();
        supplierCriteria.createCriteria().andUsernameEqualTo(name);
        List<Supplier> supplierList = supplierMapper.selectByExample(supplierCriteria);
        return !CollectionUtils.isEmpty(supplierList);
    }

    private void checkDeleteSupplier(Long id) {
        ItemCriteria itemCriteria = new ItemCriteria();
        itemCriteria.createCriteria().andSupplierEqualTo(id);
        int countItem = itemMapper.countByExample(itemCriteria);
        if (countItem > 0) {
            throw new IllegalArgumentException("当前供应商已经关联商品无法删除");
        }
    }

}
