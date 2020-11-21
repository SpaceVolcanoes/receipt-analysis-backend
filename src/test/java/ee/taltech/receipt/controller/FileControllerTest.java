package ee.taltech.receipt.controller;

import ee.taltech.receipt.service.StorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {

    @Mock
    private StorageService storage;

    @InjectMocks
    private FileController controller;

    @Test
    void listFilesReturnsWithUrls() {
        Path txt = mock(Path.class, RETURNS_DEEP_STUBS);
        Path png = mock(Path.class, RETURNS_DEEP_STUBS);

        when(storage.loadAll()).thenReturn(Stream.of(txt, png));
        when(txt.getFileName().toString()).thenReturn("file.txt");
        when(png.getFileName().toString()).thenReturn("file.png");

        List<String> actual = controller.listFiles();

        assertThat(actual).containsExactlyInAnyOrder("file.txt", "file.png");
    }

    @Test
    void serveFileReturnsContentTypeFromImage() {
        Resource image = mock(Resource.class);

        when(storage.loadAsResource("file.png")).thenReturn(image);
        when(image.getFilename()).thenReturn("file.png");

        ResponseEntity<Resource> response = controller.serveFile("file.png");

        assertThat(response.getHeaders()).containsEntry("Content-Disposition", singletonList("inline; filename=\"file.png\""));
        assertThat(response.getHeaders()).containsEntry("Content-Type", singletonList("image/png"));
        assertThat(response.getBody()).isSameAs(image);
    }

}
