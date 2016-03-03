package com.mobiwebcode.schooltimetable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserLogin extends Activity {

	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
	private ProgressDialog mProgressDialog;
	String responseString = null;
	String recemail = "";
	EditText Username, Password;
	public static String userid = "", country = "";
	final Context context = this;

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
		setContentView(R.layout.login);
		Username = (EditText) findViewById(R.id.username);
		Password = (EditText) findViewById(R.id.password);

		Button btnlogin = (Button) findViewById(R.id.Login);
		Button forgotpassword = (Button) findViewById(R.id.forgotpassword);
		Button btnregister = (Button) findViewById(R.id.register);

		SharedPreferences myPrefs = UserLogin.this.getSharedPreferences(
				"myPrefs", MODE_WORLD_READABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();

		if (myPrefs.getString("login", "") != null
				&& !myPrefs.getString("login", "").equals("")) {
			userid = myPrefs.getString("login", "nothing").trim();
			country = myPrefs.getString("selectedcountry", "nothing").trim();
			// Intent intent = new Intent(UserLogin.this, HomeScreen.class);
			// startActivity(intent);
		}

		btnlogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
				// if (!Username.getText().toString().matches(emailPattern)) {
				// final AlertDialog alertDialog = new AlertDialog.Builder(
				// UserLogin.this).create();
				// alertDialog.setIcon(R.drawable.ic_launcher);
				// alertDialog.setTitle("SKUBAG");
				// alertDialog.setMessage("Please Enter Valid Email Address");
				// alertDialog.setButton("OK",
				// new DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog,
				// int which) {
				//
				// alertDialog.dismiss();
				// }
				// });
				// alertDialog.show();
				final String email = Username.getText().toString();
				if (!isValidEmail(email)) {
					Username.setError("Invalid Email");
					Username.requestFocus();
					
				}
				 if (Username.getText().toString().equals("")
						|| Password.getText().toString().equals("")) {
					final AlertDialog alertDialog = new AlertDialog.Builder(
							UserLogin.this).create();
					alertDialog.setIcon(R.drawable.ic_launcher);
					alertDialog.setTitle("SKUBAG");
					alertDialog.setMessage("Please fill Username & Password");
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									alertDialog.dismiss();
								}
							});
					alertDialog.show();
				}else if (isValidEmail(email)) {
					new myTask_loginUser_call().execute();

				}
			}
		});

		btnregister.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(UserLogin.this,
						RegisterActivity.class);
				startActivity(myintent2);

			}
		});
		forgotpassword.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setIcon(R.drawable.ic_launcher);
				alert.setTitle("SKUBAG"); // Set Alert dialog title here
				alert.setMessage("Recover Your Password!!!"); // Message here

				// Set an EditText view to get user input
				final EditText input = new EditText(context);
				input.setHint("Enter Your Register Email..");
				alert.setView(input);

				alert.setPositiveButton("SUBMIT",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								recemail = input.getEditableText().toString();
								new myTask_recoverpassword_call().execute();
							}
						}); // End of alert.setPositiveButton
				alert.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
								dialog.cancel();
							}
						}); // End of alert.setNegativeButton
				AlertDialog alertDialog = alert.create();
				alertDialog.show();

			}
		});
	}

	class myTask_recoverpassword_call extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
		}

		protected String doInBackground(String... aurl) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;

			try {

				response = httpclient.execute(new HttpGet(
						"http://millionairesorg.com/schooltimetable/forgotpassword.php?email="
								+ recemail));
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

		protected void onPostExecute(String str_resp) {
			if (responseString.equals("success")) {
				final AlertDialog alertDialog = new AlertDialog.Builder(
						UserLogin.this).create();
				alertDialog.setIcon(R.drawable.ic_launcher);
				alertDialog.setTitle("SKUBAG");
				alertDialog.setMessage("Please check your email!!!");
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								alertDialog.dismiss();
							}
						});
				alertDialog.show();

			} else if (responseString.equals("0")) {
				final AlertDialog alertDialog = new AlertDialog.Builder(
						UserLogin.this).create();
				alertDialog.setIcon(R.drawable.ic_launcher);
				alertDialog.setTitle("SKUBAG");
				alertDialog
						.setMessage("This email does not exist in our database");
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								alertDialog.dismiss();
							}
						});

				alertDialog.show();
			}

			if (mProgressDialog != null)
				mProgressDialog.dismiss();

		}
	}

	class myTask_loginUser_call extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
		}

		protected String doInBackground(String... aurl) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;

			try {

				response = httpclient.execute(new HttpGet(
						"http://millionairesorg.com/schooltimetable/loginuser.php?email="
								+ Username.getText().toString() + "&password="
								+ Password.getText().toString()));
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

		protected void onPostExecute(String str_resp) {
			if (responseString.equals("verify")) {
				final AlertDialog alertDialog = new AlertDialog.Builder(
						UserLogin.this).create();
				alertDialog.setTitle("SKUBAG");
				alertDialog.setIcon(R.drawable.ic_launcher);
				alertDialog
						.setMessage("Please check your registered email for verification!!!");
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								alertDialog.dismiss();
							}
						});
				alertDialog.show();

			} else if (responseString.equals("0")) {
				final AlertDialog alertDialog = new AlertDialog.Builder(
						UserLogin.this).create();
				alertDialog.setIcon(R.drawable.ic_launcher);
				alertDialog.setTitle("SKUBAG");
				alertDialog
						.setMessage(" This user does not exist please check user name and password");
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								alertDialog.dismiss();
							}
						});

				alertDialog.show();
			} else {

				SharedPreferences myPrefs = UserLogin.this
						.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
				SharedPreferences.Editor prefsEditor = myPrefs.edit();
				prefsEditor.putString("login", responseString.trim());
				prefsEditor.commit();
				userid = responseString.trim();

				Intent i = new Intent(UserLogin.this, HomeScreen.class);
				startActivity(i);

			}

			if (mProgressDialog != null)
				mProgressDialog.dismiss();

		}
	}

	// validating email id
	private boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
