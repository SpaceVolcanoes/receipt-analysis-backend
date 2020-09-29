package ee.taltech.heroesbackend.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    @Setter
    @Getter
    private String location = "files";

}
