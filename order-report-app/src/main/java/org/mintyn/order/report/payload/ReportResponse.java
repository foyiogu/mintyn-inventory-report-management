package org.mintyn.order.report.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ReportResponse {
    private String message;
    private HttpStatus status;
}
