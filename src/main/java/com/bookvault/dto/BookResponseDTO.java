package com.bookvault.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

public class BookResponseDTO {
	    private UUID id;
	    private String isbn;
	    private String title;
	    private String author;
	    private String genre;
	    private int totalCopies;
	    private int availableCopies;
		public UUID getId() {
			return id;
		}
		public void setId(UUID id) {
			this.id = id;
		}
		public String getIsbn() {
			return isbn;
		}
		public void setIsbn(String isbn) {
			this.isbn = isbn;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getGenre() {
			return genre;
		}
		public void setGenre(String genre) {
			this.genre = genre;
		}
		public int getTotalCopies() {
			return totalCopies;
		}
		public void setTotalCopies(int totalCopies) {
			this.totalCopies = totalCopies;
		}
		public int getAvailableCopies() {
			return availableCopies;
		}
		public void setAvailableCopies(int availableCopies) {
			this.availableCopies = availableCopies;
		}
	
}
