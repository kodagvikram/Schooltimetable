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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiwebcode.schooltimetable.VO.AllDefinitionsVO;
import com.mobiwebcode.schooltimetable.VO.AppUtils;
import com.mobiwebcode.schooltimetable.VO.Constant;
import com.mobiwebcode.schooltimetable.VO.DefinationSubjectVo;
import com.mobiwebcode.schooltimetable.VO.DefinitionListVO;
import com.mobiwebcode.schooltimetable.VO.SchoolSingleton;

public class SelectsubjectDefination extends Activity {

	RelativeLayout Homelayout, LayoutSetupTimetable,
			LayoutGradecalculateTimetable, LayoutTertiary, LayoutDefinition,
			LayoutLayoutiqtestDefinition, Layoutedutainment, Layoutpasco,
			Layoutchat1;
	public static String selectedSubject = "";
	public SchoolSingleton singleton;
	FrameLayout mainFrameLyout;
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters;
	LinearLayout.LayoutParams listViewParameters;
	Button menuBtn;
	private DisplayMetrics metrics;
	private RelativeLayout slidingPanel;
	public EditText SearchEdittext;
	int panelWidth = 0;
	private boolean isExpanded;
	ListView listView;
	public static DefinationSubjectVo dsVo = new DefinationSubjectVo();
	public ArrayList<DefinationSubjectVo> subjectlist = new ArrayList<DefinationSubjectVo>();
	public ArrayList<String> subjectArraylist=new ArrayList<String>();
	
//	public ArrayList<AllDefinitionsVO> alldefArrayList=new ArrayList<AllDefinitionsVO>();
//	public ArrayList<Topic_SubjectVO> topicSubjectArrayList=new ArrayList<Topic_SubjectVO>();
	
	XMLParser parser = new XMLParser();
	String xml = "";
	NodeList nl;
	Document doc;
	String DEFINATION_SUBJECT_URL = "http://millionairesorg.com/schooltimetable/definitiondetail.php";
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectsubjectdefination);

		try{
		
		singleton=SchoolSingleton.getinstance(SelectsubjectDefination.this);
		
		//singleton.alldefArrayList.clear();
		
//		if(singleton.alldefArrayList.size()<1)
//			singleton.CalltoUpdateDatabase();
		
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
		
		SearchEdittext=(EditText)findViewById(R.id.inputSearch);
		
		SearchEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
		          
		        	try {
		        		
						
					singleton.definitionsArrayList.clear();
					
		        	if(!SearchEdittext.getText().toString().equalsIgnoreCase(""))
		        	{
		        	for(int i=0;i<singleton.AlldefinitionsArrayList.size();i++)
		        	{
		        		DefinitionListVO svo=singleton.AlldefinitionsArrayList.get(i);
		        		
		        		if(svo.def_name.toLowerCase().contains(SearchEdittext.getText().toString().toLowerCase().trim())||svo.def_expl.toLowerCase().contains(SearchEdittext.getText().toString().toLowerCase().trim()))
		        		{
		        			singleton.definitionsArrayList.add(singleton.AlldefinitionsArrayList.get(i));
		        		}
		        		
		        	}//end of for
		        	
		        	Intent myintent2 = new Intent(SelectsubjectDefination.this,
							DefinitionsActivity.class);
					startActivity(myintent2);
		        	
		        	}//end of if 
		        	} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}	
		            return true;
		        }
		        return false;
		    }
		});
		

		listView = (ListView) findViewById(R.id.listView);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				try {
				singleton.selectedSubject=subjectArraylist.get(arg2);
				startActivity(new Intent(SelectsubjectDefination.this,
						DefinationTopic.class));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		Button update = (Button) findViewById(R.id.asyncfromserver);
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (AppUtils.isNetworkAvailable(SelectsubjectDefination.this))
					new myTask_DefinationsubjectList_call().execute();
				else
					AppUtils.ShowAlertDialog(SelectsubjectDefination.this, "No Internet Connection is Avelable.");

			}
		});
		
		DisplaySubjects();
		
		singleton.AlldefinitionsArrayList.clear();
		singleton.dbhelper.getAlldata(this);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}//end of oncreate

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
				singleton.alldefArrayList.clear();
				if (doc != null) {
					nl = doc.getElementsByTagName("definationdetail");
					// looping through all item nodes <item>
					for (int i = 0; i < nl.getLength(); i++) {
						Element e = (Element) nl.item(i);
						
						AllDefinitionsVO subvo=new AllDefinitionsVO();
						subvo.subject = parser.getValue(e, "subject");
						subvo.topic=parser.getValue(e, "topic");
						
						subvo.definitionlilstArraylist.clear();
						NodeList defnlist=e.getChildNodes().item(2).getChildNodes();  //********************
						for (int j = 0; j < defnlist.getLength(); j++) {
							Element e2 = (Element) defnlist.item(j);
							DefinitionListVO listVO=new DefinitionListVO();
							listVO.def_id = parser.getValue(e2, "definationid");
							listVO.def_name = parser.getValue(e2, "definationname");
							listVO.def_expl = parser.getValue(e2, "explaination");
							listVO.imagename = parser.getValue(e2, "image");
							
							subvo.definitionlilstArraylist.add(listVO);
						}//end of for
						
						subvo.definitionlilstArraylist.size();
						singleton.alldefArrayList.add(subvo);
					}
				}
				       // singleton.updateDatabaseTable(singleton.alldefArrayList);
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
					 singleton.updateDatabaseTable(singleton.alldefArrayList);
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				return "";
			}

			@Override
			protected void onPostExecute(String str_resp) {
				try {
					
					 DisplaySubjects();
				if (mProgressDialog != null)
					mProgressDialog.dismiss();
				singleton.AlldefinitionsArrayList.clear();
				singleton.dbhelper.getAlldata(SelectsubjectDefination.this);
				
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
			Intent intent = new Intent(SelectsubjectDefination.this,
					HomeScreen.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutSetupTimetable) {
			Constant.MENU_ITEM_SELECTED = "SETUP TIMETABLE";
			Intent intent = new Intent(SelectsubjectDefination.this,
					OptionActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutGradecalculateTimetable) {
			Constant.MENU_ITEM_SELECTED = "GRADE CALCULATOR";
			Intent intent = new Intent(SelectsubjectDefination.this,
					GradecalculatorActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutTertiary) {
			Constant.MENU_ITEM_SELECTED = "TERTIARY INFO";
			Intent intent = new Intent(SelectsubjectDefination.this,
					TertiaryinfoActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.LayoutDefinition) {
			Constant.MENU_ITEM_SELECTED = "DEFINITIONS";
			isExpanded=false;
			new CollapseAnimation(slidingPanel, panelWidth,
					TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
					TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);
		} else if (view.getId() == R.id.LayoutLayoutiqtestDefinition) {
			Constant.MENU_ITEM_SELECTED = "IQ TEST";
			Intent intent = new Intent(SelectsubjectDefination.this,
					IQTestActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.Layoutedutainment) {
			Constant.MENU_ITEM_SELECTED = "EDUTAINMENT";
			Intent intent = new Intent(SelectsubjectDefination.this,
					EdutainmentActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.Layoutpasco) {
			Constant.MENU_ITEM_SELECTED = "PASSCO";
			Intent intent = new Intent(SelectsubjectDefination.this,
					PascoActivity.class);
			startActivity(intent);
		} else if (view.getId() == R.id.Layoutchat1) {
			Constant.MENU_ITEM_SELECTED = "CHAT";
			Intent intent = new Intent(SelectsubjectDefination.this,
					ChatActivity.class);
			startActivity(intent);
		}
	}

	public void DisplaySubjects() {
		
		try{
		subjectArraylist = (ArrayList<String>)singleton.dbhelper.getSubjects();

		if (subjectArraylist.size() > 0) {
			CustomArrayList adapter = new CustomArrayList(SelectsubjectDefination.this, subjectArraylist);
			listView.setAdapter(adapter);
		 }// end of if
		else {
			Toast.makeText(
					getApplicationContext(),
					"No data is present in database Click on SYNC for Latest updates",
					Toast.LENGTH_LONG).show();
		}// end of else

		if (mProgressDialog != null)
			mProgressDialog.dismiss();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}// end of function display subjects

}//end of Activity

class CustomArrayList extends BaseAdapter {
	private final Activity context;
	ArrayList<String> adapterTimetableList;
	TextView definationsubjectTextView;

	public CustomArrayList(Activity context,
			ArrayList<String> mainTimetableList_) {
		super();
		this.context = context;
		adapterTimetableList = mainTimetableList_;
	}

	@Override
	public View getView(final int position, final View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(
				R.layout.selectsubjectdefination_iteminfo, null, true);
		//aDefintionSubjectVo dsVo = adapterTimetableList.get(position);
		definationsubjectTextView = (TextView) rowView
				.findViewById(R.id.subject);
		definationsubjectTextView.setText(adapterTimetableList.get(position));

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
