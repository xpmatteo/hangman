package it.xpug.hangman.main;

import it.xpug.hangman.domain.*;
import it.xpug.hangman.web.*;

import java.io.UnsupportedEncodingException;
import java.util.regex.*;

import javax.servlet.http.*;
import javax.xml.bind.DatatypeConverter;

public class HttpServletWebRequest implements WebRequest {

	private HttpServletRequest request;

	public HttpServletWebRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String getPath() {
		return request.getRequestURI();
	}

	@Override
	public String getMethod() {
		return request.getMethod().toLowerCase();
	}

	@Override
	public String getParameter(String name) {
		return request.getParameter(name);
	}

	@Override
	public UserId getUserId() {
		Matcher matcher = Pattern.compile("/users/([a-f0-9]+)").matcher(getPath());
		matcher.find();
		return new UserId(matcher.group(1));
	}

	@Override
	public String getPrisonerId() {
		Matcher matcher = Pattern.compile("/users/([a-f0-9]+)/prisoners/([a-f0-9]+)").matcher(getPath());
		matcher.find();
		return matcher.group(2);
	}

	@Override
	public boolean isGet() {
		return getMethod().equals("get");
	}

	@Override
	public boolean isPost() {
		return getMethod().equals("post");
	}

	@Override
	public String getPassword() {
		String credentials = getCredentials();
		if (null == credentials)
			return null;
		return credentials.split(":")[1];
	}

	@Override
	public String getCredentials() {
		try {
			String header = request.getHeader("authorization");
			if (null == header) 
				return null;
			String[] tokens = header.split(" ");
			String authenticationMethod = tokens[0];
			if (!"basic".equalsIgnoreCase(authenticationMethod))
				return null;
			String encodedCredentials = tokens[1];
			return new String(DatatypeConverter.parseBase64Binary(encodedCredentials), "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
