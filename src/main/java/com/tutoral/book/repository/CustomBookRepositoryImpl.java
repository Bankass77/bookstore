package com.tutoral.book.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;

import com.tutoral.book.document.Book;

import reactor.core.publisher.Flux;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sample;

public class CustomBookRepositoryImpl implements CustomBookRepository {

	ReactiveMongoTemplate mongoTemplate;

	public CustomBookRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
		super();
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Flux<Book> findRandom(Pageable pageable) {

		Aggregation aggregation = newAggregation(sample(pageable.getPageSize()));
		return mongoTemplate.aggregate(aggregation, "book", Book.class);
	}

	@Override
	public Flux<Book> findAll(Query query) {

		return mongoTemplate.find(query, Book.class);
	}

}
