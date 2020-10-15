package ee.taltech.receipt.controller;

import ee.taltech.receipt.dto.EntrySummary;
import ee.taltech.receipt.model.Entry;
import ee.taltech.receipt.model.Receipt;
import ee.taltech.receipt.service.EntryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EntryControllerTest {

    private Entry entry;

    @Mock
    private EntryService entryService;

    @InjectMocks
    private EntryController entryController;

    @BeforeEach
    void setUp() {
        entry = new Entry().setId(1L);
    }

    @Test
    void createReturnsIdFromService() {
        when(entryService.create(entry)).thenReturn(entry);

        ResponseEntity<?> response = entryController.create(entry);

        assertThat(response.getBody()).isEqualTo(1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    void createReturnsErrorWhenFailed() {
        when(entryService.create(entry)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = entryController.create(entry);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void deleteReturnsOk() {
        ResponseEntity<?> response = entryController.delete(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void getEntryReturnsEntryFromServiceIfFound() {

        when(entryService.findById(1L)).thenReturn(entry);

        ResponseEntity<?> response = entryController.getEntry(1L);

        assertThat(response.getBody()).isSameAs(entry);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void getEntryReturnsNotFoundIfServiceThrowsIllegal() {
        when(entryService.findById(3L)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = entryController.getEntry(3L);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void getSimilarEntriesReturnsListIfSuccess() {
        Receipt receipt = new Receipt().setId(5L);
        Entry otherEntry = new Entry().setId(3L).setReceipt(receipt);
        entry = entry.setReceipt(receipt);
        when(entryService.getEntriesSimilarTo(1L)).thenReturn(Arrays.asList(entry, otherEntry));

        ResponseEntity<?> response = entryController.getSimilarEntries(1L);

        List<EntrySummary> body = (List) response.getBody();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(body.get(0).getId()).isEqualTo(1L);
        assertThat(body.get(1).getId()).isEqualTo(3L);
    }

    @Test
    void getSimilarEntriesReturnsNotFoundIfServiceThrowsIllegal() {
        when(entryService.getEntriesSimilarTo(3L)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = entryController.getSimilarEntries(3L);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void updateEntryReturnsEntryIfSuccess() {
        when(entryService.update(entry, 1L)).thenReturn(entry);

        ResponseEntity<?> response = entryController.updateEntry(entry, 1L);

        assertThat(response.getBody()).isSameAs(entry);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void updateEntryReturnsBadRequestIfError() {
        when(entryService.update(entry, 1L)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = entryController.updateEntry(entry, 1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

}
