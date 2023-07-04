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

    public Book findByCode(String code){
        Optional<Book> result = this.bookRepository.findByCode(code);
        Book book;
        if(result.isPresent()) {
            book = result.get();
            return(book);
        }else{
            // Will throw error if a book with that Code is not found
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
    // Deletes a book that corresponds to a code
    public Book deleteByCode(String code) {
        Optional<Book> b = this.bookRepository.findByCode(code);
        if (b.isEmpty()) {
            // Will throw error if a book with that code does not exist
            throw new BookNotFoundException();
        } else {
            this.bookRepository.deleteByCode(code);
            return b.get();
        }
    }
}
