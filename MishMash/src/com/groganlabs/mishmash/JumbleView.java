package com.groganlabs.mishmash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class JumbleView extends View{
	private Paint mTextPaint;
	private Paint mBgPaint;
	private Typeface mTypeface;
	private Context mContext;
	private int mH, mW;
	private float mCharSize, mFontSize;
	
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
	
	private void init() {
		mFontSize = 18 * mContext.getResources().getDisplayMetrics().density;
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setColor(0xffffffff);
		Log.d("JumbleView", "char size 1: " + String.valueOf(mCharSize));
		
		mBgPaint = new Paint();
		mBgPaint.setColor(0xff000000);
		
		mTypeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);
		//mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/DEJAVUSANSMOMO.TTF");
		mBgPaint.setTypeface(mTypeface);
		
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mW = w;
		mH = h;
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
		setMeasuredDimension(w, h-200);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int numCharsPerRow = (int) ((int) mW / mCharSize);
		int maxRows = 10;
		super.onDraw(canvas);
		mTextPaint.setTextSize(mCharSize);
		
		/*char[][] gameArray = {{'B', 'C', 'J', 'M', ' ', 'M', 'A', 'Q', ' ', 'P', 'J', 'M'},
				{'A', 'L', 'P', ' ', 'J', 'M', 'U', 'I', ' ', 'U', 'O', 'N', 'Y'},
				{'M', 'A', 'J', 'M', 'S', 'O', 'N', ' ', 'I', 'S', 'T', 'S', 'R'}};
		char[][] answerArray = new char[3][];*/
		String gameStr = "THIS IS A TEST. THIS IS ONLY A TEST. PLEASE DO NOT ADJUST YOUR SCREENS.";
		char[] game = gameStr.toCharArray();
		char[] answer = new char[game.length];
		
		int ii, index = 0, numRows = 0;
		int[] rowIndices = new int[maxRows];
		for(ii = 0; ii < game.length; ii++) {
			
		}
		answerArray[0] = new char[gameArray[0].length];
		answerArray[1] = new char[gameArray[1].length];
		answerArray[2] = new char[gameArray[2].length];
		answerArray[0][3] = 'T';
		answerArray[0][10] = 'E';
		answerArray[1][1] = 'N';
		answerArray[2][4] = 'E';
		float yPad = 20f;
		
		int[] xPad = new int[gameArray.length];
		String answer;
		int ii, jj;
		float aCharWidth, gCharWidth, aCharPadding, gCharPadding;
		
		for(ii = 0; ii < gameArray.length; ii++) {
			xPad[ii] = (int) ((mW - (gameArray[ii].length * mCharSize))/2);
			
			for(jj = 0; jj < gameArray[ii].length; jj++) {
				if(gameArray[ii][jj] != ' ') {
					if(answerArray[ii][jj] == 0 || answerArray[ii][jj] == ' ') {
						answer = "_";
					}
					else {
						answer = String.valueOf(answerArray[ii][jj]);
					}
					aCharWidth = mTextPaint.measureText(answer);
					gCharWidth = mTextPaint.measureText(gameArray[ii], jj, 1);
					aCharPadding = mCharSize-aCharWidth/2f;
					gCharPadding = mCharSize-gCharWidth/2f;
					canvas.drawText(answer, xPad[ii]+(mCharSize*jj)+aCharPadding, mCharSize*(2f*ii+1)+(yPad*ii), mTextPaint);
					canvas.drawText(gameArray[ii], jj, 1,xPad[ii]+(mCharSize*jj)+gCharPadding, mCharSize*(2f*ii+2)+(yPad*ii), mTextPaint);
				}
			}
			
		}
		
	}
}
