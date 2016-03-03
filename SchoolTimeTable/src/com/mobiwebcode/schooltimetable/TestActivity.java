package com.mobiwebcode.schooltimetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_main);
		
		
		Button btnexit = (Button) findViewById(R.id.btnExit);

		btnexit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(TestActivity.this,
					OptionActivity.class);
				startActivity(myintent2);

			}
		});
	}
}
