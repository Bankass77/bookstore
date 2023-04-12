package com.tutoral.book.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

import com.tutoral.book.document.Book;

import reactor.core.publisher.Flux;

public interface CustomBookRepository {

	Flux<Book> findRandom(Pageable pageable);

	Flux<Book> findAll(Query query);

}
