package com.groganlabs.mishmash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class JumbleView extends View{
	private Paint mTextPaint;
	private Paint mBgPaint;
	private Typeface mTypeface;
	private Context mContext;
	private int mH, mW;
	
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
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setColor(0xffffffff);
		mTextPaint.setTextSize(32);
		
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
	protected void onMeasure(int w, int h) {
		setMeasuredDimension(w, h-200);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int charSize = 32;
		
		//char[] chars = {'H', 'I', 'M', 'W'};
		//float[] ints = new float[4];
		char[][] gameArray = {{'B', 'C', 'J', 'M', ' ', 'M', 'A', 'Q', ' ', 'P', 'J', 'M'},
				{'A', 'L', 'P', ' ', 'J', 'M', 'U', 'I', ' ', 'U', 'O', 'N', 'Y'},
				{'M', 'A', 'J', 'M', 'S', 'O', 'N', ' ', 'I', 'S', 'T', 'S', 'R'}};
		char[][] answerArray = new char[3][];
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
		/*for(int ii = 0; ii < 4; ii++) {
			canvas.drawText(chars, ii, 1, w*ii, 32, mTextPaint);
			canvas.drawText(String.valueOf(ints[ii]), w*ii, w*(ii+2), mTextPaint);
			canvas.drawText(String.valueOf(mTextPaint.measureText(chars, ii, 1)), w*ii, w*ii+4*w, mTextPaint);
		}*/
		int ii, jj;
		for(ii = 0; ii < gameArray.length; ii++) {
			xPad[ii] = (mW - (gameArray[ii].length * charSize))/2;
			
			for(jj = 0; jj < gameArray[ii].length; jj++) {
				if(gameArray[ii][jj] != ' ') {
					if(answerArray[ii][jj] == 0 || answerArray[ii][jj] == ' ') {
						answer = "_";
					}
					else {
						answer = String.valueOf(answerArray[ii][jj]);
					}
					canvas.drawText(answer, xPad[ii]+(charSize*jj), charSize*(2f*ii+1)+(yPad*ii), mTextPaint);
					canvas.drawText(gameArray[ii], jj, 1,xPad[ii]+(charSize*jj), charSize*(2f*ii+2)+(yPad*ii), mTextPaint);
				}
			}
			
		}
		
	}
}
