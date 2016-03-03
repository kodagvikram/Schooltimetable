package com.mobiwebcode.schooltimetable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mobiwebcode.schooltimetable.Selectsubjectjamb.CustomList;
import com.mobiwebcode.schooltimetable.Selectsubjectjamb.myTask_JambsubjectList_call;
import com.mobiwebcode.schooltimetable.VO.JambSubjectVo;
import com.mobiwebcode.schooltimetable.VO.SearchresultVo;

public class Searchlistview extends Activity {
	ListView listView;
	EditText inputSearch;
	ArrayList<SearchresultVo> searchresultlist = new ArrayList<SearchresultVo>();
	String responseString = null;
	XMLParser parser = new XMLParser();
	String xml = "";
	NodeList nl;
	Document doc;
	String SEARCH_RESULT_URL = "http://millionairesorg.com/schooltimetable/tertieryinfosearchresults.php?search="
			;
	CustomList adapter;
	private ProgressDialog mProgressDialog;
	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;

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
		setContentView(R.layout.search_listview);

		listView = (ListView) findViewById(R.id.listView);
		inputSearch = (EditText) findViewById(R.id.inputSearch);

		inputSearch
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							SchoollistActivity.searchResult = inputSearch
									.getText().toString();
							SchoollistActivity.searchResult = SchoollistActivity.searchResult
									.replace(" ", "_");
							new myTask_SearchResultList_call().execute();

							return true;
						}
						return false;
					}
				});

		new myTask_SearchResultList_call().execute();

	}

	// DownloadJSON AsyncTask for activityDetails
	class myTask_SearchResultList_call extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
		}

		@Override
		protected String doInBackground(String... aurl) {
			try {
				xml = parser.getXmlFromUrl(SEARCH_RESULT_URL+ SchoollistActivity.searchResult);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return "";

		}

		@Override
		protected void onPostExecute(String str_resp) {

			doc = parser.getDomElement(xml); // getting
			searchresultlist.clear();
			if (doc != null) {
				
				nl = doc.getElementsByTagName("searchresult");
				
				// looping through all item nodes <item>
				for (int i = 0; i < nl.getLength(); i++) {
					Element e = (Element) nl.item(i);
					SearchresultVo srVo = new SearchresultVo();
					srVo.result = parser.getValue(e, "result");

					searchresultlist.add(srVo);
				}
			}

			adapter = new CustomList(Searchlistview.this, searchresultlist);
			listView.setAdapter(adapter);
			if (mProgressDialog != null)
				mProgressDialog.dismiss();

		}
	}

	public class CustomList extends BaseAdapter {
		private final Activity context;
		private String[] web;
		private Integer[] imageId;
		ArrayList<SearchresultVo> adapterTimetableList;
		TextView product_name;

		public CustomList(Activity context,
				ArrayList<SearchresultVo> mainTimetableList_) {
			this.context = context;
			adapterTimetableList = mainTimetableList_;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.search_listview_item,
					null, true);
			SearchresultVo jsVo = searchresultlist.get(position);
			product_name = (TextView) rowView.findViewById(R.id.product_name);
			product_name.setText(jsVo.result);

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
