package com.example.books.book;

import com.example.books.models.Book;
import com.example.books.models.Person;
import com.example.books.client.PersonRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.books.utils.Constants.BOOK_IS_FREE;

@Controller
@Slf4j
@Scope(WebApplicationContext.SCOPE_SESSION)
public class BookController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookServiceImpl bookServiceImpl;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    BookValidator bookValidator;

    @GetMapping("/books")
    @Transactional(readOnly = true)
    public String index(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @RequestParam(value = "page", required = false) @Min(0) Integer page,
                        @RequestParam(value = "size", required = false, defaultValue = "5") @Min(0) @Max(100) Integer size,
                        @RequestParam(value = "sort", required = false) boolean sortByYear) {

        int totalPages = (int) Math.ceil(1.0 * bookRepository.count() / size);
        if (totalPages > 1) {
            List<Integer> pagenumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", pagenumbers);
        }

        model.addAttribute("current_page", page);
        if (page == null || size == null) {
            model.addAttribute("books", bookServiceImpl.findAllWithPagination(0, size));
            //model.addAttribute("books", bookServiceImpl.findAll(sortByYear));
        } else {
            model.addAttribute("books", bookServiceImpl.findAllWithPagination(page - 1, size));
        }
        return "books";
    }

    @GetMapping("/search")
    @Transactional(readOnly = true)
    public String index(Model model, String query, RedirectAttributes redirAttrs) {
        List<Book> result = bookServiceImpl.find(query);
        if (!result.isEmpty()) {
            model.addAttribute("result", result);
            return "search";

        } else {
            redirAttrs.addFlashAttribute("error", "Book is not founded");
            model.addAttribute("books", bookServiceImpl.findAllWithPagination(0, 5));
            return "redirect:/books";
        }
    }

    @GetMapping("/add")
    @Transactional
    public String newBook(@ModelAttribute("book") Book book) {
        return "add";
    }

    @PostMapping("/add")
    @Transactional
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "add";
        }
        bookServiceImpl.addBook(book);
        return "redirect:/books";
    }

    @Transactional
    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam Long id, RedirectAttributes redirAttrs) {
        boolean isDeleted = bookServiceImpl.deleteById(id);
        if (isDeleted) {
            redirAttrs.addFlashAttribute("success", "Book was deleted");
        } else {
            redirAttrs.addFlashAttribute("error", "Book is reserved, you can not delete it!");
        }
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
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "updateBook";
        }
        bookServiceImpl.update(book);
        return "redirect:/books";
    }

    @GetMapping("/bookCard")
    @Transactional(readOnly = true)
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
    @Transactional
    public String booking(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @ModelAttribute("person") Person person) {
        bookServiceImpl.bookBook(book);
        return "redirect:/books";
    }

    public String getFreeStatus() {
        return BOOK_IS_FREE;
    }
}
