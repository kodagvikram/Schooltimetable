package com.mobiwebcode.schooltimetable;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;


public class LoginActivity extends Activity implements OnClickListener {
	GPSTracker gps;
	public static Double Latitude, Longutude;
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
    //public static Activity activity;
	// set buttons facebok and twitter

	// twitter

	/* Shared preference keys */
	private static final String PREF_NAME = "sample_twitter_pref";
	private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	private static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";
	private static final String PREF_USER_NAME = "twitter_user_name";

	/* Any number for uniquely distinguish your request */
	public static final int WEBVIEW_REQUEST_CODE = 100;
	String parameter_email = "", responseString = "";
	private ProgressDialog pDialog;

	private static Twitter twitter;
	private static RequestToken requestToken;

	private static SharedPreferences mSharedPreferences;

	private EditText mShareEditText;
	private TextView userName;
	private View loginLayout;
	private View shareLayout;

	private String consumerKey = null;
	private String consumerSecret = null;
	private String callbackUrl = null;
	private String oAuthVerifier = null;

	@SuppressLint("NewApi")
	final Context context = this;
	private Button howtoRegisterButton;
	private LoginButton loginButton;
	private PendingAction pendingAction = PendingAction.NONE;
	private final String PENDING_ACTION_BUNDLE_KEY = "com.mobiwebcode.pgfh:PendingAction";
	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
	private ProgressDialog mProgressDialog;

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

	private enum PendingAction {
		NONE, POST_PHOTO, POST_STATUS_UPDATE
	}

	private UiLifecycleHelper uiHelper;

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (pendingAction != PendingAction.NONE
				&& (exception instanceof FacebookOperationCanceledException || exception instanceof FacebookAuthorizationException)) {
			new AlertDialog.Builder(LoginActivity.this)
					.setTitle(R.string.cancelled)
					.setMessage(R.string.permission_not_granted)
					.setPositiveButton(R.string.ok, null).show();
			pendingAction = PendingAction.NONE;
		} else if (state == SessionState.OPENED_TOKEN_UPDATED) {
		}
	}

	private interface GraphObjectWithId extends GraphObject {
		String getId();
	}

	private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
		@Override
		public void onError(FacebookDialog.PendingCall pendingCall,
				Exception error, Bundle data) {
			Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
		}

		@Override
		public void onComplete(FacebookDialog.PendingCall pendingCall,
				Bundle data) {
			Log.d("HelloFacebook", "Success!");
		}
	};

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);

		outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100) {

			String verifier = data.getExtras().getString(oAuthVerifier);
			try {
				AccessToken accessToken = twitter.getOAuthAccessToken(
						requestToken, verifier);

				long userID = accessToken.getUserId();
				final User user = twitter.showUser(userID);
				String username = user.getName().replace(" ", "_");
				RegisterActivity.twitterusername = username;
				parameter_email = username;
				new myTask_checkFacebookTwitterLogin_call().execute();

			} catch (Exception e) {
				Log.e("Twitter Login Failed", e.getMessage());
			}
		} else {

			uiHelper.onActivityResult(requestCode, resultCode, data,
					dialogCallback);
			new Request(Session.getActiveSession(), "me", null, HttpMethod.GET,
					new Request.Callback() {
						public void onCompleted(Response response) {
							if (response.getGraphObject() != null) {
								RegisterActivity.selectedGraphUser = response
										.getGraphObject().getInnerJSONObject();
								try {
									parameter_email = RegisterActivity.selectedGraphUser
											.getString("name").toString().replace(" ", "_");
									new myTask_checkFacebookTwitterLogin_call()
											.execute();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}).executeAsync();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();

		// Call the 'deactivateApp' method to log an app event for use in
		// analytics and advertising
		// reporting. Do so in the onPause methods of the primary Activities
		// that an app may be launched into.
		AppEventsLogger.deactivateApp(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	// twitter
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* initializing twitter parameters from string.xml */
		initTwitterConfigs();

		//  activity=LoginActivity.this;
		
		/* Enabling strict mode */
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.loginactivity);

		File file = new File("/sdcard/.skubag/jamb.txt");
		if (file.exists() == true) {
			// delete it
			file.delete();
		}
		file = new File("/sdcard/.skubag/wassce.txt");
		if (file.exists() == true) {
			// delete it
			file.delete();
		}
		file = new File("/sdcard/.skubag/novdec.txt");
		if (file.exists() == true) {
			// delete it
			file.delete();
		}
		gps = new GPSTracker(LoginActivity.this);
		LocationManager locationManager;
		locationManager = (LocationManager) LoginActivity.this
				.getSystemService(LOCATION_SERVICE);
		isGPSEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		isNetworkEnabled = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps.canGetLocation()) {

			Latitude = gps.getLatitude();
			Longutude = gps.getLongitude();

		} else {
			if (!isGPSEnabled && !isNetworkEnabled) {

				gps.showSettingsAlert();
			} else {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						LoginActivity.this);
				alert.setTitle("Message");
				alert.setMessage("Gps Service can not find your location plz try latter");
				alert.setPositiveButton("ok",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

								dialog.dismiss();
							}
						});

				alert.create().show();
			}
		}

		loginLayout = (RelativeLayout) findViewById(R.id.login_layout);
		shareLayout = (LinearLayout) findViewById(R.id.share_layout);
		mShareEditText = (EditText) findViewById(R.id.share_text);
		userName = (TextView) findViewById(R.id.user_name);

		/* register button click listeners */
		findViewById(R.id.twitterLogin).setOnClickListener(this);
		findViewById(R.id.btn_share).setOnClickListener(this);

		SharedPreferences myPrefs = LoginActivity.this.getSharedPreferences(
				"myPrefs", MODE_WORLD_READABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();

		if (myPrefs.getString("login", "") != null
				&& !myPrefs.getString("login", "").equals("")) {
			UserLogin.userid = myPrefs.getString("login", "").trim();
			UserLogin.country = myPrefs.getString("selectedcountry", "")
					.trim();
			Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
			startActivity(intent);
		}
		
		

		/* Check if required twitter keys are set */
		if (TextUtils.isEmpty(consumerKey) || TextUtils.isEmpty(consumerSecret)) {
			Toast.makeText(this, "Twitter key and secret not configured",
					Toast.LENGTH_SHORT).show();
			return;
		}

		/* Initialize application preferences */
		mSharedPreferences = getSharedPreferences(PREF_NAME, 0);

		boolean isLoggedIn = mSharedPreferences.getBoolean(
				PREF_KEY_TWITTER_LOGIN, false);

		/* if already logged in, then hide login layout and show share layout */
		if (isLoggedIn) {
			loginLayout.setVisibility(View.GONE);
			shareLayout.setVisibility(View.VISIBLE);

			String username = mSharedPreferences.getString(PREF_USER_NAME, "");
			userName.setText(getResources().getString(R.string.hello)
					+ username);

		} else {
			loginLayout.setVisibility(View.VISIBLE);
			shareLayout.setVisibility(View.GONE);

			Uri uri = getIntent().getData();

			if (uri != null && uri.toString().startsWith(callbackUrl)) {

				String verifier = uri.getQueryParameter(oAuthVerifier);

				try {

					/* Getting oAuth authentication token */
					AccessToken accessToken = twitter.getOAuthAccessToken(
							requestToken, verifier);

					/* Getting user id form access token */
					long userID = accessToken.getUserId();
					final User user = twitter.showUser(userID);
					final String username = user.getName();

					/* save updated token */

					loginLayout.setVisibility(View.GONE);
					shareLayout.setVisibility(View.VISIBLE);
					userName.setText(getString(R.string.hello) + username);

				} catch (Exception e) {
					Log.e("Failed to login Twitter!!", e.getMessage());
				}
			}

		}

		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			String name = savedInstanceState
					.getString(PENDING_ACTION_BUNDLE_KEY);
			pendingAction = PendingAction.valueOf(name);
		}
		loginButton = (LoginButton) findViewById(R.id.facebookLogin);
		loginButton.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
		Button twlogin = (Button) findViewById(R.id.twitterLogin);
		Button login = (Button) findViewById(R.id.Login);

		// add button listener

		login.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(LoginActivity.this,
						UserLogin.class);
				startActivity(myintent2);

			}
		});
	}

	/* Reading twitter essential configuration parameters from strings.xml */
	private void initTwitterConfigs() {
		consumerKey = getString(R.string.twitter_consumer_key);
		consumerSecret = getString(R.string.twitter_consumer_secret);
		callbackUrl = getString(R.string.twitter_callback);
		oAuthVerifier = getString(R.string.twitter_oauth_verifier);
	}

	private void loginToTwitter() {
		boolean isLoggedIn = mSharedPreferences.getBoolean(
				PREF_KEY_TWITTER_LOGIN, false);

		if (!isLoggedIn) {
			final ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(consumerKey);
			builder.setOAuthConsumerSecret(consumerSecret);

			final Configuration configuration = builder.build();
			final TwitterFactory factory = new TwitterFactory(configuration);
			twitter = factory.getInstance();

			try {
				requestToken = twitter.getOAuthRequestToken(callbackUrl);

				/**
				 * Loading twitter login page on webview for authorization Once
				 * authorized, results are received at onActivityResult
				 * */
				final Intent intent = new Intent(this, WebViewActivity.class);
				intent.putExtra(WebViewActivity.EXTRA_URL,
						requestToken.getAuthenticationURL());
				startActivityForResult(intent, WEBVIEW_REQUEST_CODE);

			} catch (TwitterException e) {
				e.printStackTrace();
			}
		} else {

			loginLayout.setVisibility(View.GONE);
			shareLayout.setVisibility(View.VISIBLE);
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.twitterLogin:
			loginToTwitter();
			break;
		}
	}

	class myTask_checkFacebookTwitterLogin_call extends
			AsyncTask<String, Void, String> {
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
						"http://millionairesorg.com/schooltimetable/facebooktwitterlogin.php?email="
								+ parameter_email));
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

			return null;
		}

		protected void onPostExecute(String str_resp) {
			if (responseString.equals("0")) {
				final Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}else if (responseString.equals("verify")) {
				final AlertDialog alertDialog = new AlertDialog.Builder(
						LoginActivity.this).create();
				alertDialog.setTitle("SKUBAG");
				alertDialog
						.setMessage("Please check your registered email for verification!!!");
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
								alertDialog.dismiss();
							}
						});
				alertDialog.show();
			}else {
				SharedPreferences myPrefs = LoginActivity.this
						.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
				SharedPreferences.Editor prefsEditor = myPrefs.edit();
				prefsEditor.putString("login", responseString.trim());
				prefsEditor.commit();
				final Intent intent = new Intent(LoginActivity.this,
						HomeScreen.class);
				startActivity(intent);
			}
			if (mProgressDialog != null)
				mProgressDialog.dismiss();

		}
	}

	public void showError(String string, boolean b) {
		// TODO Auto-generated method stub
		
	}

}
