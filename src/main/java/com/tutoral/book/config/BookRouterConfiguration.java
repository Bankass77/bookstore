package com.tutoral.book.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import com.tutoral.book.handler.BookHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class BookRouterConfiguration {

	@Bean
	RouterFunction<ServerResponse> routerFunction(BookHandler bookHandler) {

		return route(GET("/books"), bookHandler.list)
				.andRoute(GET("/books/{isbn}"), bookHandler::findByIsbn)
				.andRoute(POST("/books"), bookHandler::save)
				.andRoute(DELETE("/books/{id}"), bookHandler.delete)
				.filter((request, next) -> {
					log.info("Before handler invocation: " + request.path());
					return next.handle(request);
				});
	}

}
