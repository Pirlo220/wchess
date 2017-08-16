package com.uned.gateway.services;

import javax.servlet.http.HttpServletRequest;

public class DecisionPointServiceImpl implements DecisionPointService {

	@Override
	public Boolean isAccepted(String tokenUUID, HttpServletRequest request) {
		// By-passed
		return true;
	}
}