package com.book.librarymanagement.Controller;

import com.book.librarymanagement.Exceptions.ResourceNotFoundException;
import com.book.librarymanagement.model.book;
import com.book.librarymanagement.services.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
public class bookControl {
    @Autowired
    private  BookService bookService;
    @GetMapping("")
    public ResponseEntity<Iterable<book>> getBooks() {
        Iterable<book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<book> getBookById(@PathVariable @Min(1) long id) {
        book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping("")
    public ResponseEntity<book> addBook(@Valid @RequestBody book book) {
        book addedBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable long id, @Valid @RequestBody book book) {
        try {
            book updatedBook = bookService.updateBook(id, book);
            return ResponseEntity.ok(updatedBook);
        } catch (ResourceNotFoundException ex) {
            return handleBookNotFoundException(ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            return handleBookNotFoundException(ex);
        }
    }

    @ExceptionHandler
    public ResponseEntity<String> handleBookNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body("Validation error: " + ex.getFieldError().getDefaultMessage());
    }
}