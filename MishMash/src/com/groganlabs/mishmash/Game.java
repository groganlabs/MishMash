package com.groganlabs.mishmash;

public class Game {
	public static int JUMBLE_GAME = 1;
	public static int CRYPTO_GAME = 2;
	public static int DROP_GAME = 3;
	
	//ids from the db
	private int gameId, packId;
	private String solution;
	private int gameType;
	// solution - the original phrase
	// answer - the player's answers
	// puzzle - the solution converted into the puzzle played
	private char[] puzzleArr, answerArr, solutionArr;
	

}
