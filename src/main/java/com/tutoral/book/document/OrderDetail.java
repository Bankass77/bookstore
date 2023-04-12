package com.tutoral.book.document;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "orderDetail")
@Data
public class OrderDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private Book book;

	private int quantity = 1;

	public BigDecimal getPrice() {

		if (this.book != null) {
			return this.book.getPrice().multiply(new BigDecimal(this.quantity));
		}
		return BigDecimal.ZERO;
	}
}
