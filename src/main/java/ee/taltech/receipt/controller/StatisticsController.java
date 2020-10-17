package ee.taltech.receipt.controller;

import ee.taltech.receipt.dto.GeneralStatistics;
import ee.taltech.receipt.service.StatisticsService;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("statistics")
@RestController
@AllArgsConstructor
public class StatisticsController {

    private final StatisticsService service;

    @GetMapping()
    @ApiResponse(
        code = HttpServletResponse.SC_OK,
        message = "Statistics available"
    )
    public ResponseEntity<?> generalStatistics() {
        GeneralStatistics statistics = service.generalStatistics();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

}
