package com.rafaelsousa.api.repository;

import com.rafaelsousa.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByISBN(String isbn);

}
