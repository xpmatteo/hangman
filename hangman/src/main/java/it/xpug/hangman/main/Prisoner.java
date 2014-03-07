package it.xpug.hangman.main;

import java.util.*;

public class Prisoner {

	private String word;
	private int guessesRemaining = 18;

	public Prisoner(String id, String word) {
		this.word = word;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> result = new HashMap<>();
		result.put("word", getMaskedWord());
		result.put("guesses_remaining", guessesRemaining);
		return result;
	}

	private String getMaskedWord() {
		return word.replaceAll(".", "*");
	}

}
