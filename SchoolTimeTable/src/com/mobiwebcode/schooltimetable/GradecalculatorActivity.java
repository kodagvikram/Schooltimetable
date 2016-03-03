package com.mobiwebcode.schooltimetable;


import android.app.Activity;
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

public class GradecalculatorActivity extends Activity {
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gradecalculator_main);
		
		
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

//		Button home = (Button) findViewById(R.id.btn_home);
//		Button btnsetuptimetable = (Button) findViewById(R.id.btn_setuptimetable);
//		Button btngeneratetimetable = (Button) findViewById(R.id.btn_generatetimetable);
//		Button btngradecalculate = (Button) findViewById(R.id.btn_gradecalculate);
//		Button btntertiary = (Button) findViewById(R.id.btn_tertiary);
//		Button btndefination = (Button) findViewById(R.id.btn_Definition);
//	//	Button btnsetting = (Button) findViewById(R.id.btn_setuptimetable);
//		Button btnpasco = (Button) findViewById(R.id.btn_pasco);
//		Button btniqtest = (Button) findViewById(R.id.btn_iqtest);
//		Button btnedutainment = (Button) findViewById(R.id.btn_edutainment);
//
//		Button buttonmtnmm = (Button) findViewById(R.id.buttonmtnmm);
//		buttonmtnmm.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
//	             myWebLink.setData(Uri.parse("http://www.waecdirect.org/"));
//	             startActivity(myWebLink);
//
//			}
//		});
//
//		Button buttontigocash = (Button) findViewById(R.id.buttontigocash);
//		buttontigocash.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
//	             myWebLink.setData(Uri.parse("http://www.waecdirect.org/"));
//	             startActivity(myWebLink);
//
//			}
//		});
//		
//		Button buttonairtelmm = (Button) findViewById(R.id.buttonairtelmm);
//		buttonairtelmm.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
//	             myWebLink.setData(Uri.parse("http://www.waecdirect.org/"));
//	             startActivity(myWebLink);
//
//			}
//		});
//		
//		Button btnchat = (Button) findViewById(R.id.btn_chat);
//		btnsetuptimetable.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(GradecalculatorActivity.this,
//						OptionActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//
//		
//
//		btngradecalculate.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(GradecalculatorActivity.this,
//						GradecalculatorActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//
//		btntertiary.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(GradecalculatorActivity.this,
//						TertiaryinfoActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//
//		btndefination.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(GradecalculatorActivity.this,
//						SelectsubjectDefination.class);
//				startActivity(myintent2);
//
//			}
//		});
//
//		
//
//		btnpasco.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(GradecalculatorActivity.this,
//						PascoActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//		
//		btniqtest.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(GradecalculatorActivity.this,
//						IQTestActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//		btnedutainment.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent3 = new Intent(GradecalculatorActivity.this,
//						EdutainmentActivity.class);
//				startActivity(myintent3);
//
//			}
//		});
//		btnchat.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent3 = new Intent(GradecalculatorActivity.this,
//						ChatActivity.class);
//				startActivity(myintent3);
//
//			}
//		});
//		home.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent3 = new Intent(GradecalculatorActivity.this,
//						HomeScreen.class);
//				startActivity(myintent3);
//
//			}
//		});
//		
//		
	}
	

	public void onMenuOptionClicked(View view) {
		if (view.getId() == R.id.Homelayout) {
			Constant.MENU_ITEM_SELECTED = "Home";
			Intent intent = new Intent(GradecalculatorActivity.this, HomeScreen.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutSetupTimetable) {
			Constant.MENU_ITEM_SELECTED = "SETUP TIMETABLE";
			Intent intent = new Intent(GradecalculatorActivity.this, OptionActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutGradecalculateTimetable) {
			Constant.MENU_ITEM_SELECTED = "GRADE CALCULATOR";
			new CollapseAnimation(slidingPanel, panelWidth,
					TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
					TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);
		}else if (view.getId() == R.id.LayoutTertiary) {
			Constant.MENU_ITEM_SELECTED = "TERTIARY INFO";
			Intent intent = new Intent(GradecalculatorActivity.this, TertiaryinfoActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutDefinition) {
			Constant.MENU_ITEM_SELECTED = "DEFINITIONS";
			Intent intent = new Intent(GradecalculatorActivity.this, SelectsubjectDefination.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutLayoutiqtestDefinition) {
			Constant.MENU_ITEM_SELECTED = "IQ TEST";
			Intent intent = new Intent(GradecalculatorActivity.this,IQTestActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.Layoutedutainment) {
			Constant.MENU_ITEM_SELECTED = "EDUTAINMENT";
			Intent intent = new Intent(GradecalculatorActivity.this, EdutainmentActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.Layoutpasco) {
			Constant.MENU_ITEM_SELECTED = "PASSCO";
			Intent intent = new Intent(GradecalculatorActivity.this, PascoActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.Layoutchat1) {
			Constant.MENU_ITEM_SELECTED = "CHAT";
			Intent intent = new Intent(GradecalculatorActivity.this, ChatActivity.class);
			startActivity(intent);
		}
	}
	public void onBackPressed() {
		
	}
}

