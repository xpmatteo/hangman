package it.xpug.hangman.main;

import java.util.*;

public class WordList {
	private List<String> words = new ArrayList<String>() {{
		add("repay");
		add("waterline");
		add("guardhouse");
		add("uniform");
		add("footrest");
		add("misstep");
		add("problematic");
		add("nightcap");
		add("whence");
		add("brigade");
		add("decadence");
		add("disorderly");
		add("underrate");
		add("rubbish");
		add("quicken");
		add("legibility");
		add("assurance");
		add("sunny");
		add("thimble");
		add("advise");
		add("american");
		add("beggar");
		add("embassy");
		add("grasp");
		add("scanty");
		add("ineligibility");
	}};
	private Random random;

	public WordList() {
		this(new Random());
	}

	public WordList(Random random) {
		this.random = random;
	}

	public String getRandomWord() {
		return words.get(random.nextInt(words.size()));
	}
}
