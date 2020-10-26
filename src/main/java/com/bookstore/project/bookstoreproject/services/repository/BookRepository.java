package com.bookstore.project.bookstoreproject.services.repository;

import com.bookstore.project.bookstoreproject.entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Books, String> {
    public Books findByIsbn(String isbn);
}
