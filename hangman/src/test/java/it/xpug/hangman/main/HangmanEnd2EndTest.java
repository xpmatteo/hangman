package it.xpug.hangman.main;


import static org.junit.Assert.*;
import it.xpug.generic.web.*;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.*;
import org.junit.*;
import org.mortbay.util.ajax.*;


public class HangmanEnd2EndTest {

	@Test
	public void rootUrl() throws Exception {
		get("/");
		assertStatus(200);
		assertMimeType("application/json; charset=UTF-8");
		assertBody("{\"index\":\"/\",\"prisoners\":\"/prisoners\",\"users\":\"/users\"}");
	}

	@Test
	public void unauthenticatedUsers() throws Exception {
		get("/users");
		assertBody("{" +
				"\"description\": \"Use POST on /users to create a user\",\n" +
				"\"status\": \"Method not allowed\",\n" +
				"\"status_code\": 405\n" +
				"}");
		assertStatus(405);
		assertMimeType("application/json; charset=UTF-8");
	}

	// other urls should return 404
	// authenticated get to /users/1234
	// authenticated get to wrong /users/9999
	// unauth get to /prisoners
	// auth get to /prisoners
	// create user invalid or missing data

	@Test
	public void createAUser() throws Exception {
		nextUserId = "12345";

		params.put("name", "Pippo");
		params.put("password", "Pluto");
		post("/users");

		assertStatus(303);
		assertMimeType("application/json; charset=UTF-8");
		assertLocationHeader("http://localhost:8123/users/12345");
		assertBody("{\"status_code\": 303, \"location\": \"/users/12345\", \"status\": \"See other\"}");
	}

	@Test
	public void seeMyself() throws Exception {
		givenUser("888", "Pippoz", "s3cr3t");

		params.put("name", "Pippoz");
		params.put("password", "s3cr3t");
		get("/users/888");

		assertStatus(200);
		assertMimeType("application/json; charset=UTF-8");
		assertBody("{" +
				" \"prisoners\": \"/prisoners\",\n" +
				" \"id\": \"888\",\n" +
				" \"name\": \"Pippoz\",\n" +
				" \"url\": \"/users/888\"\n" +
				"}\n" +
				"");
	}

	@Test
	public void wrongPassword() throws Exception {
		givenUser("888", "Pippoz", "s3cr3t");

		params.put("name", "Pippoz");
		params.put("password", "nottherightpassword");
		get("/users/888");

		assertStatus(403);
		assertMimeType("application/json; charset=UTF-8");
		assertBody("{\n" +
				" \"description\": \"You don't have the permission to access the requested resource. It is either read-protected or not readable by the server.\",\n" +
				" \"status\": \"Forbidden\",\n" +
				" \"status_code\": 403\n" +
				"}");
	}

	// wrong userid

	private void givenUser(String userId, String name, String password) {
		users.add(name, password, userId);
	}

	private void assertBody(String expectedBody) throws IllegalStateException, IOException {
		byte[] bytes = new byte[10000];
		response.getEntity().getContent().read(bytes);
		String body = new String(bytes, Charset.forName("UTF-8"));
		assertEquals("Body", JSON.parse(expectedBody), JSON.parse(body));
	}

	private void assertMimeType(String expectedMimeType) {
		Header contentType = response.getLastHeader("content-type");
		assertNotNull("Mime type not set", contentType);
		assertEquals("Mime type", expectedMimeType, contentType.getValue());
	}

	private void assertLocationHeader(String expectedLocation) {
		Header location = response.getLastHeader("location");
		assertNotNull("Location not set", location);
		assertEquals("Location", expectedLocation, location.getValue());
	}

	private void assertStatus(int expectedStatus) {
		assertEquals("Status code", expectedStatus, response.getStatusLine().getStatusCode());
	}

	private void get(String path) throws IOException, URISyntaxException {
		URI url = new URI(baseUrl() + path + queryString());
		System.out.println(url);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		this.response = httpClient.execute(request);
	}

	private String baseUrl() {
		return "http://localhost:" + APPLICATION_PORT;
	}

	private String queryString() {
		String queryString = "";
		for (String name : params.keySet()) {
			if (!queryString.isEmpty())
				queryString += "&";
			queryString += name + "=" + params.get(name);
		}
		if (!queryString.isEmpty())
			queryString = "?" + queryString;
		return queryString;
	}

	private void post(String path) throws URISyntaxException, ClientProtocolException, IOException {
		URI url = new URI(baseUrl() + path);
		HttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
		HttpPost request = new HttpPost(url);
		addParameters(request);
		this.response = httpClient.execute(request);
	}

	private void addParameters(HttpPost request) throws UnsupportedEncodingException {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		for (String name : params.keySet()) {
			parameters.add(new BasicNameValuePair(name, params.get(name)));
		}
		HttpEntity entity = new UrlEncodedFormEntity(parameters);
		request.setEntity(entity);
	}

	@BeforeClass
	public static void startApplication() throws Exception {
		app.start(APPLICATION_PORT, "../hangman-server/src/main/webapp");
	}

	@AfterClass
	public static void shutdownApplication() throws Exception {
		app.shutdown();
	}

	private static String nextUserId;

	private static UserIdSequence userIdSequence = new UserIdSequence() {
		@Override
		public boolean hasNext() {
			return nextUserId != null;
		}
		@Override
		public String next() {
			try {
				return nextUserId;
			} finally {
				nextUserId = null;
			}
		}
		@Override
		public void remove() {
		}
	};

	private static final int APPLICATION_PORT = 8123;
	private static UserBase users = new UserBase();
	private static ReusableJettyApp app = new ReusableJettyApp(new HangmanServlet(userIdSequence, users));
	private HttpResponse response;
	private Map<String, String> params = new HashMap<String, String>();
}
