package ee.taltech.heroesbackend.controller;

import ee.taltech.heroesbackend.service.StorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileUploadControllerTest {

    @Mock
    private StorageService storage;

    @InjectMocks
    private FileUploadController controller;

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

}
