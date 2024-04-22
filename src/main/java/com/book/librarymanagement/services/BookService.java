package com.book.librarymanagement.services;

import com.book.librarymanagement.Exceptions.ResourceNotFoundException;
import com.book.librarymanagement.model.book;
import com.book.librarymanagement.repostery.BookRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepo bookRepo;

    @Transactional
    @CacheEvict(value = "books", allEntries = true)
    public book addBook(book book) {
        return bookRepo.save(book);
    }

    @Transactional
    @CachePut(value = "books", key = "#id")
    public book updateBook(long id, book book) {
        book existingBook = bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
        if (existingBook == null) {
            return null;
        } else {
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setIsbn(book.getIsbn());
            existingBook.setPublicationYear(book.getPublicationYear());
            return bookRepo.save(existingBook);
        }
    }

    @Transactional
    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(long id) {
        book existingBook = bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
        if (existingBook != null) {
            bookRepo.deleteById(id);
        }

    }

    @Cacheable(value = "books", key = "#id")
    public book getBookById(long id) {
        return bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
    }

    @Cacheable(value = "books")
    public Iterable<book> getAllBooks() {
        return bookRepo.findAll();
    }
}
