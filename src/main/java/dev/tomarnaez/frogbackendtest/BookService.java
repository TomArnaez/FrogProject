package dev.tomarnaez.frogbackendtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    // CREATE
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
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
        Book book = bookRepository.findById(bookId).get();
        book.setISBN(bookDetails.getISBN());
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublishYear(bookDetails.getPublishYear());

        return bookRepository.save(book);
    }
}
