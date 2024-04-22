package com.book.librarymanagement.repostery;

import com.book.librarymanagement.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepo extends JpaRepository<Patron, Long> {
}
