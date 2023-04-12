package com.tutoral.book.document;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import com.tutoral.book.formatter.DateFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Document(collection = "account")
@RequiredArgsConstructor
public class Account {

	@Id
	private String id;

	private String firstName;

	private String lastName;

	@DateFormat(format="YYYY-MM-DD")
	private Date dateOfBirth;

	private Address address = new Address();

	
	@NotNull
	@Email
	private String emailAddress;

	private String username;

	@NotEmpty
	private String password;
	
	List<Order> orders;

}
