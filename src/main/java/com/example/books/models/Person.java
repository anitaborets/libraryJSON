package com.example.books.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;
import java.util.Set;

@Entity
@Table
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "surName")
    private String surName;

    @Min(value = 1920, message = "Age should be greater than 1920")
    @Max(value = 2120)
    @Column(name = "yearOfBorn")
    private int yearOfBorn;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "owner")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    //pre cascading persist mame pouzivat nie save ale persist v repository
    private Set<Book> personCard;

    public Person() {
    }

    public Set<Book> getPersonCard() {
        return personCard;
    }

    public Person(int id, String name, String surName, int yearOfBorn, String email) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.yearOfBorn = yearOfBorn;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getYearOfBorn() {
        return yearOfBorn;
    }

    public void setYearOfBorn(int yearOfBorn) {
        this.yearOfBorn = yearOfBorn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        StringBuilder builder = new StringBuilder();
        builder.append(name)
                .append(" ")
                .append(surName);
        return builder.toString();
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", yearOfBorn=" + yearOfBorn +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return new EqualsBuilder().append(getName(), person.getName()).append(getSurName(), person.getSurName()).append(getEmail(), person.getEmail()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getName()).append(getSurName()).append(getEmail()).toHashCode();
    }
}
