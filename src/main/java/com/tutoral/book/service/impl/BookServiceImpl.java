package com.tutoral.book.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutoral.book.document.Account;
import com.tutoral.book.document.Book;
import com.tutoral.book.document.Cart;
import com.tutoral.book.document.Order;
import com.tutoral.book.repository.AccountRepository;
import com.tutoral.book.repository.BookRepository;
import com.tutoral.book.search.BookSearchCriteria;
import com.tutoral.book.service.BookService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional(readOnly = true)
@Service
public class BookServiceImpl implements BookService {

	private static final int RANDOM_BOOKS = 2;

	private final BookRepository bookRepository;

	private final AccountRepository accountRepository;

	@Autowired
	public BookServiceImpl(BookRepository bookRepository, AccountRepository accountRepository) {
		super();
		this.bookRepository = bookRepository;
		this.accountRepository = accountRepository;
	}

	@Override
	public Mono<Book> findBook(String id) {

		return this.bookRepository.findById(id);
	}

	@Override
	public Flux<Book> findBooksByCategory(String category) {

		return this.bookRepository.findByCategory(category);
	}

	@Override
	public Flux<Book> findBooks(BookSearchCriteria bookSearchCriteria) {

		Query query = new Query();
		if (bookSearchCriteria.getTitle() != null) {
			query.addCriteria(Criteria.where("title").is(bookSearchCriteria.getTitle()));
		}

		if (bookSearchCriteria.getCategory() != null) {

			query.addCriteria(Criteria.where("category").is(bookSearchCriteria.getCategory()));
		}
		return bookRepository.findAll();
	}

	@Override
	public Boolean deleteBookById(int id) {

		return true;
	}

	@Override
	public Mono<Book> findByIsbn(String isbn) {

		return bookRepository.findByIsbn(isbn);
	}

	@Override
	public Flux<Book> findRandomBooks() {

		PageRequest pageRequest = PageRequest.of(0, RANDOM_BOOKS);

		return this.bookRepository.findRandom(pageRequest);
	}

	@Override
	public Mono<Order> findOrder(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<List<Order>> findOrdersForAccountId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Book> addBook(Book book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Order> createOrder(Cart cart, Account account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> deleteBookById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> deleteBook(String bookIsbn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Book> randomBookNews() {
		// TODO Auto-generated method stub
		return null;
	}

}
