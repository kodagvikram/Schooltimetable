package com.mobiwebcode.schooltimetable;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mobiwebcode.schooltimetable.VO.PascoTopicVo;

public class PascoTopic extends Activity {
	RadioGroup rg;
	RadioButton rb;
	public static String selectedtopic = "";
	ArrayList<PascoTopicVo> topiclist = new ArrayList<PascoTopicVo>();
	XMLParser parser = new XMLParser();
	String xml = "";
	NodeList nl;
	Document doc;
	String DEFINATION_TOPIC_URL = "http://millionairesorg.com/schooltimetable/passcotopiclist.php?subject="
			+ PascoActivity.selectedSubject;
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
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pascotopic);
		rb = new RadioButton(this);
		final Button btnselecttopic = (Button) findViewById(R.id.btn_Select);
		btnselecttopic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent next = new Intent(PascoTopic.this,
						PascoQuestionActivity.class);
				startActivity(next);
			}
		});
		new myTask_DefinationTopicList_call().execute();

	}

	// DownloadJSON AsyncTask for activityDetails
	class myTask_DefinationTopicList_call extends
			AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
		}

		@Override
		protected String doInBackground(String... aurl) {
			try {
				xml = parser.getXmlFromUrl(DEFINATION_TOPIC_URL);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return "";

		}

		@Override
		protected void onPostExecute(String str_resp) {
			doc = parser.getDomElement(xml); // getting
			if (doc != null) {
				nl = doc.getElementsByTagName("topic");
				topiclist.clear();
				// looping through all item nodes <item>
				for (int i = 0; i < nl.getLength(); i++) {
					Element e = (Element) nl.item(i);
					PascoTopicVo plVo = new PascoTopicVo();
					plVo.topicid = parser.getValue(e, "topicid");
					plVo.topicname = parser.getValue(e, "topicname");

					topiclist.add(plVo);

				}
			}
			fillUI();
			if (mProgressDialog != null)
				mProgressDialog.dismiss();

		}

	}

	void fillUI() {
		int counter = 0;
		final LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.mainlinearlayout);

		for (int i = 0; i < topiclist.size(); i++) {

			LinearLayout ll = new LinearLayout(this);
			ll.setOrientation(LinearLayout.HORIZONTAL);
			ll.setWeightSum(100);
			ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 100));

			final TextView topicname = new TextView(this);
			LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			p1.weight = 80;
			topicname.setGravity(Gravity.CENTER);
			topicname.setTextSize(20);
			topicname.setId(i);

			topicname.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					for (int j = 0; j < mainLinearLayout.getChildCount(); j++) {
						LinearLayout childLayout = (LinearLayout) mainLinearLayout
								.findViewById(j + 1000);
						RadioButton childRadioButton = (RadioButton) childLayout
								.findViewById(j + 100);
						childRadioButton.setChecked(false);
					}

					LinearLayout outerLayout = (LinearLayout) mainLinearLayout
							.findViewById(topicname.getId() + 1000);
					RadioButton selectedRadioButton = (RadioButton) outerLayout
							.findViewById(topicname.getId() + 100);
					selectedRadioButton.setChecked(true);
					selectedtopic = topicname.getText().toString();
				}
			});

			// add radio buttons
			RadioButton rb = new RadioButton(this);
			rb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					for (int j = 0; j < mainLinearLayout.getChildCount(); j++) {
						LinearLayout childLayout = (LinearLayout) mainLinearLayout
								.findViewById(j + 1000);
						RadioButton childRadioButton = (RadioButton) childLayout
								.findViewById(j + 100);
						childRadioButton.setChecked(false);
					}

					LinearLayout outerLayout = (LinearLayout) mainLinearLayout
							.findViewById(topicname.getId() + 1000);
					RadioButton selectedRadioButton = (RadioButton) outerLayout
							.findViewById(topicname.getId() + 100);
					selectedRadioButton.setChecked(true);
					selectedtopic = topicname.getText().toString();
				}
			});
			LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			p2.weight = 20;
			rb.setGravity(Gravity.RIGHT);
			rb.setId(100 + i);
			ll.addView(topicname, p1);
			ll.addView(rb, p2);
			ll.setId(1000 + i);

			PascoTopicVo plVo = topiclist.get(i);
			plVo.topicname = plVo.topicname.replace(" ", "_");
			topicname.setText(plVo.topicname);

			mainLinearLayout.addView(ll);
		}

	}

}
