package org.mintyn.order.report.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Order;
import org.mintyn.app.configuration.exception.ApiBadRequestException;
import org.mintyn.app.configuration.exception.ApiResourceNotFoundException;
import org.mintyn.order.report.model.OrderReport;
import org.mintyn.order.report.payload.CreatedOrder;
import org.mintyn.order.report.payload.ReportRangeRequest;
import org.mintyn.order.report.payload.ReportResponse;
import org.mintyn.order.report.repository.OrderReportRepository;
import org.mintyn.order.report.service.ReportService;
import org.mintyn.order.report.utility.ExcelFileGenerator;
import org.mintyn.order.report.utility.OrderReportMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService {

    private final OrderReportRepository orderReportRepository;

    private final OrderReportMapper orderReportMapper;

    private final ExcelFileGenerator excelFileGenerator;

    @Override
    public CreatedOrder addOrderReport(CreatedOrder order) {
        try {
            OrderReport orderReport = orderReportMapper.mapToOrderReport(order);
            orderReportRepository.save(orderReport);
            return orderReportMapper.mapToCreatedOrder(orderReport);
        }catch (Exception ex){
            throw new ApiBadRequestException("Can not create Report Order");
        }
    }

    @Override
    public ReportResponse getReportFromDateRange(LocalDate startDate, LocalDate endDate){
        try {
            List<OrderReport> rangedOrderReport = orderReportRepository
                    .findAllByOrderCreatedDateIsBetween(startDate, endDate);

            excelFileGenerator.generateTerminalRequestData(rangedOrderReport, startDate + " to " + endDate);
            return new ReportResponse("Excel File Generated", HttpStatus.OK);
        }catch (Exception ex){
            throw new ApiResourceNotFoundException("Could not find orders within this date range");
        }
    }
}
