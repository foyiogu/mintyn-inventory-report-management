package org.mintyn.order.report.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportRangeRequest {
    LocalDateTime start;
    LocalDateTime end;
}
