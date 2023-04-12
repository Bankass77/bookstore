package com.tutoral.book.formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import lombok.Setter;

@Setter
public class DateFormatter implements Formatter<Date> {

	private String format;

	@Override
	public String print(Date object, Locale locale) {
		return getDateFormat(locale).format(object);
	}

	@Override
	public Date parse(String text, Locale locale) throws ParseException {
		return getDateFormat(locale).parse(text);
	}

	private DateFormat getDateFormat(Locale locale) {
		if (StringUtils.hasText(this.format)) {
			return (DateFormat) new SimpleDateFormat(this.format, locale);
		} else {
			return (DateFormat) SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, locale);
		}
	}

}
