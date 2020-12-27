package com.rafaelsousa.api.repository;

import com.rafaelsousa.api.dto.request.BookRequestDTO;
import com.rafaelsousa.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByISBN(String isbn);

    @Query(value = "SELECT * FROM BOOK B "
            + "WHERE B.TITLE = :#{#dto.title} "
            + "AND B.AUTHOR = :#{#dto.author} "
            + "AND B.ISBN = :#{#dto.ISBN}", nativeQuery = true)
    Optional<Book> findByFields(BookRequestDTO dto);

}
