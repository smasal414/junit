package com.junit.rest.api.application.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.junit.rest.api.application.entity.Book;
import com.junit.rest.api.application.repository.BookRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.MockitoAnnotations;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)

public class BookControllerTest {

    ObjectMapper objectMapper = new ObjectMapper(); // convert json to string for that purpose
    ObjectWriter objectWriter = objectMapper.writer();
    @Mock
    BookRepository bookRepository;
    Book record_1 = new Book(1l, "core java", "basic concepts of java", "5");
    Book Record_2 = new Book(2l, "atomic habits ", "how to build better habits", "5");
    Book recors_3 = new Book(3l, "greeky algorithm", "data structure", "5");
    private MockMvc mockMvc;
    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @AfterEach
    public void end() {
        record_1 = null;
        Record_2 = null;
        recors_3 = null;
    }

    @Test
    public void getAllRecords_success() throws Exception {
        List<Book> list = new ArrayList<>(Arrays.asList(record_1, Record_2, recors_3));
        Mockito.when(this.bookRepository.findAll()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("greeky algorithm")));

    }

    @Test
    public void getBookByIdTest() throws Exception {
        Mockito.when(bookRepository.findById(record_1.getBookId())).thenReturn(Optional.of(record_1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("core java")));


    }

    @Test
    public void saveBookTest() throws Exception {
        Book record = Book.builder()
                .bookId(4l)
                .name("introducation to c")
                .summary("basic knowledhe")
                .rating("5")
                .build();
        Mockito.when(this.bookRepository.save(record)).thenReturn(record);
        String content = objectWriter.writeValueAsString(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("introducation to c")));

    }

    @Test
    public void updateBookTest() throws Exception {
        Book book = Book.builder()
                .bookId(1l)
                .name("new book upadted")
                .summary("see record")
                .rating("5")
                .build();

        String contenString = objectWriter.writeValueAsString(book);
        Mockito.when(this.bookRepository.findById(record_1.getBookId())).thenReturn(Optional.of(record_1));
        Mockito.when(this.bookRepository.save(book)).thenReturn(book);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/book/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(contenString);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("new book upadted")));



    }


//@Test
//    public void updateBookPositiveTest() throws Exception
//{
//    Book  book = Book.builder()
//                    .bookId(2l)
//                            .rating("3")
//                                    .name("my book")
//                                            .summary("my slef")
//                                                    .build();
//
//    Mockito.when(this.bookRepository.findById(record_1.getBookId())).thenReturn(Optional.of(record_1));
//    Mockito.when(this.bookRepository.save(book)).thenReturn(book);
//    Book book1 = this.bookController.updateBookRecord(book);
//    assertEquals(book.getBookId(),book1.getBookId());
    






}

