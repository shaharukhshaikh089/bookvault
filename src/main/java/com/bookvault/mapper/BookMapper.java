package com.bookvault.mapper;

import com.bookvault.dto.BookRequestDTO;
import com.bookvault.dto.BookResponseDTO;
import com.bookvault.entity.Book;

public class BookMapper {

	 public static Book toEntity(BookRequestDTO dto) {
	        Book book = new Book();
	        book.setIsbn(dto.getIsbn());
	        book.setTitle(dto.getTitle());
	        book.setAuthor(dto.getAuthor());
	        book.setGenre(dto.getGenre());
	        book.setTotalCopies(dto.getTotalCopies());
	        book.setAvailableCopies(dto.getTotalCopies());
	        return book;
	    }
	 
	 public static BookResponseDTO toDTO(Book book) {
	        BookResponseDTO dto = new BookResponseDTO();
	        dto.setId(book.getId());
	        dto.setIsbn(book.getIsbn());
	        dto.setTitle(book.getTitle());
	        dto.setAuthor(book.getAuthor());
	        dto.setGenre(book.getGenre());
	        dto.setTotalCopies(book.getTotalCopies());
	        dto.setAvailableCopies(book.getAvailableCopies());
	        return dto;
	    }
}
