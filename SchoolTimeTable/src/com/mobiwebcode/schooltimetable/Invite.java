package com.mobiwebcode.schooltimetable;

import java.util.ArrayList;

import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.WebDialog;

import com.mobiwebcode.schooltimetable.VO.FriendlistVo;

public class Invite extends Activity {
	public static ConfigurationBuilder builder;
	public static final int PICK_CONTACT = 0;
	Button facebook, twitters, sms;
	WebDialog dialog;
	String dialogAction = "apprequests";
	Bundle dialogParams;
	private UiLifecycleHelper uiHelper;
	CustomArrayList adapter;
	public static String consumerKey = "";
	public static String consumerSecret = "";
	public static String OAuthAccessToken = "";
	public static String OAuthAccessTokenSecret = "";
	private String callbackUrl = null;
	private String oAuthVerifier = null;
	private TextView userName;
	String screenname;
	public static Twitter twitter;
	private static RequestToken requestToken;
	private static SharedPreferences mSharedPreferences;
	private static final String PREF_NAME = "sample_twitter_pref";
	private static final String PREF_USER_NAME = "twitter_user_name";
	private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	private static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";
	public static final int WEBVIEW_REQUEST_CODE = 100;
	private View navigationLayoutmain;
	private View shareLayout;
	EditText mShareEditText;

	public static ArrayList<FriendlistVo> list = new ArrayList<FriendlistVo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invite);

		uiHelper = new UiLifecycleHelper(Invite.this, callback);
		shareLayout = (LinearLayout) findViewById(R.id.share_layout);
		mShareEditText = (EditText) findViewById(R.id.share_text);
		navigationLayoutmain = (RelativeLayout) findViewById(R.id.navigationLayoutmain);
		userName = (TextView) findViewById(R.id.user_name);
		facebook = (Button) findViewById(R.id.facebook);
		twitters = (Button) findViewById(R.id.twitter);
		sms = (Button) findViewById(R.id.sms);
		initTwitterConfigs();
		twitters.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginToTwitter();

			}
		});

		facebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				facebookLogin();

			}

			private Context getApplicationContext() {
				// TODO Auto-generated method stub
				return null;
			}
		});

		sms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Invite.this, SendSMS.class);
				startActivity(intent);
			}
		});

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
			navigationLayoutmain.setVisibility(View.GONE);
			shareLayout.setVisibility(View.VISIBLE);

			String username = mSharedPreferences.getString(PREF_USER_NAME, "");
			userName.setText(getResources().getString(R.string.hello)
					+ username);

		} else {
			navigationLayoutmain.setVisibility(View.VISIBLE);
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

					navigationLayoutmain.setVisibility(View.GONE);
					shareLayout.setVisibility(View.VISIBLE);
					userName.setText(getString(R.string.hello) + username);
					screenname = twitter.showUser(userID).getScreenName();

				} catch (Exception e) {
					Log.e("Failed to login Twitter!!", e.getMessage());
				}
			}

		}

	}

	private void initTwitterConfigs() {
		consumerKey = getString(R.string.twitter_consumer_key);
		consumerSecret = getString(R.string.twitter_consumer_secret);
		OAuthAccessToken = getString(R.string.twitter_OAuthAccessToken);
		OAuthAccessTokenSecret = getString(R.string.twitter_OAuthAccessTokenSecret);
		callbackUrl = getString(R.string.twitter_callback);
		oAuthVerifier = getString(R.string.twitter_oauth_verifier);
	}

	private void loginToTwitter() {

		boolean isLoggedIn = mSharedPreferences.getBoolean(
				PREF_KEY_TWITTER_LOGIN, false);

		if (!isLoggedIn) {
			builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(consumerKey);
			builder.setOAuthConsumerSecret(consumerSecret);
			builder.setOAuthAccessToken(null);
			builder.setOAuthAccessTokenSecret(null);
			
			TwitterFactory tf = new TwitterFactory(builder.build());
			 twitter = tf.getInstance();
			
//			Configuration configuration = builder.build();
//			TwitterFactory factory = new TwitterFactory(configuration);
//			twitter = factory.getInstance();
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
			navigationLayoutmain.setVisibility(View.GONE);
			shareLayout.setVisibility(View.VISIBLE);
		}
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			showfacebookfriendrequestdialog();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
		if (requestCode == 100) {

			String verifier = data.getExtras().getString(oAuthVerifier);
			try {
				AccessToken accessToken = twitter.getOAuthAccessToken(
						requestToken, verifier);

				long userID = accessToken.getUserId();
				final User user = twitter.showUser(userID);
				String username = user.getName();
				getfriendlist();
				Intent i = new Intent(Invite.this, TwitterFriendlist.class);
				startActivity(i);

			} catch (Exception e) {
				Log.e("Twitter Login Failed", e.getMessage());
			}
		}
	}

	void facebookLogin() {
		if (Session.getActiveSession() == null
				|| !Session.getActiveSession().isOpened()) {
			Session.openActiveSession(Invite.this, true, callback);
		} else {
			showfacebookfriendrequestdialog();
		}
	}

	void showfacebookfriendrequestdialog() {
		Bundle params = new Bundle();
		params.putString("message", "Check out SKU.BAGS App on google play");
		showDialogWithoutNotificationBar("apprequests", params);
	}

	private void showDialogWithoutNotificationBar(String action, Bundle params) {
		// Context appContext = this.getApplicationContext();
		dialog = new WebDialog.Builder(Invite.this,
				com.facebook.Session.getActiveSession(), action, params)
				.setOnCompleteListener(new WebDialog.OnCompleteListener() {
					@Override
					public void onComplete(Bundle values,
							FacebookException error) {
						if (error != null
								&& !(error instanceof FacebookOperationCanceledException)) {
							((LoginActivity) getApplicationContext())
									.showError("Error!!!!", false);
							System.out.println("Error!!!!" + error);
						}

						dialog = null;
						dialogAction = null;
						dialogParams = null;
					}
				}).build();

		Window dialog_window = dialog.getWindow();
		dialog_window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		dialogAction = action;
		dialogParams = params;

		dialog.show();
	}

	void getfriendlist() {
		PagableResponseList<User> followersList;

		try {
			long cursor = -1;
			followersList = twitter.getFollowersList(twitter.getId(), cursor);
			// IDs followersIDs = twitter.getFollowersIDs(userId,
			// cursor);
			for (int i = 0; i < followersList.size(); i++) {
				User user = followersList.get(i);
				FriendlistVo flVo = new FriendlistVo();
				String username = user.getName();
				String screenname = user.getScreenName();
				flVo.screenname = screenname;
				flVo.username = username;
				list.add(flVo);
				System.out.println("Name" + i + ":" + flVo.username);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
