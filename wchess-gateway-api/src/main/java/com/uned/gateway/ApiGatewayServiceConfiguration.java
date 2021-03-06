package com.uned.gateway;

import java.util.Collections;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uned.gateway.controllers.GatewayInterceptor;

@Configuration
@ComponentScan
@EnableAutoConfiguration

@EnableConfigurationProperties({ ApiGatewayProperties.class })
public class ApiGatewayServiceConfiguration extends WebMvcConfigurerAdapter {

	@Bean
	public RestTemplate restTemplate(HttpMessageConverters converters) {

		// we have to define Apache HTTP client to use the PATCH verb
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/json"));
		converter.setObjectMapper(new ObjectMapper());

		HttpClient httpClient = HttpClients.createDefault();
		RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>>singletonList(converter));
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

		restTemplate.setErrorHandler(new RestTemplateErrorHandler());

		return restTemplate;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(new GatewayInterceptor()).addPathPatterns("/api/**");
	}

	@Bean
	public FilterRegistrationBean greetingFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setName("greeting");
		CSPPoliciesApplier greetingFilter = new CSPPoliciesApplier();
		registrationBean.setFilter(greetingFilter);
		registrationBean.setOrder(1);
		return registrationBean;
	}

	@Bean
	public ServletRegistrationBean dispatcherRegistration() {
		return new ServletRegistrationBean(dispatcherServlet());
	}

	@Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
	public DispatcherServlet dispatcherServlet() {
		return new AuditorDispatcherServlet();
	}
}
