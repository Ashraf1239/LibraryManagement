package com.book.librarymanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Table(name = "patron")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patron_id")
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;

    private String phoneNumber;
    @JsonIgnore
    @OneToMany(mappedBy = "patron", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BorrowingRecord> borrowings = new HashSet<>();
}
