package org.mintyn.order.report.service;

import org.mintyn.order.report.model.OrderReport;
import org.mintyn.order.report.payload.CreatedOrder;
import org.mintyn.order.report.payload.ReportRangeRequest;
import org.mintyn.order.report.payload.ReportResponse;

public interface ReportService {

    CreatedOrder addOrderReport(CreatedOrder order);

    ReportResponse getReportFromDateRange(ReportRangeRequest rangeRequest);
}
