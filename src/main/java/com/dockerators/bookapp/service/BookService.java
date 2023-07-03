package com.dockerators.bookapp.service;

import com.dockerators.bookapp.entity.Book;

import java.util.List;

// An interface for the implementation of the book service
public interface BookService {
    public List<Book> findAll();
    public Book findById(int id);
    public Book findByCode(String code);
    public Book save(Book book);
    public Book  deleteById(int id);
    public Book updateBook(Book book);
}
