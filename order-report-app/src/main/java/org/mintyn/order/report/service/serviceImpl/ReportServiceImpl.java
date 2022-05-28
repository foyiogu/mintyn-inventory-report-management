package org.mintyn.order.report.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mintyn.app.configuration.exception.ApiResourceNotFoundException;
import org.mintyn.app.configuration.config.OrderResponse;
import org.mintyn.order.report.model.OrderReport;
import org.mintyn.order.report.payload.ReportResponse;
import org.mintyn.order.report.repository.OrderReportRepository;
import org.mintyn.order.report.service.ReportService;
import org.mintyn.order.report.utility.ExcelFileGenerator;
import org.mintyn.order.report.utility.OrderReportMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final OrderReportRepository orderReportRepository;

    private final OrderReportMapper orderReportMapper;

    private final ExcelFileGenerator excelFileGenerator;

    @Override
    public void saveOrder(OrderResponse orderResponse){
        OrderReport orderReport = orderReportMapper.mapToOrderReport(orderResponse);
        orderReportRepository.save(orderReport);
        log.info("ORDER REPORT WITH PRODUCT NAME {} HAS BEEN SAVED", orderResponse.getProductName());
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
