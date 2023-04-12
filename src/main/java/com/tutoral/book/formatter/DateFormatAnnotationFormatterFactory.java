package com.tutoral.book.formatter;

import java.util.Date;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;



public class DateFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<DateFormat> {

	@Override
	public Set<Class<?>> getFieldTypes() {
		
		return Set.of(Date.class);
	}

	@Override
	public Printer<?> getPrinter(DateFormat annotation, Class<?> fieldType) {
		
		return createFormatter(annotation);
	}

	private  DateFormatter createFormatter(DateFormat annotation) {
		
		var formatter= new DateFormatter();
		 formatter.setFormat(annotation.format());
		return formatter;
	}

	@Override
	public Parser<?> getParser(DateFormat annotation, Class<?> fieldType) {
	
		return createFormatter(annotation);
	}

}
