package com.tutoral.book.document;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class Book {

	@Id
	private String id;

	private String title;
	
	private String description;
	
	private BigDecimal price;
	
	private Integer year;
	
	private String author;
	
	@Indexed(unique = true)
	private String isbn;
	
	private String category;
	
	
	public static class Category{
		public static final String SPRING= "Spring";
		public static final String JAVA= "java";
		public static final String WEB= "Web";
	}

}
