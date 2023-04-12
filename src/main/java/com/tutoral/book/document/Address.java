package com.tutoral.book.document;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Address {

	@NotEmpty
	private String street;

	private String houseNumber;
	private String boxNumber;

	@NotEmpty
	private String city;

	@NotEmpty
	private String country;

	public Address(Address address) {
		super();
		this.street = address.street;
		this.houseNumber = address.houseNumber;
		this.boxNumber = address.boxNumber;
		this.city = address.city;
		this.country = address.country;
	}

}
