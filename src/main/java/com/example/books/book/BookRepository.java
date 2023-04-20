package com.example.books.book;

import com.example.books.models.Book;
import com.example.books.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    @Transactional(readOnly = true)
    Optional<Book> findByISDN(String isdn);
}
