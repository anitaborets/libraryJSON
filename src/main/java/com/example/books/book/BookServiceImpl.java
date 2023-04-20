package com.example.books.book;

import com.example.books.models.Book;
import com.example.books.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        List books;
        try {
            books = bookRepository.findAll();
        } catch (Exception e) {
            books = Collections.EMPTY_LIST;
        }
        return books;
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
        }

        bookRepository.save(returningBook);
    }

    @Override
    @Transactional
    public void addBook(Book book) {
        bookRepository.findByISDN(book.getISDN()).orElseGet(() -> bookRepository.save(book));
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
                System.out.println(e.getMessage());
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
                System.out.println(e.getMessage());
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
