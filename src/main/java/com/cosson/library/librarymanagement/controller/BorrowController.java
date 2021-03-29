package com.cosson.library.librarymanagement.controller;

import com.cosson.library.librarymanagement.entity.Borrow;
import com.cosson.library.librarymanagement.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RequestMapping("/books")
@RestController
public class BorrowController {

	@Autowired
	private BorrowService borrowService;

	@PostMapping("/borrow")
	public ResponseEntity<Borrow> borrowBook(@RequestBody Borrow borrow) {
		if (Objects.isNull(borrow.getUserId())
				|| Objects.isNull(borrow.getBookId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId and bookId are mandatory");
		}
		return ResponseEntity.ok(borrowService.borrowBook(borrow));
	}

	@PostMapping("/return")
	public ResponseEntity<Borrow> returnBook(@RequestBody Borrow returnBook) {
		if (Objects.isNull(returnBook.getUserId())
				|| Objects.isNull(returnBook.getBookId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId and bookId are mandatory");
		}
		return ResponseEntity.ok(borrowService.returnBook(returnBook));
	}
}
