package com.rest.app.controllers;

import com.rest.app.models.Book;
import com.rest.app.repos.BookRepo;
import com.rest.app.services.BookService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private BookService bookService;


    @GetMapping(path = "/allbooks")
    public List<Book> bookList() {
        return this.bookService.findAll();
        // return bookRepo.findAll();

    }

    @GetMapping(path = "/book")
    public Optional<Book> getBookById(@RequestParam("id") long id) {
        return bookService.findById(id);
    }

    @PostMapping(path = "/createBook")
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping(path = "/updateBook")
    public Book updateBook(@RequestBody Book book) throws NotFoundException {
        if (book.equals(null) || book.getId() == null)
            throw new NotFoundException("book not found");

        Optional<Book> book1 = bookService.findById(book.getId());
        if (!book1.isPresent())
            throw new NotFoundException("book does not exist");
        return bookService.updateBook(book);
    }
@DeleteMapping(path = "/deleteBook")
    public void deleteBook(@RequestBody Book book) throws NotFoundException {
        Optional<Book> book1 = bookService.findById(book.getId());
        if (!book1.isPresent())
            throw new NotFoundException("book does not exist");

         bookService.deleteBook(book);

    }

    @DeleteMapping(path = "/deleteBookById")
    public void deleteBook(@RequestParam("id") Long id) throws NotFoundException {
        Optional<Book> book1 = bookService.findById(id);
        if (!book1.isPresent())
            throw new NotFoundException("book does not exist");

        bookService.deleteBookById(id);

    }

}
