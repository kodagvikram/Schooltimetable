package com.mobiwebcode.schooltimetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import app.tabsample.SmartImageView.NormalSmartImageView;

public class SchoolDescription extends Activity {
	TextView schoolnameTextView, schoolnameTextView1,
			schooldescriptionTextView;
	NormalSmartImageView schoolimage;
	public static SchoollistActivityVo schoolVO = new SchoollistActivityVo();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schooldescription);
		schoolnameTextView = (TextView) findViewById(R.id.schoolnameTextView);
		schoolnameTextView1 = (TextView) findViewById(R.id.schoolnameTextView1);
		schooldescriptionTextView = (TextView) findViewById(R.id.schooldescriptionTextView);
		schoolimage = (NormalSmartImageView) findViewById(R.id.schoolimage);

		schoolimage.setImageUrl(schoolVO.schoolpicture);
		schoolnameTextView.setText(schoolVO.schoolname);
		schoolnameTextView1.setText(schoolVO.schoolname);
		schooldescriptionTextView.setText(schoolVO.schoolinfo);

	}
}
