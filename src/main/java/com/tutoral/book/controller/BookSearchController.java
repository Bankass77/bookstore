package com.tutoral.book.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring6.context.webflux.IReactiveSSEDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

import com.tutoral.book.document.Book;
import com.tutoral.book.util.BookNewReleasesUtil;

import reactor.core.publisher.Flux;

@Controller
public class BookSearchController {

	@GetMapping(value = "/book/new", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public String newBooks(final Model model) {
		Flux<Book> newReleases = Flux.interval(Duration.ofSeconds(3)).map(delay -> BookNewReleasesUtil.randomRelease());

		final IReactiveSSEDataDriverContextVariable dataDriver = new ReactiveDataDriverContextVariable(newReleases, 1);
		model.addAttribute("newBooks", dataDriver);
		return "/book/search:: #newBooks";

	}

}
