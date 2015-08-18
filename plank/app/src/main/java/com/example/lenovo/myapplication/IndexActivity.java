package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class IndexActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.index);
		new Thread(new delayThread()).start();


	}
	public class delayThread implements Runnable{

		@Override
		public void run() {
			try {
				Thread.sleep(3000);
				Intent intent = new Intent(IndexActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

}
