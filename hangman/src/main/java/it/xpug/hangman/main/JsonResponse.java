package it.xpug.hangman.main;

import static java.lang.String.*;
import static javax.servlet.http.HttpServletResponse.*;

import java.util.*;

import javax.servlet.http.*;

public class JsonResponse extends HashMap<Object, Object> {

	private HttpServletResponse httpServletResponse;
	private HttpServletRequest httpServletRequest;

	public JsonResponse(HttpServletRequest request, HttpServletResponse response) {
		this.httpServletRequest = request;
		this.httpServletResponse = response;

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	}

	public void methodNotAllowed(String description) {
		put("description", description);
		setStatus(SC_METHOD_NOT_ALLOWED, "Method not allowed");
	}

	public void redirect(String path) {
		put("location", path);
		httpServletResponse.setHeader("Location", makeAbsoluteUrl(path));
		setStatus(SC_SEE_OTHER, "See other");
	}

	public void forbidden(String description) {
		put("description", description);
		setStatus(SC_FORBIDDEN, "Forbidden");
	}

	private void setStatus(int statusCode, String statusDescription) {
		put("status", statusDescription);
		put("status_code", statusCode);
		httpServletResponse.setStatus(statusCode);
	}

	private String makeAbsoluteUrl(String path) {
		String serverName = httpServletRequest.getServerName();
		int localPort = httpServletRequest.getLocalPort();
		return format("http://%s:%s%s", serverName, localPort, path);
	}
}
