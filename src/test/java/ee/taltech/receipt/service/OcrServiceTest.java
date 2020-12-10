package ee.taltech.receipt.service;

import ee.taltech.receipt.dto.OcrMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OcrServiceTest {

    @Mock
    private RestTemplate template;

    @Mock
    private FileSystemStorageService storageService;

    @Mock
    private Logger logger;

    @InjectMocks
    private OcrService service;

    @BeforeEach
    void setUp() {
        when(storageService.loadAsResource("temp.png")).thenReturn(mock(Resource.class));
    }

    @Test
    void identifyLogsErrorIfOcrHasNoBody() {
        ResponseEntity<OcrMessage> response = mock(ResponseEntity.class);
        when(response.hasBody()).thenReturn(false);

        when(template.postForEntity(eq("?lang=est"), any(HttpEntity.class), eq(OcrMessage.class))).thenReturn(response);

        List<String> actual = service.identify("temp.png");

        assertThat(actual).isEmpty();
        verify(logger).error("OCR returned no body");
    }

    @Test
    void identifyLogsErrorIfOcrReturns500() {
        ResponseEntity<OcrMessage> response = mock(ResponseEntity.class, RETURNS_DEEP_STUBS);
        when(response.hasBody()).thenReturn(true);
        when(response.getStatusCode().isError()).thenReturn(true);
        when(response.getStatusCode().is5xxServerError()).thenReturn(true);
        when(response.getBody()).thenReturn(new OcrMessage().setMessage("Fatal"));

        when(template.postForEntity(eq("?lang=est"), any(HttpEntity.class), eq(OcrMessage.class))).thenReturn(response);

        List<String> actual = service.identify("temp.png");

        assertThat(actual).isEmpty();
        verify(logger).error("OCR ran into an error: Fatal");
    }

    @Test
    void identifyLogsWarnIfOcrReturns400() {
        ResponseEntity<OcrMessage> response = mock(ResponseEntity.class, RETURNS_DEEP_STUBS);
        when(response.hasBody()).thenReturn(true);
        when(response.getStatusCode().isError()).thenReturn(true);
        when(response.getStatusCode().is5xxServerError()).thenReturn(false);
        when(response.getBody()).thenReturn(new OcrMessage().setMessage("Bad image"));

        when(template.postForEntity(eq("?lang=est"), any(HttpEntity.class), eq(OcrMessage.class))).thenReturn(response);

        List<String> actual = service.identify("temp.png");

        assertThat(actual).isEmpty();
        verify(logger).warn("OCR failed: Bad image");
    }

    @Test
    void identifyReturnsListIfOcrReturnsText() {
        ResponseEntity<OcrMessage> response = mock(ResponseEntity.class, RETURNS_DEEP_STUBS);
        when(response.hasBody()).thenReturn(true);
        when(response.getStatusCode().isError()).thenReturn(false);
        when(response.getBody()).thenReturn(new OcrMessage().setText("First\nSecond\n \n"));

        when(template.postForEntity(eq("?lang=est"), any(HttpEntity.class), eq(OcrMessage.class))).thenReturn(response);

        List<String> actual = service.identify("temp.png");

        assertThat(actual).containsExactly("First", "Second");
        verifyNoInteractions(logger);
    }

}
