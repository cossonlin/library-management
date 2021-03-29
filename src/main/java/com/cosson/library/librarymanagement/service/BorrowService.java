package com.cosson.library.librarymanagement.service;

import com.cosson.library.librarymanagement.entity.Borrow;
import com.cosson.library.librarymanagement.repo.BookRepository;
import com.cosson.library.librarymanagement.repo.BorrowRepository;
import com.cosson.library.librarymanagement.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowService {

	private final BorrowRepository borrowRepository;
	private final UserRepository userRepository;
	private final BookRepository bookRepository;
	private final int borrowQuota;
	private final int duration;

	@Autowired
	public BorrowService(@Value("${borrow.quota}") int borrowQuota, @Value("${borrow.duration}") int duration, BorrowRepository borrowRepository, UserRepository userRepository, BookRepository bookRepository) {
		this.borrowQuota = borrowQuota;
		this.duration = duration;
		this.borrowRepository = borrowRepository;
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
	}

	public Borrow borrowBook(Borrow borrow) {
		if (!isValidBorrowRequest(borrow)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide valid userId and bookId");
		}
		if (borrowRepository.countByUserIdAndReturnDateNull(borrow.getUserId()) >= borrowQuota) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sorry, you've exceeded your quota");
		}
		synchronized (this) {
			if (isBookAvailable(borrow)) {
				borrow.setBorrowDate(LocalDate.now());
				borrow.setExpiryDate(LocalDate.now().plusDays(duration));
				borrow.setReturnDate(null);
				return borrowRepository.save(borrow);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sorry, book is not available");
			}
		}
	}

	private boolean isValidBorrowRequest(Borrow borrow) {
		return userRepository.existsById(borrow.getUserId())
				&& bookRepository.existsById(borrow.getBookId());
	}

	private boolean isBookAvailable(Borrow borrow) {
		return !borrowRepository.existsByBookIdAndReturnDateNull(borrow.getBookId());
	}

	public Borrow returnBook(Borrow returnBook) {
		Optional<Borrow> optional = borrowRepository.findTopByBookIdAndUserIdAndReturnDateNull(returnBook.getBookId(), returnBook.getUserId());
		if (optional.isPresent()) {
			Borrow borrow = optional.get();
			borrow.setReturnDate(LocalDate.now());
			return borrowRepository.save(borrow);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sorry, can't find your borrow record");
		}
	}
}
