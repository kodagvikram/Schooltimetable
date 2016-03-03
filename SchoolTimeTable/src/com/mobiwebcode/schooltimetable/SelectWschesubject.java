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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.mobiwebcode.schooltimetable.SelectsubjectDefination.myTask_DefinationsubjectList_call;
import com.mobiwebcode.schooltimetable.VO.DefinationSubjectVo;
import com.mobiwebcode.schooltimetable.VO.NovDecSubjectVo;
import com.mobiwebcode.schooltimetable.VO.WasscheSubjectVo;

import android.R.string;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SelectWschesubject extends Activity implements OnItemClickListener {
	ListView listView;
	ArrayList<WasscheSubjectVo> subjectlist = new ArrayList<WasscheSubjectVo>();
	String responseString = null;
	XMLParser parser = new XMLParser();
	String xml = "";
	NodeList nl;
	Document doc;
	String WASSCE_SUBJECT_URL = "http://millionairesorg.com/schooltimetable/timetablesubjects.php";
	CustomList adapter;
	private ProgressDialog mProgressDialog;
	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
	public static String selectedsubject = "";

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
		setContentView(R.layout.selectwaschesubject);

		listView = (ListView) findViewById(R.id.listView);
		listView.setItemsCanFocus(false);
		listView.setTextFilterEnabled(true);
		Button btnselectsubject = (Button) findViewById(R.id.btn_Select);

		btnselectsubject.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				selectedsubject = new String();

				for (int i = 0; i < subjectlist.size(); i++)

				{
					if (adapter.mCheckStates.get(i) == true) {
						WasscheSubjectVo wsVo = (WasscheSubjectVo) subjectlist
								.get(i);
						selectedsubject = selectedsubject + "'"
								+ (wsVo.subjectname.replace(" ", "_")) + "'";
						selectedsubject = selectedsubject + ",";
					}

				}
				selectedsubject = selectedsubject.substring(0,
						selectedsubject.length() - 1);

				WassceTimeTableActivity.isSave = true;
				Intent myintent2 = new Intent(SelectWschesubject.this,
						WassceTimeTableActivity.class);
				startActivity(myintent2);

			}
		});
		new myTask_WasschesubjectList_call().execute();
	}

	// DownloadJSON AsyncTask for activityDetails
	class myTask_WasschesubjectList_call extends
			AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
		}

		@Override
		protected String doInBackground(String... aurl) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;

			try {

				response = httpclient
						.execute(new HttpGet(
								"http://millionairesorg.com/schooltimetable/timetablesubjects.php"));
				StatusLine statusLine = response.getStatusLine();

				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					responseString = out.toString();

					out.close();

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return "";

		}

		@Override
		protected void onPostExecute(String str_resp) {

			xml = parser.getXmlFromUrl(WASSCE_SUBJECT_URL);
			doc = parser.getDomElement(xml); // getting
			if (doc != null) {
				nl = doc.getElementsByTagName("subject");
				subjectlist.clear();
				// looping through all item nodes <item>
				for (int i = 0; i < nl.getLength(); i++) {
					Element e = (Element) nl.item(i);
					WasscheSubjectVo slVo = new WasscheSubjectVo();
					slVo.subjectid = parser.getValue(e, "subjectid");
					slVo.subjectname = parser.getValue(e, "subjectname");

					subjectlist.add(slVo);
				}
			}

			adapter = new CustomList(SelectWschesubject.this, subjectlist);
			listView.setAdapter(adapter);
			if (mProgressDialog != null)
				mProgressDialog.dismiss();

		}

	}

	public class CustomList extends BaseAdapter implements
			CompoundButton.OnCheckedChangeListener {
		private final Activity context;
		private String[] web;
		private Integer[] imageId;
		ArrayList<WasscheSubjectVo> adapterTimetableList;
		TextView wasschesubjectTextView;
		CheckBox checkbox;
		public SparseBooleanArray mCheckStates;

		public CustomList(Activity context,
				ArrayList<WasscheSubjectVo> mainTimetableList_) {
			mCheckStates = new SparseBooleanArray(subjectlist.size());
			this.context = context;
			adapterTimetableList = mainTimetableList_;
		}

		@Override
		public View getView(final int position, final View view,
				ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView = inflater.inflate(
					R.layout.selectsubjectwascci_iteminfo, null, true);
			WasscheSubjectVo wsVo = subjectlist.get(position);
			wasschesubjectTextView = (TextView) rowView
					.findViewById(R.id.Subject);
			wasschesubjectTextView.setText(wsVo.subjectname);
			checkbox = (CheckBox) rowView.findViewById(R.id.checkBox1);
			checkbox.setTag(position);
			checkbox.setChecked(mCheckStates.get(position, false));
			checkbox.setOnCheckedChangeListener(this);

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

		public boolean isChecked(int position) {
			return mCheckStates.get(position, false);
		}

		public void setChecked(int position, boolean isChecked) {
			mCheckStates.put(position, isChecked);
		}

		public void toggle(int position) {
			setChecked(position, !isChecked(position));
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			mCheckStates.put((Integer) buttonView.getTag(), isChecked);

		}

		public void setmCheckStates(SparseBooleanArray mCheckStates) {
			// TODO Auto-generated method stub
			this.mCheckStates = mCheckStates;

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		adapter.toggle(arg2);

	}
}
