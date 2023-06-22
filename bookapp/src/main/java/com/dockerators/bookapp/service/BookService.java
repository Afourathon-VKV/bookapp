package com.dockerators.bookapp.service;

import com.dockerators.bookapp.entity.Book;

import java.util.List;

public interface BookService {
    public List<Book> findAll();
    public Book findById(int id);
    public Book save(Book employee);
    public void  deleteById(int id);
}
