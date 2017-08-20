package com.uned.gateway.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import java.util.Enumeration;
import java.util.stream.Collectors;
import com.uned.gateway.ApiGatewayProperties;
import com.uned.gateway.services.DecisionPointService;
import com.uned.gateway.utils.ContentRequestTransformer;
import com.uned.gateway.utils.HeadersRequestTransformer;
import com.uned.gateway.utils.URLRequestTransformer;

@Configuration
@RestController
public class GatewayController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ApiGatewayProperties apiGatewayProperties;
	@Autowired
	private DecisionPointService decisionPointService;
	
	private HttpClient httpClient;	

	@PostConstruct
	public void init() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

		httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}

	@RequestMapping(value = "/api/**", method = { GET, PUT, POST, DELETE })
	@ResponseBody
	public ResponseEntity<String> proxyRequest(@RequestHeader(value = "Authorization", defaultValue = "", required = false) String authorizationHeader,	HttpServletRequest request) throws NoHandlerFoundException,	IOException, URISyntaxException, NoSuchRequestHandlingMethodException {
		HttpUriRequest proxiedRequest = createHttpUriRequest(request);
		logger.info("request: {}", proxiedRequest);
		String tokenUUID = extraerToken(authorizationHeader);
		HttpResponse proxiedResponse = null;
		if (decisionPointService.isAccepted(tokenUUID, request)) {
			proxiedResponse = httpClient.execute(proxiedRequest);
			logger.info("Response {}", proxiedResponse.getStatusLine().getStatusCode());
		} else {
			// Throw corresponding Exception
		}
		return new ResponseEntity<>(read(proxiedResponse.getEntity().getContent()), 
				makeResponseHeaders(proxiedResponse), 
				HttpStatus.valueOf(proxiedResponse.getStatusLine().getStatusCode()));
	}

	private HttpHeaders makeResponseHeaders(HttpResponse response) {
		HttpHeaders result = new HttpHeaders();
		Header h = response.getFirstHeader("Content-Type");
		result.set(h.getName(), h.getValue());
		return result;
	}

	private HttpUriRequest createHttpUriRequest(HttpServletRequest request)	throws URISyntaxException, NoSuchRequestHandlingMethodException, IOException {
		URLRequestTransformer urlRequestTransformer = new URLRequestTransformer(apiGatewayProperties);
		ContentRequestTransformer contentRequestTransformer = new ContentRequestTransformer();
		HeadersRequestTransformer headersRequestTransformer = new HeadersRequestTransformer();
		headersRequestTransformer.setPredecessor(contentRequestTransformer);
		contentRequestTransformer.setPredecessor(urlRequestTransformer);
		HttpUriRequest u = headersRequestTransformer.transform(request).build();
		return u;
	}

	private String read(InputStream input) throws IOException {
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(
				input))) {
			return buffer.lines().collect(Collectors.joining("\n"));
		}
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void handle(HttpMessageNotReadableException e) {
		logger.error("Returning HTTP 400 Bad Request: " + e);
		throw e;
	}

	private String extraerToken(String authorizationHeader) {		
		if (authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		} else {
			throw new com.uned.gateway.exceptions.AuthorizationHeaderException();
		}
	}
	/*
	 * @ExceptionHandler(AccessNotAllowed.class)
	 * 
	 * @ResponseBody public ResponseEntity<ErrorAPI>
	 * handleErrorEnvioPacienteRISException(myCustomException e){
	 * LOGGER.error("handleErrorEnvioPacienteRISException >> {} - {}",
	 * e.getStackTrace(), e.getMessage()); ErrorAPI error = new ErrorAPI();
	 * error.setCodigo("ERRORENVIO_LOQUESEA_XCEPTION");
	 * error.setMensaje(e.getMessage()); return new
	 * ResponseEntity<ErrorAPI>(error, HttpStatus.INTERNAL_SERVER_ERROR); }
	 */
}
