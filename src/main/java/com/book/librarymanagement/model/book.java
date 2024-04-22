package com.book.librarymanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Table(name = "book")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotNull
    @Column(name = "publication_year")
    private Year publicationYear;
    @NotBlank
    @Pattern(regexp = "\\d{13}", message = "ISBN must be 13 digits")
    @Column(name = "isbn", unique = true, length = 13)
    private String isbn;
    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BorrowingRecord> borrowings = new HashSet<>();
}
