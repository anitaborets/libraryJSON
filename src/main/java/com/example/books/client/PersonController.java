package com.example.books.client;

import com.example.books.book.BookServiceImpl;
import com.example.books.models.Book;
import com.example.books.models.Person;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.books.utils.Constants.NO_BOOKS;

@Controller
@Slf4j
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PersonController {
//    @Autowired
//    PersonRepository personRepository;
//    @Autowired
//    PersonValidator personValidator;
//    @Autowired
//    PersonServiceImpl personService;
//    @Autowired
//    BookServiceImpl bookService;

    @GetMapping("/users")
    @Transactional(readOnly = true)
    public String index(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @RequestParam(value = "page", required = false) @Min(0) Integer page,
                        @RequestParam(value = "size", required = false, defaultValue = "5") @Min(0) @Max(100) Integer size) {

        //int totalPages = (int) Math.ceil(1.0 * personRepository.count() / size);
        int totalPages = 10;
        if (totalPages > 1) {
            List<Integer> pagenumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", pagenumbers);
        }
        model.addAttribute("current_page", page);
        if (page == null || size == null) {
           // model.addAttribute("users", personService.findAllWithPagination(0, size));
        } else {
            //model.addAttribute("users", personService.findAllWithPagination(page - 1, size));
        }
        return "users";
    }

    @PostMapping("/registration")
    @Transactional
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
       // personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        //personService.addPerson(person);
        log.warn("person was added" + person.getId());
        return "redirect:/users";
    }

    @Transactional
    @GetMapping("/delete")
    public String deleteUser(@RequestParam Long id, RedirectAttributes redirAttrs) {
        //boolean isDeleted = personService.deletePersonById(id);
        //if (isDeleted) {
//            redirAttrs.addFlashAttribute("success", "Person was deleted");
//        } else {
//            redirAttrs.addFlashAttribute("error", "Person has books, you can not delete it!");
//        }
        return "redirect:/users";
    }

    @GetMapping("/update")
    public String edit(Model model, @RequestParam int id) {
//        Person person = personService.showFormForUpdatePerson((long) id);
//        model.addAttribute("person", person);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
      //  personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "update";
        }
       // personService.updatePerson(person);
        return "redirect:/users";
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam Long id) {
        Person person = null;
        Set<Book> books = Collections.EMPTY_SET;

 //       try {
//            Optional<Person> personFromDB = personRepository.findById(id);
//            if (personFromDB.isPresent()) {
//                person = personFromDB.get();
//                books = person.getPersonCard();
//            }
//        } catch (Exception e) {
//            log.error("no books" + e.getMessage());
//            model.addAttribute("books", books);
//            model.addAttribute("person", person);
//        }
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
        //bookService.returnBook(id);
        return "redirect:/books";
    }

    @GetMapping("/debtors")
    @Transactional(readOnly = true)
    public String debtors(Model model,RedirectAttributes redirAttrs) {
//        if (!bookService.overdue().isEmpty()) {
//            System.out.println(bookService.overdue());
//            List<Person> debtors = new ArrayList<>();
//            for (Book book : bookService.overdue()) {
//                debtors.add(book.getOwner());
//            }
//            model.addAttribute("debtors", debtors);
            return "debtors";
      //  }
       // redirAttrs.addFlashAttribute("error", "There are no any debtor");
      //  return "redirect:/users";
    }

//    public String getNoBooks() {
//        return NO_BOOKS;
//    }

}
