package dev.tomarnaez.frogbackendtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    BookService bookService;

    @RequestMapping(value="/books", method= RequestMethod.POST)
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @RequestMapping(value="/books/{bookId}", method=RequestMethod.GET)
    public Book getBook(@PathVariable(value="bookId") Long id) {
        return bookService.getBook(id);
    }

    @RequestMapping(value="/books", method=RequestMethod.GET)
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @RequestMapping(value="/books/{bookId}", method=RequestMethod.PUT)
    public Book updateBook(@PathVariable(value="bookId") Long id, @RequestBody Book bookDetails) {
        return bookService.updateBook(id, bookDetails);
    }

    @RequestMapping(value="/books/{bookId}", method=RequestMethod.DELETE)
    public void deleteBook(@PathVariable(value = "bookId") Long id) {
        bookService.deleteBook(id);
    }

}
