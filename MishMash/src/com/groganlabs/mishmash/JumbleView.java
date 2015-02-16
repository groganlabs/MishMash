package com.groganlabs.mishmash;

import android.content.Context;
import android.graphics.Canvas;
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
	public String test = "test";
	
	/**
	 * constructor used to create view from code
	 * @param context
	 */
	public JumbleView(Context context) {
		super(context);
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
		mBgPaint.setColor(0xff000000);
		
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

	@Override
	protected void onDraw(Canvas canvas) {
		int maxCharsPerRow = (int) ((int) mW / mCharSize);
		int maxRows = 10;
		super.onDraw(canvas);
		mTextPaint.setTextSize(mCharSize);
		
		/*char[][] gameArray = {{'B', 'C', 'J', 'M', ' ', 'M', 'A', 'Q', ' ', 'P', 'J', 'M'},
				{'A', 'L', 'P', ' ', 'J', 'M', 'U', 'I', ' ', 'U', 'O', 'N', 'Y'},
				{'M', 'A', 'J', 'M', 'S', 'O', 'N', ' ', 'I', 'S', 'T', 'S', 'R'}};
		char[][] answerArray = new char[3][];*/
		String gameStr = "THIS IS A TEST. THIS IS ONLY A TEST. PLEASE DO NOT ADJUST YOUR SCREENS.";
		char[] gameArray = gameStr.toCharArray();
		char[] answerArray = new char[gameArray.length];
		char[] solutionArray = new char[gameArray.length];
		
		int ii, lastSpace = 0, rowSize = 0;
		mNumRows = 0;
		int[] rowIndices = new int[maxRows];
		rowIndices[0] = 0;
		
		while(true) {
			if(rowIndices[mNumRows] + maxCharsPerRow >= gameArray.length) {
				mNumRows++;
				break;
			}
			
			lastSpace = gameStr.lastIndexOf(' ', rowIndices[mNumRows] + maxCharsPerRow);
			mNumRows++;
			// TODO: insert a - into the arrays & string
			if(lastSpace == -1) {
				rowIndices[mNumRows] = rowIndices[mNumRows-1] + maxCharsPerRow;
			}
			else {
				rowIndices[mNumRows] = lastSpace;
			}
		}
		
		mYPad = 20f;
		
		mXPad = new int[mNumRows];
		String answer;
		int jj;
		
		float aCharWidth, gCharWidth, aCharPadding, gCharPadding;
		
		for(ii = 0; ii < mNumRows; ii++) {
			if(ii == mNumRows - 1) {
				//rowSize = gameArray.length - rowIndices[ii] - 1;
				rowSize = gameArray.length - rowIndices[ii];
				mXPad[ii] = (int) ((mW - (rowSize * mCharSize))/2 - mCharSize);
			}
			else if(ii > 0) {
				//rowSize = rowIndices[ii+1] - rowIndices[ii] - 1;
				rowSize = rowIndices[ii+1] - rowIndices[ii];
				mXPad[ii] = (int) ((mW - (rowSize * mCharSize))/2 - mCharSize);
			}
			else {
				rowSize = rowIndices[ii+1] - rowIndices[ii];
				mXPad[ii] = (int) ((mW - (rowSize * mCharSize))/2);
			}
			
			//mXPad[ii] = (int) ((mW - (rowSize * mCharSize))/2);
			
			for(jj = 0; jj < rowSize; jj++) {
				if(ii == 0 && jj == rowSize) {
					continue;
				}
				
				if(gameArray[rowIndices[ii]+jj] != ' ') {
					if(gameArray[rowIndices[ii]+jj] < 'A' || gameArray[rowIndices[ii]+jj] > 'Z') {
						answerArray[rowIndices[ii]+jj] = gameArray[rowIndices[ii]+jj];	
					}
					if(answerArray[rowIndices[ii]+jj] == 0 || answerArray[rowIndices[ii]+jj] == ' ') {
						answer = "_";
					}
					else {
						answer = String.valueOf(answerArray[rowIndices[ii]+jj]);
					}
					aCharWidth = mTextPaint.measureText(answer);
					gCharWidth = mTextPaint.measureText(gameArray, rowIndices[ii]+jj, 1);
					aCharPadding = mCharSize-aCharWidth/2f;
					gCharPadding = mCharSize-gCharWidth/2f;
					canvas.drawText(answer, mXPad[ii]+(mCharSize*jj)+aCharPadding, mCharSize*(2f*ii+1)+(mYPad*ii), mTextPaint);
					canvas.drawText(gameArray, rowIndices[ii]+jj, 1, mXPad[ii]+(mCharSize*jj)+gCharPadding, mCharSize*(2f*ii+2)+(mYPad*ii), mTextPaint);
				}
			}
		}
	}
}
