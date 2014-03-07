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
		userBase.add(new UserId("123"), "pippo", "secret");
		assertEquals("authenticates", true, userBase.contains(new UserId("123"), "secret"));
		assertEquals("wrong password", false, userBase.contains(new UserId("123"), "zot"));
		assertEquals("non-existing user", false, userBase.contains(new UserId("999"), "secret"));
	}

	@Test
	public void moreThanOneUser() throws Exception {
		UserBase userBase = new UserBase();
		userBase.add(new UserId("123"), "pippo", "secret");
		userBase.add(new UserId("456"), "pluto", "asdf");

		assertEquals("first user", true, userBase.contains(new UserId("123"), "secret"));
		assertEquals("second user", true, userBase.contains(new UserId("456"), "asdf"));
	}

	// error un duplicate id


}
