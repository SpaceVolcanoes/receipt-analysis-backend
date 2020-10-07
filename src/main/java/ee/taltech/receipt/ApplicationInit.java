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
        Customer mart = customerRepository.save(new Customer().setName("Mart"));

        Receipt maxima = receiptRepository.save(new Receipt().setCustomer(krissu).setIssuer("Maxima"));
        Receipt coop = receiptRepository.save(new Receipt().setCustomer(krissu).setIssuer("Coop"));
        Receipt rimi = receiptRepository.save(new Receipt().setCustomer(mart).setIssuer("Rimi"));

        entryRepository.saveAll(asList(
            new Entry().setName("Piim").setCost(2.0).setReceipt(maxima),
            new Entry().setName("Juust").setCost(3.0).setReceipt(maxima),
            new Entry().setName("Rõba").setCost(4.0).setReceipt(maxima),
            new Entry().setName("Köis").setCost(3.0).setReceipt(coop),
            new Entry().setName("Taburet").setCost(15.99).setReceipt(coop),
            new Entry().setName("Muna").setCost(2.99).setReceipt(rimi),
            new Entry().setName("Vesi").setCost(0.99).setReceipt(rimi),
            new Entry().setName("Kana").setCost(3.35).setReceipt(rimi)
        ));
    }

}
