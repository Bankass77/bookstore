package com.tutoral.book.config.i18n;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration;
import org.springframework.web.server.i18n.LocaleContextResolver;


@Configuration
public class LocalSupportConfig extends DelegatingWebFluxConfiguration{
	@Override
	protected LocaleContextResolver createLocaleContextResolver() {
		return new RequestParamLocaleResolver("lang");
	}

}
