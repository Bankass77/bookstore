package com.tutoral.book.config.i18n;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.i18n.LocaleContextResolver;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestParamLocaleResolver implements LocaleContextResolver {

	private String languageParameterName;
	private static final String LOCALE_REQUEST_ATTRIBUTE_NAME = "book.Cookie.LOCALE";

	@Override
	public LocaleContext resolveLocaleContext(ServerWebExchange exchange) {

		Locale defaultLocale = Locale.getDefault();

		List<String> referLang = exchange.getRequest().getQueryParams().get(languageParameterName);

		if (!CollectionUtils.isEmpty(referLang)) {
			String lang = referLang.get(0);
			defaultLocale = Locale.forLanguageTag(lang);
			setLocaleToCookie(lang, exchange);
		}
		return new SimpleLocaleContext(defaultLocale);
	}

	@Override
	public void setLocaleContext(ServerWebExchange exchange, LocaleContext localeContext) {

	}

	private void setLocaleToCookie(final String lang, final ServerWebExchange exchange) {
		MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
		HttpCookie cookie = cookies.getFirst(LOCALE_REQUEST_ATTRIBUTE_NAME);

		if (cookie == null || !lang.equals(cookie.getName())) {

			ResponseCookie responseCookie = ResponseCookie.from(LOCALE_REQUEST_ATTRIBUTE_NAME, lang)
					.maxAge(Duration.ofMinutes(5)).build();
			exchange.getResponse().addCookie(responseCookie);
		}
	}

	private Locale getLocaleFromCookie(final ServerWebExchange exchange) {
		MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();

		HttpCookie cookie = cookies.getFirst(LOCALE_REQUEST_ATTRIBUTE_NAME);

		return cookie != null ? Locale.forLanguageTag(cookie.getValue()) : Locale.getDefault();
	}

	public RequestParamLocaleResolver(final String languageParameterName) {
		super();
		this.languageParameterName = languageParameterName;
	}

}
