package com.book.librarymanagement.repostery;

import com.book.librarymanagement.model.book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<book,Long> {
}
