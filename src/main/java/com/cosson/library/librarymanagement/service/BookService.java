package com.cosson.library.librarymanagement.service;

import com.cosson.library.librarymanagement.entity.Book;
import com.cosson.library.librarymanagement.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

	private final BookRepository bookRepository;

	@Autowired
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public Book addBook(Book book) {
		return bookRepository.save(book);
	}

	public Book updateBook(Book book) {
		Optional<Book> optional = bookRepository.findById(book.getId());
		if (optional.isPresent()) {
			return bookRepository.save(book);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "book ID is invalid");
		}
	}

	public List<Book> searchBooksBySpec(Specification<Book> spec) {
		return bookRepository.findAll(spec);
	}

	/*
	 * Assume we can delete the book which is still on loan
	 * */
	public boolean deleteBook(Book book) {
		// It's deleted by ID only
		bookRepository.delete(book);
		return true;
	}
}
