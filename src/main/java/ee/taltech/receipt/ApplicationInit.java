package ee.taltech.receipt;

import ee.taltech.receipt.model.Customer;
import ee.taltech.receipt.model.Entry;
import ee.taltech.receipt.model.Receipt;
import ee.taltech.receipt.repository.CustomerRepository;
import ee.taltech.receipt.repository.EntryRepository;
import ee.taltech.receipt.repository.ReceiptRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;

@Component
@AllArgsConstructor
public class ApplicationInit implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final ReceiptRepository receiptRepository;
    private final EntryRepository entryRepository;

    @Override
    public void run(String... args) {
        Customer krissu = customerRepository.save(new Customer().setName("Krissu"));

        Receipt maxima = receiptRepository.save(new Receipt().setUserId(krissu.getId()).setIssuer("Maxima"));

        entryRepository.saveAll(asList(
            new Entry().setName("Piim").setCost(2.0).setReceipt(maxima),
            new Entry().setName("Juust").setCost(3.0).setReceipt(maxima),
            new Entry().setName("Rõba").setCost(4.0).setReceipt(maxima)
        ));
    }

}
