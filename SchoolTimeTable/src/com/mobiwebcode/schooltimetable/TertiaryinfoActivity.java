package com.mobiwebcode.schooltimetable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mobiwebcode.schooltimetable.SelectsubjectDefination.myTask_DefinationsubjectList_call;
import com.mobiwebcode.schooltimetable.SelectsubjectDefination.myTask_UpdatedDatabase_call;
import com.mobiwebcode.schooltimetable.VO.AllDefinitionsVO;
import com.mobiwebcode.schooltimetable.VO.AppUtils;
import com.mobiwebcode.schooltimetable.VO.Constant;
import com.mobiwebcode.schooltimetable.VO.DefinitionListVO;
import com.mobiwebcode.schooltimetable.VO.SchoolSingleton;
import com.mobiwebcode.schooltimetable.VO.TertieryinfodetailVO;


public class TertiaryinfoActivity extends Activity {
	RelativeLayout Homelayout, LayoutSetupTimetable,
	LayoutGradecalculateTimetable, LayoutTertiary, LayoutDefinition,
	LayoutLayoutiqtestDefinition, Layoutedutainment, Layoutpasco,
	Layoutchat1;
	ListView list;
	String[] web = { "UNIVERSITY", "POLYTECHNICS(10)", "TRAINING COLLEGES(30)"};
	Integer[] imageId = { R.drawable.info, R.drawable.info, R.drawable.info,
			R.drawable.info };
	
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
	
	public SchoolSingleton singleton;
	XMLParser parser = new XMLParser();
	String xml = "";
	NodeList nl;
	Document doc;
	String DEFINATION_SUBJECT_URL = "http://millionairesorg.com/schooltimetable/tertieryinfo.php";
	private ProgressDialog mProgressDialog;
	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS1:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Database Updating, Please wait ...");
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
		setContentView(R.layout.tertiaryinfo_main);
		try{
		singleton=SchoolSingleton.getinstance(this);
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

		Button btnuniversity = (Button) findViewById(R.id.buttonuniversity);
		btnuniversity.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				singleton.SelectedSchooltype="University";
				Intent myintent2 = new Intent(TertiaryinfoActivity.this,
						SchoollistActivity.class);
				startActivity(myintent2);

			}
		});
		Button btnpolitechnics = (Button) findViewById(R.id.buttonpolitechnics);
		btnpolitechnics.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				singleton.SelectedSchooltype="Polytechnic";
				Intent myintent2 = new Intent(TertiaryinfoActivity.this,
						SchoollistActivity.class);
				startActivity(myintent2);

			}
		});
		Button btntrainingcolleges = (Button) findViewById(R.id.buttontraining);
		btntrainingcolleges.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myintent2 = new Intent(TertiaryinfoActivity.this,
						TrainingColleges.class);
				startActivity(myintent2);

			}
		});
		

		Button update = (Button) findViewById(R.id.asyncfromserver);
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (AppUtils.isNetworkAvailable(TertiaryinfoActivity.this))
					new myTask_DefinationsubjectList_call().execute();
				else
					AppUtils.ShowAlertDialog(TertiaryinfoActivity.this, "No Internet Connection is Avelable.");

			}
		});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}//end of Oncreate()
	
	
	// DownloadJSON AsyncTask for activityDetails
		class myTask_DefinationsubjectList_call extends
				AsyncTask<String, Void, String> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
			}

			@Override
			protected String doInBackground(String... aurl) {
				try {
					xml = parser.getXmlFromUrl(DEFINATION_SUBJECT_URL);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				return "";
			}

			@Override
			protected void onPostExecute(String str_resp) {
				try {
					doc = parser.getDomElement(xml); // getting
					singleton.AllTertieryinfodetailArrayList.clear();
					if (doc != null) {
						nl = doc.getElementsByTagName("tertieryinfo");
						// looping through all item nodes <item>
						for (int i = 0; i < nl.getLength(); i++) {
							Element e = (Element) nl.item(i);
							
							TertieryinfodetailVO subvo=new TertieryinfodetailVO();
							
							subvo.ter_id = parser.getValue(e, "tertiryinfoid");
							subvo.ter_name=parser.getValue(e, "tertiryinfoname");
							subvo.ter_type = parser.getValue(e, "tertiryinfotype");
							subvo.picture_path=parser.getValue(e, "picture");
							subvo.ter_description = parser.getValue(e, "description");
							subvo.ter_info=parser.getValue(e, "info");
							subvo.prog_diploma = parser.getValue(e, "diploma_program");
							subvo.prog_certificate=parser.getValue(e, "certificate_program");
							subvo.prog_undergraduate = parser.getValue(e, "undergraduate_program");
							//subvo.definitionlilstArraylist.size();
							singleton.AllTertieryinfodetailArrayList.add(subvo);
						}
					}
					        //singleton.updateTertiaryDatabaseTable(singleton.AllTertieryinfodetailArrayList);
					      //  DisplaySubjects();
					
					singleton.alldefArrayList.size();
				
				if (mProgressDialog != null)
					mProgressDialog.dismiss();
				
				new myTask_UpdatedDatabase_call().execute();
				
				//singleton.AlldefinitionsArrayList.clear();
				//singleton.dbhelper.getAlldata(SelectsubjectDefination.this);
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					if (mProgressDialog != null)
						mProgressDialog.dismiss();
				}
				
			}//end of onpost
		}//end of Async
		
		
		// Updateddtabase AsyncTask for activityDetails
			class myTask_UpdatedDatabase_call extends
					AsyncTask<String, Void, String> {

				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
				}

				@SuppressWarnings("static-access")
				@Override
				protected String doInBackground(String... aurl) {
					try {
						singleton.updateTertiaryDatabaseTable(singleton.AllTertieryinfodetailArrayList);
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					return "";
				}

				@Override
				protected void onPostExecute(String str_resp) {
					try {
						
						// DisplaySubjects();
					if (mProgressDialog != null)
						mProgressDialog.dismiss();
					singleton.AlldefinitionsArrayList.clear();
					singleton.dbhelper.getAlldata(TertiaryinfoActivity.this);
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						if (mProgressDialog != null)
							mProgressDialog.dismiss();
					}
					
				}//end of onpost
			}//end of Async
	
	public void onMenuOptionClicked(View view) {
		if (view.getId() == R.id.Homelayout) {
			Constant.MENU_ITEM_SELECTED = "Home";
			Intent intent = new Intent(TertiaryinfoActivity.this, HomeScreen.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutSetupTimetable) {
			Constant.MENU_ITEM_SELECTED = "SETUP TIMETABLE";
			Intent intent = new Intent(TertiaryinfoActivity.this, OptionActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutGradecalculateTimetable) {
			Constant.MENU_ITEM_SELECTED = "GRADE CALCULATOR";
			Intent intent = new Intent(TertiaryinfoActivity.this, GradecalculatorActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutTertiary) {
			Constant.MENU_ITEM_SELECTED = "TERTIARY INFO";
			new CollapseAnimation(slidingPanel, panelWidth,
					TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
					TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);
		}else if (view.getId() == R.id.LayoutDefinition) {
			Constant.MENU_ITEM_SELECTED = "DEFINITIONS";
			Intent intent = new Intent(TertiaryinfoActivity.this, SelectsubjectDefination.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutLayoutiqtestDefinition) {
			Constant.MENU_ITEM_SELECTED = "IQ TEST";
			Intent intent = new Intent(TertiaryinfoActivity.this,IQTestActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.Layoutedutainment) {
			Constant.MENU_ITEM_SELECTED = "EDUTAINMENT";
			Intent intent = new Intent(TertiaryinfoActivity.this, EdutainmentActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.Layoutpasco) {
			Constant.MENU_ITEM_SELECTED = "PASSCO";
			Intent intent = new Intent(TertiaryinfoActivity.this, PascoActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.Layoutchat1) {
			Constant.MENU_ITEM_SELECTED = "CHAT";
			Intent intent = new Intent(TertiaryinfoActivity.this, ChatActivity.class);
			startActivity(intent);
		}
	}

	public void onBackPressed() {
	}

}//end of Activity
