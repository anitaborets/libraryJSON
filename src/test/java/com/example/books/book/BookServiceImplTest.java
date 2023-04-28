package com.example.books.book;

import com.example.books.models.Book;
import com.example.books.models.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    BookRepository bookRepository;

    @Test
    void should_return_first_page() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("bookName"));
        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));

        var actual = bookService.findAllWithPagination(0, 5);
        assertNotNull(actual);
        assertInstanceOf(Page.class, actual);

        verify(bookRepository, times(1)).findAll(pageable);
    }

    @Test
    void should_find_book_by_id() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(new Book()));
        var actual = bookRepository.findById(1L);
        assertNotNull(actual);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void should_not_find_book_by_id() {
        when(bookRepository.findById(1L)).thenReturn(null);
        var actual = bookRepository.findById(1L);
        assertNull(actual);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void should_not_find_book_by_isdn() {
        when(bookRepository.findByISDN("some isdn")).thenReturn(Optional.empty());
        var actual = bookRepository.findByISDN("some isdn");
        assertInstanceOf(Optional.class, actual);
        verify(bookRepository, times(1)).findByISDN("some isdn");
    }

    @Test
    void should_delete_by_id() {
        Book book = new Book();
        book.setBookState(BookState.FREE);
        book.setOwner(null);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean isDeleted = bookService.deleteById(1L);

        assertTrue(isDeleted);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void should_delete_by_id_reserved_book() {
        Book book = new Book();
        book.setBookState(BookState.RESERVED);
        book.setOwner(null);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean isDeleted = bookService.deleteById(1L);

        assertFalse(isDeleted);
        verify(bookRepository, never()).deleteById(1L);
    }

    @Test
    void should_delete_by_id_book_has_owner() {
        Book book = new Book();
        book.setOwner(new Person());
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean isDeleted = bookService.deleteById(1L);

        assertFalse(isDeleted);
        verify(bookRepository, never()).deleteById(1L);
    }

    @Test
    void should_update_book() {
        Book book = new Book();
        book.setBookName("Test book");
        book.setAuthor("Author");
        book.setBookState(BookState.FREE);
        book.setISDN("isdn");
        book.setId(1);

        bookRepository.save(book);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        //update
        book.setBookName("new test name");
        bookService.update(book);
        verify(bookRepository, times(2)).save(book);
    }

}