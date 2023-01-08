package dev.tomarnaez.frogbackendtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// Reset database after each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations= "classpath:test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BookService bookService;

    @Test
    void post_WorksThroughAllLayers() throws Exception {
        Book book = new Book("195-52958-252", "Book Title", "Thomas", 1997);

        ResultActions response = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn", is(book.getISBN())))
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.author", is(book.getAuthor())))
                .andExpect(jsonPath("$.publishYear", is(book.getPublishYear())));
    }

    @Test
    void post_MultipleBooks_ReturnsList() throws Exception {
        Book book1 = new Book("1852", "Book1", "Author1", 1990);
        Book book2 = new Book("1862", "Book2", "Author2", 1991);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book1)))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book2)))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].isbn", is("1852")))
                .andExpect(jsonPath("$[1].isbn", is("1862")))
                .andExpect(jsonPath("$[0].author", is("Author1")))
                .andExpect(jsonPath("$[1].author", is("Author2")));
    }

    // test for GET book by id - negative scenario
    @Test
    public void whenGetBook_givenValidBookId_thenReturnBook() throws Exception {
        long bookId = 1L;
        Book book = new Book("195-52958-252", "Book Title", "Thomas", 1997);

        bookService.addBook(book);

        ResultActions response = mockMvc.perform(get("/api/books/{id}", bookId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(book)));
    }

    // test for GET book by id - negative scenario
    @Test
    public void whenGetBook_givenInvalidBookId_thenReturn404() throws Exception {
        long bookId = 2L;
        Book book = new Book("195-52958-252", "Book Title", "Thomas", 1997);

        bookService.addBook(book);

        ResultActions response = mockMvc.perform(get("/api/books/{id}", bookId));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    // test for update book REST API - positive scenario
    @Test
    public void whenUpdateBook_givenValidId_thenReturnUpdatedBook() throws Exception {
        Book savedBook = new Book("195-52958-252", "Book Title", "Thomas", 1997);

        bookService.addBook(savedBook);

        Book updatedBook = new Book("195-52958-252", "New Book Title", "Paul", 1994);

        ResultActions response = mockMvc.perform(put("/api/books/{id}", savedBook.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedBook)));


        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.isbn", is(updatedBook.getISBN())))
                .andExpect(jsonPath("$.title", is(updatedBook.getTitle())))
                .andExpect(jsonPath("$.author", is(updatedBook.getAuthor())))
                .andExpect(jsonPath("$.publishYear", is(updatedBook.getPublishYear())));
    }

    // test for update book REST API - negative scenario
    @Test
    public void whenUpdateBook_givenInvalidId_thenReturn404() throws Exception {
        Long bookId = 2L;
        Book savedBook = new Book("195-52958-252", "Book Title", "Thomas", 1997);

        bookService.addBook(savedBook);

        Book updatedBook = new Book("195-52958-252", "New Book Title", "Paul", 1994);

        ResultActions response = mockMvc.perform(put("/api/books/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedBook)));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    // test for delete book - status code
    @Test
    void givenBookId_whenDeleteBook_thenReturn200() throws Exception {
        Book book = new Book("195-52958-252", "Book Title", "Thomas", 1997);

        bookService.addBook(book);

        ResultActions response = mockMvc.perform(delete("/api/books/{id}", book.getId()));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }

}
