package org.mintyn.inventory.kafka.report;

import org.mintyn.app.configuration.config.OrderResponse;

public interface ReportSenderService {

    void sendOrderReport( OrderResponse responseMessage);
}
