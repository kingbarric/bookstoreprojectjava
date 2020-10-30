package com.bookstore.project.bookstoreproject.services.services;

import com.bookstore.project.bookstoreproject.entities.Books;
import com.bookstore.project.bookstoreproject.entities.Message;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // create a method that saves the book

    public ResponseEntity saveBook(Books book){
        Message message = new Message();
        if(book.getIsbn().isEmpty()){
            message.setCode(1);
            message.setMessage("ISBN can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if(book.getTitle().isEmpty()){
            message.setCode(1);
            message.setMessage("Book Title can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if( book.getCategory().isEmpty()){
            message.setCode(1);
            message.setMessage("Book Category can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if(book.getPublisher().isEmpty()){
            message.setCode(1);
            message.setMessage("Publisher can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if(StringUtils.isEmpty(book.getQuantity()+"")){
            message.setCode(1);
            message.setMessage("Book Quantity can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        bookRepository.save(book);
        message.setCode(0);
        message.setMessage("Book created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    public ResponseEntity updateBook(Books book){
        Message message = new Message();
        if(book.getIsbn().isEmpty()){
            message.setCode(1);
            message.setMessage("ISBN can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if(book.getTitle().isEmpty()){
            message.setCode(1);
            message.setMessage("Book Title can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if( book.getCategory().isEmpty()){
            message.setCode(1);
            message.setMessage("Book Category can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if(book.getPublisher().isEmpty()){
            message.setCode(1);
            message.setMessage("Publisher can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if(StringUtils.isEmpty(book.getQuantity()+"")){
            message.setCode(1);
            message.setMessage("Book Quantity can not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        Books updatedBook = bookRepository.findByIsbn(book.getIsbn());
        if(updatedBook ==null){
            message.setCode(-3);
            message.setMessage("No such book to edit");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        bookRepository.save(updatedBook);
        message.setCode(0);
        message.setMessage("Book Updated successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
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


    public ResponseEntity delete(@PathVariable String id) {
        Books b = bookRepository.findByIsbn(id);
        bookRepository.delete(b);
        Map m = new HashMap();
        m.put("code",0);
        m.put("message","The Book was successfully deleted");
        return ResponseEntity.status(HttpStatus.OK).body(m);
    }
}
