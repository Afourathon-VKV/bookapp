package com.dockerators.bookapp.dao;

import com.dockerators.bookapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository <Book, Integer> {
}
