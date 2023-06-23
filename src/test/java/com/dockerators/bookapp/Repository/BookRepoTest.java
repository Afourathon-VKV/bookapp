package com.dockerators.bookapp.Repository;


import com.dockerators.bookapp.dao.BookRepository;
import com.dockerators.bookapp.entity.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase
public class BookRepoTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveBookTest(){
        Book book = new Book(1,"Study in Scarlet","Conan Doyle","Mystery Book","123456789");
        bookRepository.save(book);
        Assertions.assertTrue(book.getId() > 0);
    }

    @Test
    @Order(2)
    @AutoConfigureTestDatabase
    public void getBookTest(){
        Book search_book = bookRepository.findById(1).get();
        Assertions.assertTrue((search_book.getId()) == 1);
    }

    @Test
    @Order(3)
    @AutoConfigureTestDatabase
    public void getNonPresentBookTest(){
        Boolean isPresent = bookRepository.findById(2).isPresent();
        Assertions.assertTrue(isPresent == false);
    }

    @Test
    @Order(3)
    @AutoConfigureTestDatabase
    public void getListOfBooksTest(){
        List<Book> books = bookRepository.findAll();
        Assertions.assertTrue((books.size()) > 0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    @AutoConfigureTestDatabase
    public void updateBookTest(){
        Book book = bookRepository.findById(1).get();
        book.setTitle("Hounds of baskervilles");
        Assertions.assertTrue((book.getTitle()).equals("Hounds of baskervilles"));
    }


    @Test
    @Order(5)
    @AutoConfigureTestDatabase
    public void deleteBookTest(){
        Book book = bookRepository.findById(1).get();
        bookRepository.delete(book);
        Book book1 = null;
        Optional<Book> optionalBook = bookRepository.findByTitle("Hounds of baskervilles");
        if(optionalBook.isPresent()){
            book1 = optionalBook.get();
        }
        Assertions.assertTrue(book1 == null);
    }

}

