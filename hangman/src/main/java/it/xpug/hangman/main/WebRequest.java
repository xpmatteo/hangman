package it.xpug.hangman.main;

public interface WebRequest {

	String getRequestURI();

	String getMethod();

	String getParameter(String name);

	UserId getUserId();

	String getPrisonerId();

}
