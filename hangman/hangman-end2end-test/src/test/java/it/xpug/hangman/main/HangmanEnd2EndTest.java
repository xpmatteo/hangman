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
import org.eclipse.jetty.util.ajax.*;
import org.junit.*;


public class HangmanEnd2EndTest {

	@Test
	public void rootUrl() throws Exception {
		get("/");
		assertStatus(200);
		assertMimeType("application/json; charset=UTF-8");
		assertBody("{\"index\":\"/\",\"prisoners\":\"/prisoners\",\"me\":\"/me\"}");
	}

	@Test
	public void unauthenticatedMe() throws Exception {
		get("/me");
		assertBody("{}");
		assertStatus(404);
		assertMimeType("application/json; charset=UTF-8");
	}

	// other urls should return 404

	@Test
	public void createAUser() throws Exception {
		nextUserId = "12345";
		params.put("name", "Pippo");
		params.put("password", "Pluto");
		post("/me");
		assertStatus(201);
		assertMimeType("application/json; charset=UTF-8");
		assertBody("{" +
				" \"prisoners\": \"/prisoners\",\n" +
				" \"id\": \"12345\",\n" +
				" \"name\": \"Pippo\",\n" +
				" \"url\": \"/users/12345\"\n" +
				"}\n" +
				"");
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

	private void assertStatus(int expectedStatus) {
		assertEquals("Status code", expectedStatus, response.getStatusLine().getStatusCode());
	}

	private void get(String path) throws IOException, URISyntaxException {
		URI url = new URI("http://localhost:" + APPLICATION_PORT + path);
		HttpClient httpClient = HttpClientBuilder.create().build();
		this.response = httpClient.execute(new HttpGet(url));
	}

	private void post(String path) throws URISyntaxException, ClientProtocolException, IOException {
		URI url = new URI("http://localhost:" + APPLICATION_PORT + path);
		HttpClient httpClient = HttpClientBuilder.create().build();
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
	protected static ReusableJettyApp app = new ReusableJettyApp(new HangmanServlet(userIdSequence));
	private HttpResponse response;
	private Map<String, String> params = new HashMap<String, String>();
}
