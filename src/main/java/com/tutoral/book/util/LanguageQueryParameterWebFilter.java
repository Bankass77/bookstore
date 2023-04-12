package com.tutoral.book.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.adapter.DefaultServerWebExchange;
import org.springframework.web.server.adapter.HttpWebHandlerAdapter;

import com.tutoral.book.document.Book;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
public class LanguageQueryParameterWebFilter implements WebFilter {
	
	private HttpWebHandlerAdapter httpWebHandlerAdapter;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

		final ServerHttpRequest request = exchange.getRequest();

		final MultiValueMap<String, String> queryParams = request.getQueryParams();
		final String languageValue = queryParams.getFirst("lang");
		final ServerWebExchange localizedExchange = getSeverWebExchange(languageValue, exchange);

		return chain.filter(localizedExchange);
	}

	private ServerWebExchange getSeverWebExchange(String languageValue, ServerWebExchange exchange) {

		return isEmpty(languageValue) ? getLocaleFromCookie(exchange)
				: getLocalizedServerWebExchange(languageValue, exchange);
	}

	private ServerWebExchange getLocaleFromCookie(ServerWebExchange exchange) {
		WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/randomBookNews")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_EVENT_STREAM_VALUE)
				.defaultCookie("InternalCookie", "all").build();
		Flux<Book> newReleases = webClient.get().uri("/").retrieve().bodyToFlux(Book.class);
		return null;
	}

	private ServerWebExchange getLocalizedServerWebExchange(String languageValue, final ServerWebExchange exchange) {
		setLocaleToCookie(languageValue, exchange);

		final ServerHttpRequest httpRequest = exchange.getRequest().mutate()
				.headers(httpHeaders -> httpHeaders.set("Accept-Language", languageValue)).build();

		return new DefaultServerWebExchange(httpRequest, exchange.getResponse(),
				httpWebHandlerAdapter.getSessionManager(), httpWebHandlerAdapter.getCodecConfigurer(),
				httpWebHandlerAdapter.getLocaleContextResolver());
	}

	private void setLocaleToCookie(String languageValue, ServerWebExchange exchange) {
		// TODO Auto-generated method stub
		
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

}
