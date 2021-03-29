package com.cosson.library.librarymanagement.service;

import com.cosson.library.librarymanagement.entity.Borrow;
import com.cosson.library.librarymanagement.repo.BookRepository;
import com.cosson.library.librarymanagement.repo.BorrowRepository;
import com.cosson.library.librarymanagement.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class BorrowServiceTest {
	private final BorrowRepository borrowRepository = mock(BorrowRepository.class);
	private final UserRepository userRepository = mock(UserRepository.class);
	private final BookRepository bookRepository = mock(BookRepository.class);
	private BorrowService borrowService;
	@Value("${borrow.quota}")
	private int borrowQuota;
	@Value("${borrow.duration}")
	private int duration;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.borrowService = new BorrowService(borrowQuota, duration, borrowRepository, userRepository, bookRepository);
	}

	@Test
	void borrowBook_ShouldReturnBorrow_WhenEverythingIsGood() {
		Borrow borrow = new Borrow();
		borrow.setUserId("id");
		borrow.setBookId(1L);
		when(userRepository.existsById(anyString())).thenReturn(true);
		when(bookRepository.existsById(anyLong())).thenReturn(true);
		when(borrowRepository.countByUserIdAndReturnDateNull(anyString())).thenReturn(1);
		when(borrowRepository.existsByBookIdAndReturnDateNull(anyLong())).thenReturn(false);
		when(borrowRepository.save(any(Borrow.class))).thenReturn(borrow);

		Borrow createdBorrow = borrowService.borrowBook(borrow);

		assertEquals(borrow.getUserId(), createdBorrow.getUserId());
		assertEquals(borrow.getBookId(), createdBorrow.getBookId());
	}

	@Test
	void borrowBook_ShouldThrowException_WhenBookIsNotAvailable() {
		Borrow borrow = new Borrow();
		borrow.setUserId("id");
		borrow.setBookId(1L);
		when(userRepository.existsById(anyString())).thenReturn(true);
		when(bookRepository.existsById(anyLong())).thenReturn(true);
		when(borrowRepository.countByUserIdAndReturnDateNull(anyString())).thenReturn(1);
		when(borrowRepository.existsByBookIdAndReturnDateNull(anyLong())).thenReturn(true);
		when(borrowRepository.save(any(Borrow.class))).thenReturn(borrow);

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> borrowService.borrowBook(borrow));
		assertEquals("Sorry, book is not available", exception.getReason());
	}

	@Test
	void returnBook_ShouldReturnBorrow_WhenBookIsReturnSuccess() {
		Borrow borrow = new Borrow();
		borrow.setUserId("id");
		borrow.setBookId(1L);

		when(borrowRepository.findTopByBookIdAndUserIdAndReturnDateNull(anyLong(), anyString())).thenReturn(Optional.of(borrow));
		when(borrowRepository.save(any(Borrow.class))).thenReturn(borrow);

		Borrow createdReturn = borrowService.returnBook(borrow);

		assertEquals(borrow.getUserId(), createdReturn.getUserId());
		assertEquals(borrow.getBookId(), createdReturn.getBookId());
		assertEquals(LocalDate.now(), createdReturn.getReturnDate());
	}

	@Test
	void returnBook_ShouldThrowException_WhenBorrowRecordCannotBeFound() {
		Borrow borrow = new Borrow();
		borrow.setUserId("id");
		borrow.setBookId(1L);

		when(borrowRepository.findTopByBookIdAndUserIdAndReturnDateNull(anyLong(), anyString())).thenReturn(Optional.empty());
		when(borrowRepository.save(any(Borrow.class))).thenReturn(borrow);

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> borrowService.returnBook(borrow));
		assertEquals("Sorry, can't find your borrow record", exception.getReason());
	}
}
