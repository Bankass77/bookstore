package com.tutoral.book.search;

import com.tutoral.book.config.DataInitializer.Category;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BookSearchCriteria {
	
	private String title;
	private Category category;

}
