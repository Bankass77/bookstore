package com.tutoral.book.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Component;

import com.tutoral.book.document.Book;
import com.tutoral.book.repository.BookRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class DataInitializer {

	public static class Category {

		public static final String SPRING = "Spring";
		public static final String JAVA = "Java";
		public static final String WEB = "web";

	}

	private final BookRepository bookRepository;

	@Autowired
	private ReactiveMongoOperations operations;

	public DataInitializer(BookRepository bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}

	@PostConstruct
	public void init() {
		operations.collectionExists(Book.class)
				.flatMap(exists -> exists ? operations.dropCollection(Book.class) : Mono.just(exists))
				.then(operations.createCollection(Book.class, CollectionOptions.empty()))
				.subscribe(data -> log.info("Collection saved: {}", data), error -> log.info("Oups!"),
						() -> log.info("Collections initialized!"));

		log.info("-->> Starting collection initialization...");
		bookRepository.saveAll(Flux.just(
				new Book("1", "Spring Boot 2 Recipes", "Marten Deinum", new BigDecimal(25), 2023, "Georges Town",
						"9781484227893", Category.SPRING),
				new Book("2", "Pivotal Certified Professional Core Spring 5 Developer Exam", "Iuliana Cosmina",
						new BigDecimal(30), 1977, "9781484251355","VBHGJHKHHK" ,Category.SPRING),
				new Book("3", "Java for Absolute Beginners", "Iuliana Cosmina",new BigDecimal(45), 2000,"gdqhkdqkqkd", "9781484237779",
						Category.JAVA)))
				.subscribe(data -> log.info("Saved {} books", data), error -> log.error("Oups!"),
						() -> log.info("Collection initialized!"));
	}

}
