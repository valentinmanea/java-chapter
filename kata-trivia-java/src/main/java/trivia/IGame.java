package trivia;

public interface IGame {

	String POP = "Pop";
	String SCIENCE = "Science";
	String SPORTS = "Sports";
	String ROCK = "Rock";

	boolean add(String playerName);

	void roll(int roll);

	boolean wasCorrectlyAnswered();

	boolean wrongAnswer();

}