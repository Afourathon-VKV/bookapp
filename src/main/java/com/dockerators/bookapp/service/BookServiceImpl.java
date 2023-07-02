package com.dockerators.bookapp.service;

import com.dockerators.bookapp.dao.BookRepository;
import com.dockerators.bookapp.entity.Book;
import com.dockerators.bookapp.exception.BookAlreadyExistsException;
import com.dockerators.bookapp.exception.BookNotFoundException;
import com.dockerators.bookapp.exception.NullFieldsException;
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
        }else{
            // Will throw error if a book with that ID is not found
            throw new BookNotFoundException();
        }
    }

    @Override
    public Book updateBook(Book book) throws RuntimeException{
        try{
            Optional <Book> result = this.bookRepository.findByCode(book.getCode());
            if(result.isEmpty()){
                // Will throw error if a book with that code is not found
                throw new BookNotFoundException();
            }
            book.setId(result.get().getId());
            return this.bookRepository.save(book);
        } catch (BookNotFoundException e) {
            throw e;
        } catch (RuntimeException e){
            // Will throw error if any of the fields are empty.
            throw new NullFieldsException();
        }
    }

    @Override
    // Adds a book to the database
    public Book save(Book book) {
        try{
            Optional<Book> s = this.bookRepository.findByCode(book.getCode());
            if(s.isPresent()){
                throw new BookAlreadyExistsException();
            }else{
                return (this.bookRepository.save(book));
            }
        }catch (BookAlreadyExistsException e){
            // Will throw error if a book with that Code already exists
            throw e;
        }catch (RuntimeException e){
            // Will throw error if any of the fields are NULL.
            throw new NullFieldsException();
        }
    }

    @Override
    // Deletes a book that corresponds to an id
    public Book deleteById(int id) {
        Optional<Book> b = this.bookRepository.findById(id);
        if (b.isEmpty()) {
            // Will throw error if a book with that ID does not exist
            throw new BookNotFoundException();
        } else {
            this.bookRepository.deleteById(id);
            return b.get();
        }
    }
}
