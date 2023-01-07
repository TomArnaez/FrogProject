package dev.tomarnaez.frogbackendtest;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "books")
class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="ISBN")
    private String ISBN;

    @Column(name="title")
    private String title;

    @Column(name="author")
    private String author;

    @Column(name="publishYear")
    private Integer publishYear;

    public Book() {}

    public Book(String ISBN, String title, String author, int publishYear) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
    }

    public Long getId() {
        return this.id;
    }

    public String getISBN() {
        return this.ISBN;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public Integer getPublishYear() {
        return this.publishYear;
    }

    public void setISBN(String ISBN) { this.ISBN = ISBN; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishYear(Integer publish_year) {
        this.publishYear = publish_year;
    }

    @Override
    public boolean equals(Object o) {
        return true;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + this.id + ", title='" + this.title + "\'" + ", author='" + this.author + "\'" + "}";
    }
}
