package com.bookvault.controller.repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.bookvault.entity.Book;
import com.bookvault.repository.BookRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldFilterBooksByAuthorUsingSpecification() {
        Book book1 = new Book();
        book1.setIsbn("978-1234567890");
        book1.setTitle("Atomic Habits");
        book1.setAuthor("James Clear");
        book1.setGenre("Self Help");
        book1.setTotalCopies(5);
        book1.setAvailableCopies(5);

        Book book2 = new Book();
        book2.setIsbn("978-1234567891");
        book2.setTitle("Clean Code");
        book2.setAuthor("Robert Martin");
        book2.setGenre("Programming");
        book2.setTotalCopies(3);
        book2.setAvailableCopies(3);

        bookRepository.saveAll(List.of(book1, book2));

        Specification<Book> spec = (root, query, cb) ->
                cb.like(cb.lower(root.get("author")), "%james%");

        List<Book> result = bookRepository.findAll(spec);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAuthor()).isEqualTo("James Clear");
    }
}