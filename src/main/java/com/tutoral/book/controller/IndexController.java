package com.tutoral.book.controller;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

import reactor.core.publisher.Flux;

@RestController
public class IndexController implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

	@GetMapping(path = { "/", "index.html" })
	public String index(final Model model) {

		return "index";

	}

	@ResponseBody
	@GetMapping(value = "/beans", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getBeanNames() {
		List<String> beans = Arrays.stream(applicationContext.getBeanDefinitionNames()).sorted()
				.collect(Collectors.toList());
		Flux<String> flux = Flux.fromIterable(beans).delayElements(Duration.ofMillis(200));
		IReactiveDataDriverContextVariable dataDriver = new ReactiveDataDriverContextVariable(flux, 10);
		return Flux.fromIterable(beans).delayElements(Duration.ofMillis(200));
	}

}
