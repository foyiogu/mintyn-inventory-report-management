package org.mintyn.order.report.utility;

import lombok.AllArgsConstructor;
import org.mintyn.order.report.model.OrderReport;
import org.mintyn.order.report.payload.CreatedOrder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderReportMapper {

    private final ModelMapper modelMapper;

    public OrderReport mapToOrderReport(CreatedOrder createdOrder){
        return modelMapper.map(createdOrder, OrderReport.class);
    }

    public CreatedOrder mapToCreatedOrder(OrderReport orderReport){
        return modelMapper.map(orderReport, CreatedOrder.class);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
