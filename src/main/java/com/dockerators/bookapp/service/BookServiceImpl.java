package com.dockerators.bookapp.service;

import com.dockerators.bookapp.dao.BookRepository;
import com.dockerators.bookapp.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return (this.bookRepository.findAll());
    }

    @Override
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
    public Book save(Book book) {
        return (this.bookRepository.save(book));
    }

    @Override
    public void deleteById(int id) {
        this.bookRepository.deleteById(id);
    }
}
