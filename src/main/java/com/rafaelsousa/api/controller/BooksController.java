package com.rafaelsousa.api.controller;

import com.rafaelsousa.api.dto.request.BookRequestDTO;
import com.rafaelsousa.api.dto.response.BookResponseDTO;
import com.rafaelsousa.api.model.Book;
import com.rafaelsousa.api.repository.BooksRepository;
import com.rafaelsousa.api.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BooksController {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private BooksService booksService;

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid BookRequestDTO dto) {
        return booksService.save(dto);
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAll() {
        List<Book> bookList = booksRepository.findAll();

        return !bookList.isEmpty() ? ResponseEntity.ok().body(bookList) : ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/find")
    public ResponseEntity<BookResponseDTO> findByISBN(@RequestParam(name = "isbn", required = false, defaultValue = "") String isbn) {
        return booksService.findBookByISBN(isbn);
    }

}
