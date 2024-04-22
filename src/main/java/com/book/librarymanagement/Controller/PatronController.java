package com.book.librarymanagement.Controller;

import com.book.librarymanagement.Exceptions.ResourceNotFoundException;
import com.book.librarymanagement.model.Patron;
import com.book.librarymanagement.services.PatronService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping("")
    public Iterable<Patron> getPatrons() {
        return patronService.getAllPatrons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable long id) {
        Patron patron = patronService.getPatronById(id);
        if (patron == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patron);
    }

    @PostMapping("")
    public ResponseEntity<?> addPatron(@RequestBody @Valid Patron patron, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors: " + result.getAllErrors());
        }
        Patron savedPatron = patronService.addPatron(patron);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatron);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatron(@PathVariable long id, @RequestBody @Valid Patron patron,
                                          BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors: " + result.getAllErrors());
        }
        Patron updatedPatron = patronService.updatePatron(id, patron);
        if (updatedPatron == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPatron);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable long id) {
        Patron patron = patronService.getPatronById(id);

        if (patron == null) {

            return ResponseEntity.notFound().build();
        }
        patronService.deletePatron(id);
        return ResponseEntity.noContent().build();
    }

    // Exception handler for handling specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Generic exception handler for handling unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
}

