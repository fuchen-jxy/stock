package com.ies.controller;

import com.ies.domain.Item;
import com.ies.domain.Stock;
import com.ies.domain.StockInOutRecord;
import com.ies.domain.User;
import com.ies.service.IStockService;
import com.ies.service.ItemService;
import com.ies.service.UserService;
import com.ies.utils.DataGridView;
import com.ies.vo.StockInOutRecordVo;
import com.ies.vo.StockVo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.testng.collections.Lists;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-08 00:27
 **/
@RestController
@RequestMapping("stock")
public class StockController {

    @Resource
    private IStockService stockService;
    @Resource
    private UserService userService;
    @Resource
    private ItemService itemService;

    @RequestMapping("loadAllStockInOutRecord")
    public DataGridView loadAllStockInOutRecord(StockInOutRecordVo stockInOutRecordVo) {
        DataGridView dataGridView = stockService.loadAllStockInOutRecord(stockInOutRecordVo);
        List<StockInOutRecord> stockInOutRecordList = (List<StockInOutRecord>) dataGridView.getData();
        if (CollectionUtils.isEmpty(stockInOutRecordList)) {
            return dataGridView;
        } else {
            List<StockInOutRecordVo> stockInOutRecordVos = Lists.newArrayList();
            for (StockInOutRecord stockInOutRecord : stockInOutRecordList) {
                StockInOutRecordVo _stockInOutRecordVo = new StockInOutRecordVo();
                BeanUtils.copyProperties(stockInOutRecord, _stockInOutRecordVo);
                User user = userService.queryById(stockInOutRecord.getUserId());
                _stockInOutRecordVo.setUserName(user.getUsername());
                Item item = itemService.getItem(stockInOutRecord.getItemId());
                _stockInOutRecordVo.setItemName(item.getName());
                stockInOutRecordVos.add(_stockInOutRecordVo);
            }
            dataGridView.setData(stockInOutRecordVos);
        }
        return dataGridView;
    }

    @RequestMapping("loadAllItemYear")
    public DataGridView loadAllItemYear(StockVo stockVo) {
        DataGridView dataGridView = stockService.loadAllItemYear(stockVo);
        List<Stock> stockList = (List<Stock>) dataGridView.getData();
        if (CollectionUtils.isEmpty(stockList)) {
            return dataGridView;
        } else {
            List<StockVo> stockVoList = new ArrayList<>();
            for (Stock stock : stockList) {
                StockVo _stockVo = new StockVo();
                BeanUtils.copyProperties(stock, _stockVo);
                Item item = itemService.getItem(stock.getItemId());
                _stockVo.setItemName(item.getName());
                stockVoList.add(_stockVo);
            }
            dataGridView.setData(stockVoList);
        }
        return dataGridView;
    }

}
