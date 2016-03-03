package com.mobiwebcode.schooltimetable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mobiwebcode.schooltimetable.VO.SchoolSingleton;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class TrainingColleges extends Activity {

	private CheckBox coremaths, physics, chemistry;
	private Button Clickhere;
	private ProgressDialog mProgressDialog;
	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
	String responseString = "", other = "other", selectedcountry = "";
	public SchoolSingleton singleton;

	ArrayList<SchoollistActivityVo> schoollist = new ArrayList<SchoollistActivityVo>();
	public static SchoollistActivityVo slVo = new SchoollistActivityVo();

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS1:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Processing request, Please wait ...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			return mProgressDialog;

		default:
			return null;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.trainingcolleges);
		try{
		singleton=SchoolSingleton.getinstance(this);
		Button btnnursingcolleges = (Button) findViewById(R.id.buttonnursingcolleges);

		btnnursingcolleges.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				singleton.SelectedSchooltype="Nursing Training";
				
				Intent myintent2 = new Intent(TrainingColleges.this,
						SchoollistActivity.class);
				startActivity(myintent2);

			}
		});

		Button btnteachercolleges = (Button) findViewById(R.id.buttonteachertrainingcolleges);

		btnteachercolleges.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				singleton.SelectedSchooltype="Teacher Training";
				Intent myintent2 = new Intent(TrainingColleges.this,
						SchoollistActivity.class);
				startActivity(myintent2);

			}
		});
		Button btnothers = (Button) findViewById(R.id.buttonothers);

		btnothers.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				singleton.SelectedSchooltype="Other";
				//SchoollistActivity.schooltype="other";
				Intent myintent2 = new Intent(TrainingColleges.this,
						SchoollistActivity.class);
				startActivity(myintent2);
				
			}
		});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}