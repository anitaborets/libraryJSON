package com.example.books.book;

import com.example.books.models.Book;
import com.example.books.models.Person;
import com.example.books.client.PersonRepository;
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

import java.util.List;
import java.util.Optional;

import static com.example.books.utils.Constants.BOOK_IS_FREE;

@Controller
@Slf4j
@Scope(WebApplicationContext.SCOPE_SESSION)
public class BookController {
    public int bookId = 0;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookServiceImpl bookServiceImpl;
    @Autowired
    PersonRepository personRepository;

    @GetMapping("/books")
    public String index(Model model) {
        List<Book> books = bookServiceImpl.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/add")
    public String newBook(@ModelAttribute("book") Book book) {
        return "add";
    }

    @PostMapping("/add")
    @Transactional
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add";
        }
        bookServiceImpl.addBook(book);
        return "redirect:/books";
    }

    @Transactional
    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam Long id) {
        //TODO alert You can not delete reserved book
        boolean isDeleted = bookServiceImpl.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/updateBook")
    @Transactional(readOnly = true)
    public String edit(Model model, @RequestParam int id) {
        Book book = bookServiceImpl.showFormForUpdateBook((long) id);
        model.addAttribute("book", book);
        return "updateBook";
    }

    @PostMapping("/updateBook")
    @Transactional
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "updateBook";
        }
        bookServiceImpl.update(book);
        return "redirect:/books";
    }

    @GetMapping("/bookCard")
    public String view(Model model, @RequestParam Long id) {
        Book book = new Book();
        String owner = " ";
        List<Person> users = personRepository.findAll();
        try {
            Optional<Book> bookOptional = bookRepository.findById((long) id);
            if (bookOptional.isPresent()) {
                book = bookOptional.get();
                owner = book.getOwner().getFullname();
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        model.addAttribute("users", users);
        model.addAttribute("owner", owner);
        model.addAttribute("book", book);

        return "bookCard";
    }

    @PostMapping("/assign")
    public String booking(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookServiceImpl.bookBook(book);
        return "redirect:/books";
    }

    public String getFreeStatus() {
        return BOOK_IS_FREE;
    }
}
