package com.junit.rest.api.application.controller;

import com.junit.rest.api.application.entity.Book;
import com.junit.rest.api.application.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBookRecord() {
        return bookRepository.findAll();
    }

    @GetMapping(value = "{bookId}")
    public Book getBookById(@PathVariable Long bookId) {
        return bookRepository.findById(bookId).get();
    }

    @PostMapping
    public Book createBookrecord(@RequestBody @Validated Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/books")
    public Book updateBookRecord(@RequestBody @Validated Book book) throws RuntimeException {
        if (book == null || book.getBookId() == 0) {
            throw new RuntimeException("Book record must not be null");
        }
        Optional<Book> optional = this.bookRepository.findById(book.getBookId());
        if (!optional.isPresent()) {
            throw new RuntimeException("book with id is " + book.getBookId() + "does not exist");
        }
        Book book1 = optional.get();
        book1.setName(book.getName());
        book1.setRating(book.getRating());
        book1.setSummary(book.getSummary());
        return bookRepository.save(book1);
    }

    @GetMapping("findbyname/{name}")
    public ResponseEntity<Book> findByBookName(@PathVariable String names) {
        if (names == null) {
            throw new RuntimeException();
        }
            Book byBookName = this.bookRepository.findByBookName(names);
            return new ResponseEntity<>(byBookName, HttpStatus.FOUND);
        }

    }