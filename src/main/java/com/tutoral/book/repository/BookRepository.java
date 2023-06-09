package com.tutoral.book.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.tutoral.book.document.Book;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepository extends ReactiveMongoRepository<Book, String>, CustomBookRepository {

	@Query("{'category': {'$regex' : ?0}}")
	Flux<Book> findByCategory(String category);
	
	@Query(value = "{}", fields = "{'id': 1, 'isbn' : 1, 'category' :1}")
	Flux<Book> findAllLight();
	

	@Query("{'isbn': ?0 }")
	Mono<Book> findByIsbn(String isbn);


}
