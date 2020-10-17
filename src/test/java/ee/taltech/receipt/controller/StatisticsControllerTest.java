package ee.taltech.receipt.controller;

import ee.taltech.receipt.dto.GeneralStatistics;
import ee.taltech.receipt.service.StatisticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticsControllerTest {

    @Mock
    private StatisticsService service;

    @InjectMocks
    private StatisticsController controller;

    @Test
    void generalStatisticsReturnsDto() {
        GeneralStatistics statistics = mock(GeneralStatistics.class);
        when(service.generalStatistics()).thenReturn(statistics);

        ResponseEntity<?> response = controller.generalStatistics();

        assertThat(response.getBody()).isEqualTo(statistics);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

}
