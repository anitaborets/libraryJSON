package com.example.books.book;

import com.example.books.models.Book;
import com.example.books.models.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    List<Book> findAll();

    void addBook(Book book);
    Book showFormForUpdateBook(Long id);
    void update(Book book);

    boolean deleteById(Long id);

    void bookBook(Book book);

    void returnBook(Long bookId);

    String getDateOfReturn(Book book);


}
