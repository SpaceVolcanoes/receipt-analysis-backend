package ee.taltech.receipt.controller;

import ee.taltech.receipt.exception.StorageFileNotFoundException;
import ee.taltech.receipt.service.StorageService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("files")
@RestController
@AllArgsConstructor
public class FileUploadController {

    private final StorageService storageService;

    @GetMapping()
    @ApiOperation(
        value = "List uploaded files",
        produces = "application/json"
    )
    public List<String> listFiles() {
        return storageService.loadAll()
            .map(path -> path.getFileName().toString())
            .collect(Collectors.toList());
    }

    @GetMapping("{filename}")
    @ApiOperation(
        value = "Get the file by name",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);

        return ResponseEntity.ok().header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + file.getFilename() + "\""
        ).body(file);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

}
