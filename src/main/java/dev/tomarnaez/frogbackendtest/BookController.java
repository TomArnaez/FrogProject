package dev.tomarnaez.frogbackendtest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    BookService bookService;

    @RequestMapping(value="/books", method= RequestMethod.POST)
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        bookService.addBook(book);
        return new ResponseEntity<Book>(book, HttpStatus.CREATED);
    }

    @RequestMapping(value="/books/{bookId}", method=RequestMethod.GET)
    public ResponseEntity<Book> getBook(@Valid @PathVariable(value="bookId") Long id) {
        Book book = bookService.getBook(id);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @RequestMapping(value="/books", method=RequestMethod.GET)
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.getBooks();
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    @RequestMapping(value="/books/{bookId}", method=RequestMethod.PUT)
    public ResponseEntity<Book> updateBook(@Valid @PathVariable(value="bookId") Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return new ResponseEntity<Book>(updatedBook, HttpStatus.OK);
    }

    @RequestMapping(value="/books/{bookId}", method=RequestMethod.DELETE)
    public ResponseEntity deleteBook(@PathVariable(value = "bookId") Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
