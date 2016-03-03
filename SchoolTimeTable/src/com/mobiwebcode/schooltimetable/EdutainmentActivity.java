package com.mobiwebcode.schooltimetable;

import java.util.ArrayList;

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
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mobiwebcode.schooltimetable.VO.Constant;

public class EdutainmentActivity extends Activity {
	RelativeLayout Homelayout, LayoutSetupTimetable,
	LayoutGradecalculateTimetable, LayoutTertiary, LayoutDefinition,
	LayoutLayoutiqtestDefinition, Layoutedutainment, Layoutpasco,
	Layoutchat1;
	ListView list;
	ArrayList<String> edutainmentArrayList = new ArrayList<String>();
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
		setContentView(R.layout.edutainment_main);
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
		
		Button btnnews = (Button) findViewById(R.id.buttonnews);
		btnnews.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(EdutainmentActivity.this,
						News.class);
				startActivity(myintent2);

			}
		});
		Button btnquotes = (Button) findViewById(R.id.buttonQuotes);
		btnquotes.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(EdutainmentActivity.this,
						Quotes.class);
				startActivity(myintent2);

			}
		});
		Button btnimages = (Button) findViewById(R.id.buttonimages);
		btnimages.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(EdutainmentActivity.this,
						Images.class);
				startActivity(myintent2);

			}
		});
		Button btnvideos = (Button) findViewById(R.id.buttonvideos);
		btnvideos.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(EdutainmentActivity.this,
						Videos.class);
				startActivity(myintent2);

			}
		});
		
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
//		Button btnchat = (Button) findViewById(R.id.btn_chat);
//
//		home.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(EdutainmentActivity.this,
//						HomeScreen.class);
//				startActivity(myintent2);
//
//			}
//		});
//		btnsetuptimetable.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(EdutainmentActivity.this,
//						OptionActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//
//
//		btngradecalculate.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(EdutainmentActivity.this,
//						GradecalculatorActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//
//		btntertiary.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(EdutainmentActivity.this,
//						TertiaryinfoActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//
//		btndefination.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(EdutainmentActivity.this,
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
//				Intent myintent2 = new Intent(EdutainmentActivity.this,
//						PascoActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//		
//		btniqtest.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(EdutainmentActivity.this,
//						IQTestActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//		btnedutainment.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent3 = new Intent(EdutainmentActivity.this,
//						EdutainmentActivity.class);
//				startActivity(myintent3);
//
//			}
//		});
//		btnchat.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent3 = new Intent(EdutainmentActivity.this,
//						ChatActivity.class);
//				startActivity(myintent3);
//
//			}
//		});
		
		
		
}
	public void onMenuOptionClicked(View view) {
		if (view.getId() == R.id.Homelayout) {
			Constant.MENU_ITEM_SELECTED = "Home";
			Intent intent = new Intent(EdutainmentActivity.this, HomeScreen.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutSetupTimetable) {
			Constant.MENU_ITEM_SELECTED = "SETUP TIMETABLE";
			Intent intent = new Intent(EdutainmentActivity.this, OptionActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutGradecalculateTimetable) {
			Constant.MENU_ITEM_SELECTED = "GRADE CALCULATOR";
			Intent intent = new Intent(EdutainmentActivity.this, GradecalculatorActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutTertiary) {
			Constant.MENU_ITEM_SELECTED = "TERTIARY INFO";
			Intent intent = new Intent(EdutainmentActivity.this, TertiaryinfoActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutDefinition) {
			Constant.MENU_ITEM_SELECTED = "DEFINITIONS";
			Intent intent = new Intent(EdutainmentActivity.this, SelectsubjectDefination.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutLayoutiqtestDefinition) {
			Constant.MENU_ITEM_SELECTED = "IQ TEST";
			Intent intent = new Intent(EdutainmentActivity.this, IQTestActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.Layoutedutainment) {
			Constant.MENU_ITEM_SELECTED = "EDUTAINMENT";
			new CollapseAnimation(slidingPanel, panelWidth,
					TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
					TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);
		}else if (view.getId() == R.id.Layoutpasco) {
			Constant.MENU_ITEM_SELECTED = "PASSCO";
			Intent intent = new Intent(EdutainmentActivity.this, PascoActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.Layoutchat1) {
			Constant.MENU_ITEM_SELECTED = "CHAT";
			Intent intent = new Intent(EdutainmentActivity.this, ChatActivity.class);
			startActivity(intent);
		}
	}
	public void onBackPressed() {
		
	}
}	
	
	
