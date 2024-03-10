package com.example.books.book;

import com.example.books.DTO.BookJSONDTO;
import com.example.books.DTO.Borrowed;
import com.example.books.DTO.Root;
import com.example.books.entities.BookEntity;
import com.example.books.entities.PersonEntity;
import com.example.books.models.Author;
import com.example.books.models.Book;
import com.example.books.utils.FileWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@Slf4j
public class BookServiceJSONImpl implements BookService {

    BookRepositoryJSON bookRepositoryJSON = new BookRepositoryJSON();



    public Set<BookEntity> library() throws IOException {
        Set<BookEntity> library = new HashSet<>();
        ArrayList<BookJSONDTO> allBooks = bookRepositoryJSON.getAllFromFile();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        for (BookJSONDTO book : allBooks) {
            BookEntity bookEntity = new BookEntity(book.getName(), new Author(book.getAuthor()));
            if (book.getBorrowed() != null) {
                bookEntity.setBorrowed(true);
            }

            library.add(bookEntity);
        }

        return library;

    }



    public HashMap<BookEntity, Pair<PersonEntity, LocalDate>> getReservations() throws IOException {
        HashMap<BookEntity, Pair<PersonEntity, LocalDate>> reservations = new HashMap<>();

        ArrayList<BookJSONDTO> allBooks = bookRepositoryJSON.getAllFromFile();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        for (BookJSONDTO book : allBooks) {
            BookEntity bookEntity = new BookEntity(book.getName(), new Author(book.getAuthor()));
            if (book.getBorrowed() != null) {
                bookEntity.setBorrowed(true);

                PersonEntity person = new PersonEntity(book.getBorrowed().getFirstName(), book.getBorrowed().getLastName());
                LocalDate dateFrom = book.getBorrowed().getFrom().isEmpty() ? null : LocalDate.parse(book.getBorrowed().getFrom(), formatter);

                if (dateFrom != null) {
                    Pair<PersonEntity, LocalDate> pair = Pair.of(person, dateFrom);
                    reservations.putIfAbsent(bookEntity, pair);
                }

            }

        }
        for (Map.Entry<BookEntity, Pair<PersonEntity, LocalDate>> entry : reservations.entrySet()) {
            log.info(entry.getKey() + " : " + entry.getValue());
        }

        return reservations;
    }

    @Override
    public List<Book> findAll(boolean sortByYear) {
        return null;
    }

    @Override
    public Page<Book> findAllWithPagination(Integer page, Integer size) {
        return null;
    }

    @Override
    public List<Book> find(String title) {
        return null;
    }

    @Override
    public void addBook(Book book) throws IOException {
        Root root = null;
        FileWorker fileWorker = new FileWorker();

        BookJSONDTO bookJSONDTO = new BookJSONDTO(book.getBookName(), book.getAuthor(), new Borrowed(book.getOwner().getSurName(), book.getOwner().getName(), book.getReturnDate().toString()));

        ArrayList<BookJSONDTO> allBooks = bookRepositoryJSON.getAllFromFile();
        allBooks.add(new BookJSONDTO("The Great Gatsby", "F. Scott Fitzgerald", null));
        allBooks.add(bookJSONDTO);

        bookRepositoryJSON.saveAllFromFile(allBooks);
    }

    @Override
    public Book showFormForUpdateBook(Long id) {
        return null;
    }

    @Override
    public void update(Book book) {

    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public void bookBook(Book book) {

    }

    @Override
    public void returnBook(Long bookId) {

    }

    @Override
    public String getDateOfReturn(Book book) {
        return null;
    }

    @Override
    public List<Book> overdue() {
        return null;
    }
}
