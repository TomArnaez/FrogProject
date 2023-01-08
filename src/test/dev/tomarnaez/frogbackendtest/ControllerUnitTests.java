package dev.tomarnaez.frogbackendtest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(locations= "classpath:test.properties")
@WebMvcTest(BookController.class)
class ControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @Test
    public void get_BookDoesntExist_Returns404() throws Exception {
        Mockito.doThrow(new BookNotFoundException()).when(bookService).getBook(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void get_AllBooks_returnsOkWithListOfBooks() throws Exception {
        List<Book> bookList = new ArrayList<>();
        Book book1 = new Book("1852", "Book1", "Author1", 1990);
        Book book2 = new Book("1862", "Book2", "Author2", 1991);

        bookList.add(book1);
        bookList.add(book2);

        // Mocking out the book service
        Mockito.when(bookService.getBooks()).thenReturn(bookList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].isbn", is("1852")))
                .andExpect(jsonPath("$[1].isbn", is("1862")))
                .andExpect(jsonPath("$[0].author", is("Author1")))
                .andExpect(jsonPath("$[1].author", is("Author2")));
    }

    @Test
    public void post_createsNewBook_andReturnsObjWith201() throws Exception {
        Book book = new Book("978-3-16-148410-0", "Harry Potter", "JK Rowling", 1997);

        Mockito.when(bookService.addBook(Mockito.any(Book.class))).thenReturn(book);

        // Build post request with book object payload
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(book));

        mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.isbn", is("978-3-16-148410-0")))
                .andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(book)));
    }

    @Test
    public void post_submitsInvalidBook_WithEmptyTitle_Returns400() throws Exception {
        Book book = new Book("972-3-16-149525-0", "", "Author", 1990);

        String bookJsonString = this.mapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(bookJsonString)).andExpect(status().isBadRequest());
    }

    @Test
    public void put_updatesAndReturnsUpdatedObjWith202() throws Exception {
        Book book = new Book("972-3-16-149525-0", "", "Author", 1990);

        Mockito.when(bookService.updateBook(1L, book)).thenReturn(book);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/api/books/1", book).contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsString(book));

        mockMvc.perform(builder).andExpect(status().isOk());
    }
        @Test
    public void delete_deleteBook_Returns204Status() throws Exception {
        Long id = 1L;

        BookService serviceSpy = Mockito.spy(bookService);
        Mockito.doNothing().when(serviceSpy).deleteBook(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBook(id);
    }

}
