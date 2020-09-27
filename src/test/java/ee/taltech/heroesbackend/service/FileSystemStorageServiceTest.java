package ee.taltech.heroesbackend.service;

import ee.taltech.heroesbackend.configuration.StorageProperties;
import ee.taltech.heroesbackend.exception.StorageException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileSystemStorageServiceTest {

    private StorageProperties properties = new StorageProperties();
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
		service.store(new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
				"Hello, World".getBytes()));
		assertThat(service.load("foo.txt")).exists();
	}

	@Test
	public void saveNotPermitted() {
		assertThrows(StorageException.class, () -> service.store(new MockMultipartFile("foo", "../foo.txt",
		MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes())));
	}

	@Test
	public void savePermitted() {
		service.store(new MockMultipartFile("foo", "bar/../foo.txt",
				MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes()));
	}
}