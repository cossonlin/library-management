package com.cosson.library.librarymanagement.controller;

import com.cosson.library.librarymanagement.entity.Book;
import com.cosson.library.librarymanagement.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private BookService service;

	@Test
	void addBook_whenMissingMandatoryField_thenThrowException() throws Exception {
		Book book = new Book();
		book.setIbsn("i");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(book)))
				.andExpect(status().isBadRequest())
				.andReturn();

		assertEquals("ISBN and Name are mandatory fields", ((ResponseStatusException) mvcResult.getResolvedException()).getReason());
	}

	@Test
	void addBook_whenAllMandatoryFieldsAreThere_thenReturnAddedBook() throws Exception {
		Book book = new Book();
		book.setIbsn("i");
		book.setName("name");

		when(service.addBook(book)).thenReturn(book);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(book)))
				.andExpect(status().isOk())
				.andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		Book returnObject = objectMapper.readValue(content, Book.class);

		assertEquals(book.getIbsn(), returnObject.getIbsn());
		assertEquals(book.getName(), returnObject.getName());
	}
}
