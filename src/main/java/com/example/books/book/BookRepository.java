package com.example.books.book;

import com.example.books.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Transactional(readOnly = true)
    List<Book> findByBookNameStartingWithIgnoreCase(String title);

    @Transactional(readOnly = true)
    @Query(value = "SELECT * FROM BOOK b WHERE (CURRENT_DATE-b.booking_date)>14",
            nativeQuery = true)
    List<Book> findOverdue();

}
