package com.bookvault.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class BookRequestDTO {
	
	@NotBlank(message = "ISBN is required")
    @Pattern(
        regexp = "^978-\\d{10}$",
        message = "ISBN must follow format 978-XXXXXXXXXX"
    )
    private String isbn;

	@NotBlank(message = "Title cannot be empty")
    private String title;

	@NotBlank(message = "Author cannot be empty")
    private String author;

    private String genre;

    @Min(value = 1, message = "Total copies must be at least 1")
    private int totalCopies;

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
    
}
