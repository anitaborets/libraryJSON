package com.example.books.models;

import com.example.books.book.BookState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Reference;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.Temporal;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "book_name")
    private String bookName;

    // @Embedded
    //private Author author;

    @Column(name = "author")
    private String author;
    @Column(name = "isdn")
    private String ISDN;

    @Min(value = 899, message = "Age should be greater than 1920")
    @Max(value = 2120)
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "booking_date")
    private Date bookingDate;

    @Column(name = "book_state")
    @Enumerated(EnumType.STRING)
    private BookState bookState = BookState.FREE;

    @Transient
    private java.util.Date returnDate;


    public long isOverdue() {
        if (returnDate != null) {
            LocalDate today = LocalDate.now();
            java.util.Date utilDate = new java.util.Date(returnDate.getTime());
            LocalDate temp = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Period difference = Period.between(temp,today);
            return difference.getDays();
        } else return 0;
    }

}
