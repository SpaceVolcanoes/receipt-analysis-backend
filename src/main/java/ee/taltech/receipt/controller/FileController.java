package ee.taltech.receipt.controller;

import ee.taltech.receipt.exception.StorageFileNotFoundException;
import ee.taltech.receipt.security.Role;
import ee.taltech.receipt.service.ReceiptService;
import ee.taltech.receipt.service.StorageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("files")
@RestController
@AllArgsConstructor
public class FileController {

    private final Logger logger;
    private final StorageService storageService;
    private final ReceiptService receiptService;

    @Role.Admin
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

    @Role.Guest
    @SuppressWarnings("ConstantConditions")
    @GetMapping("{filename}")
    @ApiOperation(
        value = "Get the file by name"
    )
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        String type = file.getFilename().endsWith("png") ? MediaType.IMAGE_PNG_VALUE : MediaType.IMAGE_JPEG_VALUE;

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
            .header(HttpHeaders.CONTENT_TYPE, type)
            .body(file);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exception) {
        logger.warn(exception.getMessage(), exception);
        return ResponseEntity.notFound().build();
    }

    @Role.Admin
    @DeleteMapping("{filename}")
    @ApiResponses({
        @ApiResponse(
            code = HttpServletResponse.SC_OK,
            message = "File deleted"
        ),
    })
    public ResponseEntity<?> delete(@PathVariable String filename) {
        receiptService.removeFile(filename);
        storageService.deleteByFileName(filename);
        return ResponseEntity.ok().build();
    }

}
