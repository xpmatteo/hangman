package it.xpug.hangman.main;

import java.util.*;

import org.mortbay.util.ajax.JSON;

public class Prisoner implements JSON.Generator {

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

	@Override
	public void addJSON(StringBuffer buffer) {
		buffer.append(JSON.toString(this.toMap()));
	}

}
