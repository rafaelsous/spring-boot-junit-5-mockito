package com.rafaelsousa.api.service.comum;

import com.rafaelsousa.api.dto.request.BookRequestDTO;
import com.rafaelsousa.api.model.Book;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComumService {

    @Autowired
    private ModelMapper modelMapper;

    public Book getBook(BookRequestDTO dto) {
        Book book;
        book = modelMapper.map(dto, Book.class);

        return book;
    }

}
