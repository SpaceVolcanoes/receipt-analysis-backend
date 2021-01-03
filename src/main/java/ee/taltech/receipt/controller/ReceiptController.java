package ee.taltech.receipt.controller;

import ee.taltech.receipt.dto.Base64File;
import ee.taltech.receipt.exception.StorageException;
import ee.taltech.receipt.model.Receipt;
import ee.taltech.receipt.security.Role;
import ee.taltech.receipt.security.SessionUser;
import ee.taltech.receipt.security.UserSessionService;
import ee.taltech.receipt.service.ReceiptService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("receipts")
@RestController
@AllArgsConstructor
public class ReceiptController {

    private final Logger logger;
    private final ReceiptService receiptService;
    private final UserSessionService sessionService;

    @Role.User
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
            code = HttpServletResponse.SC_FORBIDDEN,
            message = "Service has reached its current capacity"
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
        return createReceipt(file);
    }

    @Role.User
    @PostMapping("/base64")
    @ApiOperation(
        value = "Create a Receipt from a base64 image file",
        produces = "text/plain"
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
    public ResponseEntity<?> create(@RequestBody Base64File file) {
        return createReceipt(file);
    }

    @Role.User
    @DeleteMapping("{id}")
    @ApiResponses({
        @ApiResponse(
            code = HttpServletResponse.SC_OK,
            message = "Receipt deleted"
        ),
        @ApiResponse(
            code = HttpServletResponse.SC_NOT_FOUND,
            message = "No Receipt exist for a given ID"
        ),
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Receipt receipt = receiptService.findById(id);
            receiptService.delete(receipt);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @Role.User
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
            Receipt receipt = receiptService.findById(id);
            return new ResponseEntity<>(receipt, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @Role.User
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
            Receipt updated = receiptService.update(receipt, id);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    /**
     * Handle multiple input and response cases for create endpoint
     */
    private <T> ResponseEntity<?> createReceipt(T file) {
        try {
            SessionUser user = sessionService.getUser();

            if (file instanceof Base64File) {
                return new ResponseEntity<>(receiptService.create((Base64File) file, user.getId()).getId(), CREATED);
            }
            if (file instanceof MultipartFile) {
                return new ResponseEntity<>(receiptService.create((MultipartFile) file, user.getId()).getId(), CREATED);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (StorageException exception) {
            logger.error(exception.getMessage(), exception);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).header("Retry-After", "10").build();
        } catch (IllegalArgumentException exception) {
            logger.warn(exception.getMessage(), exception);
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        } catch (IllegalStateException exception) {
            logger.warn(exception.getMessage(), exception);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
