package com.book.librarymanagement.Controller;

import com.book.librarymanagement.Exceptions.ResourceNotFoundException;
import com.book.librarymanagement.model.BorrowingRecord;
import com.book.librarymanagement.services.BorrowService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class borrowControl {

    @Autowired
    private BorrowService borrowService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<?> borrowBook(
            @PathVariable @Min(1) Long bookId,
            @PathVariable @Min(1) Long patronId
    ) {
        try {
            BorrowingRecord borrowingRecord = borrowService.borrowBook(bookId, patronId);
            return ResponseEntity.ok(borrowingRecord);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(
            @PathVariable @Min(1) Long bookId,
            @PathVariable @Min(1) Long patronId
    ) {
        BorrowingRecord returnedRecord = borrowService.returnBook(bookId, patronId);
        if (returnedRecord == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(returnedRecord);
    }

    // Exception handler for handling IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Generic exception handler for handling unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
}