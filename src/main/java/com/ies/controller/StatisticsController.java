package com.ies.controller;

import com.ies.service.IStatisticsService;
import com.ies.utils.DataGridView;
import com.ies.vo.statistics.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-09 23:28
 **/
@RestController
@RequestMapping("statistics")
public class StatisticsController {

    @Resource
    private IStatisticsService statisticsService;

    @RequestMapping("caigouReport")
    public DataGridView caigouReport(CaigouReportVo caigouReportVo) {
        return statisticsService.caigouReport(caigouReportVo);
    }

    @RequestMapping("caigouTotalPriceReport")
    public Object caigouTotalPriceReport(CaigouReportVo caigouReportVo) {
        return statisticsService.caigouTotalPriceReport(caigouReportVo);
    }

    @RequestMapping("breakageReport")
    public DataGridView breakageReport(BreakageReportVo breakageReportVo) {
        return statisticsService.breakageReport(breakageReportVo);
    }

    @RequestMapping("breakageTotalPriceReport")
    public Object breakageTotalPriceReport(BreakageReportVo breakageReportVo) {
        return statisticsService.breakageTotalPriceReport(breakageReportVo);
    }

    @RequestMapping("saleReport")
    public DataGridView saleReport(SaleReportVo saleReportVo) {
        return statisticsService.saleReport(saleReportVo);
    }

    @RequestMapping("saleTotalPriceReport")
    public Object saleTotalPriceReport(SaleReportVo saleReportVo) {
        return statisticsService.saleTotalPriceReport(saleReportVo);
    }

    @RequestMapping("stockReport")
    public DataGridView stockReport(StockReportVo stockReportVo) {
        return statisticsService.stockReport(stockReportVo);
    }

    @RequestMapping("incomeReport")
    public DataGridView incomeReport(IncomeReportVo incomeReportVo) {
        return statisticsService.incomeReport(incomeReportVo);
    }

    @RequestMapping("incomeTotalPriceReport")
    public Object incomeTotalPriceReport(IncomeReportVo incomeReportVo) {
        return statisticsService.incomeTotalPriceReport(incomeReportVo);
    }

}
