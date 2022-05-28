package org.mintyn.order.report.kafkaConsumer;

import lombok.RequiredArgsConstructor;
import org.mintyn.app.configuration.exception.ApiBadRequestException;
import org.mintyn.app.configuration.config.OrderResponse;
import org.mintyn.order.report.service.ReportService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final ReportService orderListenerService;

    @KafkaListener(topics = "order", groupId = "mintyn", containerFactory = "reportListenerContainerFactory")
    void listener(OrderResponse orderResponse){
        try{

            if(!ObjectUtils.isEmpty(orderResponse)){
                orderListenerService.saveOrder(orderResponse);
            }
        }catch(ApiBadRequestException err){
            throw new ApiBadRequestException("Can not create Report Order");
        };
    }

}
