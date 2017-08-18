package com.uned.gateway;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.output.TeeOutputStream;
import org.springframework.stereotype.Component;

/**
 * Sample filter implementation to define a set of Content Security Policies.<br/>
 * 
 * This implementation has a dependency on Commons Codec API.<br/>
 * 
 * This filter set CSP policies using all HTTP headers defined into W3C specification.<br/>
 * <br/>
 * This implementation is oriented to be easily understandable and easily adapted.<br/>
 * 
 */
@Component
@WebFilter("/*")
public class CSPPoliciesApplier implements Filter {

	/** Configuration member to specify if web app use web fonts */
	public static final boolean APP_USE_WEBFONTS = false;

	/** Configuration member to specify if web app use videos or audios */
	public static final boolean APP_USE_AUDIOS_OR_VIDEOS = false;

	/** Configuration member to specify if filter must add CSP directive used by Mozilla (Firefox) */
	public static final boolean INCLUDE_MOZILLA_CSP_DIRECTIVES = true;

	/** Filter configuration */
	@SuppressWarnings("unused")
	private FilterConfig filterConfig = null;

	/** List CSP HTTP Headers */
	private List<String> cspHeaders = new ArrayList<String>();

	/** Collection of CSP polcies that will be applied */
	private String policies = null;

	/** Used for Script Nonce */
	private SecureRandom prng = null;

	/**
	 * Used to prepare (one time for all) set of CSP policies that will be applied on each HTTP response.
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// Get filter configuration
		this.filterConfig = fConfig;

		// Init secure random
		try {
			this.prng = SecureRandom.getInstance("SHA1PRNG");
		}
		catch (NoSuchAlgorithmException e) {
			throw new ServletException(e);
		}

		// Define list of CSP HTTP Headers
		this.cspHeaders.add("Content-Security-Policy");
		this.cspHeaders.add("X-Content-Security-Policy");
		this.cspHeaders.add("X-WebKit-CSP");

		// Define CSP policies
		// Loading policies for Frame and Sandboxing will be dynamically defined : We need to know if context use Frame
		List<String> cspPolicies = new ArrayList<String>();
		String originLocationRef = "'self'";
		// --Disable default source in order to avoid browser fallback loading using 'default-src' locations
		cspPolicies.add("default-src 'none'");
		// --Define loading policies for Scripts
		cspPolicies.add("script-src " + originLocationRef + " 'unsafe-inline' 'unsafe-eval'");
		if (INCLUDE_MOZILLA_CSP_DIRECTIVES) {
			cspPolicies.add("options inline-script eval-script");
			cspPolicies.add("xhr-src 'self'");
		}
		// --Define loading policies for Plugins
		cspPolicies.add("object-src " + originLocationRef);
		// --Define loading policies for Styles (CSS)
		cspPolicies.add("style-src " + originLocationRef);
		// --Define loading policies for Images
		cspPolicies.add("img-src " + originLocationRef);
		// --Define loading policies for Form
		cspPolicies.add("form-action " + originLocationRef);
		// --Define loading policies for Audios/Videos
		if (APP_USE_AUDIOS_OR_VIDEOS) {
			cspPolicies.add("media-src " + originLocationRef);
		}
		// --Define loading policies for Fonts
		if (APP_USE_WEBFONTS) {
			cspPolicies.add("font-src " + originLocationRef);
		}
		// --Define loading policies for Connection
		cspPolicies.add("connect-src " + originLocationRef);
		// --Define loading policies for Plugins Types
		cspPolicies.add("plugin-types application/pdf application/x-shockwave-flash");
		// --Define browser XSS filtering feature running mode
		cspPolicies.add("reflected-xss block");

		// Target formating
		this.policies = cspPolicies.toString().replaceAll("(\\[|\\])", "").replaceAll(",", ";").trim();
	}

	/**
	 * Add CSP policies on each HTTP response.
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fchain) throws IOException, ServletException {
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		HttpServletResponse httpResponse = ((HttpServletResponse) response);

		 //Step 1 : Detect if target resource is a Frame 
		// Customize here according to your context...
		boolean isFrame = true;

		//Step 2 : Add CSP policies to HTTP response 
		StringBuilder policiesBuffer = new StringBuilder(this.policies);

		// If resource is a frame add Frame/Sandbox CSP policy
		if (isFrame) {
			// Frame + Sandbox : Here sandbox allow nothing, customize sandbox options depending on your app....
			policiesBuffer.append(";").append("frame-src 'self';sandbox");
			if (INCLUDE_MOZILLA_CSP_DIRECTIVES) {
				policiesBuffer.append(";").append("frame-ancestors 'self'");
			}
		}

		// Add Script Nonce CSP Policy
		// --Generate a random number
		String randomNum = new Integer(this.prng.nextInt()).toString();
		// --Get its digest
		MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("SHA-1");
		}
		catch (NoSuchAlgorithmException e) {
			throw new ServletException(e);
		}
		byte[] digest = sha.digest(randomNum.getBytes());
		// --Encode it into HEXA
		String scriptNonce = Hex.encodeHexString(digest);
		policiesBuffer.append(";").append("script-nonce ").append(scriptNonce);
		// --Made available script nonce in view app layer
		httpRequest.setAttribute("CSP_SCRIPT_NONCE", scriptNonce);

		// Add policies to all HTTP headers
		for (String header : this.cspHeaders) {
			httpResponse.setHeader(header, policiesBuffer.toString());
		}

		// Step 3 : Let request continue chain filter 
		fchain.doFilter(request, response);		
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// Not used
	}

}