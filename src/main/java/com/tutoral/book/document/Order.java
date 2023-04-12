package com.tutoral.book.document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "order")
@Data
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private Address shippingAddress;

	private Address billingAddress;

	@Transient
	private Account account;

	private boolean billingSameAsShipping = true;
	private Date orderDate;
	private BigDecimal totalOrderPrice = BigDecimal.ZERO;
	private final List<OrderDetail> orderDetails = new ArrayList<>();

	public Address getBillingAddress() {

		if (this.billingSameAsShipping) {
			return this.shippingAddress;
		}

		return this.billingAddress;
	}

	public void updateOrderDetail() {

		BigDecimal total = BigDecimal.ZERO;
		Iterator<OrderDetail> details = this.orderDetails.iterator();

		while (details.hasNext()) {
			OrderDetail orderDetail = (OrderDetail) details.next();

			if (orderDetail.getQuantity() == 0) {

				details.remove();
			} else {
				total = total.add(orderDetail.getPrice());
			}

		}
	}
}
