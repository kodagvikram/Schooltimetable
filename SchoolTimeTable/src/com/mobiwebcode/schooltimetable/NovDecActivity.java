package com.mobiwebcode.schooltimetable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mobiwebcode.schooltimetable.VO.TimetableVO;
import com.mobiwebcode.schooltimetable.WassceTimeTableActivity.myTask_WASSCE_Timetable_call;

public class NovDecActivity extends Activity {
	ListView novdecListView;
	public static Boolean isSave = false;
	public static Button Save;
	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
	private ProgressDialog mProgressDialog;
	String responseString = null;
	XMLParser parser = new XMLParser();
	String xml = "";
	NodeList nl;
	Document doc;
	String NOVDEC_TIMETABLE_URL = "http://millionairesorg.com/schooltimetable/timetabledetail.php?timetabletype="
			+ "novdec" + "&subject=" + Selectsubjectnovdec.selectedsubject;
	ArrayList<TimetableVO> novdecTimetableList = new ArrayList<TimetableVO>();

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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.novdesactivity);
		Save = (Button) findViewById(R.id.btnsave);
		novdecListView = (ListView) findViewById(R.id.novdecListView);
		if (isSave == true) {
			Save.setText("Save");

		} else
			Save.setText("Edit");
		File file = new File("/sdcard/.skubag/novdec.txt");
		if (file.exists() == true && isSave == false) {
			read("novdec.txt");
		} else {
			new myTask_NOVDEC_Timetable_call().execute("");
			isSave = false;
		}
		Save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Save.getText().toString().equals("Save")) {
					try {
						File myFolder = new File("/sdcard/.skubag");
						myFolder.mkdirs();
						File myFile = new File("/sdcard/.skubag/novdec.txt");
						myFile.createNewFile();
						FileOutputStream fOut = new FileOutputStream(myFile);
						OutputStreamWriter myOutWriter = new OutputStreamWriter(
								fOut);
						myOutWriter.append(xml);
						myOutWriter.close();
						fOut.close();
						final AlertDialog alertDialog = new AlertDialog.Builder(
								NovDecActivity.this).create();
						alertDialog.setIcon(R.drawable.ic_launcher);
						alertDialog.setTitle("SKUBAG");
						alertDialog
								.setMessage("Timetable Saved Successfully!!!");
						alertDialog.setButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

										alertDialog.dismiss();
									}
								});
						alertDialog.show();

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Intent i = new Intent(NovDecActivity.this,
							Selectsubjectnovdec.class);
					startActivity(i);
				}
			}
		});
	}

	public void read(String fname) {

		try {
			File myFile = new File("/sdcard/.skubag/novdec.txt");
			StringBuffer output = new StringBuffer();
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(new InputStreamReader(
					fIn));
			String line = "";
			while ((line = myReader.readLine()) != null) {
				output.append(line + "");
			}
			xml = output.toString();
			parse_xml();
			myReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class myTask_NOVDEC_Timetable_call extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
		}

		protected String doInBackground(String... aurl) {
			try {
				xml = parser.getXmlFromUrl(NOVDEC_TIMETABLE_URL);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return "";
		}

		protected void onPostExecute(String str_resp) {
			parse_xml();
		}

	}

	public class CustomList extends BaseAdapter {
		private final Activity context;
		private String[] web;
		private Integer[] imageId;
		ArrayList<TimetableVO> adapterTimetableList;
		TextView dateTextView, timeTextView, noofhrsTextView,
				papercodeTextView, subjectTextView;

		public CustomList(Activity context,
				ArrayList<TimetableVO> mainTimetableList_) {
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
			TimetableVO ttvo = novdecTimetableList.get(position);
			dateTextView = (TextView) rowView.findViewById(R.id.dateTextView);
			timeTextView = (TextView) rowView.findViewById(R.id.timeTextView);
			noofhrsTextView = (TextView) rowView
					.findViewById(R.id.noofhrsTextView);
			papercodeTextView = (TextView) rowView
					.findViewById(R.id.papercodeTextView);
			subjectTextView = (TextView) rowView
					.findViewById(R.id.subjectTextView);
			dateTextView.setText(ttvo.timetable_date);
			timeTextView.setText(ttvo.duration);
			noofhrsTextView.setText(ttvo.timetable_hrs);
			papercodeTextView.setText(ttvo.papercode);
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
	void parse_xml() {
		// TODO Auto-generated method stub
			doc = parser.getDomElement(xml); // getting
			if (doc != null) {
				nl = doc.getElementsByTagName("timetable");
				novdecTimetableList.clear();
				// looping through all item nodes <item>
				for (int i = 0; i < nl.getLength(); i++) {
					Element e = (Element) nl.item(i);
					TimetableVO ttvo = new TimetableVO();
					ttvo.timetableid = parser.getValue(e, "timetableid");
					ttvo.timetable_date = parser.getValue(e, "timetable_date");
					ttvo.papercode = parser.getValue(e, "papercode");
					ttvo.subjectname = parser.getValue(e, "subjectname");
					ttvo.duration = parser.getValue(e, "duration");
					ttvo.timetable_hrs = parser.getValue(e, "timetable_hrs");
					ttvo.country = parser.getValue(e, "country");

					if (ttvo.country.toString().equalsIgnoreCase("all")
							|| ttvo.country.toString().equalsIgnoreCase(
									UserLogin.country)) {

						novdecTimetableList.add(ttvo);
					}

				}
			}
			CustomList adapter = new CustomList(NovDecActivity.this,
					novdecTimetableList);
			novdecListView.setAdapter(adapter);
			if (mProgressDialog != null)
				mProgressDialog.dismiss();
		
	}
	public void onBackPressed() {
		File file = new File("/sdcard/.skubag/novdec.txt");
		if (file.exists() == true) {
			Intent i = new Intent(NovDecActivity.this,
					OptionActivity.class);
			startActivity(i);
		}
		finish();
	}
}
