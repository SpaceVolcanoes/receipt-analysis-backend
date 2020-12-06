package ee.taltech.receipt.service;

import ee.taltech.receipt.dto.OcrMessage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class OcrService {

    @Qualifier("OcrTemplate")
    private final RestTemplate template;

    private final FileSystemStorageService storageService;

    private final Logger logger;

    public OcrService(RestTemplate template, FileSystemStorageService storageService, Logger logger) {
        this.template = template;
        this.storageService = storageService;
        this.logger = logger;
    }

    public List<String> identify(String fileName) {
        String text = fetchRawText(fileName);
        if (text == null) {
            return List.of();
        }

        return stream(text.split("\n"))
            .map(String::trim)
            .filter(line -> !line.isEmpty() && line.length() > 4)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("ConstantConditions")
    private String fetchRawText(String fileName) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", storageService.loadAsResource(fileName));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body);

        ResponseEntity<OcrMessage> response = template.postForEntity("?lang=est", requestEntity, OcrMessage.class);

        if (!response.hasBody()) {
            logger.error("OCR returned no body");
            return null;
        }

        if (!response.getStatusCode().isError()) {
            return response.getBody().getText();
        }

        if (response.getStatusCode().is5xxServerError()) {
            logger.error("OCR ran into an error: " + response.getBody().getMessage());
        } else {
            logger.warn("OCR failed: " + response.getBody().getMessage());
        }
        return null;
    }

}
