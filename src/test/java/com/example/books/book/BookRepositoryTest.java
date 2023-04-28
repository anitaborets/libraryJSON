package com.example.books.book;

import com.example.books.models.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@Rollback
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;
    static Book book = new Book();

    @BeforeAll
    public static void before() {
        book.setBookName("Test book");
        book.setAuthor("Author");
        book.setBookState(BookState.FREE);
        book.setYear(1989);
        book.setISDN("isdn");
    }

    @Test
    void should_save() {
        Book book = new Book();
        book.setBookName("Test book");
        book.setAuthor("Author");
        book.setBookState(BookState.FREE);
        book.setYear(1989);
        book.setISDN("isdn1234");
        bookRepository.save(book);
        assertNotNull(bookRepository.findByISDN("isdn1234"));
    }

    @Test
    void should_update() {
        book.setBookName("new test name");
        bookRepository.save(book);
        Book afterUpdate = bookRepository.findById((long) book.getId()).get();
        assertEquals("new test name", afterUpdate.getBookName());
    }

    @Test
    void should_find_all() {
        bookRepository.save(book);
        List<Book> result = bookRepository.findAll();
        assertFalse(result.isEmpty());
        assertNotNull(result.get(0));
    }

}