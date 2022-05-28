package org.mintyn.order.report.utility;

import lombok.AllArgsConstructor;
import org.mintyn.app.configuration.config.OrderResponse;
import org.mintyn.order.report.model.OrderReport;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderReportMapper {

    public OrderReport mapToOrderReport(OrderResponse orderResponse){
        OrderReport orderReport = new OrderReport();
        orderReport.setCustomerName(orderResponse.getCustomerName());
        orderReport.setCustomerPhoneNumber(orderResponse.getCustomerPhoneNumber());
        orderReport.setOrderCreatedDate(orderResponse.getOrderCreatedDate());
        orderReport.setProductName(orderResponse.getProductName());
        orderReport.setProductPrice(orderResponse.getProductPrice());
        orderReport.setOrderQuantity(orderResponse.getOrderQuantity());
        orderReport.setTotalProductPrice(orderResponse.getTotalProductPrice());

        return orderReport;
    }


}
