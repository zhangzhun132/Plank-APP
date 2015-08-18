package com.example.lenovo.myapplication;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class SetScroll extends ScrollView {
	private float dx;
	private float dy;
	private float l = 100;

	public SetScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFadingEdgeLength(0);
	}







	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		System.out.println("--------正在滚动-------");

		float x = ev.getX();
		float y = ev.getY();

		switch(ev.getAction()){
			case MotionEvent.ACTION_DOWN:
				System.out.println("ACTION_DOWN");
				dx = x;
				dy = y;
				break;
			case MotionEvent.ACTION_MOVE:

				System.out.println("ACTION_MOVE=======");
				if(y-dy<100){
					return false;
				}
				break;
		}
		return false;
	}










}






















