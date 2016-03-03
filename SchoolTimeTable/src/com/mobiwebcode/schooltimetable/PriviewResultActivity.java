package com.mobiwebcode.schooltimetable;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PriviewResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.previewresult_main);
		Button buttonback=(Button)findViewById(R.id.btn_back);


		
	
	}public void onBackPressed() {
        Intent i = new Intent(this,GradecalculatorActivity.class);
    i.putExtra("exit", true);
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(i);
    super.onBackPressed();
    }

		}

