package com.example.books.book;

import com.example.books.book.BookRepository;
import com.example.books.client.PersonRepository;
import com.example.books.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

//best practice - validator for every entity
@Component
public class BookValidator implements Validator {

    @Autowired
    BookRepository bookRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        Optional<Book> optional = bookRepository.findByISDN(book.getISDN());
        //if isdn exists - not save
        if (optional.isPresent()) {
            errors.rejectValue("ISDN", "", "ISDN is already exists");
        }
    }
}
