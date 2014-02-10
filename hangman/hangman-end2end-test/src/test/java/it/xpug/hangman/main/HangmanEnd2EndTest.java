package it.xpug.hangman.main;


import static org.apache.commons.io.IOUtils.*;
import static org.junit.Assert.*;

import java.io.*;
import java.net.*;
import java.util.*;

import it.xpug.generic.web.*;

import org.apache.commons.io.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.junit.*;

public class HangmanEnd2EndTest {

	@Test
	public void rootUrl() throws Exception {
		get("/");
		assertStatus(200);
		assertBody("{}");
		assertMimeType("application/json; charset=UTF-8");
	}

	private void assertBody(String expectedBody) throws IllegalStateException, IOException {
		List<String> body = readLines(response.getEntity().getContent());
		assertEquals("Body", expectedBody, body.get(0));
	}

	private void assertMimeType(String expectedMimeType) {
		assertEquals("Mime type", expectedMimeType, response.getLastHeader("content-type").getValue());
	}

	private void assertStatus(int expectedStatus) {
		assertEquals("Status code", expectedStatus, response.getStatusLine().getStatusCode());
	}

	private void get(String path) throws IOException, URISyntaxException {
		URI url = new URI("http://localhost:" + APPLICATION_PORT + path);
		HttpClient httpClient = HttpClientBuilder.create().build();
		this.response = httpClient.execute(new HttpGet(url));
	}

	@BeforeClass
	public static void startApplication() throws Exception {
		app.start(APPLICATION_PORT, "../hangman-server/src/main/webapp");
	}

	@AfterClass
	public static void shutdownApplication() throws Exception {
		app.shutdown();
	}

	private static final int APPLICATION_PORT = 8123;
	protected static ReusableJettyApp app = new ReusableJettyApp(new HangmanServlet());
	private HttpResponse response;

}
