package com.groganlabs.mishmash;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.jumble);
		
		//JumbleView jumble = (JumbleView) findViewById(R.id.jumbleView1);
		
		solutionStr = "THIS IS A TEST. THIS IS ONLY A TEST. PLEASE DO NOT ADJUST YOUR SCREENS.";
		solutionArr = solutionStr.toCharArray();
		puzzleArr = solutionArr;
		answerArr = new char[solutionArr.length];
		
		for(int ii = 0; ii < solutionArr.length; ii++) {
			if(solutionArr[ii] < 'A' || solutionArr[ii] > 'Z') {
				answerArr[ii] = solutionArr[ii];
			}
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
				Log.d("Jumble", "puzzle was touched at "+e.getX()+" by "+e.getY());
				return gameTouch(e.getX(), e.getY());
			}
		});
		
		//AlphaView alpha = (AlphaView) findViewById(R.id.alphaView1);
		
		alpha.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				char res = ((AlphaView) v).getLetter(event.getX(), event.getY());
				Log.d("jumbleActivity", "you pressed "+String.valueOf(res));
				//if we've highlighted a letter
				int curChar;
				if((curChar = jumble.getHighlight()) >= 0) {
					answerArr[curChar] = res;
					jumble.invalidate();
					//if answerArr == solutionArr, notify player and add to db
				}
				//If no letter is selected, do nothing
				//Should keyboard be grayed out if no letter's selected?
				//
				return false;
			}
		});
	}

	public boolean gameTouch(float x, float y) {
		//If no letter touched, and a letter was highlighted
		//Tell the view to remove the highlight and draw
		//Else
		//Get the letter selected - index of the array
		//Save the index in the activity
		//Have the view highlight that character and draw
		int touched = jumble.getTouched(x, y);
		int newC;
		if(touched < 0) {
			Log.d("jumble", "nothing touched");
			newC = -1;
		}
		else if(touched >= solutionStr.length()) {
			Log.d("jumble", "Oops, too high: "+String.valueOf(touched));
			newC = -1;
		}
		else {
			Log.d("jumble", solutionArr[touched]+" was touched");
			if(solutionArr[touched] >= 'A' && solutionArr[touched] <= 'Z')
				newC = touched;
			else
				newC = -1;
		}
		Boolean draw = jumble.setHighlight(newC);
		if(draw)
			jumble.invalidate();
		return false;
	}
	
	public char[] getSolution() {
		return solutionArr;
	}
	
	public char[] getAnswer() {
		return answerArr;
	}
	
	public char[] getPuzzle() {
		return puzzleArr;
	}
	
	public String getSolutionStr() {
		return solutionStr;
	}
}
