package com.rafaelsousa.api.service;

import com.rafaelsousa.api.domain.ValidationMessage;
import com.rafaelsousa.api.dto.request.BookRequestDTO;
import com.rafaelsousa.api.dto.response.BookResponseDTO;
import com.rafaelsousa.api.exceptions.InvalidParameterException;
import com.rafaelsousa.api.model.Book;
import com.rafaelsousa.api.repository.BooksRepository;
import com.rafaelsousa.api.service.comum.ComumService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BooksService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private ComumService comumService;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity save(BookRequestDTO dto) {
        Book book = comumService.getBook(dto);

        booksRepository.save(book);

        return ResponseEntity.ok().build();
    }

    public List<Book> findAll() {
        List<Book> books = booksRepository.findAll();
        System.out.println("Mocked data: " + books);

        return books;
    }

    public ResponseEntity<BookResponseDTO> findBookByISBN(String isbn) {
        if (Objects.isNull(isbn) || isbn.trim().isEmpty()) {
            throw new InvalidParameterException(ValidationMessage.PARA_CONSULTAR_DEVE_SER_INFORMADO_O_ISBN_DO_LIVRO.getDescription());
        }

        Optional<Book> bookOptional = booksRepository.findBookByISBN(isbn);
        if (bookOptional.isPresent()) {
            return ResponseEntity.ok().body(modelMapper.map(bookOptional.get(), BookResponseDTO.class));
        }

        return ResponseEntity.noContent().build();
    }

    public void delete(Book book) {
        booksRepository.delete(book);
    }

}
