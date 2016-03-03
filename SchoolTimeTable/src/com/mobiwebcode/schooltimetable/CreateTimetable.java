package com.mobiwebcode.schooltimetable;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mobiwebcode.schooltimetable.VO.Constant;
import com.mobiwebcode.schooltimetable.VO.MyTimetableVO;

public class CreateTimetable extends Activity {

	TextView subject, date, time, teacher;
	ListView timetableListView;
	ArrayList<MyTimetableVO> mytimetableArrayList = new ArrayList<MyTimetableVO>();
	Button btngeneratetimetable;

	// database

//	private void copyDatabase() {
//		try {
//			File f = new File(Constants.path);
//			File f2 = new File(Constants.DB_PATH);
//			if (!f.exists()) {
//				InputStream in = getAssets().open("SchoolTimeTable.sqlite");
//				if (!f2.exists())
//					f2.mkdir();
//				OutputStream out = new FileOutputStream(Constants.path);
//
//				byte[] buffer = new byte[1024];
//				int length;
//				while ((length = in.read(buffer)) > 0) {
//					out.write(buffer, 0, length);
//				}
//				in.close();
//				out.close();
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}

	void readTimetable() {
		mytimetableArrayList.clear();
		try {
			Cursor cursor = null;

			cursor = Constant.schooltimetable.rawQuery(
					"SELECT * FROM generatetimetable", null);
			if (cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					MyTimetableVO mtvo = new MyTimetableVO();
					mtvo.subjectid = cursor.getInt(cursor
							.getColumnIndex("subjectid"));
					mtvo.subjectname = cursor.getString(cursor
							.getColumnIndex("subjectname"));
					mtvo.dateset = cursor.getString(cursor
							.getColumnIndex("dateset"));
					mtvo.timeset = cursor.getString(cursor
							.getColumnIndex("timeset"));
					mtvo.teachername = cursor.getString(cursor
							.getColumnIndex("teachername"));
					mtvo.timeendset = cursor.getString(cursor
							.getColumnIndex("timeendset"));
					mytimetableArrayList.add(mtvo);
				}
			}//end if
			cursor.close();
			CustomList adapter = new CustomList(CreateTimetable.this,
					mytimetableArrayList);
			timetableListView.setAdapter(adapter);
		} catch (Exception e) {
			// TODO: handle exception
		}//end try-catch
	}//end readTimetable

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.createtimetable);

		subject = (TextView) findViewById(R.id.textsubject);
		date = (TextView) findViewById(R.id.textdate);
		time = (TextView) findViewById(R.id.texttime);
		teacher = (TextView) findViewById(R.id.textteacher);
		timetableListView = (ListView) findViewById(R.id.timetableListView);
		timetableListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TimetableActivity.editTimetableVO = mytimetableArrayList
						.get(arg2);
				startActivity(new Intent(CreateTimetable.this,
						TimetableActivity.class));
			}
		});
//		copyDatabase();
//		Constants.schooltimetable = SQLiteDatabase.openDatabase(Constants.path,
//				null, SQLiteDatabase.OPEN_READWRITE);
		readTimetable();
	}//end oncreate

	public void addTimetable(View view) {
		Intent myintent2 = new Intent(CreateTimetable.this,
				TimetableActivity.class);
		startActivity(myintent2);
	}

	public void onBackPressed() {
		Intent i = new Intent(this, OptionActivity.class);
		i.putExtra("exit", true);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		super.onBackPressed();
	}

	public class CustomList extends BaseAdapter {
		private final Activity context;
		private String[] web;
		private Integer[] imageId;
		ArrayList<MyTimetableVO> adapterTimetableList;
		TextView dateTextView, timeTextView, noofhrsTextView,
				papercodeTextView, subjectTextView;

		public CustomList(Activity context,
				ArrayList<MyTimetableVO> mainTimetableList_) {
			super();
			this.context = context;
			adapterTimetableList = mainTimetableList_;
		}

		@Override
		public View getView(final int position, final View view,
				ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.timetable_listview, null,
					true);
			MyTimetableVO ttvo = mytimetableArrayList.get(position);
			dateTextView = (TextView) rowView.findViewById(R.id.dateTextView);
			timeTextView = (TextView) rowView.findViewById(R.id.timeTextView);
			noofhrsTextView = (TextView) rowView
					.findViewById(R.id.noofhrsTextView);
			papercodeTextView = (TextView) rowView
					.findViewById(R.id.papercodeTextView);
			subjectTextView = (TextView) rowView
					.findViewById(R.id.subjectTextView);
			dateTextView.setText(ttvo.dateset);
			timeTextView.setText(ttvo.timeset + " To " + ttvo.timeendset);
			try {
				noofhrsTextView
						.setText(Integer.parseInt(ttvo.timeendset.split(":")[0])
								- Integer.parseInt(ttvo.timeset.split(":")[0])
								+ " hrs");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			subjectTextView.setText(ttvo.subjectname);

			return rowView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return adapterTimetableList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

}