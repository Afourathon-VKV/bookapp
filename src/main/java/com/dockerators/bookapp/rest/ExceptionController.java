package com.dockerators.bookapp.rest;

import com.dockerators.bookapp.exception.BookAlreadyExistsException;
import com.dockerators.bookapp.exception.BookNotFoundException;
import com.dockerators.bookapp.exception.NullFieldsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@CrossOrigin(origins = "*")
public class ExceptionController {

    @ExceptionHandler(value = NullFieldsException.class)
    public ResponseEntity<Object> nullFieldsInBody(NullFieldsException nullFieldsException) {
        return new ResponseEntity<>("Null Fields in Body.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<Object> bookNotFound(BookNotFoundException bookNotFoundException){
        return new ResponseEntity<>("A book with that id/code not found.",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BookAlreadyExistsException.class)
    public ResponseEntity<Object> bookAlreadyExists(BookAlreadyExistsException bookAlreadyExistsException){
        return new ResponseEntity<>("A Book with that code already exists.",HttpStatus.NOT_FOUND);
    }
}
