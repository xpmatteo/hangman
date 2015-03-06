package it.xpug.hangman.main;

import static org.junit.Assert.*;
import it.xpug.hangman.domain.*;

import javax.servlet.http.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.junit.*;

public class HttpServletWebRequestTest {

	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();
	private HttpServletRequest httpServletRequest = context.mock(HttpServletRequest.class);

	@Test
	public void test() {
		context.checking(new Expectations() {
			{
				allowing(httpServletRequest).getRequestURI();
				will(returnValue("/users/abc123"));
			}
		});
		HttpServletWebRequest request = new HttpServletWebRequest(httpServletRequest);
		assertEquals(new UserId("abc123"), request.getUserId());
	}
	
	@Test
	public void authenticationParsed() throws Exception {
		context.checking(new Expectations() {{
				allowing(httpServletRequest).getHeader("authorization");
				will(returnValue("Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ=="));
		}});
		HttpServletWebRequest request = new HttpServletWebRequest(httpServletRequest);
		assertEquals("Aladdin:open sesame", request.getCredentials());		
		assertEquals("open sesame", request.getPassword());		
	}

	@Test
	public void authenticationMissing() throws Exception {
		context.checking(new Expectations() {{
				allowing(httpServletRequest).getHeader("authorization");
				will(returnValue(null));
		}});
		HttpServletWebRequest request = new HttpServletWebRequest(httpServletRequest);
		assertEquals(null, request.getCredentials());
		assertEquals(null, request.getPassword());
	}

	@Test
	public void wrongAuthenticationMethod() throws Exception {
		context.checking(new Expectations() {{
				allowing(httpServletRequest).getHeader("authorization");
				will(returnValue("something else"));
		}});
		HttpServletWebRequest request = new HttpServletWebRequest(httpServletRequest);
		assertEquals(null, request.getCredentials());		
		assertEquals(null, request.getPassword());		
	}

}
