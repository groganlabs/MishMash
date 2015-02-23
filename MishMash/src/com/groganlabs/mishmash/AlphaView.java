package com.groganlabs.mishmash;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class AlphaView extends View {
	Context mContext;
	Boolean mPortrait;
	int mW;
	int mH;
	float mFontSize, mCharSize;
	private Paint mTextPaint, mBgPaint;
	private String mDelete = "<-x-]";

	/**
	 * Constructor to create view from code
	 * @param context
	 */
	public AlphaView(Context context) {
		super(context);
		mContext = context;
		init();
	}
	
	/**
	 * Constructor used to create view from xml
	 * @param context
	 * @param attrs
	 */
	public AlphaView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	private void init() {
		//do I need to determine a minimum size?
		mFontSize = 18 * mContext.getResources().getDisplayMetrics().density;
		//setup Paint object
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setColor(0xffffffff);
		
		mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBgPaint.setColor(Color.GREEN);
		mBgPaint.setStyle(Style.FILL);
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
		//int h = MeasureSpec.getSize(hMS);
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
		    mPortrait = false;
		}
		else {
			mPortrait = true;
		}
		float minCharSize;
		if(mPortrait) {
			minCharSize = w / 20f;
		}
		else {
			minCharSize = w / 30f;
		}
		
		//might be able to skip this step
		//if(minCharSize >= mFontSize) {
			mCharSize = minCharSize;
		//}
		//else {
		//	mCharSize = mFontSize;
		//}
		
		int newH;
		if(mPortrait) {
			newH = (int) (mCharSize * 4);
		}
		else {
			newH = (int) (mCharSize * 3);
		}
		setMeasuredDimension(w, newH);
		/* Similar to the JumbleView, need to set char size and spacing
		 * use the horizontal or portrait to determine size
		 * portrait = 3x10, landscape = 2x14 or 2x15
		Log.d("JumbleView", "width: " + String.valueOf(w));
		
		Log.d("JumbleView", "char size 2: " + String.valueOf(mCharSize));
		setMeasuredDimension(w, h-200);*/
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//filler for displaying element size - for dev testing only
		//canvas.drawPaint(mBgPaint);
		mTextPaint.setTextSize(mCharSize);
		//start at 0,0
		float xPad = .5f * mCharSize;
		float y = mCharSize;;
		float x = xPad;
		if(mPortrait) {
			for(char ch = 'A'; ch <= 'J'; ch++) {
				//canvas.drawText(String, float, float, Paint)
				canvas.drawText(String.valueOf(ch), x, y, mTextPaint);
				x += 2 * mCharSize;
			}
			
			x = xPad;
			y += 1.5f * mCharSize;
			for(char ch = 'K'; ch <= 'T'; ch++) {
				canvas.drawText(String.valueOf(ch), x, y, mTextPaint);
				x += 2 * mCharSize;
			}
			
			x = xPad;
			y += 1.5f * mCharSize;
			for(char ch = 'U'; ch <= 'Z'; ch++) {
				canvas.drawText(String.valueOf(ch), x, y, mTextPaint);
				x += 2 * mCharSize;
			}
			//draw delete character
			canvas.drawText(mDelete, x, y, mTextPaint);
		}
		else {
			for(char ch = 'A'; ch <= 'O'; ch++) {
				canvas.drawText(String.valueOf(ch), x, y, mTextPaint);
				x += 2 * mCharSize;
			}
			
			x = xPad;
			y += 1.5f * mCharSize;
			for(char ch = 'P'; ch <= 'Z'; ch++) {
				canvas.drawText(String.valueOf(ch), x, y, mTextPaint);
				x += 2 * mCharSize;
			}
			//again, draw delete character
			canvas.drawText(mDelete, x, y, mTextPaint);
		}
	}	
	
	/**
	 * Takes the x and y values of a touch event and returns the appropriate character.
	 * If the delete section is touched, return 'd'.
	 * If no character is touched, return ' '.
	 * @param x
	 * @param y
	 * @return char
	 */
	public char getLetter(float x, float y) {
		//row = y / (mCharSize * 1.5f) rounded up
		/*Log.d("alpha", "x = "+String.valueOf(x));
		Log.d("alpha", "y = "+String.valueOf(y));
		Log.d("alpha", "charSize = "+String.valueOf(mCharSize)); */
		
		int row = (int) Math.ceil(y / (mCharSize * 1.5f));
		//Log.d("alpha", "row = "+String.valueOf(row));
		int charNum = (int) Math.ceil(x / (mCharSize * 2));
		//Log.d("alpha", "charNum = "+String.valueOf(charNum));
		char ret;
		if(mPortrait) {
			if(row == 1) {
				ret = (char) ('A' + charNum - 1);
			}
			else if(row == 2) {
				ret = (char) ('K' + charNum - 1);
			}
			else {
				if(charNum <= 6)
					ret = (char) ('U' + charNum - 1);
				else if(charNum <= 8)
					ret = ' ';
				else
					ret = ' ';
			}
		}
		else {
			if(row == 1) {
				ret = (char) ('A' + charNum - 1);
			}
			else {
				if(charNum <= 11)
					ret = (char) ('P' + charNum - 1);
				else if(charNum <= 13)
					ret = 'd';
				else
					ret = ' ';
			}
		}
		return ret;
	}
	
	public static float getHeight(float w, Boolean portrait) {
		float charSize;
		if(portrait) {
			charSize = w / 20f;
			return charSize * 4;
		}
		else {
			charSize = w / 30f;
			return charSize * 3;
		}
	}
}