package ee.taltech.receipt.service;

import ee.taltech.receipt.configuration.StorageProperties;
import ee.taltech.receipt.dto.Base64File;
import ee.taltech.receipt.exception.StorageException;
import ee.taltech.receipt.exception.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import static java.lang.String.valueOf;
import static java.lang.Integer.parseInt;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final String limit;

    @Autowired
    public FileSystemStorageService(StorageProperties properties, Environment environment) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.limit = environment.getProperty("FILE_LIMIT");
    }

    public boolean isImage(MultipartFile file) {
        try {
            return isImage(file.getBytes());
        } catch (Exception exception) {
            return false;
        }
    }

    private boolean isImage(byte[] content) {
        try {
            ImageIO.read(new ByteArrayInputStream(content)).toString();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public String store(Base64File base64) throws StorageException, IllegalArgumentException {
        String extension = base64.getExtension();
        byte[] data = base64.getBytes();

        if (!isImage(data)) {
            throw new IllegalArgumentException("Receipt file must be an image");
        }

        String filename = DigestUtils.md5DigestAsHex(data) + "." + extension;

        File file = new File(valueOf(this.rootLocation.resolve(filename)));
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
            return filename;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public String store(MultipartFile file) throws StorageException {
        if (file.getOriginalFilename() == null) {
            throw new StorageException("File is missing a name");
        }

        if (limit != null && parseInt(limit) <= loadAll().count()) {
            throw new IllegalStateException(
                "Service has reached its current capacity, "
                + "please contact the service administrator for more information"
            );
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                throw new StorageException(
                    "Cannot store file with relative path outside current directory " + filename
                );
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
                return filename;
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                .filter(path -> !path.equals(this.rootLocation))
                .map(this.rootLocation::relativize);
        } catch (NoSuchFileException e) {
            return Stream.empty();
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }

            throw new StorageFileNotFoundException("Could not read file: " + filename);
        } catch (MalformedURLException | InvalidPathException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public void deleteByFileName(String fileName) {
        try {
            File file = new File(valueOf(this.rootLocation.resolve(fileName)));
            file.delete();
        } catch (Exception e) {
            throw new StorageFileNotFoundException("Could not read file: " + fileName, e);
        }
    }

}
