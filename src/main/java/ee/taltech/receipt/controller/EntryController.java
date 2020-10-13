package ee.taltech.receipt.controller;

import ee.taltech.receipt.model.Entry;
import ee.taltech.receipt.service.EntryService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("entry")
@RestController
@AllArgsConstructor
public class EntryController {

    private final EntryService entryService;

    @PostMapping()
    @ApiResponses({
        @ApiResponse(
            code = HttpServletResponse.SC_CREATED,
            message = "Entry created successfully"
        ),
        @ApiResponse(
            code = HttpServletResponse.SC_BAD_REQUEST,
            message = "Your input data was invalid, please check and try again"
        ),
    })
    public ResponseEntity<?> create(@RequestBody Entry entry) {
        try {
            Long id = entryService.create(entry).getId();
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiResponses({
        @ApiResponse(
            code = HttpServletResponse.SC_OK,
            message = "Entry deleted"
        ),
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        entryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    @ApiResponses({
        @ApiResponse(
            code = HttpServletResponse.SC_OK,
            message = "Entry found"
        ),
        @ApiResponse(
            code = HttpServletResponse.SC_NOT_FOUND,
            message = "No entry exists for the given ID"
        ),
    })
    public ResponseEntity<?> getEntry(@PathVariable Long id) {
        try {
            Entry entry = entryService.findById(id);
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    @ApiResponses({
        @ApiResponse(
            code = HttpServletResponse.SC_OK,
            message = "Entry updated"
        ),
        @ApiResponse(
            code = HttpServletResponse.SC_BAD_REQUEST,
            message = "Your input data was invalid, please check and try again."
        ),
    })
    public ResponseEntity<?> updateEntry(@RequestBody Entry entry, @PathVariable Long id) {
        try {
            Entry updated = entryService.update(entry, id);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
