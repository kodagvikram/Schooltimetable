package com.mobiwebcode.schooltimetable;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.mobiwebcode.schooltimetable.CreateTimetable.CustomList;
import com.mobiwebcode.schooltimetable.VO.Constant;
import com.mobiwebcode.schooltimetable.VO.MyTimetableVO;

public class TimetableActivity extends Activity implements OnClickListener {
	// database
	// Widget GUI
	EditText txtDate, txtTime;
	Button btngeneratetimetable;
	EditText subject;
	EditText teacher;
	EditText date;
	EditText starttime, endtime;
	public static MyTimetableVO editTimetableVO = new MyTimetableVO();
	// Variable for storing current date and time
	private int mYear, mMonth, mDay, mHour, mMinute;

	// enter your database name with extension, this name must same with
	// database on assets folder

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable_main);

		subject = (EditText) findViewById(R.id.subjectnameEditText);
		date = (EditText) findViewById(R.id.dateEditText);
		starttime = (EditText) findViewById(R.id.starttimeEditText);
		endtime = (EditText) findViewById(R.id.endTimeEditText);
		teacher = (EditText) findViewById(R.id.teachernameEditText);
		btngeneratetimetable = (Button) findViewById(R.id.btn_generatetimetable);
		if (editTimetableVO != null) {
			if (editTimetableVO.subjectname != null) {
				subject.setText(editTimetableVO.subjectname);
				date.setText(editTimetableVO.dateset);
				starttime.setText(editTimetableVO.timeset);
				endtime.setText(editTimetableVO.timeendset);
				teacher.setText(editTimetableVO.teachername);
				btngeneratetimetable.setText("Update Class");
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (v == btngeneratetimetable) {
			if (subject.getText().toString().equals("")
					|| date.getText().toString().equals("")
					|| starttime.getText().toString().equals("")
					|| endtime.getText().toString().equals("")
					|| teacher.getText().toString().equals("")) {
				final AlertDialog alertDialog = new AlertDialog.Builder(
						TimetableActivity.this).create();
				alertDialog.setIcon(R.drawable.ic_launcher);
				alertDialog.setTitle("SKUBAG");
				alertDialog.setMessage("Please fill in all fields to proceed");
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								alertDialog.dismiss();
							}
						});
				alertDialog.show();
			} else {
				if (editTimetableVO != null) {
					if (editTimetableVO.subjectname == null)
						addTimetable();
					else
						updateTimetable();
				} else {
					addTimetable();
				}
			}
		}
		if (v == date) {
			// Process to get Current Date
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);

			// Launch Date Picker Dialog
			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// Display Selected date in textbox
							date.setText(dayOfMonth + "-" + (monthOfYear + 1)
									+ "-" + year);

						}
					}, mYear, mMonth, mDay);
			dpd.show();
		}
		if (v == starttime) {

			// Process to get Current Time
			final Calendar c = Calendar.getInstance();
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);

			// Launch Time Picker Dialog
			TimePickerDialog tpd = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							// Display Selected time in textbox
							starttime.setText(String.valueOf(hourOfDay) + ":"
									+ String.valueOf(minute));
						}
					}, mHour, mMinute, false);

			tpd.show();
		}
		if (v == endtime) {

			// Process to get Current Time
			final Calendar c = Calendar.getInstance();
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);

			// Launch Time Picker Dialog
			TimePickerDialog tpd = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							// Display Selected time in textbox
							endtime.setText(String.valueOf(hourOfDay) + ":"
									+ String.valueOf(minute));
						}
					}, mHour, mMinute, false);
			tpd.show();
		}
	}

	public void addTimetable() {
		try {
			ContentValues values = new ContentValues();
			values.put("subjectname", subject.getText().toString());
			values.put("dateset", date.getText().toString());
			values.put("timeset", starttime.getText().toString());
			values.put("teachername", teacher.getText().toString());
			values.put("timeendset", endtime.getText().toString());
			final long mytimetable_id = Constant.schooltimetable.insert(
					Constant.MyTimeTable, null, values);
			subject.setText("");
			date.setText("");
			starttime.setText("");
			endtime.setText("");
			teacher.setText("");
			final AlertDialog alertDialog = new AlertDialog.Builder(
					TimetableActivity.this).create();
			alertDialog.setIcon(R.drawable.ic_launcher);
			alertDialog.setTitle("SKUBAG");
			alertDialog
					.setMessage("New Class added successfully to My Timetable..");
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					alertDialog.dismiss();
				}
			});
			alertDialog.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateTimetable() {
		ContentValues values = new ContentValues();
		values.put("subjectname", subject.getText().toString());
		values.put("dateset", date.getText().toString());
		values.put("timeset", starttime.getText().toString());
		values.put("teachername", teacher.getText().toString());
		values.put("timeendset", endtime.getText().toString());
		Constant.schooltimetable.update(Constant.MyTimeTable, values,
				"subjectid " + "=" + editTimetableVO.subjectid, null);
		final AlertDialog alertDialog = new AlertDialog.Builder(
				TimetableActivity.this).create();
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setTitle("SKUBAG");
		alertDialog.setMessage("Class updated successfully in My Timetable..");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				onBackPressed();
				alertDialog.dismiss();
			}
		});
		alertDialog.show();
	}

	public void onBackPressed() {
		Intent i = new Intent(this, CreateTimetable.class);
		i.putExtra("exit", true);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		super.onBackPressed();
	}
}