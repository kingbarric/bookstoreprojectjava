package com.bookstore.project.bookstoreproject.services.services;

import com.bookstore.project.bookstoreproject.entities.Books;
import com.bookstore.project.bookstoreproject.entities.Messages;
import com.bookstore.project.bookstoreproject.services.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // create a method that saves the book

    public ResponseEntity saveBook(Books book){
        Messages messages = new Messages();
        if(book.getIsbn().isEmpty()){
            messages.setCode(1);
            messages.setMessagae("ISBN can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
        }
        if(book.getTitle().isEmpty()){
            messages.setCode(1);
            messages.setMessagae("Book Title can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
        }
        if( book.getCategory().isEmpty()){
            messages.setCode(1);
            messages.setMessagae("Book Category can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
        }
        if(book.getPublisher().isEmpty()){
            messages.setCode(1);
            messages.setMessagae("Publisher can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
        }
        if(StringUtils.isEmpty(book.getQuantity()+"")){
            messages.setCode(1);
            messages.setMessagae("Book Quantity can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
        }

        bookRepository.save(book);
        messages.setCode(0);
        messages.setMessagae("Book created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(messages);
    }

    public ResponseEntity updateBook(Books book){
        Messages messages = new Messages();
        if(book.getIsbn().isEmpty()){
            messages.setCode(1);
            messages.setMessagae("ISBN can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
        }
        if(book.getTitle().isEmpty()){
            messages.setCode(1);
            messages.setMessagae("Book Title can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
        }
        if( book.getCategory().isEmpty()){
            messages.setCode(1);
            messages.setMessagae("Book Category can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
        }
        if(book.getPublisher().isEmpty()){
            messages.setCode(1);
            messages.setMessagae("Publisher can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
        }
        if(StringUtils.isEmpty(book.getQuantity()+"")){
            messages.setCode(1);
            messages.setMessagae("Book Quantity can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
        }
        Books updatedBook = bookRepository.findByIsbn(book.getIsbn());
        if(updatedBook ==null){
            messages.setCode(-3);
            messages.setMessagae("No such book to edit");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(messages);
        }
        bookRepository.save(updatedBook);
        messages.setCode(0);
        messages.setMessagae("Book created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(messages);
    }


    public List<Books> findAll(){
        return this.bookRepository.findAll();
    }

    public Books findByISBN(String isbn){
        try{
          return   this.bookRepository.findByIsbn(isbn);
        }catch (Exception e){
            System.out.println();
            return null;
        }
    }


    public List<Books> searchBooks(@RequestBody Books search) {
        System.out.println(search);
        String sql= "SELECT * FROM books where ";

        sql +=" category ='"+search.getCategory()+"' ";

        sql +=" OR publisher ='"+search.getPublisher()+"' ";
        if(!StringUtils.isEmpty(search.getLowPrice()) && !StringUtils.isEmpty(search.getHighPrice()) ){
            sql +=" OR   price BETWEEN  "+search.getLowPrice()+"  AND "+search.getHighPrice()+" ";
        }
        System.out.println(sql);
        RowMapper<Books> rowMapper = new BeanPropertyRowMapper<>(Books.class);
        return jdbcTemplate.query(sql, rowMapper);
    }
}
