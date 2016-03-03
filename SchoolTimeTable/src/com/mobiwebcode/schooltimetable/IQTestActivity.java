package com.mobiwebcode.schooltimetable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mobiwebcode.schooltimetable.VO.Constant;

public class IQTestActivity extends Activity {
	RelativeLayout Homelayout, LayoutSetupTimetable,
			LayoutGradecalculateTimetable, LayoutTertiary, LayoutDefinition,
			LayoutLayoutiqtestDefinition, Layoutedutainment, Layoutpasco,
			Layoutchat1;
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
	String selectedsubject = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iqtest_main);

		Button starttest = (Button) findViewById(R.id.btn_starttest);
		starttest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selectedsubject.equals("")) {

					final AlertDialog alertDialog = new AlertDialog.Builder(
							IQTestActivity.this).create();
					alertDialog.setIcon(R.drawable.ic_launcher);
					alertDialog.setTitle("SKUBAG");
					alertDialog.setMessage("Please choose your subject");
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									alertDialog.dismiss();
								}
							});
					alertDialog.show();
				} else {
					Intent myintent2 = new Intent(IQTestActivity.this,
							TestActivity.class);
					startActivity(myintent2);

				}
			}

		});
		Button btnschoolsmeritlist = (Button) findViewById(R.id.btn_Schoolsmeritlist);

		btnschoolsmeritlist.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(IQTestActivity.this,
						SchoolsMeritList.class);
				startActivity(myintent2);

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
		// Button btngeneratetimetable = (Button)
		// findViewById(R.id.btn_generatetimetable);
		// Button btngradecalculate = (Button)
		// findViewById(R.id.btn_gradecalculate);
		// Button btntertiary = (Button) findViewById(R.id.btn_tertiary);
		// Button btndefination = (Button) findViewById(R.id.btn_Definition);
		// // Button btnsetting = (Button)
		// findViewById(R.id.btn_setuptimetable);
		// Button btnpasco = (Button) findViewById(R.id.btn_pasco);
		// Button btniqtest = (Button) findViewById(R.id.btn_iqtest);
		// Button btnedutainment = (Button) findViewById(R.id.btn_edutainment);
		// Button btnchat = (Button) findViewById(R.id.btn_chat);
		//
		// home.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(IQTestActivity.this,
		// HomeScreen.class);
		// startActivity(myintent2);
		//
		// }
		// });
		// btnsetuptimetable.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(IQTestActivity.this,
		// OptionActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		//
		// btngradecalculate.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(IQTestActivity.this,
		// GradecalculatorActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		// btntertiary.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(IQTestActivity.this,
		// TertiaryinfoActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		// btndefination.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(IQTestActivity.this,
		// SelectsubjectDefination.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		//
		//
		// btnpasco.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(IQTestActivity.this,
		// PascoActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		// btniqtest.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(IQTestActivity.this,
		// IQTestActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		// btnedutainment.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent3 = new Intent(IQTestActivity.this,
		// EdutainmentActivity.class);
		// startActivity(myintent3);
		//
		// }
		// });
		// btnchat.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent3 = new Intent(IQTestActivity.this,
		// ChatActivity.class);
		// startActivity(myintent3);
		//
		// }
		// });
		//

		// Prepare the Interstitial Ad
	}

	public void onMenuOptionClicked(View view) {
		if (view.getId() == R.id.Homelayout) {
			Constant.MENU_ITEM_SELECTED = "Home";
			Intent intent = new Intent(IQTestActivity.this, HomeScreen.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutSetupTimetable) {
			Constant.MENU_ITEM_SELECTED = "SETUP TIMETABLE";
			Intent intent = new Intent(IQTestActivity.this,
					OptionActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutGradecalculateTimetable) {
			Constant.MENU_ITEM_SELECTED = "GRADE CALCULATOR";
			Intent intent = new Intent(IQTestActivity.this,
					GradecalculatorActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutTertiary) {
			Constant.MENU_ITEM_SELECTED = "TERTIARY INFO";
			Intent intent = new Intent(IQTestActivity.this,
					TertiaryinfoActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutDefinition) {
			Constant.MENU_ITEM_SELECTED = "DEFINITIONS";
			Intent intent = new Intent(IQTestActivity.this,
					SelectsubjectDefination.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutLayoutiqtestDefinition) {
			Constant.MENU_ITEM_SELECTED = "IQ TEST";
			new CollapseAnimation(slidingPanel, panelWidth,
					TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
					TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);
		} else if (view.getId() == R.id.Layoutedutainment) {
			Constant.MENU_ITEM_SELECTED = "EDUTAINMENT";
			Intent intent = new Intent(IQTestActivity.this,
					EdutainmentActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.Layoutpasco) {
			Constant.MENU_ITEM_SELECTED = "PASSCO";
			Intent intent = new Intent(IQTestActivity.this, PascoActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.Layoutchat1) {
			Constant.MENU_ITEM_SELECTED = "CHAT";
			Intent intent = new Intent(IQTestActivity.this, ChatActivity.class);
			startActivity(intent);
		}
	}

	public void onBackPressed() {

	}
}
