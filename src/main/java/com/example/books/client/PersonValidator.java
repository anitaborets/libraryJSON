package com.example.books.client;

import com.example.books.book.BookRepository;
import com.example.books.models.Book;
import com.example.books.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

//best practice - validator for every entity
@Component
public class PersonValidator implements Validator {
    @Autowired(required = false)
    PersonRepository personRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> optional = personRepository.findByEmail(person.getEmail());
        //if email exists - not save
        if (optional.isPresent()) {
            errors.rejectValue("email", "", "email is already exists");
        }
    }
}
