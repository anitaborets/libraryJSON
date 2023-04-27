package com.example.books.book;

import com.example.books.models.Book;
import com.example.books.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    List<Book> findAll(boolean sortByYear);

    Page<Book> findAllWithPagination(Integer page, Integer size);

    List<Book> find(String title);

    void addBook(Book book);

    Book showFormForUpdateBook(Long id);

    void update(Book book);

    boolean deleteById(Long id);

    void bookBook(Book book);

    void returnBook(Long bookId);

    String getDateOfReturn(Book book);

    List<Book> overdue();



}
