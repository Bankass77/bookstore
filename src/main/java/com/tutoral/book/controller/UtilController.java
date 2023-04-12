package com.tutoral.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tutoral.book.document.Book;
import com.tutoral.book.service.BookService;

import reactor.core.publisher.Flux;

public class UtilController {
	private BookService bookService;

	@Autowired
	public UtilController(BookService bookService) {
		super();
		this.bookService = bookService;
	}
	
	
	@RequestMapping(value = "/randomBookNews", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Book> getBeanNames(){
		return bookService.randomBookNews();
	}
	
}
