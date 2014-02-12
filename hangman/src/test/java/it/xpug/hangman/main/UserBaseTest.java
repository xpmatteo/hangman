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
}
