package ee.taltech.heroesbackend.service;

import ee.taltech.heroesbackend.model.Receipt;
import ee.taltech.heroesbackend.repository.ReceiptRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ReceiptService {

    private final FileSystemStorageService fileService;
    private final ReceiptRepository repository;

    public Receipt create(MultipartFile file) {
        if (!fileService.isImage(file)) {
            throw new IllegalArgumentException("Receipt file must be an image");
        }

        String name = fileService.store(file);
        return repository.save(new Receipt().setFileName(name));
    }

    public Receipt findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Receipt with ID " + id));
    }

}
