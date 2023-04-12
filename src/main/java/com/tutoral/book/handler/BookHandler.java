package com.tutoral.book.handler;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.tutoral.book.document.Book;
import com.tutoral.book.search.BookSearchCriteria;
import com.tutoral.book.service.BookService;
import com.tutoral.book.validator.BookValidator;
import jakarta.validation.ValidationException;


import reactor.core.publisher.Mono;

@Component
public class BookHandler {

	private final BookService bookService;

	private final Validator validator = new BookValidator();

	public HandlerFunction<ServerResponse> list;

	public HandlerFunction<ServerResponse> delete;

	@Autowired
	public BookHandler(BookService bookService) {
		super();
		this.bookService = bookService;
		list = serverRequest -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(bookService.findRandomBooks(),
				Book.class);

		delete = serverRequest -> ServerResponse.noContent()
				.build(bookService.deleteBookById(((ServerRequest) serverRequest).pathVariable("id")));
	}

	public Mono<ServerResponse> findByIsbn(ServerRequest serverRequest) {

		Mono<Book> bookMono = bookService.findByIsbn(serverRequest.pathVariable("isbn"));
		return bookMono.flatMap(book -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(book))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> save(ServerRequest serverRequest) {

		Mono<Book> bookMono = serverRequest.bodyToMono(Book.class).doOnNext(bookService::addBook);

		return bookMono
				.flatMap(book -> ServerResponse.created(URI.create("/books/" + book.getId()))
						.contentType(MediaType.APPLICATION_JSON).bodyValue(book))
				.switchIfEmpty(ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
	}

	/*
	 * public Mono<ServerResponse> create(ServerRequest serverRequest) { return
	 * serverRequest.bodyToMono(Book.class) .flatMap(this::validate) .flatMap(book
	 * -> bookService.addBook(book)) .collectList() .flatMap(books ->
	 * ServerResponse.created(URI.create("/book/isbn/" + books.get(0).getIsbn()))
	 * .contentType(MediaType.APPLICATION_JSON) .bodyValue(books.get(0)))
	 * .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error)); }
	 */

	public Mono<ServerResponse> search(ServerRequest serverRequest) {
		var criteriaMono = serverRequest.bodyToMono(BookSearchCriteria.class);
		return criteriaMono.log().flatMap(criteria -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(bookService.findBooks(criteria), Book.class));
	}

	public Mono<ServerResponse> detail(ServerRequest serverRequest) {
		Mono<Book> bookMono = bookService.findByIsbn(serverRequest.pathVariable("isbn"));
		return bookMono
				.flatMap(book -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(Book.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	private Mono<Book> validate(Book book) {
		Errors errors = new BeanPropertyBindingResult(book, "book");
		validator.validate(book, errors);

		if (errors.hasErrors()) {

			throw new ValidationException(errors.toString());
		}

		return Mono.just(book);
	}
}
