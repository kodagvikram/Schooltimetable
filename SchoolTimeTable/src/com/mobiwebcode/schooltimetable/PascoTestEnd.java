package com.mobiwebcode.schooltimetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PascoTestEnd extends Activity {
	Button endsession, restart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pascotestend);

		endsession = (Button) findViewById(R.id.endsession);
		restart = (Button) findViewById(R.id.restart);

		endsession.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent session = new Intent(PascoTestEnd.this,
						PascoActivity.class);
				startActivity(session);
			}
		});

		restart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent session = new Intent(PascoTestEnd.this,
						PascoQuestionActivity.class);
				startActivity(session);
			}
		});

	}
}
