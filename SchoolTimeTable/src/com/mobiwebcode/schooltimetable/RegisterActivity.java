package com.mobiwebcode.schooltimetable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mobiwebcode.schooltimetable.VO.Constant;
import com.mobiwebcode.schooltimetable.VO.RegisterVo;

public class RegisterActivity extends Activity {

	public String Black = "#ffffff";
	public String yeargrade = "";

	EditText EditTextName, EditTextSchool, EditTextEmail, EditTextPassword,
			EditTextPhonenumber, EditTextCountry, EditTextYear, EditTextState,
			EditTextRepeatPassword;
	ImageView nameValidationImage, countryValidationImage,
			stateValidationImage, yearValidationImage, schoolValidationImage,
			emailValidationImage, passwordValidationImage,
			phoneValidationImage, repeatpasswordValidationImage;
	Constant Constant = null;
	// String Name, School, Class, Email, Password, Phonenumber, Country, State,
	// City;
	String countrylist[] = { "GHANA", "NIGERIA", "LIBERIA", "GAMBIA",
			"SIERRA LEONE" };

	String maincountrylist[] = { "Angola, Antigua & Deps",
			"Argentina, Armenia", "Australia", "Austria", "Azerbaijan",
			"Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus",
			"Belgium", "Belize", "Benin", "Bhutan", "Bolivia",
			"Bosnia Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria",
			"Burkina", "Burma", "Burundi", "Cambodia", "Cameroon", "Canada",
			"Cape Verde", "Central African Rep", "Chad", "Chile",
			"People's Republic of China", "Republic of China", "Colombia",
			"Comoros", "Democratic Republic of the Congo",
			"Republic of the Congo", "Costa Rica", "Croatia", "Cuba", "Cyprus",
			"Czech Republic", "Danzig", "Denmark", "Djibouti", "Dominica",
			"Dominican Republic", "East Timor", "Ecuador", "Egypt",
			"El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",
			"Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gaza Strip",
			"Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada",
			"Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti",
			"Holy Roman Empire", "Honduras", "Hungary", "Iceland", "India",
			"Indonesia", "Iran", "Iraq", "Republic of Ireland", "Israel",
			"Italy", "Ivory Coast", "Jamaica", "Japan", "Jonathanland",
			"Jordan", "Kazakhstan", "Kenya", "Kiribati", "North Korea",
			"South Korea", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia",
			"Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein",
			"Lithuania", "Luxembourg", "Macedonia", "Madagascar", " Malawi",
			"Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands",
			"Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova",
			"Monaco", "Mongolia", "Montenegro", "Morocco", "Mount Athos",
			"Mozambique", "Namibia", "Nauru", "Nepal", "Newfoundland",
			"Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria",
			"Norway", "Oman", "Ottoman Empire", "Pakistan", "Palau", "Panama",
			"Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland",
			"Portugal", "Prussia", "Qatar", "Romania", "Rome",
			"Russian Federation", "Rwanda", "St Kitts & Nevis", "St Lucia",
			"Saint Vincent & the", "Grenadines", "Samoa", "San Marino",
			"Sao Tome & Principe", "Saudi Arabia", "Senegal", "Serbia",
			"Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia",
			"Solomon Islands", "Somalia", "South Africa", "Spain", "Sri Lanka",
			"Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria",
			"Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga",
			"Trinidad & Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu",
			"Uganda", "Ukraine", "United Arab Emirates", "United Kingdom",
			"Uruguay", "Uzbekistan", " Vanuatu", "Vatican City", "Venezuela",
			"Vietnam", "Yemen", "Zambia", "Zimbabwe" };

	String countrycodelist[] = { "+233-", "+234-", "+231-", "+220-", "+232-" };
	XMLParser parser = new XMLParser();
	String xml = "";
	NodeList nl;
	Document doc;
	String REGISTER_URL = "http://millionairesorg.com/schooltimetable/stateschool_register.php";
	String REGISTER_YEAR = "http://www.millionairesorg.com/schooltimetable/registergrade.php?country=";
	ArrayList<RegisterVo> StateSchoolList = new ArrayList<RegisterVo>();
	ArrayList<String> yeargradelist = new ArrayList<String>();

	public static JSONObject selectedGraphUser;
	public static String twitterusername;

	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
	public static final String Other = "Other";
	private ProgressDialog mProgressDialog;
	public static String responseString = "", selectedstate = "",
			selectedyear = "", selectedschool = "", selectedcountry = "",
			selectedcountrycode = "";

	public static String userid = "", registertype = "";
	String CurrentCountry = "";

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
		setContentView(R.layout.register_main);
		Constant = new Constant(RegisterActivity.this);

		EditTextName = (EditText) findViewById(R.id.edt_name);
		EditTextCountry = (EditText) findViewById(R.id.edt_country);
		EditTextState = (EditText) findViewById(R.id.edt_state);
		EditTextYear = (EditText) findViewById(R.id.edt_year);
		EditTextSchool = (EditText) findViewById(R.id.edt_school);
		// EditTextClass = (EditText) findViewById(R.id.edt_Class);
		EditTextEmail = (EditText) findViewById(R.id.edt_Email);
		EditTextPassword = (EditText) findViewById(R.id.edt_Password);
		EditTextPhonenumber = (EditText) findViewById(R.id.edt_Phonnumber);
		EditTextRepeatPassword = (EditText) findViewById(R.id.edt_repeatPassword);

		nameValidationImage = (ImageView) findViewById(R.id.nameValidationImage);
		countryValidationImage = (ImageView) findViewById(R.id.countryValidationImage);
		stateValidationImage = (ImageView) findViewById(R.id.stateValidationImage);
		yearValidationImage = (ImageView) findViewById(R.id.yearValidationImage);
		schoolValidationImage = (ImageView) findViewById(R.id.schoolValidationImage);
		// classValidationImage = (ImageView)
		// findViewById(R.id.classValidationImage);
		emailValidationImage = (ImageView) findViewById(R.id.emailValidationImage);
		passwordValidationImage = (ImageView) findViewById(R.id.passwordValidationImage);
		phoneValidationImage = (ImageView) findViewById(R.id.phoneValidationImage);
		repeatpasswordValidationImage = (ImageView) findViewById(R.id.repeatpasswordValidationImage);

		Geocoder gCoder = new Geocoder(RegisterActivity.this);
		ArrayList<Address> addresses = null;
		if (LoginActivity.Latitude != null && LoginActivity.Longutude != null) {
			try {
				addresses = (ArrayList<Address>) gCoder.getFromLocation(
						LoginActivity.Latitude, LoginActivity.Longutude, 2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (addresses != null && addresses.size() > 0) {
				// if (addresses.get(0).getFeatureName() != null)
				// AddressC = addresses.get(0).getFeatureName();
				// if (addresses.get(0).getLocality() != null)
				// AddressC = AddressC + "-" + addresses.get(0).getLocality();
				// if (addresses.get(0).getAdminArea() != null)
				// AddressC = AddressC + "-" + addresses.get(0).getAdminArea();
				if (addresses.get(0).getCountryName() != null)
					CurrentCountry = CurrentCountry
							+ addresses.get(0).getCountryName();
			}
			if (!CurrentCountry.equals("")
					&& CurrentCountry.equalsIgnoreCase(CurrentCountry)) {
				EditTextCountry.setText(CurrentCountry);
			} else {
				EditTextCountry.setText(CurrentCountry);
			}
		}

		EditTextCountry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditTextState.setText("");
				EditTextYear.setText("");
				EditTextSchool.setText("");
				EditTextPhonenumber.setText("");
				AlertDialog.Builder builderSingle = new AlertDialog.Builder(
						RegisterActivity.this);
				builderSingle.setIcon(R.drawable.ic_launcher);
				builderSingle.setTitle("Select One Contry");
				final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
						RegisterActivity.this,
						android.R.layout.select_dialog_singlechoice);
				for (int i = 0; i < maincountrylist.length; i++) {
					arrayAdapter.add(maincountrylist[i]);
				}

				builderSingle.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								dialog.dismiss();
							}
						});

				builderSingle.setAdapter(arrayAdapter,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								selectedcountry = arrayAdapter.getItem(which);

								EditTextCountry.setText(selectedcountry);

								getcountry();
								new myTask_Register_Gradeinfo_call().execute();

								// if
								// (!Arrays.asList(countrylist).contains(selectedcountry)){
								// selectedcountrycode = countrycodelist[which];
								// EditTextPhonenumber
								// .setText(selectedcountrycode);
								// }
							}
						});
				builderSingle.show();

			}

		});

		EditTextState.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (EditTextCountry.getText().toString().matches("")) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegisterActivity.this);
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("SKUBAG");
					builder.setMessage("Please first select country");
					builder.setCancelable(true);
					builder.setNeutralButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();

								}
							});

					AlertDialog alert = builder.create();
					alert.show();
				} else if (!Arrays.asList(countrylist).contains(
						EditTextCountry.getText().toString().toUpperCase())) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegisterActivity.this);
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("SKUBAG");
					builder.setMessage(
							" SKUBAG will be available soon in your country, Please select another country!!!")
							.setCancelable(true)
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// do things
										}
									});
					AlertDialog alert = builder.create();
					alert.show();

				} else {

					AlertDialog.Builder builderSingle = new AlertDialog.Builder(
							RegisterActivity.this);
					builderSingle.setIcon(R.drawable.ic_launcher);
					builderSingle.setTitle("Select One State");
					final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
							RegisterActivity.this,
							android.R.layout.select_dialog_singlechoice);
					for (int i = 0; i < StateSchoolList.size(); i++) {
						RegisterVo rVo = StateSchoolList.get(i);
						if (EditTextCountry.getText().toString()
								.equalsIgnoreCase(rVo.country)) {
							arrayAdapter.add(rVo.state);
						}
					}
					builderSingle.setNegativeButton("OK",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									dialog.dismiss();
								}
							});

					builderSingle.setAdapter(arrayAdapter,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									selectedstate = arrayAdapter.getItem(which);
									// selectedstate = selectedstate.replaceAll(
									// " ", "_");
									EditTextState.setText(selectedstate);

								}
							});
					builderSingle.show();
				}

			}
		});

		EditTextYear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (EditTextCountry.getText().toString().matches("")) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegisterActivity.this);
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("SKUBAG");
					builder.setMessage("Please first select country");
					builder.setCancelable(true);
					builder.setNeutralButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();

								}
							});

					AlertDialog alert = builder.create();
					alert.show();
				} else if (!Arrays.asList(countrylist).contains(
						EditTextCountry.getText().toString().toUpperCase())) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegisterActivity.this);
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("SKUBAG");
					builder.setMessage(
							" SKUBAG will be available soon in your country, Please select another country!!!")
							.setCancelable(true)
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// do things
										}
									});
					AlertDialog alert = builder.create();
					alert.show();

				} else {
					AlertDialog.Builder builderSingle = new AlertDialog.Builder(
							RegisterActivity.this);
					builderSingle.setIcon(R.drawable.ic_launcher);
					builderSingle.setTitle("Select One Year/Grade");

					final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
							RegisterActivity.this,
							android.R.layout.select_dialog_singlechoice);
					for (int i = 0; i < yeargradelist.size(); i++) {
						yeargrade = yeargradelist.get(i);

						arrayAdapter.add(yeargrade);

					}
					builderSingle.setNegativeButton("OK",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									dialog.dismiss();
								}
							});

					builderSingle.setAdapter(arrayAdapter,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									selectedyear = arrayAdapter.getItem(which);
									// selectedyear = selectedyear.replace(" ",
									// "_");
									EditTextYear.setText(selectedyear);

								}
							});
					builderSingle.show();
				}

			}
		});
		EditTextSchool.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (EditTextCountry.getText().toString().matches("")) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegisterActivity.this);
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("SKUBAG");
					builder.setMessage("Please first select country");
					builder.setCancelable(true);
					builder.setNeutralButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();

								}
							});

					AlertDialog alert = builder.create();
					alert.show();
				} else if (!Arrays.asList(countrylist).contains(
						EditTextCountry.getText().toString().toUpperCase())) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegisterActivity.this);
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("SKUBAG");
					builder.setMessage(
							" SKUBAG will be available soon in your country, Please select another country!!!")
							.setCancelable(true)
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// do things
										}
									});
					AlertDialog alert = builder.create();
					alert.show();

				} else if (EditTextState.getText().toString().matches("")) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegisterActivity.this);
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("SKUBAG");
					builder.setMessage("Please first select state");
					builder.setCancelable(true);
					builder.setNeutralButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();

								}
							});

					AlertDialog alert = builder.create();
					alert.show();
				} else {
					AlertDialog.Builder builderSingle = new AlertDialog.Builder(
							RegisterActivity.this);
					builderSingle.setIcon(R.drawable.ic_launcher);
					builderSingle.setTitle("Select One School");

					final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
							RegisterActivity.this,
							android.R.layout.select_dialog_singlechoice);
					for (int i = 0; i < StateSchoolList.size(); i++) {
						RegisterVo rVo = StateSchoolList.get(i);

						if (EditTextState.getText().toString()
								.equalsIgnoreCase(rVo.state)) {
							arrayAdapter.add(rVo.school);
						}

					}
					arrayAdapter.add(Other);

					builderSingle.setNegativeButton("OK",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									dialog.dismiss();
								}
							});

					builderSingle.setAdapter(arrayAdapter,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (arrayAdapter.getItem(which).contains(
											Other)) {
										AlertDialog.Builder builder1 = new AlertDialog.Builder(
												RegisterActivity.this);
										final EditText edittext = new EditText(
												RegisterActivity.this);
										edittext.setHint("Enter School!!!");
										builder1.setIcon(R.drawable.ic_launcher);
										builder1.setTitle("SKUBAG");
										builder1.setCancelable(true);
										builder1.setView(edittext);
										builder1.setPositiveButton(
												"Yes",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														String school = edittext
																.getEditableText()
																.toString();
														EditTextSchool
																.setText(school);
														dialog.dismiss();

													}
												});
										builder1.setNegativeButton(
												"No",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														dialog.cancel();
													}
												});

										AlertDialog alert11 = builder1.create();
										alert11.show();
									}
									selectedschool = arrayAdapter
											.getItem(which);

									EditTextSchool.setText(selectedschool);
								}
							});
					builderSingle.show();

				}

			}
		});

		try {

			if (selectedGraphUser != null) {
				if (!selectedGraphUser.isNull("name")) {
					registertype = "facebook";

					EditTextName.setText(selectedGraphUser.getString("name"));
					EditTextPassword.setFocusable(true);
					EditTextPassword.setFocusableInTouchMode(true);
					EditTextName.setFocusable(false);
					EditTextName.setFocusableInTouchMode(false);
				} else {

					EditTextName.setFocusable(true);
					EditTextName.setFocusableInTouchMode(true);
				}

				if (!selectedGraphUser.isNull("email")) {
					EditTextEmail.setText(selectedGraphUser.getString("email"));
					EditTextEmail.setFocusable(false);
					EditTextEmail.setFocusableInTouchMode(false);
				} else {

					EditTextEmail.setFocusable(true);
					EditTextEmail.setFocusableInTouchMode(true);
				}
			} else if (twitterusername != null) {
				registertype = "twitter";
				EditTextName.setText(twitterusername);
				EditTextPassword.setFocusable(true);
				EditTextPassword.setFocusableInTouchMode(true);
				EditTextName.setFocusable(false);
				EditTextName.setFocusableInTouchMode(false);
			} else {
				registertype = "normal";
				EditTextName.setFocusable(true);
				EditTextName.setFocusableInTouchMode(true);
				EditTextPassword.setFocusable(true);
				EditTextPassword.setFocusableInTouchMode(true);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Button submit = (Button) findViewById(R.id.submit);

		submit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				boolean name = Constant.validateText(EditTextName,
						nameValidationImage);
				boolean country = Constant.validateText(EditTextCountry,
						countryValidationImage);
				boolean state = Constant.validateText(EditTextState,
						stateValidationImage);
				boolean year = Constant.validateText(EditTextYear,
						yearValidationImage);
				boolean school = Constant.validateText(EditTextSchool,
						schoolValidationImage);
				// boolean edtclass = Constant.validateText(EditTextClass,
				// classValidationImage);
				boolean email = Constant.validateText(EditTextEmail,
						emailValidationImage);
				boolean password = Constant.validateText(EditTextPassword,
						passwordValidationImage);
				boolean repeatpassword = Constant.validateText(
						EditTextRepeatPassword, repeatpasswordValidationImage);
				boolean phone = Constant.validateText(EditTextPhonenumber,
						phoneValidationImage);

				final String Pwd = EditTextPassword.getText().toString();
				final String ConfPwd = EditTextRepeatPassword.getText()
						.toString();
				final String mail = EditTextEmail.getText().toString();

				if (!isValidEmail(mail)) {
					EditTextEmail.setError("Invalid Email");
					// EditTextEmail.requestFocus();

				}
				if (!isValidPassword(Pwd, ConfPwd)) {
					// EditTextPassword.setError("Invalid Password");
					// EditTextPassword.requestFocus();

					EditTextRepeatPassword
							.setError("Password do not match please try again");
					// EditTextRepeatPassword.requestFocus();

				}

				if (EditTextName.getText().toString().equals("")
						|| EditTextCountry.getText().toString().equals("")
						|| EditTextState.getText().toString().equals("")
						|| EditTextYear.getText().toString().equals("")
						|| EditTextSchool.getText().toString().equals("")
						// || EditTextClass.getText().toString().equals("")
						|| EditTextEmail.getText().toString().equals("")
						|| EditTextPassword.getText().toString().equals("")
						|| EditTextRepeatPassword.getText().toString()
								.equals("")
						|| EditTextPhonenumber.getText().toString().equals("")) {

					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegisterActivity.this);
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("SKUBAG");
					builder.setMessage("All field are mandatory!!!")
							.setCancelable(false)
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// do things
										}
									});
					AlertDialog alert = builder.create();
					alert.show();

				} else if (isValidEmail(mail) && isValidPassword(Pwd, ConfPwd)
						&& name && country && state && year && school && phone) {

					new myTask_registerUser_call().execute();

				}

			}

		});

		new myTask_Register_info_call().execute();
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	void getcountry() {

		for (int i = 0; i < countrylist.length; i++) {
			if (countrylist[i].equalsIgnoreCase(selectedcountry)) {
				selectedcountrycode = countrycodelist[i];
				EditTextPhonenumber.setText(selectedcountrycode);
			}
		}
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
			if (!responseString.equals("")) {
				final AlertDialog alertDialog = new AlertDialog.Builder(
						RegisterActivity.this).create();
				alertDialog.setTitle("SKUBAG");
				alertDialog.setMessage("User Register Succesfully!!!");
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent i = new Intent(RegisterActivity.this,
										HomeScreen.class);
								startActivity(i);
							}
						});
				alertDialog.show();
			}
			SharedPreferences myPrefs = RegisterActivity.this
					.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
			SharedPreferences.Editor prefsEditor = myPrefs.edit();
//			prefsEditor.putString("selectedcountry", EditTextCountry.getText()
//					.toString());
//			UserLogin.country = EditTextCountry.getText().toString();
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
							+ EditTextName.getText().toString()
									.replace(" ", "_")
							+ "&school="
							+ EditTextSchool.getText().toString()
									.replace(" ", "_")
							// + "&class="
							// + EditTextClass.getText().toString()
							+ "&email="
							+ EditTextEmail.getText().toString()
							+ "&password="
							+ EditTextPassword.getText().toString()
							+ "&repeatpassword="
							+ EditTextRepeatPassword.getText().toString()
							+ "&phonenumber="
							+ EditTextPhonenumber.getText().toString()
							+ "&country="
							+ EditTextCountry.getText().toString()
									.replace(" ", "_")
							+ "&grade="
							+ EditTextYear.getText().toString()
									.replace(" ", "_")
							+ "&registertype="
							+ registertype
							+ "&state="
							+ EditTextState.getText().toString()
									.replace(" ", "_")));

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

	// DownloadJSON AsyncTask for activityDetails
	class myTask_Register_info_call extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
		}

		@Override
		protected String doInBackground(String... aurl) {
			try {
				xml = parser.getXmlFromUrl(REGISTER_URL);

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
				nl = doc.getElementsByTagName("stateschool");
				StateSchoolList.clear();
				// looping through all item nodes <item>
				for (int i = 0; i < nl.getLength(); i++) {
					Element e = (Element) nl.item(i);
					RegisterVo rVo = new RegisterVo();
					rVo.country = parser.getValue(e, "country");
					rVo.state = parser.getValue(e, "state");
					rVo.school = parser.getValue(e, "school");

					StateSchoolList.add(rVo);

				}
			}

			if (mProgressDialog != null)
				mProgressDialog.dismiss();

		}

	}

	// DownloadJSON AsyncTask for activityDetails
	class myTask_Register_Gradeinfo_call extends
			AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
		}

		@Override
		protected String doInBackground(String... aurl) {
			try {
				xml = parser.getXmlFromUrl(REGISTER_YEAR
						+ selectedcountry.replace(" ", "%20"));

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
				nl = doc.getElementsByTagName("registergrade");
				yeargradelist.clear();
				// looping through all item nodes <item>
				for (int i = 0; i < nl.getLength(); i++) {
					Element e = (Element) nl.item(i);
					yeargrade = parser.getValue(e, "yeargrade");

					yeargradelist.add(yeargrade);

				}
			}

			if (mProgressDialog != null)
				mProgressDialog.dismiss();

		}

	}

	// validating email id
	private boolean isValidEmail(String mail) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(mail);
		return matcher.matches();
	}

	// validating password with retype password
	private boolean isValidPassword(String Pwd, String ConfPwd) {
		if (Pwd.equals(ConfPwd)) {
			return true;
		}
		return false;
	}

}
