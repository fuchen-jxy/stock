package com.ies.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.ies.constast.SysConstast;
import com.ies.domain.Caigou;
import com.ies.mapper.*;
import com.ies.service.IStatisticsService;
import com.ies.utils.DataGridView;
import com.ies.utils.DateUtils;
import com.ies.vo.statistics.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.testng.collections.Lists;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-09 23:47
 **/
@Service
public class StatisticsServiceImpl implements IStatisticsService {

    @Resource
    private CaigouMapper caigouMapper;
    @Resource
    private BreakageMapper breakageMapper;
    @Resource
    private SaleMapper saleMapper;
    @Resource
    private StockMapper stockMapper;
    @Resource
    private IncomeMapper incomeMapper;

    @Override
    public DataGridView caigouReport(CaigouReportVo caigouReportVo) {
//        DataGridView dataGridView = new DataGridView();
//        if (caigouReportVo.getType() == null) {
//            caigouReportVo.setType(SysConstast.CODE_ONE);
//        }
//        caigouReportVo.setOffset((caigouReportVo.getPage() - 1) * caigouReportVo.getLimit());
//        List<CaigouReportVo> caigouReportVos = caigouMapper.caigouReport(caigouReportVo);
//        if (CollectionUtils.isEmpty(caigouReportVos)) {
//            dataGridView.setCount(0L);
//            dataGridView.setData(Lists.newArrayList());
//        } else {
//            String minDate = caigouReportVos.get(0).getMonth();
//            String maxDate = caigouReportVos.get(caigouReportVos.size() - 1).getMonth();
//            List<String> days = DateUtils.getDays(minDate, maxDate);
//            List<CaigouReportVo> finalList = new ArrayList<>();
//            for (String day : days) {
//                boolean find = false;
//                for (CaigouReportVo _caigouReportVo : caigouReportVos) {
//                    if (day.equals(_caigouReportVo.getMonth())) {
//                        find = true;
//                        finalList.add(_caigouReportVo);
//                        break;
//                    }
//                }
//                if (!find) {
//                    CaigouReportVo _empty = new CaigouReportVo();
//                    _empty.setMonth(day);
//                    _empty.setType(caigouReportVo.getType());
//                    _empty.setPrice(BigDecimal.ZERO);
//                    _empty.setCount(0);
//                    _empty.setItemName("无");
//                    finalList.add(_empty);
//                }
//            }
//            dataGridView.setData(finalList);
//            dataGridView.setCount(finalList.size() * 1L);
//        }
//
//        return dataGridView;
        if (caigouReportVo.getType() == null) {
            caigouReportVo.setType(SysConstast.CODE_ONE);
        }
        Page<Object> page = PageHelper.startPage(caigouReportVo.getPage(), caigouReportVo.getLimit());
        List<CaigouReportVo> caigouReportVos = caigouMapper.caigouReport(caigouReportVo);
        return new DataGridView(page.getTotal(), caigouReportVos);
    }

    @Override
    public DataGridView breakageReport(BreakageReportVo breakageReportVo) {
        Page<Object> page = PageHelper.startPage(breakageReportVo.getPage(), breakageReportVo.getLimit());
        List<BreakageReportVo> breakageReportVos = breakageMapper.breakageReport(breakageReportVo);
        return new DataGridView(page.getTotal(), breakageReportVos);
    }

    @Override
    public DataGridView saleReport(SaleReportVo saleReportVo) {
        if (saleReportVo.getType() == null) {
            saleReportVo.setType(SysConstast.CODE_ONE);
        }
        Page<Object> page = PageHelper.startPage(saleReportVo.getPage(), saleReportVo.getLimit());
        List<SaleReportVo> saleReportVos = saleMapper.saleReport(saleReportVo);
        return new DataGridView(page.getTotal(), saleReportVos);
    }

    @Override
    public DataGridView stockReport(StockReportVo stockReportVo) {
        Page<Object> page = PageHelper.startPage(stockReportVo.getPage(), stockReportVo.getLimit());
        List<StockReportVo> stockReportVos = stockMapper.stockReport(stockReportVo);
        return new DataGridView(page.getTotal(), stockReportVos);
    }

    @Override
    public DataGridView incomeReport(IncomeReportVo incomeReportVo) {
        Page<Object> page = PageHelper.startPage(incomeReportVo.getPage(), incomeReportVo.getLimit());
        List<IncomeReportVo> incomeReportVos = incomeMapper.incomeReport(incomeReportVo);
        return new DataGridView(page.getTotal(), incomeReportVos);
    }
}
