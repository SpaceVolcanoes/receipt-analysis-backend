package ee.taltech.receipt;

import java.io.File;
import java.io.IOException;

import static java.nio.file.Files.readAllBytes;
import static java.util.Objects.requireNonNull;

public interface FileAwareTest {

    default File readFile(String fileName) {
        return new File(requireNonNull(FileAwareTest.class.getClassLoader().getResource(fileName)).getFile());
    }

    default byte[] readBytes(String fileName) throws IOException {
        return readAllBytes(readFile(fileName).toPath());
    }

}
