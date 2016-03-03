package com.mobiwebcode.schooltimetable;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mobiwebcode.schooltimetable.VO.Constant;

public class ChatActivity extends Activity {
	RelativeLayout Homelayout, LayoutSetupTimetable,
			LayoutGradecalculateTimetable, LayoutTertiary, LayoutDefinition,
			LayoutLayoutiqtestDefinition, Layoutedutainment, Layoutpasco,
			Layoutchat1;
	private EditText message;
	FrameLayout mainFrameLyout;
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters;
	LinearLayout.LayoutParams listViewParameters;
	Button menuBtn;
	private static final int PICK_FROM_FILE = 2;
	private static final int PICK_FROM_FILE_VIDEO = 3;
	private DisplayMetrics metrics;
	private RelativeLayout slidingPanel;
	int panelWidth = 0;
	private boolean isExpanded;
	protected int SELECT_PHOTO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatactivity_main);

		message = (EditText) findViewById(R.id.editText1);

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
		// Button btnchat1 = (Button) findViewById(R.id.message);
		// btnchat1.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// String whatsAppMessage = message.getText().toString();
		//
		// Intent sendIntent = new Intent();
		// sendIntent.setAction(Intent.ACTION_SEND);
		// sendIntent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
		// sendIntent.setType("text/plain");
		//
		// // Do not forget to add this to open whatsApp App specifically
		// sendIntent.setPackage("com.whatsapp");
		// startActivity(sendIntent);
		//
		// }
		// });
		//
		// Button pickImage = (Button) findViewById(R.id.btnMedia);
		// pickImage.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View view) {
		// {
		// Intent intent = new Intent();
		// intent.setType("image/*");
		// intent.setAction(Intent.ACTION_GET_CONTENT);
		// startActivityForResult(
		// Intent.createChooser(intent, "Select Picture"),
		// PICK_FROM_FILE);
		// }
		// }
		// });
		//
		// Button pickVideo = (Button) findViewById(R.id.btnVideo);
		// pickVideo.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View view) {
		// {
		// Intent intent = new Intent();
		// intent.setType("video/*");
		// intent.setAction(Intent.ACTION_GET_CONTENT);
		// startActivityForResult(
		// Intent.createChooser(intent, "Select Video"),
		// PICK_FROM_FILE_VIDEO);
		// }
		// }
		// });
		//
		// home.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(ChatActivity.this,
		// HomeScreen.class);
		// startActivity(myintent2);
		//
		// }
		// });
		// btnsetuptimetable.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(ChatActivity.this,
		// OptionActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		// btngradecalculate.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(ChatActivity.this,
		// GradecalculatorActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		// btntertiary.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(ChatActivity.this,
		// TertiaryinfoActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		// btndefination.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(ChatActivity.this,
		// SelectsubjectDefination.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		// btnpasco.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(ChatActivity.this,
		// PascoActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		//
		// btniqtest.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent2 = new Intent(ChatActivity.this,
		// IQTestActivity.class);
		// startActivity(myintent2);
		//
		// }
		// });
		// btnedutainment.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent3 = new Intent(ChatActivity.this,
		// EdutainmentActivity.class);
		// startActivity(myintent3);
		//
		// }
		// });
		// btnchat.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// Intent myintent3 = new Intent(ChatActivity.this,
		// ChatActivity.class);
		// startActivity(myintent3);
		//
		// }
		// });

		// Prepare the Interstitial Ad
	}

	public void sendMessage(View v) {
		if (v.getId() == R.id.message) {
			String whatsAppMessage = message.getText().toString();
            if(!whatsAppMessage.equalsIgnoreCase(""))
            {
            	Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
			sendIntent.setType("text/plain");
			// Do not forget to add this to open whatsApp App specifically
			sendIntent.setPackage("com.whatsapp");
			startActivity(sendIntent);
            }
		} else if (v.getId() == R.id.btnMedia) {
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(
					Intent.createChooser(intent, "Select Picture"),
					PICK_FROM_FILE);
		}else if(v.getId() == R.id.btnVideo){
			 Intent intent = new Intent();
			intent.setType("video/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(
					Intent.createChooser(intent, "Select Video"),
					PICK_FROM_FILE_VIDEO);
		}
	}


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		else if (requestCode == PICK_FROM_FILE) {
			Uri selectedImage = data.getData();
			Intent share = new Intent(Intent.ACTION_SEND);
			share.putExtra(Intent.EXTRA_TEXT, "Shared From SKUBAG");
			share.setType("text/plain");
			share.putExtra(Intent.EXTRA_STREAM, selectedImage);
			share.setType("image/jpeg*");
			share.setPackage("com.whatsapp");
			
			startActivity(Intent.createChooser(share, "Share Image!"));
			
		} else if (requestCode == PICK_FROM_FILE_VIDEO) {
			Uri selectedImage = data.getData();
			Intent share = new Intent(Intent.ACTION_SEND);
	
			share.putExtra(Intent.EXTRA_STREAM, selectedImage);
			share.setType("video/*");
			share.setPackage("com.whatsapp");
			startActivity(share);
			
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "Shared From SKUBAG");
			sendIntent.setType("text/plain");
			sendIntent.setPackage("com.whatsapp");
			startActivity(sendIntent);

			
		}

	

	}

	public void onMenuOptionClicked(View view) {
		if (view.getId() == R.id.Homelayout) {
			Constant.MENU_ITEM_SELECTED = "Home";
			Intent intent = new Intent(ChatActivity.this, HomeScreen.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutSetupTimetable) {
			Constant.MENU_ITEM_SELECTED = "SETUP TIMETABLE";
			Intent intent = new Intent(ChatActivity.this, OptionActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutGradecalculateTimetable) {
			Constant.MENU_ITEM_SELECTED = "GRADE CALCULATOR";
			Intent intent = new Intent(ChatActivity.this,
					GradecalculatorActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutTertiary) {
			Constant.MENU_ITEM_SELECTED = "TERTIARY INFO";
			Intent intent = new Intent(ChatActivity.this,
					TertiaryinfoActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutDefinition) {
			Constant.MENU_ITEM_SELECTED = "DEFINITIONS";
			Intent intent = new Intent(ChatActivity.this,
					SelectsubjectDefination.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutLayoutiqtestDefinition) {
			Constant.MENU_ITEM_SELECTED = "IQ TEST";
			Intent intent = new Intent(ChatActivity.this, IQTestActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.Layoutedutainment) {
			Constant.MENU_ITEM_SELECTED = "EDUTAINMENT";
			Intent intent = new Intent(ChatActivity.this,
					EdutainmentActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.Layoutpasco) {
			Constant.MENU_ITEM_SELECTED = "PASSCO";
			Intent intent = new Intent(ChatActivity.this, PascoActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.Layoutchat1) {
			Constant.MENU_ITEM_SELECTED = "CHAT";
			new CollapseAnimation(slidingPanel, panelWidth,
					TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
					TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);
		}
	}

	public void onBackPressed() {

	}

}
