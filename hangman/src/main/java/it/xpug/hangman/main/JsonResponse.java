package it.xpug.hangman.main;

import static java.lang.String.*;

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
		put("status", "Method not allowed");
		put("status_code", 405);
		httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	public void respondWithRedirect(String path) {
		put("status", "See other");
		put("status_code", HttpServletResponse.SC_SEE_OTHER);
		put("location", path);
		httpServletResponse.setStatus(HttpServletResponse.SC_SEE_OTHER);
		httpServletResponse.setHeader("Location", makeAbsoluteUrl(path));
	}

	public void forbidden(String description) {
		put("description", description);
		put("status", "Forbidden");
		put("status_code", HttpServletResponse.SC_FORBIDDEN);
		httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}

	private String makeAbsoluteUrl(String path) {
		String serverName = httpServletRequest.getServerName();
		int localPort = httpServletRequest.getLocalPort();
		return format("http://%s:%s%s", serverName, localPort, path);
	}
}
