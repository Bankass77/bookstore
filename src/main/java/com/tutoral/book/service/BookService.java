package com.tutoral.book.service;

import java.util.List;

import com.tutoral.book.document.Account;
import com.tutoral.book.document.Book;
import com.tutoral.book.document.Cart;
import com.tutoral.book.document.Order;
import com.tutoral.book.search.BookSearchCriteria;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

	Mono<Book> findBook(String id);

	Flux<Book> findBooksByCategory(String category);

	Flux<Book> findRandomBooks();

	Mono<Order> findOrder(String id);

	Mono<List<Order>> findOrdersForAccountId(String id);

	Flux<Book> findBooks(BookSearchCriteria bookSearchCriteria);

	Flux<Book> addBook(Book book);

	Mono<Order> createOrder(Cart cart, Account account);

	Boolean deleteBookById(int id);

	Mono<Void> deleteBookById(String id);

	Mono<Void> deleteBook(String bookIsbn);

	Flux<Book> randomBookNews();

	Mono<Book> findByIsbn(String isbn);

}
