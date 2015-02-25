package com.groganlabs.mishmash;

import java.util.Arrays;
import java.util.Random;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.groganlabs.mishmash.JumbleView;

public class JumbleActivity extends Activity {

	private String solutionStr;
	// solution - the original phrase
	// answer - the player's answers
	// puzzle - the solution converted into the puzzle played
	private char[] puzzleArr, answerArr, solutionArr;
	private JumbleView jumble;
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//see if we have a saved instance with a solution, if not we need to create one
		//with the accompanying arrays
		if(savedInstanceState == null || savedInstanceState.getCharArray("solutionArr") == null) {
			solutionStr = "THIS IS A TEST. THIS IS ONLY A TEST. PLEASE DO NOT ADJUST YOUR SCREENS.";
			solutionArr = solutionStr.toCharArray();
			puzzleArr = new char[solutionArr.length];
			answerArr = new char[solutionArr.length];
			createGame();
			
			for(int ii = 0; ii < solutionArr.length; ii++) {
				if(solutionArr[ii] < 'A' || solutionArr[ii] > 'Z') {
					answerArr[ii] = solutionArr[ii];
				}
			}
		}
		//we do have game information saved, so we'll use that
		else {
			solutionStr = savedInstanceState.getString("solutionStr");
			solutionArr = savedInstanceState.getCharArray("solutionArr");
			puzzleArr = savedInstanceState.getCharArray("puzzleArr");
			answerArr = savedInstanceState.getCharArray("answerArr");
		}
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		
		TextView title = new TextView(this);
		title.setText("Jumble");
		title.setLayoutParams(lp);
		layout.addView(title);
		
		jumble = new JumbleView(this);
		jumble.setLayoutParams(lp);
		layout.addView(jumble);
		
		AlphaView alpha = new AlphaView(this);
		alpha.setLayoutParams(lp);
		layout.addView(alpha);
		
		setContentView(layout);
		
		jumble.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent e) {
				return gameTouch(e.getX(), e.getY());
			}
		});
		
		alpha.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				//result of the keyboard touch
				char res = ((AlphaView) v).getLetter(event.getX(), event.getY());
				int curChar;
				
				//if we've highlighted a letter
				if((curChar = jumble.getHighlight()) >= 0) {
					answerArr[curChar] = res;
					
					//if we aren't deleting
					if(res != 0) {
						curChar++;
						//advance the highlight to the next word character
						while(!isWordChar(solutionArr, curChar)) {
							curChar++;
							if(curChar == solutionArr.length)
								curChar = 0;
						}
						
						jumble.setHighlight(curChar);
					}
					jumble.invalidate();
					//if answerArr == solutionArr, notify player and add to db
					if(Arrays.equals(answerArr, solutionArr)) {
						//TODO: notification and db updating
						Log.d("jumble", "You win!");
					}
				}
				//If no letter is selected, do nothing
				
				return false;
			}
		});
	}

	/**
	 * take the x and y coordinates of a touch event and determines if a
	 * letter in the puzzle was touched then tells the view to update
	 * highlighting as necessary
	 * @param x
	 * @param y
	 * @return wait, why did I do this as boolean instead of void?
	 */
	public boolean gameTouch(float x, float y) {
		//If no letter touched, and a letter was highlighted
		//Tell the view to remove the highlight and draw
		//Else
		//Get the letter selected - index of the array
		//Save the index in the activity
		//Have the view highlight that character and draw
		int touched = jumble.getTouched(x, y);
		int newC;
		
		//no game letter was touched
		if(touched < 0) {
			newC = -1;
		}
		//should never happen, but just in case
		else if(touched >= solutionStr.length()) {
			newC = -1;
		}
		//the user touched a spot within the game
		else {
			if(solutionArr[touched] >= 'A' && solutionArr[touched] <= 'Z')
				newC = touched;
			else
				newC = -1;
		}
		
		//We only need to redraw if the highlighting is changing
		Boolean draw = jumble.setHighlight(newC);
		if(draw)
			jumble.invalidate();
		
		return false;
	}
	
	/**
	 * returns the solution array for the game
	 * @return
	 */
	public char[] getSolution() {
		return solutionArr;
	}
	
	/**
	 * returns the answer array (user's input) for the game
	 * @return
	 */
	public char[] getAnswer() {
		return answerArr;
	}
	
	/**
	 * returns the puzzle array for the game
	 * @return
	 */
	public char[] getPuzzle() {
		return puzzleArr;
	}
	
	/**
	 * returns the solution string for the game
	 * @return
	 */
	public String getSolutionStr() {
		return solutionStr;
	}
	
	/**
	 * Create the puzzle by copying non-word characters and mixing up
	 * the words in the solution array.
	 */
	private void createGame() {
		
		int start = 0;
		int end;
		for(int ii = 0; ii < solutionArr.length; ii++) {
			// any non-word character
			// For a -, the char before should be ' ' otherwise it's in a word
			if(((solutionArr[ii] < 'A' || solutionArr[ii] > 'Z') && solutionArr[ii] != '\'') || (solutionArr[ii] == '-' && solutionArr[ii-1] == ' ')) {
				puzzleArr[ii] = solutionArr[ii];
				if(start == ii) {
					start++;
				}
				else {
					end = ii - 1;
					mixupWord(start, end, puzzleArr, solutionArr);
					start = ii+1;
				}
			}
		}
	}
	
	/**
	 * Randomly mixes up the order of a word located in solution from
	 * solution[start] to solution[end] inclusive and copies it to
	 * the game array in the same indices.
	 * @param start index of the first letter of the word
	 * @param end index of the last letter of the word
	 * @param game array the mixed up word is copied to
	 * @param solution source of the word
	 */
	public void mixupWord(int start, int end, char[] game, char[] solution) {
		//doesn't make sense, let's just get out
		if(start > end ) 
			return;
		//one letter word, no mixing needed
		else if(start == end) {
			game[start] = solution[start];
			return;
		}
		
		Random rand = new Random();
		int length = end - start + 1;
		int[] used = new int[length];
		int newLetter;
		
		for(int ii = 0; ii <length; ii ++) {
			newLetter = rand.nextInt(length);
			while(used[newLetter] == 1) {
				newLetter++;
				if(newLetter == length) {
					newLetter = 0;
				}
			}
			used[newLetter] = 1;
			game[start + ii] = solution[start + newLetter];
		}
	}
	
	/**
	 * save key information before onStop or onDestroy is called
	 * Other info like time played can be added later
	 */
	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		
		bundle.putString("solutionStr", solutionStr);
		bundle.putCharArray("solutionArr", solutionArr);
		bundle.putCharArray("puzzleArr", puzzleArr);
		bundle.putCharArray("answerArr", answerArr);
	}
	
	private boolean isWordChar(char[] charArray, int ii) {
		if(ii >= charArray.length) {
			return false;
		}
		if(((charArray[ii] < 'A' || charArray[ii] > 'Z') && charArray[ii] != '\'') || (charArray[ii] == '-' && charArray[ii-1] == ' ')) {
			return false;
		}
		else
			return true;
	}
}
