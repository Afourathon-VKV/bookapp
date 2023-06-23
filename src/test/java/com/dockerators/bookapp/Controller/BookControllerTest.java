package com.dockerators.bookapp.Controller;

import com.dockerators.bookapp.entity.Book;
import com.dockerators.bookapp.rest.BookRestController;
import com.dockerators.bookapp.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(BookRestController.class)
public class BookControllerTest {
    ObjectMapper om = new ObjectMapper();
    @MockBean
    private BookService bookService;

    @InjectMocks
    private BookRestController bookRestController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllBooks() throws Exception {
        // Mock data
        Book book1 = new Book(1,"A Study in Scarlet","Conan Doyle", "Murder Story","987654321");
        Book book2 = new Book(2,"And Then There Were None","Agatha Christie", "Murder Story","123456789");
        List<Book> books = Arrays.asList(book1,book2);

        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("A Study in Scarlet"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("And Then There Were None"));

    }

    @Test
    public void testGetBookFromID() throws Exception {
        Book book = new Book(1,"A Study in Scarlet","Conan Doyle", "Murder Story","987654321");

        when(bookService.findById(1)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("A Study in Scarlet"));

    }

    @Test
    public void testAddBook() throws Exception {
        Book book = new Book(0,"A Study in Scarlet","Conan Doyle", "Murder Story","987654321");

        when(bookService.save(book)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("A Study in Scarlet"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Conan Doyle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("987654321"));
    }


    @Test
    public void testUpdateBook() throws Exception {
        Book book = new Book(1,"A Study in Scarlet","Conan Doyle", "Murder Story","987654321");
        when(bookService.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("A Study in Scarlet"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Conan Doyle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("987654321"));

        Mockito.verify(bookService).save(book);
    }



    @Test
    public void testDeleteBook() throws Exception {
        int bookId = 1;
        Book book = new Book(bookId,"A Study in Scarlet","Conan Doyle", "Murder Story","987654321");
        when(bookService.findById(bookId)).thenReturn(book);
        doNothing().when(bookService).deleteById(bookId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{book_id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Deleted book id - " + bookId));

        Mockito.verify(bookService).findById(bookId);
        Mockito.verify(bookService).deleteById(bookId);
    }

}
