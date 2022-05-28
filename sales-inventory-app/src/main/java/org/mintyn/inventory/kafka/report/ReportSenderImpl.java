package org.mintyn.inventory.kafka.report;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.mintyn.app.configuration.config.OrderResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportSenderImpl implements ReportSenderService {

    private final KafkaTemplate<String, OrderResponse> responseKafkaTemplate;

    @Override
    public void sendOrderReport( OrderResponse responseMessage){
        String topicName = "order";
        log.info("Sending message='{}' to topic='{}'", responseMessage, topicName);
        ListenableFuture<SendResult<String, OrderResponse>> kafkaResultFuture = responseKafkaTemplate.send(topicName, responseMessage);
        addCallback(topicName, responseMessage, kafkaResultFuture);
    }

    private void addCallback(String topicName, OrderResponse responseMessage,
                             ListenableFuture<SendResult<String, OrderResponse>> kafkaResultFuture) {
        kafkaResultFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(@NonNull Throwable throwable) {
                log.error("Error while sending message {} to topic {}", responseMessage.toString(), topicName, throwable);
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(SendResult<String, OrderResponse> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.debug("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp(),
                        System.nanoTime());
            }
        });
    }

}
