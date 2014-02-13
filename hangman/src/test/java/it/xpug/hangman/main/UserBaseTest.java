package it.xpug.hangman.main;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

public class UserBaseTest {

	@Test
	public void returnsNextId() throws Exception {
		Random random = new Random(123);
		UserBase userBase = new UserBase(random);

		assertEquals("46de0e23", userBase.getNextUserId());
		assertEquals("3cbc0495", userBase.getNextUserId());
	}

	@Test
	public void authenticateUsers() throws Exception {
		UserBase userBase = new UserBase();
		userBase.add("pippo", "secret", new UserId("123"));
		assertEquals("authenticates", true, userBase.contains(new UserId("123"), "secret"));
		assertEquals("wrong password", false, userBase.contains(new UserId("123"), "zot"));
		assertEquals("non-existing user", false, userBase.contains(new UserId("999"), "secret"));
	}
}
