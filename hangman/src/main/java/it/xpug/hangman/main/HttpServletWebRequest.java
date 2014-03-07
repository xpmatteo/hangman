package it.xpug.hangman.main;

import java.util.regex.*;

import javax.servlet.http.*;

public class HttpServletWebRequest implements WebRequest {

	private HttpServletRequest request;

	public HttpServletWebRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String getRequestURI() {
		return request.getRequestURI();
	}

	@Override
	public String getMethod() {
		return request.getMethod();
	}

	@Override
	public String getParameter(String name) {
		return request.getParameter(name);
	}

	@Override
	public UserId getUserId() {
		Matcher matcher = Pattern.compile("/users/([a-f0-9]+)").matcher(getRequestURI());
		matcher.find();
		return new UserId(matcher.group(1));
	}

	@Override
	public String getPrisonerId() {
		Matcher matcher = Pattern.compile("/users/([a-f0-9]+)/prisoners/([a-f0-9]+)").matcher(getRequestURI());
		matcher.find();
		return matcher.group(2);
	}


}
