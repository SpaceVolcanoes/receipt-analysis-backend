package ee.taltech.heroesbackend.controller;

import ee.taltech.heroesbackend.exception.StorageFileNotFoundException;
import ee.taltech.heroesbackend.service.StorageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("files")
@RestController
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping()
    @ApiOperation(
        value = "List uploaded files",
        produces = "application/json"
    )
    public List<String> listFiles() {
        return storageService.loadAll()
            .map(path -> MvcUriComponentsBuilder.fromMethodName(
                FileUploadController.class,
                "serveFile",
                path.getFileName().toString()
            ).build().toUri().toString())
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

    @PostMapping()
    @ApiOperation(
        value = "Upload receipt file",
        produces = "text/plain",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> saveFile(
        @ApiParam(name = "file", value = "Select the file to Upload", required = true)
            @RequestPart("file") MultipartFile file
    ) {
        storageService.store(file);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

}
