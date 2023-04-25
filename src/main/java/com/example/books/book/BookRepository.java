package com.example.books.book;

import com.example.books.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Transactional(readOnly = true)
    Optional<Book> findByISDN(String isdn);

    @Transactional(readOnly = true)
    List<Book> findAll();

    // @Transactional(readOnly = true)
    // List<Book> findByTitleStartingWith(String title);


}
