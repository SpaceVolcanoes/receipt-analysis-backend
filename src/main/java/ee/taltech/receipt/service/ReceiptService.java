package ee.taltech.receipt.service;

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
    private final EntryService entryService;
    private final CustomerService customerService;
    private final ReceiptRepository repository;

    public Receipt create(MultipartFile file) {
        if (!fileService.isImage(file)) {
            throw new IllegalArgumentException("Receipt file must be an image");
        }

        String name = fileService.store(file);
        return repository.save(new Receipt().setFileName(name));
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

        updated.getEntries().forEach(entry -> {
            entry.setReceipt(old);
            if (entry.getId() != null) {
                entryService.update(entry, entry.getId());
            } else {
                entryService.create(entry);
            }
        });

        old.setIssuer(updated.getIssuer());
        old.setIssuedAt(updated.getIssuedAt());
        old.setModifiedAt(Timestamp.from(Instant.now()));

        return repository.save(old);
    }

    public List<Receipt> getAllCustomerReceipts(Long customerId) {
        return customerService.findById(customerId).getReceipts();
    }

}
