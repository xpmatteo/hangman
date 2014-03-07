package it.xpug.hangman.main;

import static org.junit.Assert.*;

import org.junit.*;

public class PrisonerTest {

	@Test
	public void newPrisonerToJSON() {
		Prisoner prisoner = new Prisoner("abc123", "someword");
		assertEquals("********", prisoner.toMap().get("word"));
		assertEquals(18, prisoner.toMap().get("guesses_remaining"));
	}

}
