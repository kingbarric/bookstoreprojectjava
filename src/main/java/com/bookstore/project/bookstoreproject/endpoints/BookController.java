package com.bookstore.project.bookstoreproject.endpoints;

import com.bookstore.project.bookstoreproject.entities.Books;
import com.bookstore.project.bookstoreproject.services.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping(value = "/endpoint/savebook")
    public ResponseEntity saveBook(@RequestBody final Books book) {
        return bookService.saveBook(book);
    }

    @PutMapping(value = "endpoint/updatebook")
    public ResponseEntity updateBook(@RequestBody final Books book) {
        return bookService.updateBook(book);
    }

    @GetMapping(value = "/endpoint/findallbook")
    public ResponseEntity findAllBook() {
         return  ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping(value = "/endpoint/findbyisbn/{isbn}")
    public Books findById(@PathVariable String isbn){
        return bookService.findByISBN(isbn);
    }

    @DeleteMapping(value = "/endpoint/deletebook/{id}")
    public ResponseEntity delete(@PathVariable String id) {
     return   bookService.delete(id);

    }

    @PostMapping(value = "/endpoint/search")
    public List<Books> searchBooks(@RequestBody Books search) {
        return bookService.searchBooks(search);
    }
}
