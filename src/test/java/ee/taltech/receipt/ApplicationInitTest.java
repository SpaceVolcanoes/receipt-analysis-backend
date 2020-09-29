package ee.taltech.receipt;

import ee.taltech.receipt.repository.EntryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationInitTest {

    @Autowired
    private EntryRepository entryRepository;

    @Disabled("Seems to fail to load ApplicationContext some % of times when running with test coverage")
    @Test
    public void runStoresEntries() {
        assertThat(entryRepository.count()).isEqualTo(3);
    }

}
