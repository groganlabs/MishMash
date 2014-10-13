package com.groganlabs.mishmash;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.groganlabs.mishmash.JumbleView;

public class JumbleActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jumble);
		
		JumbleView jumble = (JumbleView) findViewById(R.id.jumbleView1);
		
		jumble.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent e) {
				Log.d("Jumble", "puzzle was touched at "+e.getX()+" by "+e.getY());
				return gameTouch(e.getX(), e.getY());
			}
		});
	}

	public boolean gameTouch(float x, float y) {
		JumbleView jumble = (JumbleView) findViewById(R.id.jumbleView1);
		
		return false;
	}
	
}
