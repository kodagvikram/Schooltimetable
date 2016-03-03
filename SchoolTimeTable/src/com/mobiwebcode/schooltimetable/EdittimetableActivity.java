package com.mobiwebcode.schooltimetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class EdittimetableActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.edittimetableactivity);

		Button btnedittimetable = (Button) findViewById(R.id.btn_edittimetable);

		btnedittimetable.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(EdittimetableActivity.this,
						CreateTimetable.class);
				startActivity(myintent2);

			}
		});

	}

	public void onBackPressed() {
		Intent i = new Intent(this, OptionActivity.class);
		i.putExtra("exit", true);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		super.onBackPressed();
	}
}