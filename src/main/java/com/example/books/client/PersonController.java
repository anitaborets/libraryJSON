package com.example.books.client;

import com.example.books.book.BookServiceImpl;
import com.example.books.models.Person;
import com.example.books.book.BookRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.books.utils.Constants.NO_BOOKS;

@Controller
@Slf4j
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PersonController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    PersonServiceImpl personService;
    @Autowired
    BookServiceImpl bookService;

    @GetMapping("/users")
    public String index(Model model) {
        List users = personService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @Transactional
    @PostMapping("/registration")
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        personService.addPerson(person);
        return "redirect:/users";
    }

    @Transactional
    @GetMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        boolean isDeleted = personService.deletePersonById(id);
        return "redirect:/users";
    }

    @GetMapping("/update")
    public String edit(Model model, @RequestParam int id) {
        Person person = personService.showFormForUpdatePerson((long) id);
        model.addAttribute("person", person);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update";
        }
        personService.updatePerson(person);
        return "redirect:/users";
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam Long id) {
        Person person = null;
        Set books = Collections.EMPTY_SET;

        try {
            Optional<Person> personFromDB = personRepository.findById(id);
            if (personFromDB.isPresent()) {
                person = personFromDB.get();
                books = person.getPersonCard();
            }
        } catch (Exception e) {
            log.info("no books" + e.getMessage());
            model.addAttribute("books", books);
            model.addAttribute("person", person);
        }
        model.addAttribute("books", books);
        model.addAttribute("person", person);
        return "view";
    }

    @GetMapping("/registration")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "registration";
    }

    @Transactional
    @GetMapping("/return")
    public String returnBook(@RequestParam Long id) {
        bookService.returnBook(id);
        return "redirect:/books";
    }

    public String getNoBooks() {
        return NO_BOOKS;
    }

}
