package com.cosson.library.librarymanagement.controller;

import com.cosson.library.librarymanagement.entity.Book;
import com.cosson.library.librarymanagement.service.BookService;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RequestMapping("/books")
@RestController
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		if (Objects.isNull(book.getIbsn()) || Objects.isNull(book.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN and Name are mandatory fields");
		}
		return ResponseEntity.ok(bookService.addBook(book));
	}

	@PutMapping
	public ResponseEntity<Book> updateBook(@RequestBody Book book) {
		if (book.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "book ID is not provided");
		}
		return ResponseEntity.ok(bookService.updateBook(book));
	}

	@GetMapping
	public ResponseEntity<List<Book>> searchBooks(
			@And({
					@Spec(path = "ibsn", params = "ibsn", spec = Equal.class),
					@Spec(path = "name", params = "name", spec = Equal.class)
			}) Specification<Book> spec) {
		return ResponseEntity.ok(bookService.searchBooksBySpec(spec));
	}

	@DeleteMapping
	public ResponseEntity<Boolean> deleteBook(@RequestBody Book book) {
		return ResponseEntity.ok(bookService.deleteBook(book));
	}
}
