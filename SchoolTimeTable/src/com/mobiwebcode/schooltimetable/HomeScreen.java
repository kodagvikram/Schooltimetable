package com.mobiwebcode.schooltimetable;



import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.mobiwebcode.schooltimetable.VO.Constant;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends Activity {
	
	private void copyDatabase() {
		try {
			File f = new File(Constant.path);
			File f2 = new File(Constant.DB_PATH);
			if (!f.exists()) {
				InputStream in = getAssets().open("SchoolTimeTable.sqlite");
				if (!f2.exists())
					f2.mkdir();
				OutputStream out = new FileOutputStream(Constant.path);

				byte[] buffer = new byte[1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				in.close();
				out.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);
		Button btnsetuptimetable = (Button) findViewById(R.id.btn_setuptimetable);
		Button btngeneratetimetable = (Button) findViewById(R.id.btn_generatetimetable);
		Button btngradecalculate = (Button) findViewById(R.id.btn_gradecalculate);
		Button btntertiary = (Button) findViewById(R.id.btn_tertiary);
		Button btndefination = (Button) findViewById(R.id.btn_Definition);
		// Button btnsetting = (Button) findViewById(R.id.btn_setuptimetable);
		Button btnpasco = (Button) findViewById(R.id.btn_pasco);
		Button btniqtest = (Button) findViewById(R.id.btn_iqtest);
		Button btnedutainment = (Button) findViewById(R.id.btn_edutainment);
		Button btnchat = (Button) findViewById(R.id.btn_chat);
		
		copyDatabase();
		Constant.schooltimetable = SQLiteDatabase.openDatabase(Constant.path,
				null, SQLiteDatabase.OPEN_READWRITE);

		btnsetuptimetable.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(HomeScreen.this,
						OptionActivity.class);
				startActivity(myintent2);

			}
		});

		btngradecalculate.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(HomeScreen.this,
						GradecalculatorActivity.class);
				startActivity(myintent2);

			}
		});

		btntertiary.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(HomeScreen.this,
						TertiaryinfoActivity.class);
				startActivity(myintent2);

			}
		});

		btndefination.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(HomeScreen.this,
						SelectsubjectDefination.class);
				startActivity(myintent2);

			}
		});

		btnpasco.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(HomeScreen.this,
						PascoActivity.class);
				startActivity(myintent2);

			}
		});

		btniqtest.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(HomeScreen.this,
						IQTestActivity.class);
				startActivity(myintent2);

			}
		});
		btnedutainment.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent3 = new Intent(HomeScreen.this,
						EdutainmentActivity.class);
				startActivity(myintent3);

			}
		});
		btnchat.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent3 = new Intent(HomeScreen.this,
						ChatActivity.class);
				startActivity(myintent3);

			}
		});

		

	
	}
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

}
