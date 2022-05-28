package org.mintyn.order.report.service;

import org.mintyn.app.configuration.config.OrderResponse;
import org.mintyn.order.report.payload.ReportResponse;

import java.time.LocalDate;

public interface ReportService {

    void saveOrder(OrderResponse orderResponse);
    ReportResponse getReportFromDateRange(LocalDate startDate, LocalDate endDate);
}
