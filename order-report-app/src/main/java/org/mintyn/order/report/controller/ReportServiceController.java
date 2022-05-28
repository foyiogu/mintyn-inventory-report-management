package org.mintyn.order.report.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.mintyn.order.report.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "This is a bad request, please follow the API documentation for the proper request format."),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "The server is down, please make sure that the Application is running")
})
@RequestMapping("api/v1/report")
public class ReportServiceController {

    private final ReportService reportService;


    @GetMapping("/generate-report/{begin}/{end}")
    public ResponseEntity<?> generateReport(@DateTimeFormat(pattern = "dd-MM-yyyy")
                                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                                @PathVariable("begin")
                                                LocalDate begin,
                                            @DateTimeFormat(pattern = "dd-MM-yyyy")
                                            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                            @PathVariable("end")LocalDate end){
        return new ResponseEntity<>(reportService.getReportFromDateRange(begin, end), HttpStatus.OK);
    }
}
