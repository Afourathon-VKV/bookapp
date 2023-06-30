package com.dockerators.bookapp.service;

import com.dockerators.bookapp.dao.BookRepository;
import com.dockerators.bookapp.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    // Private repository to access the book database and perform SQL Queries
    private BookRepository bookRepository;

    @Autowired
    // Constructor
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    // Will return a list of all books in the database
    public List<Book> findAll() {
        return (this.bookRepository.findAll());
    }

    @Override
    // Will return a book from the database that corresponds to an id
    public Book findById(int id) {
        // May be null.
        Optional<Book> result = this.bookRepository.findById(id);

        Book book;
        if(result.isPresent()) {
            book = result.get();
            return(book);
        }
        else{
            throw new RuntimeException("Did not find book id - " + id);
        }
    }

    @Override
    // Adds a book to the database
    public Book save(Book book) {
        return (this.bookRepository.save(book));
    }

    @Override
    // Deletes a book that corresponds to an id
    public void deleteById(int id) {
        this.bookRepository.deleteById(id);
    }
}
