package com.tutoral.book.controller;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.spring6.context.webflux.IReactiveSSEDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import com.tutoral.book.document.Book;
import com.tutoral.book.document.Book.Category;
import com.tutoral.book.exception.BookstoreException;
import com.tutoral.book.exception.BookstoreException.ServiceDeniedException;
import com.tutoral.book.search.BookSearchCriteria;
import com.tutoral.book.service.BookService;
import com.tutoral.book.util.BookNewReleasesUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class BookSearchController {

	private BookService bookService;

	@Autowired
	public BookSearchController(BookService bookService) {
		super();
		this.bookService = bookService;
	}

	@ModelAttribute("categories")
	public List<String> getCategories() {
		return List.of(Category.JAVA, Category.SPRING, Category.WEB);
	}

	@ModelAttribute
	public BookSearchCriteria bookSearchCriteria() {

		return new BookSearchCriteria();
	}

	@GetMapping(path = "/book/search")
	public String load() {

		return "book/search";
	}

	@GetMapping(value = "/book/new", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public String newBooks(final Model model) {
		WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081/randomBookNews")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_EVENT_STREAM_VALUE)
				.defaultCookie("InternalCookie", "all").build();

		Flux<Book> newReleases = webClient.get().uri("/").retrieve()
				.onStatus(HttpStatus::is4xxClientError,
						response -> Mono.error(response.statusCode() == HttpStatus.UNAUTHORIZED
								? new BookstoreException.ServiceDeniedException("You shhall not pass!")
								: new BookstoreException.ServiceDeniedException("well.. this is unfortunate!"))
						)
				.onStatus(HttpStatus::is5xxServerError,
						response -> Mono.error(response.statusCode() == HttpStatus.INTERNAL_SERVER_ERROR
								? new BookstoreException.ServiceDownException("Internal error!!")
								: new BookstoreException.ServiceDownException("Well.. this is a mistery!")))
				.bodyToFlux(Book.class);

		final IReactiveSSEDataDriverContextVariable dataDriver = new ReactiveDataDriverContextVariable(newReleases, 1,
				"newBooks");

		model.addAttribute("newBooks", dataDriver); // Flux wrapped in a DataDriver to avoid resolution
		// Will use the same "book/search" template, but only a fragment: the newBooks
		// block.
		return "book/search :: #newBooks";
	}

	@RequestMapping(value = "/tech/news", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public String techNews(final Model model) {
		final WebClient webClient = WebClient.create("http://localhost:3000/techNews");

		Flux<String> newReleases = webClient.get().uri("/").retrieve()
				.onStatus(HttpStatus::is4xxClientError,
						response -> Mono.error(response.statusCode() == HttpStatus.UNAUTHORIZED
								? new BookstoreException.ServiceDeniedException("You shall not pass!")
								: new BookstoreException.ServiceDeniedException("Well.. this is unfortunate!")))
				.onStatus(HttpStatus::is5xxServerError,
						response -> Mono.error(response.statusCode() == HttpStatus.INTERNAL_SERVER_ERROR
								? new BookstoreException.ServiceDownException("This is SpartAAA!!")
								: new BookstoreException.ServiceDownException("Well.. this is a mystery!")))
				.bodyToFlux(String.class);

		final IReactiveSSEDataDriverContextVariable dataDriver = new ReactiveDataDriverContextVariable(newReleases, 1,
				"techNews");

		model.addAttribute("techNews", dataDriver);
		return "book/search :: #techNews";
	}

}
