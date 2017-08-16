package com.uned.gateway.services;

import javax.servlet.http.HttpServletRequest;

public interface DecisionPointService {
	Boolean isAccepted(String tokenUUID, HttpServletRequest request);
}
