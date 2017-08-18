package com.uned.gateway;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

public class AuditorDispatcherServlet extends DispatcherServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!(request instanceof ContentCachingRequestWrapper)) {
			request = new ContentCachingRequestWrapper(request);
		}
		if (!(response instanceof ContentCachingResponseWrapper)) {
			response = new ContentCachingResponseWrapper(response);
		}
		HandlerExecutionChain handler = getHandler(request);
		try {
			super.doDispatch(request, response);
		} finally {
			auditMessage(request, response, handler);
			updateResponse(response);
		}
	}

	private void auditMessage(HttpServletRequest requestToCache, HttpServletResponse responseToCache,
			HandlerExecutionChain handler) throws IOException {
		final StringBuilder logMessage = new StringBuilder("REST RESPONSE - ").append("[HTTP METHOD:")
				.append(requestToCache.getMethod()).append("] [PATH INFO:").append(requestToCache.getPathInfo())
				.append("] [RESPONSE PARAMETERS:").append(requestToCache.getQueryString()).append("] [RESPONSE BODY:")
				.append(getResponsePayload(responseToCache)).append("] [REMOTE ADDRESS:")
				.append(requestToCache.getRemoteAddr()).append("]");

		final StringBuilder logMessageRequest = new StringBuilder("REST REQUEST - ").append("[HTTP METHOD:")
				.append(requestToCache.getMethod()).append("] [PATH INFO:").append(requestToCache.getPathInfo())
				.append("] [REQUEST PARAMETERS:").append(requestToCache.getParameterMap().values())
				.append("] [REQUEST BODY:").append(getRequestPayload(requestToCache)).append("] [REMOTE ADDRESS:")
				.append(requestToCache.getRemoteAddr()).append("]");
		System.out.println(logMessageRequest);
		System.out.println(logMessage);
	}

	private String getResponsePayload(HttpServletResponse response) {
		ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response,
				ContentCachingResponseWrapper.class);
		if (wrapper != null) {

			byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {
				int length = Math.min(buf.length, 5120);
				try {
					return new String(buf, 0, length, wrapper.getCharacterEncoding());
				} catch (UnsupportedEncodingException ex) {
					// NOOP
				}
			}
		}
		return "[unknown]";
	}

	private String getRequestPayload(HttpServletRequest request) {
		ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
		if (wrapper != null) {

			byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {
				int length = Math.min(buf.length, 5120);
				try {
					return new String(buf, 0, length, wrapper.getCharacterEncoding());
				} catch (UnsupportedEncodingException ex) {
					// NOOP
				}
			}
		}
		return "[unknown]";
	}

	private void updateResponse(HttpServletResponse response) throws IOException {
		ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response,
				ContentCachingResponseWrapper.class);
		responseWrapper.copyBodyToResponse();
	}

}