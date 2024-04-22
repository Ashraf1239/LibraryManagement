package com.book.librarymanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.print.Book;
import java.time.LocalDate;

@Table(name = "borrowing")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne

    @JoinColumn(name = "book_id", nullable = false)
    @NotNull
    private book book;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    private Patron patron;
    @NotNull
    @Column(nullable = false)
    private LocalDate borrowingDate;
    private Boolean returned;
    private LocalDate returnDate;

}
