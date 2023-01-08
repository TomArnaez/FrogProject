package dev.tomarnaez.frogbackendtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    // CREATE
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException());
    }
    // READ
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    // DELETE
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    // UPDATE
    public Book updateBook(Long bookId, Book bookDetails) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException());
        book.setISBN(bookDetails.getISBN());
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublishYear(bookDetails.getPublishYear());

        return bookRepository.save(book);
    }
}
