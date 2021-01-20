package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game implements IGame {

    private static final int MAX_PLACES = 6;
    private List<String> playerNames = new ArrayList<>();
    private int[] places = new int[MAX_PLACES];
    private int[] purses = new int[6];
    private boolean[] inPenaltyBox = new boolean[6];

    private List<String> popQuestions = new LinkedList<>();
    private List<String> scienceQuestions = new LinkedList<>();
    private List<String> sportsQuestions = new LinkedList<>();
    private List<String> rockQuestions = new LinkedList<>();

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int i = 0; i < 50; i++) {
            popQuestions.add("Pop Question " + i);
            scienceQuestions.add(("Science Question " + i));
            sportsQuestions.add(("Sports Question " + i));
            rockQuestions.add("Rock Question " + i);
        }
    }

    public boolean add(String playerName) {
        playerNames.add(playerName);
        places[playerNames.size()] = 0;
        purses[playerNames.size()] = 0;
        inPenaltyBox[playerNames.size()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + playerNames.size());
        return true;
    }

    public void roll(int roll) {
        System.out.println(playerNames.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(playerNames.get(currentPlayer) + " is getting out of the penalty box");
                movePlayer(roll);
                System.out.println("The category is " + currentCategory());
                askQuestion();
            } else {
                System.out.println(playerNames.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            movePlayer(roll);
            System.out.println("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void movePlayer(int roll) {
        places[currentPlayer] += roll;
        if (places[currentPlayer] > 11) {
            places[currentPlayer] -= 12;
        }

        System.out.println(playerNames.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
    }

    private void askQuestion() {
        if (POP.equals(currentCategory())) {
            System.out.println(popQuestions.remove(0));
        }
        if (SCIENCE.equals(currentCategory())) {
            System.out.println(scienceQuestions.remove(0));
        }
        if (SPORTS.equals(currentCategory())) {
            System.out.println(sportsQuestions.remove(0));
        }
        if (ROCK.equals(currentCategory())) {
            System.out.println(rockQuestions.remove(0));
        }
    }


    private String currentCategory() {
        if (places[currentPlayer] % 4 == 0) {
            return POP;
        }
        if (places[currentPlayer] % 4 == 1) {
            return SCIENCE;
        }
        if (places[currentPlayer] % 4 == 2) {
            return SPORTS;
        }
        return ROCK;
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                purses[currentPlayer]++;
                System.out.println(playerNames.get(currentPlayer)
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == playerNames.size()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == playerNames.size()) currentPlayer = 0;
                return true;
            }


        } else {

            System.out.println("Answer was corrent!!!!");
            purses[currentPlayer]++;
            System.out.println(playerNames.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == playerNames.size()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(playerNames.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == playerNames.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
