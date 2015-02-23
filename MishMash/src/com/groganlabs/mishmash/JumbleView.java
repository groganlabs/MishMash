package com.groganlabs.mishmash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class JumbleView extends View{
	private Paint mTextPaint;
	private Paint mBgPaint;
	//private Typeface mTypeface;
	private Context mContext;
	private int mW;
	private float mCharSize, mFontSize, mYPad;
	private int[] mXPad;
	private int mNumRows;
	private int mHighlighted = -1;
	private int[] mRowIndices;
	public String test = "test";
	
	/**
	 * constructor used to create view from code
	 * @param context
	 */
	public JumbleView(Context context) {
		super(context);
		mContext = context;
		init();
	}
	
	/**
	 * Constructor used to create view from xml
	 * @param context
	 * @param attrs
	 */
	public JumbleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	public float getCharSize() {
		return mCharSize;
	}
	
	public int getNumRows() {
		return mNumRows;
	}
	
	public float getYPad() {
		return mYPad;
	}
	
	public int getXPad(int ii) {
		if(ii < mNumRows) {
			return mXPad[ii];
		}
		else {
			return -1;
		}
	}
	
	private void init() {
		mFontSize = 18 * mContext.getResources().getDisplayMetrics().density;
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setColor(0xffffffff);
		Log.d("JumbleView", "char size 1: " + String.valueOf(mCharSize));
		
		mBgPaint = new Paint();
		mBgPaint.setColor(Color.DKGRAY);
		
		//mTypeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);
		//mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/DEJAVUSANSMOMO.TTF");
		//mBgPaint.setTypeface(mTypeface);
		
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mW = w;
	}
	
	@Override
	protected void onMeasure(int wMS, int hMS) {
		int w = MeasureSpec.getSize(wMS);
		int h = MeasureSpec.getSize(hMS);
		float minCharSize = w / 30f;
		Log.d("JumbleView", "width: " + String.valueOf(w));
		if(minCharSize >= mFontSize) {
			mCharSize = minCharSize;
		}
		else {
			mCharSize = mFontSize;
		}
		Log.d("JumbleView", "char size 2: " + String.valueOf(mCharSize));
		//instead of subtracting a constant, figure out how many pixels based on orientation,
		//screen width and pixel density. Need to figure out a way to do this without duplicating
		//the same calculations in AlphaView
		setMeasuredDimension(w, h-500);
	}
	
	/**
	 * Takes the x and y coordinates of a touch event and returns 
	 * the index of the letter touched, or -1 if no letter.
	 * @param x
	 * @param y
	 * @return the index of the letter touched, or -1
	 */
	public int getTouched(float x, float y) {
		int row = (int) Math.floor(y / (mCharSize * 2 + mYPad));
		Log.d("jumbleView", "row touched: "+String.valueOf(row));
		int retChar= 0, col;
		//touch registered below the game
		if(row >= mNumRows) {
			retChar = -1;
		}
		else {
			//see if x is outside the printed row
			if(x < mXPad[row] || x > mW - mXPad[row]) {
				retChar = -1;
			}
			else {
				col = (int) Math.floor((x - mXPad[row])/(mCharSize));
				Log.d("jumbleView", "col touched: "+String.valueOf(col));
				retChar += mRowIndices[row];
				retChar += col - 1;
			}
		}
		return retChar;
	}

	public boolean setHighlight(int h) {
		if(mHighlighted == h)
			return false;
		else {
			mHighlighted = h;
			return true;
		}
	}
	
	public int getHighlight() {
		return mHighlighted;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d("jumbleView", "Drawing...");
		Log.d("jumbleView", "Highlighted: "+String.valueOf(mHighlighted));
		int maxCharsPerRow = (int) ((int) mW / mCharSize);
		
		// TODO: set this based off of the size of the screen.
		// could be moved to onMeasure?
		int maxRows = 10;
		
		mTextPaint.setTextSize(mCharSize);
		
		/*char[][] gameArray = {{'B', 'C', 'J', 'M', ' ', 'M', 'A', 'Q', ' ', 'P', 'J', 'M'},
				{'A', 'L', 'P', ' ', 'J', 'M', 'U', 'I', ' ', 'U', 'O', 'N', 'Y'},
				{'M', 'A', 'J', 'M', 'S', 'O', 'N', ' ', 'I', 'S', 'T', 'S', 'R'}};
		char[][] answerArray = new char[3][];*/
		String gameStr = ( (JumbleActivity) mContext).getSolutionStr();
		char[] gameArray = ( (JumbleActivity) mContext).getPuzzle();
		char[] answerArray = ( (JumbleActivity) mContext).getAnswer();
		char[] solutionArray = ( (JumbleActivity) mContext).getSolution();
		
		int ii, lastSpace = 0, rowSize = 0;
		mNumRows = 0;
		
		//mRowIndices indicates where each row starts (1st row at 0, second at mRowIndices[1], etc)
		mRowIndices = new int[maxRows];
		mRowIndices[0] = 0;
		
		// Loop through and find the line breaks - maybe move this to onMeasure?
		// also determine how many lines we actually ave
		while(true) {
			if(mRowIndices[mNumRows] + maxCharsPerRow >= gameArray.length) {
				mNumRows++;
				break;
			}
			
			lastSpace = gameStr.lastIndexOf(' ', mRowIndices[mNumRows] + maxCharsPerRow);
			mNumRows++;
			// TODO: insert a - into the arrays & string in case of hyphenation?
			if(lastSpace == -1) {
				mRowIndices[mNumRows] = mRowIndices[mNumRows-1] + maxCharsPerRow;
			}
			else {
				mRowIndices[mNumRows] = lastSpace;
			}
		}
		
		mYPad = 20f;
		
		mXPad = new int[mNumRows];
		String answer;
		int jj;
		
		float aCharWidth, gCharWidth, aCharPadding, gCharPadding;
		
		//drawing the game, line by line
		//Starting by finding the margin for the row
		for(ii = 0; ii < mNumRows; ii++) {
			//first row
			if(ii == 0) {
				rowSize = mRowIndices[ii+1] - mRowIndices[ii];
				mXPad[ii] = (int) ((mW - (rowSize * mCharSize))/2);
			}
			//last row
			else if(ii == mNumRows - 1) {
				//rowSize = gameArray.length - mRowIndices[ii] - 1;
				rowSize = gameArray.length - mRowIndices[ii];
				mXPad[ii] = (int) ((mW - (rowSize * mCharSize))/2 - mCharSize);
			}
			//middle rows
			else {
				//rowSize = mRowIndices[ii+1] - mRowIndices[ii] - 1;
				rowSize = mRowIndices[ii+1] - mRowIndices[ii];
				mXPad[ii] = (int) ((mW - (rowSize * mCharSize))/2 - mCharSize);
			}
			
			//drawing the characters for the line
			for(jj = 0; jj < rowSize; jj++) {
				if(ii == 0 && jj == rowSize) {
					continue;
				}
				
				//don't need to do anything for spaces
				if(gameArray[mRowIndices[ii]+jj] != ' ') {
					//find out if we have an answer or a blank
					if(answerArray[mRowIndices[ii]+jj] == 0 || answerArray[mRowIndices[ii]+jj] == ' ') {
						answer = "_";
					}
					else {
						answer = String.valueOf(answerArray[mRowIndices[ii]+jj]);
					}
					
					//code for centering the individual letters
					aCharWidth = mTextPaint.measureText(answer);
					gCharWidth = mTextPaint.measureText(gameArray, mRowIndices[ii]+jj, 1);
					aCharPadding = (mCharSize-aCharWidth)/2f;
					gCharPadding = (mCharSize-gCharWidth)/2f;
					
					//highlighting the selected letter
					if(mRowIndices[ii]+jj == mHighlighted) {
						canvas.drawRect(mXPad[ii]+(mCharSize*jj), mCharSize*(2f*ii+1)+(mYPad*ii), mXPad[ii]+(mCharSize*jj) + mCharSize, mCharSize*(2f*ii+1)+(mYPad*ii)-mCharSize, mBgPaint);
					}
					canvas.drawText(answer, mXPad[ii]+(mCharSize*jj)+aCharPadding, mCharSize*(2f*ii+1)+(mYPad*ii), mTextPaint);
					canvas.drawText(gameArray, mRowIndices[ii]+jj, 1, mXPad[ii]+(mCharSize*jj)+gCharPadding, mCharSize*(2f*ii+2)+(mYPad*ii), mTextPaint);
				}
			}
		}
	}
}
