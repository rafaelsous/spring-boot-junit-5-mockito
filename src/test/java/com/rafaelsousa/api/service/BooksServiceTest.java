package com.rafaelsousa.api.service;

import com.rafaelsousa.api.domain.ValidationMessage;
import com.rafaelsousa.api.dto.request.BookRequestDTO;
import com.rafaelsousa.api.dto.response.BookResponseDTO;
import com.rafaelsousa.api.exceptions.InvalidParameterException;
import com.rafaelsousa.api.model.Book;
import com.rafaelsousa.api.repository.BooksRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BooksServiceTest {

    @MockBean
    private BooksRepository booksRepository;

    @Autowired
    private BooksService booksService;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void save() {
        Book book = new Book(1L, "A Cabana", "Rafael Sousa", "AB1123");

        when(booksRepository.save(book)).thenReturn(book);
        assertEquals(ResponseEntity.ok().build(), booksService.save(modelMapper.map(book, BookRequestDTO.class)));
    }

    @Test
    public void findAll() {
        Book book1 = new Book(1L, "A Cabana", "Rafael Sousa", "AB1123");
        Book book2 = new Book(1L, "Harry Potter", "Tatiane Araujo", "SM5571");
        Book book3 = new Book(1L, "O Mágico de Oz", "Elisa Araujo Sousa", "BB2303");

        when(booksRepository.findAll()).thenReturn(Stream.of(book1, book2, book3).collect(Collectors.toList()));
        assertEquals(3, booksService.findAll().size());
    }

    @Test
    public void findByISBN() {
        // Testanto ISBN nulo
        InvalidParameterException exception = assertThrows(InvalidParameterException.class,
                () -> booksService.findBookByISBN(null));
        assertEquals(ValidationMessage.PARA_CONSULTAR_DEVE_SER_INFORMADO_O_ISBN_DO_LIVRO.getDescription(), exception.getMessage());

        // Testanto ISBN vazio
        exception = assertThrows(InvalidParameterException.class,
                () -> booksService.findBookByISBN("  "));
        assertEquals(ValidationMessage.PARA_CONSULTAR_DEVE_SER_INFORMADO_O_ISBN_DO_LIVRO.getDescription(), exception.getMessage());

        // Testando um response NO_CONTENT
        when(booksRepository.findBookByISBN("AB1234")).thenReturn(Optional.empty());
        assertEquals(ResponseEntity.noContent().build(), booksService.findBookByISBN("AB1234"));

        // Testando um response OK
        Book book = new Book(1L, "A Cabana", "Rafael Sousa", "AB1123");
        when(booksRepository.findBookByISBN("AB1123")).thenReturn(Optional.of(book));
        assertEquals(ResponseEntity.ok().body(modelMapper.map(book, BookResponseDTO.class)), booksService.findBookByISBN("AB1123"));
    }

    @Test
    public void delete() {
        Book book = new Book(1L, "A Cabana", "Rafael Sousa", "AB1123");
        booksService.delete(book);
        verify(booksRepository, times(1)).delete(book);
    }

}
