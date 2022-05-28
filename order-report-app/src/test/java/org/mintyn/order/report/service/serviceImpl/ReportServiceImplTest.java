package org.mintyn.order.report.service.serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mintyn.app.configuration.config.OrderResponse;
import org.mintyn.order.report.model.OrderReport;
import org.mintyn.order.report.payload.ReportResponse;
import org.mintyn.order.report.repository.OrderReportRepository;
import org.mintyn.order.report.utility.ExcelFileGenerator;
import org.mintyn.order.report.utility.OrderReportMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private OrderReportRepository mockOrderReportRepository;
    @Mock
    private OrderReportMapper mockOrderReportMapper;
    @Mock
    private ExcelFileGenerator mockExcelFileGenerator;

    private ReportServiceImpl reportServiceImplUnderTest;

    OrderResponse orderResponse = new OrderResponse();

    OrderReport orderReport = new OrderReport();

    @BeforeEach
    void setUp() {
        reportServiceImplUnderTest = new ReportServiceImpl(mockOrderReportRepository, mockOrderReportMapper,
                mockExcelFileGenerator);

        orderResponse.setId(0L);
        orderResponse.setOrderCreatedDate(LocalDate.of(2020, 1, 1));
        orderResponse.setCustomerName("Frank");
        orderResponse.setCustomerPhoneNumber("0813");
        orderResponse.setProductName("productName");
        orderResponse.setOrderQuantity(1);
        orderResponse.setProductPrice(new BigDecimal("10.00"));
        orderResponse.setTotalProductPrice(new BigDecimal("10.00"));

        orderReport = new OrderReport(0L, LocalDate.of(2020, 1, 1), "Frank",
                "0813", "productName", 1, new BigDecimal("10.00"), new BigDecimal("10.00"));

    }

    @Test
    void testSaveOrder() {
        // Setup
        // Configure OrderReportMapper.mapToOrderReport(...).
        when(mockOrderReportMapper.mapToOrderReport(any())).thenReturn(orderReport);

        // Configure OrderReportRepository.save(...).
        when(mockOrderReportRepository.save(any(OrderReport.class))).thenReturn(orderReport);

        // Run the test
        reportServiceImplUnderTest.saveOrder(orderResponse);

        // Verify the results
        verify(mockOrderReportRepository).save(any(OrderReport.class));
    }

    @Test
    void testGetReportFromDateRange() {
        // Setup
        final ReportResponse expectedResult = new ReportResponse("Excel File Generated", HttpStatus.OK);

        // Configure OrderReportRepository.findAllByOrderCreatedDateIsBetween(...).
        final List<OrderReport> orderReports = List.of(orderReport);
        when(mockOrderReportRepository.findAllByOrderCreatedDateIsBetween(any(),any())).thenReturn(orderReports);

        // Run the test
        final ReportResponse result = reportServiceImplUnderTest.getReportFromDateRange(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 1));

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockExcelFileGenerator).generateTerminalRequestData(List.of(orderReport),
                "2020-01-01 to 2020-01-01");
    }

}
