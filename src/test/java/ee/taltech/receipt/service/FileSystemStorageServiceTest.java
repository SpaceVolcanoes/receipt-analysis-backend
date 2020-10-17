package ee.taltech.receipt.service;

import ee.taltech.receipt.FileAwareTest;
import ee.taltech.receipt.configuration.StorageProperties;
import ee.taltech.receipt.exception.StorageException;
import ee.taltech.receipt.exception.StorageFileNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

class FileSystemStorageServiceTest implements FileAwareTest {

    private static final byte[] TEXT_CONTENT = "Hello world".getBytes();

    private final StorageProperties properties = new StorageProperties();
    private FileSystemStorageService service;

    @BeforeEach
    public void init() {
        properties.setLocation("target/files");
        service = new FileSystemStorageService(properties);
        service.init();
    }

    @AfterEach
    public void afterAll() {
        service.deleteAll();
    }

    @Test
    public void loadNonExistent() {
        assertThat(service.load("foo.txt")).doesNotExist();
    }

    @Test
    public void saveAndLoad() {
        service.store(new MockMultipartFile("foo", "foo.txt", TEXT_PLAIN_VALUE, TEXT_CONTENT));
        assertThat(service.load("foo.txt")).exists();
    }

    @Test
    public void saveNotPermitted() {
        MultipartFile file = new MockMultipartFile("foo", "../foo.txt", TEXT_PLAIN_VALUE, TEXT_CONTENT);

        Throwable thrown = catchThrowable(() -> service.store(file));

        assertThat(thrown)
            .isInstanceOf(StorageException.class)
            .hasMessage("Cannot store file with relative path outside current directory ../foo.txt");
    }

    @Test
    public void savePermitted() {
        service.store(new MockMultipartFile("foo", "bar/../foo.txt", TEXT_PLAIN_VALUE, TEXT_CONTENT));
    }

    @Test
    public void isImageReturnsTrueIfImage() throws IOException {
        MockMultipartFile image = new MockMultipartFile("foo", "bar/foo.png", IMAGE_PNG_VALUE, readBytes("s.png"));

        boolean actual = service.isImage(image);
        assertThat(actual).isTrue();
    }

    @Test
    public void isImageReturnsFalseIfNotImage() {
        MockMultipartFile image = new MockMultipartFile("foo", "bar/foo.png", TEXT_PLAIN_VALUE, TEXT_CONTENT);

        boolean actual = service.isImage(image);
        assertThat(actual).isFalse();
    }

    @Test
    public void loadAllReturnsAllStoredFiles() {
        MockMultipartFile foo = new MockMultipartFile("foo", "foo.txt", TEXT_PLAIN_VALUE, TEXT_CONTENT);
        MockMultipartFile bar = new MockMultipartFile("bar", "bar.txt", TEXT_PLAIN_VALUE, TEXT_CONTENT);

        service.store(foo);
        service.store(bar);

        Stream<Path> actual = service.loadAll();

        assertThat(actual).hasSize(2);
    }

    @Test
    public void loadAsResourceReturnsAsResourceIfFound() {
        service.store(new MockMultipartFile("foo", "foo.txt", TEXT_PLAIN_VALUE, TEXT_CONTENT));

        Resource actual = service.loadAsResource("foo.txt");

        assertThat(actual.toString()).contains("foo.txt");
    }

    @Test
    public void loadAsResourceThrowsIfNotFound() {
        Throwable thrown = catchThrowable(() -> service.loadAsResource("foo.txt"));

        assertThat(thrown)
            .isInstanceOf(StorageFileNotFoundException.class)
            .hasMessage("Could not read file: foo.txt");
    }

    @Test
    public void loadAsResourceThrowsIfMalformedUrl() {
        Throwable thrown = catchThrowable(() -> service.loadAsResource("file:///////home/"));

        assertThat(thrown)
            .isInstanceOf(StorageFileNotFoundException.class)
            .hasMessage("Could not read file: file:///////home/");
    }

}
