package com.groganlabs.mishmash;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class AlphaView extends LinearLayout {

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
	
}
