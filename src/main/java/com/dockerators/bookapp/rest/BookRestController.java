package com.dockerators.bookapp.rest;

import com.dockerators.bookapp.entity.Book;
import com.dockerators.bookapp.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookRestController {
    // Private Book Service to obtain all functionalities of the service layer.
    // The comments documentation for the error handling is in the service code.
    private BookService bookService;

    @Autowired
    // Constructor
    public BookRestController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/books")
    // Route to get all books
    public List<Book> findAll() {
        return (this.bookService.findAll());
    }

    @GetMapping("/books/{book_id}")
    // Route to get the book that corresponds to an id
    // The id is taken as a path variable
    public Book findById(@PathVariable int book_id) {
        return this.bookService.findById(book_id);
    }

    @GetMapping("/books/code/{code}")
    // Route to get the book that corresponds to a book code
    // The code is taken as a path variable
    public Book findByCode(@PathVariable String code){
        return this.bookService.findByCode(code);
    }
    @PostMapping("/books")
    // Route to add a new book
    // The book object is accepted in the request body
    public Book addBook(@RequestBody Book book) {
        book.setId(0);      // To force an add, not an update.
        return (this.bookService.save(book));
    }

    @PutMapping("/books")
    // Route to update a book
    // The book object is accepted in the request body
    public Book updateBook(@RequestBody Book book) {
        return (this.bookService.updateBook(book));
    }

    @DeleteMapping("/books/{book_id}")
    // Route to delete a book that corresponds to an id
    // The id of the book to be deleted is taken as a path variable
    public Book deleteStudentById(@PathVariable int book_id) {
        return this.bookService.deleteById(book_id);
    }

    @Transactional
    @DeleteMapping("/books/code/{code}")
    // Route to delete a book that corresponds to a code
    // The code of the book to be deleted is taken as a path variable
    public Book deleteStudentByCode(@PathVariable String code) {
        return this.bookService.deleteByCode(code);
    }

}
