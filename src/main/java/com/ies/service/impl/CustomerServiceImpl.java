package com.ies.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ies.domain.Customer;
import com.ies.domain.CustomerCriteria;
import com.ies.domain.SaleCriteria;
import com.ies.mapper.CustomerMapper;
import com.ies.mapper.SaleMapper;
import com.ies.service.ICustomerService;
import com.ies.utils.DataGridView;
import com.ies.vo.CustomerVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-02 22:30
 **/
@Service
public class CustomerServiceImpl implements ICustomerService {

    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private SaleMapper saleMapper;

    @Override
    public DataGridView loadAllCustomer(CustomerVo customerVo) {
        CustomerCriteria customerCriteria = new CustomerCriteria();
        CustomerCriteria.Criteria criteria = customerCriteria.createCriteria();
        if (!StringUtils.isEmpty(customerVo.getUsername())) {
            criteria.andUsernameLike("%" + customerVo.getUsername() + "%");
        }
        Page<Object> page = PageHelper.startPage(customerVo.getPage(), customerVo.getLimit());
        List<Customer> customerList = customerMapper.selectByExample(customerCriteria);
        return new DataGridView(page.getTotal(), customerList);
    }

    @Override
    public Integer addCustomer(CustomerVo customerVo) {
        if (checkTheSameName(customerVo.getUsername())) {
            throw new IllegalArgumentException("用户名已经存在");
        }
        return customerMapper.insert(customerVo);
    }

    @Override
    public void updateCustomer(CustomerVo customerVo) {
        customerMapper.updateByPrimaryKey(customerVo);
    }

    @Override
    public void deleteBatchCustomer(Long[] ids) {
        for (Long id : ids) {
            this.deleteCustomer(id);
        }
    }

    @Override
    public void deleteCustomer(Long id) {
        checkDeleteCustomer(id);
        customerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Customer> loadAllCustomerJson() {
        CustomerCriteria customerCriteria = new CustomerCriteria();
        customerCriteria.createCriteria();
        return customerMapper.selectByExample(customerCriteria);
    }

    @Override
    public Customer getCustomer(Long customerId) {
        return customerMapper.selectByPrimaryKey(customerId);
    }

    private boolean checkTheSameName(String username) {
        CustomerCriteria customerCriteria = new CustomerCriteria();
        customerCriteria.createCriteria().andUsernameEqualTo(username);
        List<Customer> customerList = customerMapper.selectByExample(customerCriteria);
        return !CollectionUtils.isEmpty(customerList);
    }

    private void checkDeleteCustomer(Long id) {
        SaleCriteria saleCriteria = new SaleCriteria();
        saleCriteria.createCriteria().andCustomerIdEqualTo(id);
        int countSale = saleMapper.countByExample(saleCriteria);
        if (countSale > 0) {
            throw new IllegalArgumentException("当前客户已经关联销售无法删除");
        }
    }

}
