package it.xpug.hangman.main;

import java.io.*;
import java.util.*;

public class RandomWord {
	private File file;
	private List<String> words = new ArrayList<String>();
	private Random random;

	public RandomWord() {
		this(new File("/usr/share/dict/words"), new Random());
	}

	public RandomWord(File file) {
		this(file, new Random());
	}

	public RandomWord(File file, Random random) {
		this.file = file;
		this.random = random;
	}

	public String getAnother() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			for (int i = 0; i < 10000; i++) {
				words.add(reader.readLine());
			}
			reader.close();
			return words.get(random.nextInt(words.size()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
