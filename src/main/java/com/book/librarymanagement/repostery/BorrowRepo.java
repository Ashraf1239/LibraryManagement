package com.book.librarymanagement.repostery;

import com.book.librarymanagement.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowRepo extends JpaRepository<BorrowingRecord, Long> {
    BorrowingRecord findByBookIdAndPatronIdAndReturnedFalse(Long bookId, Long patronId);

}
