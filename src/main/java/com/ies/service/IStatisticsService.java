package com.ies.service;

import com.ies.utils.DataGridView;
import com.ies.vo.statistics.*;

import java.math.BigDecimal;

public interface IStatisticsService {

    DataGridView caigouReport(CaigouReportVo caigouReportVo);

    DataGridView breakageReport(BreakageReportVo breakageReportVo);

    DataGridView saleReport(SaleReportVo saleReportVo);

    DataGridView stockReport(StockReportVo stockReportVo);

    DataGridView incomeReport(IncomeReportVo incomeReportVo);

    BigDecimal breakageTotalPriceReport(BreakageReportVo breakageReportVo);

    BigDecimal caigouTotalPriceReport(CaigouReportVo caigouReportVo);

    BigDecimal incomeTotalPriceReport(IncomeReportVo incomeReportVo);

    BigDecimal saleTotalPriceReport(SaleReportVo saleReportVo);
}
