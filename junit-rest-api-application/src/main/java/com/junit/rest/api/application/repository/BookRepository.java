package com.junit.rest.api.application.repository;


import com.junit.rest.api.application.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Book findByBookName(String names);
}
