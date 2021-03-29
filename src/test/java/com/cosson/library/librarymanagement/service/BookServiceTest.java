package com.cosson.library.librarymanagement.service;

import com.cosson.library.librarymanagement.entity.Book;
import com.cosson.library.librarymanagement.repo.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {
	private BookService bookService;
	private BookRepository bookRepository = mock(BookRepository.class);

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.bookService = new BookService(bookRepository);
	}

	@Test
	void addBook_ShouldReturnBook_WhenUserIdIsNew() {
		Book book = new Book();
		book.setIbsn("i");
		book.setName("name");
		when(bookRepository.save(any(Book.class))).thenReturn(book);

		Book createdBook = bookService.addBook(book);
		assertEquals(book.getIbsn(), createdBook.getIbsn());
		assertEquals(book.getName(), createdBook.getName());
	}

	@Test
	void fetchBySpec_ShouldReturnBookList_WhenSpecIsProvided() {
		Book book1 = new Book();
		book1.setIbsn("i");
		book1.setName("name1");
		Book book2 = new Book();
		book2.setIbsn("i");
		book2.setName("name2");
		List<Book> bookList = new ArrayList<>();
		bookList.add(book1);
		bookList.add(book1);
		Specification querySpec = mock(Specification.class);
		when(bookRepository.findAll(any(Specification.class))).thenReturn(bookList);

		List<Book> bookList1 = bookService.searchBooksBySpec(querySpec);

		assertEquals(2L, bookList1.size());
		verify(bookRepository, times(1)).findAll(querySpec);
	}
}
