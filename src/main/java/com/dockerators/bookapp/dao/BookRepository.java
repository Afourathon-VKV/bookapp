package com.dockerators.bookapp.dao;

import com.dockerators.bookapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository <Book, Integer> {
    Optional<Book> findByTitle(String title);
}
