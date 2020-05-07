package com.ies.controller;

import com.google.common.collect.Lists;
import com.ies.domain.Item;
import com.ies.domain.Supplier;
import com.ies.service.ISupplierService;
import com.ies.service.ItemService;
import com.ies.utils.DataGridView;
import com.ies.utils.ResultObj;
import com.ies.vo.ItemVo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.testng.collections.Maps;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-05 23:01
 **/
@RestController
@RequestMapping("item")
public class ItemController {

    @Resource
    private ItemService itemService;
    @Resource
    private ISupplierService supplierService;

    @RequestMapping("loadAllItem")
    public DataGridView loadAllItem(ItemVo itemVo) {
        DataGridView dataGridView = itemService.loadAllItem(itemVo);
        List<Item> itemList = (List<Item>) dataGridView.getData();
        if (CollectionUtils.isEmpty(itemList)) {
            return dataGridView;
        } else {
            List<ItemVo> itemVoList = Lists.newArrayListWithCapacity(itemList.size());
            Map<Long, Supplier> supplierMap = Maps.newHashMap();
            for (Item item : itemList) {
                ItemVo _itemVo = new ItemVo();
                BeanUtils.copyProperties(item, _itemVo);
                if (item.getSupplier() != null) {
                    if (supplierMap.containsKey(item.getSupplier())) {
                        Supplier supplier = supplierMap.get(item.getSupplier());
                        _itemVo.setSupplierName(supplier.getUsername());
                    } else {
                        Supplier supplier = supplierService.getSupplier(item.getSupplier());
                        _itemVo.setSupplierName(supplier.getUsername());
                        supplierMap.put(item.getSupplier(), supplier);
                    }
                }
                itemVoList.add(_itemVo);
            }
            dataGridView.setData(itemVoList);
        }
        return dataGridView;
    }

    @RequestMapping("addItem")
    public ResultObj addItem(ItemVo itemVo) {
        if (itemVo.getStock() == null) {
            itemVo.setStock(0);
        }
        itemService.addItem(itemVo);
        return ResultObj.ADD_SUCCESS;
    }

    @RequestMapping("updateItem")
    public ResultObj updateItem(ItemVo itemVo) {
        itemService.updateItem(itemVo);
        return ResultObj.UPDATE_SUCCESS;
    }

    @RequestMapping("deleteBatchItem")
    public ResultObj deleteBatchItem(ItemVo itemVo) {
        itemService.deleteBatchItem(itemVo.getIds());
        return ResultObj.DELETE_SUCCESS;
    }

    @RequestMapping("deleteItem")
    public ResultObj deleteItem(ItemVo itemVo) {
        itemService.deleteItem(itemVo.getId());
        return ResultObj.DELETE_SUCCESS;
    }

    @RequestMapping("loadAllItemJson")
    public Object loadAllItemJson() {
        return itemService.loadAllItemJson();
    }

}
