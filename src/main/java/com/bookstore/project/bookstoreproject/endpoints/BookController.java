package com.bookstore.project.bookstoreproject.endpoints;

import com.bookstore.project.bookstoreproject.entities.Books;
import com.bookstore.project.bookstoreproject.services.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping(value = "/savebook")
    public ResponseEntity saveBook(@RequestBody final Books book) {
        return bookService.saveBook(book);
    }

    @PutMapping(value = "/updatebook")
    public ResponseEntity updateBook(@RequestBody final Books book) {
        return bookService.updateBook(book);
    }

    @GetMapping(value = "/findallbook")
    public ResponseEntity findAllBook() {
         return  ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping(value = "/findbyisbn/{isbn}")
    public Books findById(@PathVariable String isbn){
        return bookService.findByISBN(isbn);
    }
}
