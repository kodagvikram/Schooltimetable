package com.mobiwebcode.schooltimetable;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobiwebcode.schooltimetable.VO.Constant;
import com.mobiwebcode.schooltimetable.VO.SchoolSingleton;

public class OptionActivity extends Activity {
	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
	private ProgressDialog mProgressDialog;
	String responseString = null;
	RelativeLayout Homelayout, LayoutSetupTimetable,
			LayoutGradecalculateTimetable, LayoutTertiary, LayoutDefinition,
			LayoutLayoutiqtestDefinition, Layoutedutainment, Layoutpasco,
			Layoutchat1;
	private RadioGroup radioGroup;
	private RadioButton radioButton;
	private Button btnBack, btnContinue;
	FrameLayout mainFrameLyout;
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters;
	LinearLayout.LayoutParams listViewParameters;
	Button menuBtn;
	private DisplayMetrics metrics;
	private RelativeLayout slidingPanel;
	int panelWidth = 0;
	private boolean isExpanded;
	Button btnjamb;
	TextView jambtxt;
	
	public SchoolSingleton singleton;

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
		setContentView(R.layout.setuptimetable_main);
		
		try {
			
		
		
      singleton=SchoolSingleton.getinstance(OptionActivity.this);
		
		singleton.alldefArrayList.clear();         ///update defination database
		
		if(singleton.alldefArrayList.size()<1)
			singleton.CalltoUpdateDatabase();

		jambtxt = (TextView) findViewById(R.id.Text_jamb);

		Button buttonClick = (Button) findViewById(R.id.SettingBtn);

		// add listene to button
		buttonClick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// Create custom dialog object
				final Dialog dialog = new Dialog(OptionActivity.this);
				// Include dialog.xml file
				dialog.setContentView(R.layout.setting_aleart);
				// Set dialog title
				dialog.setTitle("                     SETTING");

				// set values for custom dialog components - text, image and
				// button
				// TextView text = (TextView)
				// dialog.findViewById(R.id.textDialog);
				// text.setText("Custom dialog Android example.");
				// ImageView image = (ImageView)
				// dialog.findViewById(R.id.imageDialog);
				// image.setImageResource(R.drawable.image0);

				dialog.show();

				Button buttonhelp = (Button) dialog
						.findViewById(R.id.buttonhelp);
				// if decline button is clicked, close the custom dialog
				buttonhelp.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent myintent2 = new Intent(OptionActivity.this,
								Help.class);
						startActivity(myintent2);
						dialog.dismiss();
					}
				});

				Button buttoninvite = (Button) dialog
						.findViewById(R.id.buttoninvite);
				// if decline button is clicked, close the custom dialog
				buttoninvite.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent myintent2 = new Intent(OptionActivity.this,
								Invite.class);
						startActivity(myintent2);
						dialog.dismiss();
					}
				});

				Button buttonreview = (Button) dialog
						.findViewById(R.id.buttonreviw);
				// if decline button is clicked, close the custom dialog
				buttonreview.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent myintent2 = new Intent(OptionActivity.this,
								Review.class);
						startActivity(myintent2);
						dialog.dismiss();
					}
				});

			}

		});

		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels) * 0.75);
		slidingPanel = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
		slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
				.getLayoutParams();
		slidingPanelParameters.width = metrics.widthPixels;
		slidingPanel.setLayoutParams(slidingPanelParameters);

		menuBtn = (Button) findViewById(R.id.menuBtn);
		menuBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!isExpanded) {
					isExpanded = true;

					// Expand
					new ExpandAnimation(slidingPanel, panelWidth,
							Animation.RELATIVE_TO_SELF, 0.0f,
							Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);
				} else {
					isExpanded = false;

					// Collapse
					new CollapseAnimation(slidingPanel, panelWidth,
							TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
							TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f,
							0, 0.0f);

				}
			}
		});

		Button btnmytimetable = (Button) findViewById(R.id.buttonmytimetable);

		btnmytimetable.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OptionActivity.this,
						CreateTimetable.class);
				startActivity(intent);
			}

		});

		Button btnnovdec = (Button) findViewById(R.id.buttonnovdesc);

		btnnovdec.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File file = new File("/sdcard/.skubag/novdec.txt");
				if (file.exists() == true) {
					Intent i = new Intent(OptionActivity.this,
							NovDecActivity.class);
					startActivity(i);

				} else {
					Intent intent = new Intent(OptionActivity.this,
							Selectsubjectnovdec.class);
					startActivity(intent);
				}
			}

		});
		Button btnwassce = (Button) findViewById(R.id.buttonwassce);

		btnwassce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File file = new File("/sdcard/.skubag/wassce.txt");
				if (file.exists() == true) {
					Intent i = new Intent(OptionActivity.this,
							WassceTimeTableActivity.class);
					startActivity(i);

				} else {
					Intent intent = new Intent(OptionActivity.this,
							SelectWschesubject.class);
					startActivity(intent);
				}
			}

		});
		btnjamb = (Button) findViewById(R.id.buttonjamb);

		btnjamb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File file = new File("/sdcard/.skubag/jamb.txt");
				if (file.exists() == true) {
					Intent i = new Intent(OptionActivity.this,
							JambTimeTableActivity.class);
					startActivity(i);

				} else {
					Intent intent = new Intent(OptionActivity.this,
							Selectsubjectjamb.class);
					startActivity(intent);
				}
			}

		});

		Homelayout = (RelativeLayout) findViewById(R.id.Homelayout);
		LayoutSetupTimetable = (RelativeLayout) findViewById(R.id.LayoutSetupTimetable);
		LayoutGradecalculateTimetable = (RelativeLayout) findViewById(R.id.LayoutGradecalculateTimetable);
		LayoutTertiary = (RelativeLayout) findViewById(R.id.LayoutTertiary);
		LayoutDefinition = (RelativeLayout) findViewById(R.id.LayoutDefinition);
		LayoutLayoutiqtestDefinition = (RelativeLayout) findViewById(R.id.LayoutLayoutiqtestDefinition);
		Layoutedutainment = (RelativeLayout) findViewById(R.id.Layoutedutainment);
		Layoutpasco = (RelativeLayout) findViewById(R.id.Layoutpasco);
		Layoutchat1 = (RelativeLayout) findViewById(R.id.Layoutchat1);

		// Button home = (Button) findViewById(R.id.btn_home);
		// Button btnsetuptimetable = (Button)
		// findViewById(R.id.btn_setuptimetable);
		// Button btnchat = (Button) findViewById(R.id.btn_chat);
		// Button btngradecalculate = (Button)
		// findViewById(R.id.btn_gradecalculate);
		// Button btntertiary = (Button) findViewById(R.id.btn_tertiary);
		// Button btndefination = (Button) findViewById(R.id.btn_Definition);
		// // Button btnsetting = (Button)
		// findViewById(R.id.btn_setuptimetable);
		// Button btnpasco = (Button) findViewById(R.id.btn_pasco);
		// Button btniqtest = (Button) findViewById(R.id.btn_iqtest);
		// Button btnedutainment = (Button) findViewById(R.id.btn_edutainment);
		//
		// home.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(OptionActivity.this,
		// HomeScreen.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		// btnsetuptimetable.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(OptionActivity.this,
		// OptionActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		// btnchat.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent3 = new Intent(OptionActivity.this,
		// ChatActivity.class);
		// startActivity(myintent3);
		//
		// }
		// });
		//
		// btngradecalculate.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(OptionActivity.this,
		// GradecalculatorActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		// btntertiary.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(OptionActivity.this,
		// TertiaryinfoActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		// btndefination.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(OptionActivity.this,
		// SelectsubjectDefination.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		// btnpasco.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(OptionActivity.this,
		// PascoActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		// btniqtest.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(OptionActivity.this,
		// IQTestActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		// btnedutainment.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent3 = new Intent(OptionActivity.this,
		// EdutainmentActivity.class);
		// startActivity(myintent3);
		//
		// }
		// });


		if (UserLogin.country.equalsIgnoreCase("NIGERIA")) {
			btnjamb.setVisibility(View.VISIBLE);
			jambtxt.setVisibility(View.VISIBLE);
		} else
			btnjamb.setVisibility(View.GONE);
		jambtxt.setVisibility(View.GONE);
		if (UserLogin.country.equals("") || (UserLogin.country == null)) {
			new myTask_usercountry_call().execute();
		}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}//end of oncreate

	public void onMenuOptionClicked(View view) {
		if (view.getId() == R.id.Homelayout) {
			Constant.MENU_ITEM_SELECTED = "Home";
			Intent intent = new Intent(OptionActivity.this, HomeScreen.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutSetupTimetable) {
			Constant.MENU_ITEM_SELECTED = "SETUP TIMETABLE";
			new CollapseAnimation(slidingPanel, panelWidth,
					TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
					TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);
		} else if (view.getId() == R.id.LayoutGradecalculateTimetable) {
			Constant.MENU_ITEM_SELECTED = "GRADE CALCULATOR";
			Intent intent = new Intent(OptionActivity.this,
					GradecalculatorActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutTertiary) {
			Constant.MENU_ITEM_SELECTED = "TERTIARY INFO";
			Intent intent = new Intent(OptionActivity.this,
					TertiaryinfoActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutDefinition) {
			Constant.MENU_ITEM_SELECTED = "DEFINITIONS";
			Intent intent = new Intent(OptionActivity.this,
					SelectsubjectDefination.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutLayoutiqtestDefinition) {
			Constant.MENU_ITEM_SELECTED = "IQ TEST";
			Intent intent = new Intent(OptionActivity.this,
					IQTestActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.Layoutedutainment) {
			Constant.MENU_ITEM_SELECTED = "EDUTAINMENT";
			Intent intent = new Intent(OptionActivity.this,
					EdutainmentActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.Layoutpasco) {
			Constant.MENU_ITEM_SELECTED = "PASSCO";
			Intent intent = new Intent(OptionActivity.this, PascoActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.Layoutchat1) {
			Constant.MENU_ITEM_SELECTED = "CHAT";
			Intent intent = new Intent(OptionActivity.this, ChatActivity.class);
			startActivity(intent);
		}
	}


	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	class myTask_usercountry_call extends AsyncTask<String, Void, String> {
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
						"http://millionairesorg.com/schooltimetable/usercountry.php?userid="
								+ UserLogin.userid));
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
			SharedPreferences myPrefs = OptionActivity.this
					.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
			SharedPreferences.Editor prefsEditor = myPrefs.edit();
			prefsEditor.putString("country", responseString.trim());
			prefsEditor.commit();

			if (responseString.equalsIgnoreCase("NIGERIA")) {
				btnjamb.setVisibility(View.VISIBLE);
				jambtxt.setVisibility(View.VISIBLE);
			} else {
				btnjamb.setVisibility(View.GONE);
				jambtxt.setVisibility(View.GONE);
			}
			if (mProgressDialog != null)
				mProgressDialog.dismiss();

		}
	}

}