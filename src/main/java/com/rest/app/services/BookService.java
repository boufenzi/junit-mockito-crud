package com.rest.app.services;

import com.rest.app.models.Book;
import com.rest.app.repos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> findAll() {
        return this.bookRepo.findAll();
    }

    public Optional<Book> findById(Long id) {
        return this.bookRepo.findById(id);
    }

    public Book createBook(Book book) {
        return this.bookRepo.save(book);
    }

    public Book updateBook(Book book) {
        return this.bookRepo.save(book);
    }

    public void deleteBook(Book book) {
         this.bookRepo.delete(book);

    }

    public void deleteBookById(Long id) {
        this.bookRepo.deleteById(id);

    }
}
