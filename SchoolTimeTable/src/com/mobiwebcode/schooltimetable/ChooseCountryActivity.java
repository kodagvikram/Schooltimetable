package com.mobiwebcode.schooltimetable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ChooseCountryActivity extends Activity {

	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
	private ProgressDialog mProgressDialog;
	String responseString = null;

	public static String userid = "", registertype = "",selectedcountry = "";

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

	ArrayList<String> countryList = new ArrayList<String>();
	public static String values[] = new String[7];
	String[] countryArray = new String[] { "GHANA","NIGERIA","LIBERIA","GAMBIA","SIERRA LEONE" };

	ListView countryListview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosecountry);
		countryListview = (ListView) findViewById(R.id.countryListview);
		fillCountryList();
		countryListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				selectedcountry = countryArray[arg2];
			}
		});
	}

	public void countrySelected(View view) {
		Intent intent = new Intent(ChooseCountryActivity.this,
				OptionActivity.class);
		startActivity(intent);
	}

	private void fillCountryList() {
		for (int i = 0; i < countryArray.length; i++) {
			countryList.add(countryArray[i]);
		}
		ArrayAdapter adapter = new ArrayAdapter<String>(
				ChooseCountryActivity.this, R.layout.custom_textview,
				countryList);
		countryListview.setAdapter(adapter);

		Button buttonselectcountry = (Button) findViewById(R.id.selectCountryButton);

		buttonselectcountry.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (selectedcountry.equals("")) {

					final AlertDialog alertDialog = new AlertDialog.Builder(
							ChooseCountryActivity.this).create();
					alertDialog.setIcon(R.drawable.ic_launcher);

					alertDialog.setTitle("SKUBAG");
					alertDialog.setMessage("Please choose your country");
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									alertDialog.dismiss();
								}
							});
					alertDialog.show();
				} else
					new myTask_registerUser_call().execute();

			}
		});
	}

	class myTask_registerUser_call extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
		}

		protected String doInBackground(String... aurl) {
			uploadData();
			return null;
		}

		protected void onPostExecute(String str_resp) {

			final AlertDialog alertDialog = new AlertDialog.Builder(
					ChooseCountryActivity.this).create();
			alertDialog.setTitle("SKUBAG");
			alertDialog.setMessage("Thank you for registering with us!!!");
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(ChooseCountryActivity.this,
							OptionActivity.class);
					startActivity(intent);
					alertDialog.dismiss();
				}
			});
			alertDialog.show();

			SharedPreferences myPrefs = ChooseCountryActivity.this
					.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
			SharedPreferences.Editor prefsEditor = myPrefs.edit();
			prefsEditor.putString("selectedcountry", selectedcountry);
			UserLogin.country = selectedcountry;
			prefsEditor.putString("login", responseString.trim());
			prefsEditor.commit();

			if (mProgressDialog != null)
				mProgressDialog.dismiss();

		}
	}

	public void uploadData() {
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		try {
			response = httpclient.execute(new HttpGet(
					"http://millionairesorg.com/schooltimetable/registeruser.php?name="
							+ values[0].replace(" ", "_") + "&school="
							+ values[1].replace(" ", "_") + "&class="
							+ values[2].replace(" ", "_") + "&email="
							+ values[3].replace(" ", "_") + "&password="
							+ values[4].replace(" ", "_") + "&phonenumber="
							+ values[5].replace(" ", "_") + "&country="
							+ selectedcountry.replace(" ", "_") + "&city="
							+ values[6].replace(" ", "_") + "&registertype="
							+ registertype.replace(" ", "_")));
			StatusLine statusLine = response.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				responseString = out.toString();
				System.out.println(responseString);

				out.close();

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
