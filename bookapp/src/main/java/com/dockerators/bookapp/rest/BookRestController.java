package com.dockerators.bookapp.rest;

import com.dockerators.bookapp.entity.Book;
import com.dockerators.bookapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookRestController {
    private BookService bookService;

    @Autowired
    public BookRestController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> findAll() {
        return (this.bookService.findAll());
    }

    @GetMapping("/books/{book_id}")
    public Book findById(@PathVariable int book_id) {
        Book book = this.bookService.findById(book_id);
        if (book == null) {
            throw new RuntimeException("Book id not found - " + book_id);
        }
        return (book);
    }

    // We can use the @RequestBody annotation to bind the request JSON body to an object.
    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        book.setId(0);      // To force an add, not an update.
        return (this.bookService.save(book));
    }

    @PutMapping("/books")
    public Book updateBook(@RequestBody Book book) {
        return (this.bookService.save(book));
    }

    @DeleteMapping("/books/{book_id}")
    public String deleteBook(@PathVariable int book_id) {
        Book book = this.bookService.findById(book_id);

        if(book == null) {
            throw new RuntimeException("Book id not found - " + book_id);
        }
        this.bookService.deleteById(book_id);
        return ("Deleted book id - " + book_id);
    }
}
