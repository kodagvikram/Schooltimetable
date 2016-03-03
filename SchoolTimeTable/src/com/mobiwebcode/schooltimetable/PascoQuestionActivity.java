package com.mobiwebcode.schooltimetable;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mobiwebcode.schooltimetable.VO.PasscoQuestionVO;

public class PascoQuestionActivity extends Activity {
	ImageView image1, image2, image3, image4;
	ArrayList<PasscoQuestionVO> passcoQuestionList = new ArrayList<PasscoQuestionVO>();
	String selectedOption = "";
	TextView questionTextView, yearTextview;
	RadioButton option1TextView, option2TextView, option3TextView,
			option4TextView;
	int questioncounter = 0;
	XMLParser parser = new XMLParser();
	String xml = "";
	NodeList nl;
	Document doc;
	String PASCO_QUESTION_URL = "http://millionairesorg.com/schooltimetable/passcoquestionlist.php?topic='"
			+ PascoTopic.selectedtopic + "'";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pascoquitionactivity);
		questionTextView = (TextView) findViewById(R.id.questionTextView);
		yearTextview = (TextView) findViewById(R.id.yearTextview);
		option1TextView = (RadioButton) findViewById(R.id.option1TextView);
		option2TextView = (RadioButton) findViewById(R.id.option2TextView);
		option3TextView = (RadioButton) findViewById(R.id.option3TextView);
		option4TextView = (RadioButton) findViewById(R.id.option4TextView);
		image1 = (ImageView) findViewById(R.id.lengthImage1);
		image2 = (ImageView) findViewById(R.id.lengthImage2);
		image3 = (ImageView) findViewById(R.id.lengthImage3);
		image4 = (ImageView) findViewById(R.id.lengthImage4);

		new myTask_PasscoquestionList_call().execute();
	}

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

	public void selectOption(View view) {

		if (view.getId() == R.id.option1TextView) {
			selectedOption = "A";
			option1TextView.setChecked(true);
		} else
			option1TextView.setChecked(false);

		if (view.getId() == R.id.option2TextView) {
			selectedOption = "B";
			option2TextView.setChecked(true);
		} else
			option2TextView.setChecked(false);

		if (view.getId() == R.id.option3TextView) {
			selectedOption = "C";
			option3TextView.setChecked(true);
		} else
			option3TextView.setChecked(false);

		if (view.getId() == R.id.option4TextView) {
			selectedOption = "D";
			option4TextView.setChecked(true);
		} else
			option4TextView.setChecked(false);

		PasscoQuestionVO pqvo = passcoQuestionList.get(questioncounter);
		if (selectedOption.equals(pqvo.answer)) {
			correctAnsEvent();
		} else {
			skipEvent();
		}
	}

	void displayQuestion() {
		option1TextView.setChecked(false);
		option2TextView.setChecked(false);
		option3TextView.setChecked(false);
		option4TextView.setChecked(false);

		PasscoQuestionVO pqvo = passcoQuestionList.get(questioncounter);
		questionTextView.setText(pqvo.question);
		yearTextview.setText("Question Year: " + pqvo.year);
		option1TextView.setText(pqvo.option_1);
		option2TextView.setText(pqvo.option_2);
		option3TextView.setText(pqvo.option_3);
		option4TextView.setText(pqvo.option_4);

		image1.setImageResource(0);
		image2.setImageResource(0);
		image3.setImageResource(0);
		image4.setImageResource(0);
		selectedOption = "";
	}

	public void nextQuestion(View view) {
		if (selectedOption.equals("")) {
			final AlertDialog alertDialog = new AlertDialog.Builder(
					PascoQuestionActivity.this).create();
			alertDialog.setIcon(R.drawable.ic_launcher);
			alertDialog.setTitle("SKUBAG");
			alertDialog.setMessage("Please Select One Answer!!!");
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					alertDialog.dismiss();
				}
			});
			alertDialog.show();
		} else {
			if (questioncounter < passcoQuestionList.size() - 1) {
				questioncounter++;
				displayQuestion();
			} else {
				Intent i = new Intent(PascoQuestionActivity.this,
						PascoTestEnd.class);
				startActivity(i);
			}
		}
	}

	public void prevQuestion(View view) {

		skipEvent();
		

	}

	// DownloadJSON AsyncTask for activityDetails
	class myTask_PasscoquestionList_call extends
			AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
		}

		@Override
		protected String doInBackground(String... aurl) {
			try {
				xml = parser.getXmlFromUrl(PASCO_QUESTION_URL);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(String str_resp) {

			doc = parser.getDomElement(xml); // getting
			if (doc != null) {
				nl = doc.getElementsByTagName("passcoquestion");
				passcoQuestionList.clear();
				// looping through all item nodes <item>
				for (int i = 0; i < nl.getLength(); i++) {
					Element e = (Element) nl.item(i);
					PasscoQuestionVO pqVo = new PasscoQuestionVO();
					pqVo.questionid = parser.getValue(e, "questionid");
					pqVo.question = parser.getValue(e, "question");
					pqVo.option_1 = parser.getValue(e, "option_1");
					pqVo.option_2 = parser.getValue(e, "option_2");
					pqVo.option_3 = parser.getValue(e, "option_3");
					pqVo.option_4 = parser.getValue(e, "option_4");
					pqVo.answer = parser.getValue(e, "answer");
					pqVo.explaination = parser.getValue(e, "explaination");
					pqVo.year = parser.getValue(e, "year");
					passcoQuestionList.add(pqVo);
				}
			}
			questioncounter = 0;
			displayQuestion();
			if (mProgressDialog != null)
				mProgressDialog.dismiss();

		}

	}

	void skipEvent() {

		PasscoQuestionVO pqvo = passcoQuestionList.get(questioncounter);
		if ((pqvo.answer).equals("A")) {
			selectedOption = "A";
			image1.setImageResource(R.drawable.success);
		} else {
			image1.setImageResource(R.drawable.fail);
		}
		if ((pqvo.answer).equals("B")) {
			selectedOption = "B";
			image2.setImageResource(R.drawable.success);
		} else {
			image2.setImageResource(R.drawable.fail);
		}
		if ((pqvo.answer).equals("C")) {
			selectedOption = "C";
			image3.setImageResource(R.drawable.success);
		} else {
			image3.setImageResource(R.drawable.fail);
		}
		if ((pqvo.answer).equals("D")) {
			selectedOption = "D";
			image4.setImageResource(R.drawable.success);
		} else {
			image4.setImageResource(R.drawable.fail);
		}
		
	}

	void correctAnsEvent() {

		PasscoQuestionVO pqvo = passcoQuestionList.get(questioncounter);
		if ((pqvo.answer).equals("A")) {
			image1.setImageResource(R.drawable.success);
		}
		if ((pqvo.answer).equals("B")) {
			image2.setImageResource(R.drawable.success);
		}
		if ((pqvo.answer).equals("C")) {
			image3.setImageResource(R.drawable.success);
		}
		if ((pqvo.answer).equals("D")) {
			image4.setImageResource(R.drawable.success);
		}
	}

}
