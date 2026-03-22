package com.bookvault.service;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bookvault.entity.Book;
import com.bookvault.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @CacheEvict(value = "books", allEntries = true)
    public Book create(Book book) {
        if (book == null) {
            throw new RuntimeException("Book cannot be null");
        }
        if (book.getIsbn() == null || book.getIsbn().isBlank()) {
            throw new RuntimeException("ISBN is required");
        }
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new RuntimeException("Title is required");
        }
        if (book.getAuthor() == null || book.getAuthor().isBlank()) {
            throw new RuntimeException("Author is required");
        }
        if (!book.getIsbn().matches("^978-\\d{10}$")) {
            throw new RuntimeException("ISBN must follow format 978-XXXXXXXXXX");
        }
        if (book.getTotalCopies() < 1) {
            throw new RuntimeException("Total copies must be at least 1");
        }
        if (book.getAvailableCopies() < 0 || book.getAvailableCopies() > book.getTotalCopies()) {
            throw new RuntimeException("Available copies must be between 0 and totalCopies");
        }
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new RuntimeException("ISBN already exists");
        }
        return bookRepository.save(book);
    }

    @Cacheable(value = "books", key = "T(java.util.Objects).hash(#genre, #author, #available)")
    public List<Book> getAll(String genre, String author, Boolean available) {
        Specification<Book> specification = Specification.where(null);

        if (genre != null && !genre.isBlank()) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("genre")), genre.toLowerCase()));
        }

        if (author != null && !author.isBlank()) {
            specification = specification.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
        }

        if (available != null) {
            if (available) {
                specification = specification.and((root, query, cb) ->
                        cb.greaterThan(root.get("availableCopies"), 0));
            } else {
                specification = specification.and((root, query, cb) ->
                        cb.equal(root.get("availableCopies"), 0));
            }
        }

        return bookRepository.findAll(specification);
    }

    public Book getById(UUID id) {
        if (id == null) {
            throw new RuntimeException("ID cannot be null");
        }
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @CacheEvict(value = "books", allEntries = true)
    public Book update(UUID id, Book updated) {
        if (updated == null) {
            throw new RuntimeException("Book cannot be null");
        }
        if (updated.getIsbn() == null || updated.getIsbn().isBlank()) {
            throw new RuntimeException("ISBN is required");
        }
        if (updated.getTitle() == null || updated.getTitle().isBlank()) {
            throw new RuntimeException("Title is required");
        }
        if (updated.getAuthor() == null || updated.getAuthor().isBlank()) {
            throw new RuntimeException("Author is required");
        }
        if (!updated.getIsbn().matches("^978-\\d{10}$")) {
            throw new RuntimeException("ISBN must follow format 978-XXXXXXXXXX");
        }
        if (updated.getTotalCopies() < 1) {
            throw new RuntimeException("Total copies must be at least 1");
        }
        if (updated.getAvailableCopies() < 0 || updated.getAvailableCopies() > updated.getTotalCopies()) {
            throw new RuntimeException("Available copies must be between 0 and totalCopies");
        }

        Book book = getById(id);

        bookRepository.findByIsbn(updated.getIsbn())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new RuntimeException("ISBN already exists");
                });

        book.setIsbn(updated.getIsbn());
        book.setTitle(updated.getTitle());
        book.setAuthor(updated.getAuthor());
        book.setGenre(updated.getGenre());
        book.setTotalCopies(updated.getTotalCopies());
        book.setAvailableCopies(updated.getAvailableCopies());

        return bookRepository.save(book);
    }

    @CacheEvict(value = "books", allEntries = true)
    public void delete(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }
        bookRepository.deleteById(id);
    }
}