package com.uned.gateway.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

public class ContentRequestTransformer extends ProxyRequestTransformer {

  @Override
  public RequestBuilder transform(HttpServletRequest request) throws NoSuchRequestHandlingMethodException, URISyntaxException, IOException {
    RequestBuilder requestBuilder = predecessor.transform(request);

    String requestContent = request.getReader().lines().collect(Collectors.joining(""));
    if (!requestContent.isEmpty()) {
      StringEntity entity = new StringEntity(requestContent, ContentType.APPLICATION_JSON);
      requestBuilder.setEntity(entity);
    }

    return requestBuilder;
  }
}
