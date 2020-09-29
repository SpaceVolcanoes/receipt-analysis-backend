package ee.taltech.heroesbackend.controller;

import ee.taltech.heroesbackend.exception.StorageException;
import ee.taltech.heroesbackend.model.Receipt;
import ee.taltech.heroesbackend.service.ReceiptService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("receipt")
@RestController
@AllArgsConstructor
public class ReceiptController {

    private final ReceiptService service;

    @PostMapping()
    @ApiOperation(
        value = "Create a Receipt from an image file",
        produces = "text/plain",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ApiResponses({
        @ApiResponse(
            code = HttpServletResponse.SC_CREATED,
            message = "Receipt created successfully"
        ),
        @ApiResponse(
            code = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
            message = "Receipt file type must be an image"
        ),
        @ApiResponse(
            code = HttpServletResponse.SC_SERVICE_UNAVAILABLE,
            responseHeaders={@ResponseHeader(name = "Retry-After", response = Long.class, description = "Delta seconds")},
            message = "Service temporarily unavailable, try again in 10 seconds"
        )
    })
    public ResponseEntity<?> create(
        @ApiParam(name = "file", value = "Select the file to Upload", required = true)
        @RequestPart("file") MultipartFile file
    ) {
        try {
            Long id = service.create(file).getId();

            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (StorageException exception) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).header("Retry-After", "10").build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }
    }

    @GetMapping("{id}")
    @ApiResponses({
        @ApiResponse(
            code = HttpServletResponse.SC_OK,
            message = "Receipt found"
        ),
        @ApiResponse(
            code = HttpServletResponse.SC_NOT_FOUND,
            message = "No Receipt exist for a given ID"
        ),
    })
    public ResponseEntity<?> getReceipt(@PathVariable Long id) {
        try {
            Receipt receipt = service.findById(id);
            return new ResponseEntity<>(receipt, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    @ApiResponses({
        @ApiResponse(
            code = HttpServletResponse.SC_OK,
            message = "Receipt updated"
        ),
        @ApiResponse(
            code = HttpServletResponse.SC_BAD_REQUEST,
            message = "Your input data was invalid, please check and try again"
        ),
    })
    public ResponseEntity<?> updateReceipt(@RequestBody Receipt receipt, @PathVariable Long id) {
        try {
            Receipt updated = service.update(receipt, id);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
