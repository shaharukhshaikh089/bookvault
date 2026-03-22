package com.bookvault.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookvault.entity.Book;
import com.bookvault.response.ApiResponse;
import com.bookvault.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> create(@Valid @RequestBody Book book) {
        return ResponseEntity.ok(new ApiResponse<>(bookService.create(book), null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAll(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Boolean available) {
        return ResponseEntity.ok(new ApiResponse<>(bookService.getAll(genre, author, available), null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(new ApiResponse<>(bookService.getById(id), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> update(@PathVariable UUID id, @Valid @RequestBody Book book) {
        return ResponseEntity.ok(new ApiResponse<>(bookService.update(id, book), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id) {
        bookService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Deleted", null));
    }
}