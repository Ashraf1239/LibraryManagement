package com.book.librarymanagement.services;

import com.book.librarymanagement.Exceptions.ResourceNotFoundException;
import com.book.librarymanagement.model.BorrowingRecord;
import com.book.librarymanagement.repostery.BookRepo;
import com.book.librarymanagement.repostery.BorrowRepo;
import com.book.librarymanagement.repostery.PatronRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowService {
    @Autowired
    private BorrowRepo borrowRepository;

    @Autowired
    private BookRepo bookRepository;

    @Autowired
    private PatronRepo patronRepository;
    @Transactional
    @CachePut(value = "borrowingRecords", key = "#bookId + '-' + #patronId")
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId)));
        borrowingRecord.setPatron(patronRepository.findById(patronId).orElseThrow(() -> new ResourceNotFoundException("Patron not found with ID: " + patronId)));
        borrowingRecord.setBorrowingDate(LocalDate.now());
        borrowingRecord.setReturnDate(LocalDate.now().plusDays(7));
        borrowingRecord.setReturned(false);
        borrowRepository.save(borrowingRecord);
        return borrowingRecord;
    }
    @Transactional
    @CachePut(value = "borrowingRecords", key = "#bookId + '-' + #patronId")
    public BorrowingRecord returnBook(Long bookId, Long patronId) {

        BorrowingRecord borrowingRecord = borrowRepository.findByBookIdAndPatronIdAndReturnedFalse(bookId, patronId);
        if(borrowingRecord == null) {
            return null;
        }else {
            System.out.println(borrowingRecord.getReturnDate());
            borrowingRecord.setReturnDate(LocalDate.now());
            borrowingRecord.setReturned(true);
            BorrowingRecord borrowingRecord1 = borrowRepository.save(borrowingRecord);
            return borrowingRecord1;
        }


    }
}
