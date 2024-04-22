package com.book.librarymanagement.services;

import com.book.librarymanagement.Exceptions.ResourceNotFoundException;
import com.book.librarymanagement.model.Patron;

import com.book.librarymanagement.repostery.PatronRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatronService {
    @Autowired
    private PatronRepo patronRepo;

    @Transactional
    public Patron addPatron(Patron patron) {
        return patronRepo.save(patron);
    }

    public Patron updatePatron(long id, Patron patron) {
        Patron existingPatron = patronRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patron not found with ID: " + id));
        if (existingPatron == null) {
            return null;
        } else {
            existingPatron.setEmail(patron.getEmail());
            existingPatron.setName(patron.getName());
            existingPatron.setPhoneNumber(patron.getPhoneNumber());

            return patronRepo.save(existingPatron);
        }
    }

    @Transactional
    public void deletePatron(long id) {
        Patron existingPatron = patronRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patron not found with ID: " + id));
        if (existingPatron != null) {
            patronRepo.deleteById(id);
        }

    }

    public Patron getPatronById(long id) {
        return patronRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patron not found with ID: " + id));
    }

    public Iterable<Patron> getAllPatrons() {
        return patronRepo.findAll();
    }
}
