package com.tutoral.book.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

import com.tutoral.book.document.Book;
import com.tutoral.book.service.BookService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BookDetailController {
	final BookService bookService;

	public BookDetailController(BookService bookstoreService) {
		this.bookService = bookstoreService;
	}

	@ResponseBody
	@RequestMapping(value = "/book/id/{id}")
	public Mono<Book> getBookById(@PathVariable String id) {
		return bookService.findBook(id);
	}

	@ResponseBody
	@RequestMapping(value = "/book/isbn/{isbn}")
	public Mono<Book> getBookByIsbn(@PathVariable String isbn) {
		return bookService.findByIsbn(isbn);
	}

	@RequestMapping(value = "/book/detail/{bookId}")
	public String details(@PathVariable String bookId, Model model) {
		WebClient webClient = WebClient.create("http://localhost:8080/book");

		Flux<Book> bookFlux = webClient.get().uri(uriBuilder -> uriBuilder.path("/id/{id}").build(bookId)).retrieve()
				.bodyToMono(Book.class).flux();

		IReactiveDataDriverContextVariable dataDriver = new ReactiveDataDriverContextVariable(bookFlux, 1);

		model.addAttribute("books", dataDriver);

		return "book/detail";
	}

}
