package com.rest.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.app.models.Book;
import com.rest.app.repos.BookRepo;
import com.rest.app.services.BookService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
@ActiveProfiles("test")
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private BookRepo bookRepo;

    private List<Book> bookList;

    @BeforeEach
    void setUp() {
        this.bookList = new ArrayList<>();
        this.bookList.add(new Book((long) 1, "java", "funda", 5));
        this.bookList.add(new Book((long) 2, "python", "funda", 5));
        this.bookList.add(new Book((long) 3, "node", "funda", 5));
    }
    @Test
    void bookList() throws Exception {
        Mockito.when(bookService.findAll()).thenReturn(Arrays.asList(new Book((long) 1, "java", "funda", 5)));
        //given(bookService.findAll()).willReturn(bookList);
        mockMvc.perform(MockMvcRequestBuilders.get("/allbooks")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("java")));

    }

    @Test
    void getBookById() throws Exception {
        Mockito.when(bookService.findById((long)1)).thenReturn(Optional.of(new Book((long) 1, "java", "funda", 5)));
        mockMvc.perform(MockMvcRequestBuilders.get("/book?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("java")));
    }

    @Test
    void createBook() throws Exception {
        given(bookService.createBook(any(Book.class))).willAnswer((invocation -> invocation.getArgument(0)));
        Book book = new Book((long) 1, "java", "funda", 5);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/createBook")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect( MockMvcResultMatchers.jsonPath("$.name",Matchers.is(book.getName())));

    }

    @Test
    void updateBook() throws Exception {
        Book book = new Book((long) 1, "java", "funda", 5);
        Mockito.when(bookService.findById((long)1)).thenReturn(Optional.of(book));
        given(bookService.updateBook(any(Book.class))).willAnswer((invocation -> invocation.getArgument(0)));

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/updateBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect( MockMvcResultMatchers.jsonPath("$.name",Matchers.is(book.getName())));
    }

    @Test
    void deleteBook() throws Exception {
        Book book = new Book((long) 1, "java", "funda", 5);
        Mockito.when(bookService.findById((long)1)).thenReturn(Optional.of(book));
        doNothing().when(bookService).deleteBook(any(Book.class));

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                        .andExpect(status().isOk());
    }

    @Test
    void deleteBookById() throws Exception {
        Book book = new Book((long) 1, "java", "funda", 5);
        Mockito.when(bookService.findById((long)1)).thenReturn(Optional.of(book));
        doNothing().when(bookService).deleteBookById(book.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteBookById?id=1"))
                .andExpect(status().isOk());
    }
}