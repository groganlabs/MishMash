package com.groganlabs.mishmash;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

public class AlphaView extends View {
	Context mContext;
	Boolean mPortrait;
	int mW;
	int mH;

	/**
	 * Constructor to create view from code
	 * @param context
	 */
	public AlphaView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
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
		//anything to do here?
		
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
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
		    mPortrait = false;
		}
		
		/* Similar to the JumbleView, need to set char size and spacing
		 * use the horizontal or portrait to determine size
		 * portrait = 3x10, landscape = 2x14 or 2x15
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
		setMeasuredDimension(w, h-200);*/
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(mPortrait) {
			for(char ch = 'A'; ch <= 'J'; ch++) {
				
			}
			
			for(char ch = 'K'; ch <= 'T'; ch++) {
				
			}
			
			for(char ch = 'U'; ch <= 'Z'; ch++) {
				
			}
			//draw delete character
		}
		else {
			for(char ch = 'A'; ch <= 'O'; ch++) {
				
			}
			
			for(char ch = 'P'; ch <= 'Z'; ch++) {
				
			}
			//again, draw delete character
		}
	}	
}
/*public class AlphaView extends LinearLayout {

	public AlphaView(Context context) {
		super(context);
		initAlphaView(context);
	}
	
	public AlphaView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAlphaView(context);
	}
	
	private void initAlphaView(Context context) {
		String infS = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		
		li = (LayoutInflater)context.getSystemService(infS);
		li.inflate(R.layout.alpha, this);
		
	}
	
}*/
