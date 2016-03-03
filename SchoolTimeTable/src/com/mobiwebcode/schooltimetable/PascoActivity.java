package com.mobiwebcode.schooltimetable;

import java.util.ArrayList;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobiwebcode.schooltimetable.VO.Constant;
import com.mobiwebcode.schooltimetable.VO.PasscoSubjectVO;

public class PascoActivity extends Activity {
	RelativeLayout Homelayout, LayoutSetupTimetable,
	LayoutGradecalculateTimetable, LayoutTertiary, LayoutDefinition,
	LayoutLayoutiqtestDefinition, Layoutedutainment, Layoutpasco,
	Layoutchat1;
	public static String selectedSubject = "";

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
	ListView passcosubjectsListView;
	XMLParser parser = new XMLParser();
	String xml = "";
	NodeList nl;
	Document doc;
	String PASCO_SUBJECT_URL = "http://millionairesorg.com/schooltimetable/passcosubjectlist.php";
	ArrayList<PasscoSubjectVO> subjectlist = new ArrayList<PasscoSubjectVO>();
	private ProgressDialog mProgressDialog;
	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;

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
		setContentView(R.layout.pascoquition_main);
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels) * 0.75);
		slidingPanel = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
		slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
				.getLayoutParams();
		slidingPanelParameters.width = metrics.widthPixels;
		slidingPanel.setLayoutParams(slidingPanelParameters);
		passcosubjectsListView = (ListView) findViewById(R.id.passcosubjectsListView);
	
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
//		// Button btnsetting = (Button) findViewById(R.id.btn_setuptimetable);
//		Button btnpasco = (Button) findViewById(R.id.btn_pasco);
//		Button btniqtest = (Button) findViewById(R.id.btn_iqtest);
//		Button btnedutainment = (Button) findViewById(R.id.btn_edutainment);
//		Button btnchat = (Button) findViewById(R.id.btn_chat);
//
//		
//		home.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(PascoActivity.this,
//						HomeScreen.class);
//				startActivity(myintent2);
//
//			}
//		});
//
//		btnsetuptimetable.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(PascoActivity.this,
//						OptionActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//
//		btngradecalculate.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(PascoActivity.this,
//						GradecalculatorActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//
//		btntertiary.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(PascoActivity.this,
//						TertiaryinfoActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//
//		btndefination.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(PascoActivity.this,
//						SelectsubjectDefination.class);
//				startActivity(myintent2);
//
//			}
//		});
//
//		btnpasco.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(PascoActivity.this,
//						PascoActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//
//		btniqtest.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent2 = new Intent(PascoActivity.this,
//						IQTestActivity.class);
//				startActivity(myintent2);
//
//			}
//		});
//		btnedutainment.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent3 = new Intent(PascoActivity.this,
//						EdutainmentActivity.class);
//				startActivity(myintent3);
//
//			}
//		});
//		btnchat.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				Intent myintent3 = new Intent(PascoActivity.this,
//						ChatActivity.class);
//				startActivity(myintent3);
//
//			}
//		});

		

		new myTask_PasscosubjectList_call().execute();
		passcosubjectsListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						PasscoSubjectVO psVo = subjectlist.get(arg2);
						selectedSubject = psVo.subjectname;
						selectedSubject = selectedSubject.replace(' ','_');

						startActivity(new Intent(PascoActivity.this,
								PascoTopic.class));
					}
				});
	}

	// DownloadJSON AsyncTask for activityDetails
	class myTask_PasscosubjectList_call extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
		}

		@Override
		protected String doInBackground(String... aurl) {
			try {
				xml = parser.getXmlFromUrl(PASCO_SUBJECT_URL);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(String str_resp) {
			
			doc = parser.getDomElement(xml); // getting
			if (doc != null) {
				nl = doc.getElementsByTagName("subject");
				subjectlist.clear();
				// looping through all item nodes <item>
				for (int i = 0; i < nl.getLength(); i++) {
					Element e = (Element) nl.item(i);
					PasscoSubjectVO slVo = new PasscoSubjectVO();
					slVo.subjectid = parser.getValue(e, "subjectid");
					slVo.subjectname = parser.getValue(e, "subjectname");
					

						subjectlist.add(slVo);
					

				}
			}
			
			CustomSubjectList adapter = new CustomSubjectList(PascoActivity.this,
						subjectlist);
				passcosubjectsListView.setAdapter(adapter);
				if (mProgressDialog != null)
					mProgressDialog.dismiss();

			} 

		}
	public void onMenuOptionClicked(View view) {
		if (view.getId() == R.id.Homelayout) {
			Constant.MENU_ITEM_SELECTED = "Home";
			Intent intent = new Intent(PascoActivity.this, HomeScreen.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutSetupTimetable) {
			Constant.MENU_ITEM_SELECTED = "SETUP TIMETABLE";
			Intent intent = new Intent(PascoActivity.this, OptionActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutGradecalculateTimetable) {
			Constant.MENU_ITEM_SELECTED = "GRADE CALCULATOR";
			Intent intent = new Intent(PascoActivity.this, GradecalculatorActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutTertiary) {
			Constant.MENU_ITEM_SELECTED = "TERTIARY INFO";
			Intent intent = new Intent(PascoActivity.this, TertiaryinfoActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutDefinition) {
			Constant.MENU_ITEM_SELECTED = "DEFINITIONS";
			Intent intent = new Intent(PascoActivity.this, SelectsubjectDefination.class);
			startActivity(intent);
		}else if (view.getId() == R.id.LayoutLayoutiqtestDefinition) {
			Constant.MENU_ITEM_SELECTED = "IQ TEST";
			Intent intent = new Intent(PascoActivity.this, IQTestActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.Layoutedutainment) {
			Constant.MENU_ITEM_SELECTED = "EDUTAINMENT";
			Intent intent = new Intent(PascoActivity.this, EdutainmentActivity.class);
			startActivity(intent);
		}else if (view.getId() == R.id.Layoutpasco) {
			Constant.MENU_ITEM_SELECTED = "PASSCO";
			new CollapseAnimation(slidingPanel, panelWidth,
					TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
					TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);
		}else if (view.getId() == R.id.Layoutchat1) {
			Constant.MENU_ITEM_SELECTED = "CHAT";
			Intent intent = new Intent(PascoActivity.this, ChatActivity.class);
			startActivity(intent);
		}
	}

	}

	 class CustomSubjectList extends BaseAdapter {
		private final Activity context;
		private String[] web;
		private Integer[] imageId;
		ArrayList<PasscoSubjectVO> adapterTimetableList;
		TextView passcosubjectTextView;

		public CustomSubjectList(Activity context,
				ArrayList<PasscoSubjectVO> mainTimetableList_) {
			super();
			this.context = context;
			adapterTimetableList = mainTimetableList_;
		}

		@Override
		public View getView(final int position, final View view,
				ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.passcosubject_listview,
					null, true);
			PasscoSubjectVO psvo = adapterTimetableList.get(position);
			passcosubjectTextView = (TextView) rowView
					.findViewById(R.id.passcosubjectTextView);
			passcosubjectTextView.setText(psvo.subjectname);

			return rowView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return adapterTimetableList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		public void onBackPressed() {
			
		}
	}


