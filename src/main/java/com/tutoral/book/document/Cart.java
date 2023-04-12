package com.tutoral.book.document;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Book, Integer> books = new HashMap<>();

	public void addBoob(Book book) {

		if (this.books.containsKey(book)) {

			int quantity = this.books.get(book);
			quantity++;
			this.books.put(book, quantity);
		} else {
			this.books.put(book, 1);
		}
	}

	public void removeBook(Book book) {

		this.books.remove(book);
	}

	public void clear() {

		this.books.clear();
	}

}
