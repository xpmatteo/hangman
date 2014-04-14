package it.xpug.hangman.main;

import it.xpug.hangman.domain.*;

public interface WebRequest {

	String getPath();

	String getMethod();

	String getParameter(String name);

	UserId getUserId();

	String getPrisonerId();

	boolean isGet();

	boolean isPost();

}
