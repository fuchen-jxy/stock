package com.ies.controller;

import com.google.common.collect.Lists;
import com.ies.domain.Customer;
import com.ies.domain.Item;
import com.ies.domain.Sale;
import com.ies.domain.User;
import com.ies.service.ICustomerService;
import com.ies.service.ISaleService;
import com.ies.service.ItemService;
import com.ies.service.UserService;
import com.ies.service.impl.CustomerServiceImpl;
import com.ies.utils.DataGridView;
import com.ies.utils.ResultObj;
import com.ies.vo.SaleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-07 02:00
 **/
@RestController
@RequestMapping("sale")
public class SaleController {

    @Resource
    private ISaleService saleService;
    @Resource
    private ItemService itemService;
    @Resource
    private UserService userService;
    @Resource
    private ICustomerService customerService;

    @RequestMapping("loadAllSale")
    public DataGridView loadAllSale(SaleVo saleVo) {
        DataGridView dataGridView = saleService.loadAllSale(saleVo);
        List<Sale> saleList = (List<Sale>) dataGridView.getData();
        if (CollectionUtils.isEmpty(saleList)) {
            return dataGridView;
        } else {
            List<SaleVo> saleVos = Lists.newArrayList();
            for (Sale sale : saleList) {
                SaleVo _saleVo = new SaleVo();
                BeanUtils.copyProperties(sale, _saleVo);
                Item item = itemService.getItem(sale.getItemId());
                _saleVo.setItemName(item.getName());
                User user = userService.queryById(sale.getUserId());
                _saleVo.setUserName(user.getUsername());
                Customer customer = customerService.getCustomer(sale.getCustomerId());
                _saleVo.setCustomerName(customer.getUsername());
                saleVos.add(_saleVo);
            }
            dataGridView.setData(saleVos);
        }
        return dataGridView;
    }

    @RequestMapping("addSale")
    public ResultObj addSale(SaleVo saleVo) {
        saleService.addSale(saleVo);
        return ResultObj.ADD_SUCCESS;
    }

    @RequestMapping("totalMoneyAndCount")
    public Map<String, Object> totalMoneyAndCount(SaleVo saleVo) {
        return saleService.totalMoneyAndCount(saleVo);
    }

    @RequestMapping("updateSale")
    public ResultObj updateSale(SaleVo saleVo) {
        saleService.updateSale(saleVo);
        return ResultObj.UPDATE_SUCCESS;
    }

    @RequestMapping("deleteBatchSale")
    public ResultObj deleteBatchSale(SaleVo saleVo) {
        saleService.deleteBatchSale(saleVo.getIds());
        return ResultObj.DELETE_SUCCESS;
    }

    @RequestMapping("deleteSale")
    public ResultObj deleteSale(SaleVo saleVo) {
        saleService.deleteSale(saleVo.getId());
        return ResultObj.DELETE_SUCCESS;
    }

}
