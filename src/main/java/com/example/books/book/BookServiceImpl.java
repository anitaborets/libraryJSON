package com.example.books.book;

import com.example.books.models.Book;
import com.example.books.models.Person;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.books.book.BookState.FREE;
import static com.example.books.utils.Constants.BOOKING_PERIOD;
import static com.example.books.book.BookState.RESERVED;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll(boolean sortByYear) {
        List books;

        try {
            if (sortByYear)
                books = bookRepository.findAll();
            else
                books = bookRepository.findAll(Sort.by("bookName"));
        } catch (Exception e) {
            books = Collections.EMPTY_LIST;
        }
        return books;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> findAllWithPagination(Integer page, Integer size) {
        if (page >= 0 && size >= 0) {
            Pageable pageable = PageRequest.of(page, size,Sort.by("bookName"));
            return bookRepository.findAll(pageable);
        } else {
            return bookRepository.findAll(PageRequest.of(1, 3,Sort.by("bookName")));
        }
    }

    @Override
    @Transactional
    public void bookBook(Book book) {
        Book bookForBooking = new Book();
        Optional<Book> optional = bookRepository.findById((long) book.getId());
        if (optional.isPresent()) {
            bookForBooking = optional.get();
            bookForBooking.setBookState(RESERVED);
            bookForBooking.setOwner(book.getOwner());

            //prepojenie z 2.strany
            book.getOwner().getPersonCard().add(bookForBooking);

            bookForBooking.setBookingDate(Date.valueOf(LocalDate.now()));
            bookRepository.save(bookForBooking);
            log.warn("Book was booking, book id: " + book.getId() + ", user id:" + book.getOwner().getId());
        }
    }

    @Override
    @Transactional
    public void returnBook(Long bookId) {
        Book returningBook = new Book();
        Optional<Book> optional = bookRepository.findById(bookId);
        if (optional.isPresent()) {
            returningBook = optional.get();
            returningBook.setOwner(null);
            returningBook.setBookingDate(null);
            returningBook.setReturnDate(null);
            returningBook.setBookState(FREE);
            log.warn("Book was return, id: " + returningBook.getId());
        }

        bookRepository.save(returningBook);
    }

    @Override
    @Transactional
    public void addBook(Book book) {

        bookRepository.findByISDN(book.getISDN()).orElseGet(() -> bookRepository.save(book));
        log.warn("book was added" + book.getBookName());
    }

    @Override
    @Transactional(readOnly = true)
    public Book showFormForUpdateBook(Long id) {
        Optional<Book> bookForUpdate = bookRepository.findById((long) id);
        return bookForUpdate.orElse(null);

    }

    @Override
    @Transactional
    public void update(Book book) {
        int idTemp = book.getId();
        book.setId(idTemp);
        book.setOwner(bookRepository.findById((long) idTemp).get().getOwner());
        if (book.getBookState().equals(FREE) && book.getOwner() == null) {
            try {
                bookRepository.save(book);
            } catch (Exception e) {
                log.error("Book" + book.getId() + "was not updated" + e.getMessage());
            }
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        boolean isDeleted = false;
        Optional<Book> bookForDelete = bookRepository.findById(id);
        if (bookForDelete.isPresent() && !bookForDelete.get().getBookState().equals(RESERVED) && bookForDelete.get().getOwner() == null) {
            try {
                bookRepository.deleteById((long) id);
                isDeleted = true;
            } catch (Exception e) {
                log.error("Book" + id + "was not deleted" + e.getMessage());
            }
        }
        return isDeleted;
    }

    @Override
    public String getDateOfReturn(Book book) {
        Date returnDate = Date.valueOf(book.getBookingDate().toLocalDate().plusDays(BOOKING_PERIOD));
        book.setReturnDate(returnDate);
        return returnDate.toString();
    }
}
