package ee.taltech.receipt.service;

import ee.taltech.receipt.dto.Base64File;
import ee.taltech.receipt.model.Customer;
import ee.taltech.receipt.model.Entry;
import ee.taltech.receipt.model.Receipt;
import ee.taltech.receipt.repository.ReceiptRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class ReceiptService {

    private final FileSystemStorageService fileService;
    private final CustomerService customerService;
    private final ReceiptRepository repository;
    private final EntryService entryService;
    private final OcrService ocrService;

    public Receipt create(Base64File base64) {
        String name = fileService.store(base64);
        return create(name);
    }

    public Receipt create(MultipartFile file) {
        if (!fileService.isImage(file)) {
            throw new IllegalArgumentException("Receipt file must be an image");
        }
        String name = fileService.store(file);
        return create(name);
    }

    private Receipt create(String fileName) {
        Receipt receipt = new Receipt()
            .setCustomer(new Customer().setId(1L))
            .setIssuedAt(Timestamp.from(Instant.now()))
            .setFileName(fileName);

        Receipt saved = repository.save(receipt);

        for (String text : ocrService.identify(fileName)) {
            entryService.create(new Entry().setName(text).setReceipt(saved));
        }

        return saved;
    }

    public Long getAmount() {
        return repository.count();
    }

    public void delete(Receipt receipt) {
        repository.delete(receipt);
    }

    public Receipt findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Receipt with ID " + id));
    }

    @Transactional
    public Receipt update(Receipt updated, Long id) {
        Receipt old = findById(id);

        old.setIssuer(updated.getIssuer());
        old.setIssuedAt(updated.getIssuedAt());
        old.setModifiedAt(Timestamp.from(Instant.now()));

        return repository.save(old);
    }

    public List<Receipt> getAllCustomerReceipts(Long customerId) {
        return customerService.findById(customerId).getReceipts();
    }

}
