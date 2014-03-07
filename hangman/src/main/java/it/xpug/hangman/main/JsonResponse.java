package it.xpug.hangman.main;

import static java.lang.String.*;
import static javax.servlet.http.HttpServletResponse.*;

import java.util.*;

import javax.servlet.http.*;

import org.mortbay.util.ajax.*;

public class JsonResponse implements WebResponse {

	private HttpServletResponse httpServletResponse;
	private HttpServletRequest httpServletRequest;
	private Map<String, Object> objects = new HashMap<>();

	public JsonResponse(HttpServletRequest request, HttpServletResponse response) {
		this.httpServletRequest = request;
		this.httpServletResponse = response;

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	}

	@Override
	public void methodNotAllowed(String description) {
		objects.put("description", description);
		setStatus(SC_METHOD_NOT_ALLOWED, "Method not allowed");
	}

	@Override
	public void redirect(String path) {
		objects.put("location", path);
		httpServletResponse.setHeader("Location", makeAbsoluteUrl(path));
		setStatus(SC_SEE_OTHER, "See other");
	}

	@Override
	public void forbidden(String description) {
		objects.put("description", description);
		setStatus(SC_FORBIDDEN, "Forbidden");
	}

	@Override
	public void put(String name, Object value) {
		objects.put(name, value);
	}

	@Override
	public String toString() {
		return JSON.toString(objects).replaceAll("\",", "\",\n") + "\n";
	}

	@Override
	public void validationError(String message) {
		setStatus(400, "Bad Request");
		objects.put("description", message);
	}

	private void setStatus(int statusCode, String statusDescription) {
		objects.put("status", statusDescription);
		objects.put("status_code", statusCode);
		httpServletResponse.setStatus(statusCode);
	}

	private String makeAbsoluteUrl(String path) {
		String serverName = httpServletRequest.getServerName();
		int localPort = httpServletRequest.getLocalPort();
		return format("http://%s:%s%s", serverName, localPort, path);
	}

}
