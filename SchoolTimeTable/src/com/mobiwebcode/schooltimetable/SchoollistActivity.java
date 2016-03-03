package com.mobiwebcode.schooltimetable;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import app.MWC.AsyncImageload.ImageLoader;
import app.tabsample.SmartImageView.NormalSmartImageView;

import com.mobiwebcode.schooltimetable.VO.SchoolSingleton;
import com.mobiwebcode.schooltimetable.VO.TertiarySchoollistVO;

public class SchoollistActivity extends Activity {

	private LinearLayout mainLinearLayout;
	ImageLoader imageLoader;
	String xml = "";
	NodeList nl;
	Document doc;
	public SchoolSingleton singleton;
	XMLParser parser = new XMLParser();
	String SCHOOL_LIST_URL = "http://millionairesorg.com/schooltimetable/tertieryinfolist.php?schooltype="
			+ schooltype;
	EditText inputSearch;
	public static String searchResult = "";
	public static ArrayList<SchoollistActivityVo> schoollist = new ArrayList<SchoollistActivityVo>();
	NormalSmartImageView imageView, imageView1;
	public static String schooltype = "";
	private ProgressDialog mProgressDialog;
	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
	String responseString = null, other = "other", selectedcountry = "";
	TextView schooltypeTextView;
	public static SchoollistActivityVo slVo = new SchoollistActivityVo();
	public static SchoollistActivityVo schoolVO = new SchoollistActivityVo();
	
	private DisplayMetrics metrics;
	int panelWidth = 0;
	
public ArrayList<TertiarySchoollistVO> schoollistArrayList=new ArrayList<TertiarySchoollistVO>();

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
		setContentView(R.layout.schoollist_main);
		
		try {
		
		inputSearch = (EditText) findViewById(R.id.inputSearch);

		singleton=SchoolSingleton.getinstance(this);
		imageLoader = new ImageLoader(getApplicationContext());
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels) * 0.34);
		
		// add LInearLayout
		schooltypeTextView = (TextView) findViewById(R.id.schooltypeTextView);
		schooltypeTextView.setText(singleton.SelectedSchooltype);
				
		DisplaySubjects();
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}//end of Oncreate()
	
	
public void DisplaySubjects() {
		
		try{
			schoollistArrayList = (ArrayList<TertiarySchoollistVO>)singleton.dbhelper.getSchoollist(singleton.SelectedSchooltype);

		if (schoollistArrayList.size() > 0) {
			setListValues(schoollistArrayList);
			//fillUI();
		 }// end of if
		else {
			AppUtils.MyShowAlertDialog("No data is present in database Click on SYNC for Latest updates");
		}// end of else

		if (mProgressDialog != null)
			mProgressDialog.dismiss();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}// end of function display subjects


	void fillUI() {
		int counter = 0;
		mainLinearLayout = (LinearLayout) findViewById(R.id.mainlinearlayout);

		for (int count = 0; count < schoollistArrayList.size(); count++) {
			
			TertiarySchoollistVO schoollistvo=schoollistArrayList.get(count);
					
			LinearLayout outerLinearLayout = new LinearLayout(
					SchoollistActivity.this);
			outerLinearLayout.setWeightSum(100f);
			outerLinearLayout
					.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
							android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
							200));
			outerLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
			LinearLayout innerLinearLayout1 = new LinearLayout(
					SchoollistActivity.this);
			LinearLayout innerLinearLayout2 = new LinearLayout(
					SchoollistActivity.this);
			innerLinearLayout1.setOrientation(LinearLayout.VERTICAL);
			innerLinearLayout2.setOrientation(LinearLayout.VERTICAL);
			android.widget.LinearLayout.LayoutParams innerLayoutParams = new android.widget.LinearLayout.LayoutParams(
					0, android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
			innerLayoutParams.setMargins(5, 30, 0, 0);
			innerLayoutParams.weight = 50f;
			innerLinearLayout1.setLayoutParams(innerLayoutParams);
			innerLinearLayout2.setLayoutParams(innerLayoutParams);

			NormalSmartImageView image1 = new NormalSmartImageView(
					SchoollistActivity.this);
			image1.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, 120));
			final int counter1 = count;
			image1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					schoolVO = schoollist.get(counter1);
					startActivity(new Intent(SchoollistActivity.this,
							ViewFlipperMainActivity.class));
				}
			});

			TextView school1 = new TextView(SchoollistActivity.this);
			school1.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, 60));
			school1.setGravity(Gravity.CENTER);
			innerLinearLayout1.addView(image1);
			innerLinearLayout1.addView(school1);

			NormalSmartImageView image2 = new NormalSmartImageView(
					SchoollistActivity.this);
			image2.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, 120));
			TextView school2 = new TextView(SchoollistActivity.this);
			school2.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, 60));
			school2.setGravity(Gravity.CENTER);
			innerLinearLayout2.addView(image2);
			innerLinearLayout2.addView(school2);

			outerLinearLayout.addView(innerLinearLayout1);
			outerLinearLayout.addView(innerLinearLayout2);

			 //slVo = schoollist.get(count);
			image1.setImageUrl(schoollistvo.ter_pacture_path);
			//slVo.schoolname = slVo.schoolname.replace(" ", "_");
			school1.setText(schoollistvo.ter_name);

			count++;
			final int counter2 = count;
			image2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					schoolVO = schoollist.get(counter2);
					startActivity(new Intent(SchoollistActivity.this,
							ViewFlipperMainActivity.class));
				}
			});

			if (count < schoollistArrayList.size()) {
				 schoollistvo=schoollistArrayList.get(count);
				image2.setImageUrl(schoollistvo.ter_pacture_path);
				//slVo.schoolname = slVo.schoolname.replace(" ", "_");
				school2.setText((schoollistvo.ter_name));
			}

			mainLinearLayout.addView(outerLinearLayout);
		}
	}//end of UI SET
	
	// **********************************************************
		void setListValues(ArrayList<TertiarySchoollistVO> fotoArrayList) {
			try {

				RelativeLayout mainlayout = (RelativeLayout) findViewById(R.id.mainlinearlayout);
				mainlayout.removeAllViews();
				ProgressBar progressBar1 ;
				ProgressBar progressBar2 ;
				ProgressBar progressBar3 ;
				for (int i = 0; i < fotoArrayList.size(); i++) {

					final TertiarySchoollistVO fotovo = fotoArrayList.get(i);

					Boolean is2img = false, is3img = false;

					// ******************************************
					RelativeLayout layout = new RelativeLayout(this);
					layout.setId(100 + i);
					RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					layoutParams.topMargin = 2;
					if (i != 0)
						layoutParams.addRule(RelativeLayout.BELOW, 98 + i);
					layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
					layout.setLayoutParams(layoutParams);

					RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(panelWidth, panelWidth - 50);
					RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(13, 20);
					RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(panelWidth, panelWidth - 50);
					RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(panelWidth, panelWidth - 50);
					RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					
					RelativeLayout.LayoutParams params7 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					params7.addRule(RelativeLayout.CENTER_IN_PARENT);
					
					RelativeLayout.LayoutParams params8 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					params8.addRule(RelativeLayout.CENTER_HORIZONTAL);
					
					RelativeLayout.LayoutParams params9 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					params9.addRule(RelativeLayout.CENTER_HORIZONTAL);
					
					RelativeLayout.LayoutParams params10 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					params10.addRule(RelativeLayout.CENTER_HORIZONTAL);
					
					 progressBar1 = new ProgressBar(this, null, android.R.attr.progressBarStyle);
					 progressBar2 = new ProgressBar(this, null, android.R.attr.progressBarStyle);
					 progressBar3 = new ProgressBar(this, null, android.R.attr.progressBarStyle);
					 
					 progressBar1.setVisibility(View.VISIBLE);
					 progressBar2.setVisibility(View.VISIBLE);
					 progressBar3.setVisibility(View.VISIBLE);
					 
					
					RelativeLayout layout2 = new RelativeLayout(this);
					layout2.setId(2000 + i);
					RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					layoutParams2.setMargins(10, 10, 10, 10);
					//layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);
					//layout2.setLayoutParams(layoutParams2);
					
					final int  k=i;
					ImageView imageView = new ImageView(this);
					imageView.setId(1);
					imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
					//imageView.setBackgroundColor(Color.parseColor("#4a688f"));
					imageLoader.DisplayImage(fotovo.ter_pacture_path, imageView, progressBar1);
					
					final TextView textView1=new TextView(this);
					textView1.setId(6000+i);
					//textView1.setTextSize();
					textView1.setTextColor(Color.parseColor("#FFFFFF"));
					textView1.setText(fotovo.ter_name);
					params8.addRule(RelativeLayout.BELOW,imageView.getId());
					
					layout2.addView(imageView,params1);
					layout2.addView(progressBar1,params7);
					layout2.addView(textView1,params8);
					
					
					
					RelativeLayout layout3 = new RelativeLayout(this);
					layout3.setId(3000 + i);
					RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					layoutParams3.addRule(RelativeLayout.RIGHT_OF, layout2.getId());
					layoutParams3.setMargins(10, 10, 10, 10);
					
					ImageView imageView2 = new ImageView(this);
					imageView2.setId(2);
					imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
					params2.leftMargin = 2;
//					//imageView2.setBackgroundColor(Color.parseColor("#4a688f"));
//					 imageLoader.DisplayImage("",
//					 imageView, myProgressBar);
					final TextView textView2=new TextView(this);
					textView2.setId(7000+i);
					//textView1.setTextSize();
					textView2.setTextColor(Color.parseColor("#FFFFFF"));
					params9.addRule(RelativeLayout.BELOW,imageView2.getId());
					//textView2.setText(fotovo.ter_name);
					
					layout3.addView(imageView2,params2);
					layout3.addView(progressBar2,params7);
					layout3.addView(textView2,params9);
					
					
					i++;
					final int  m=i;
					TertiarySchoollistVO fotovo2;
					if (i < fotoArrayList.size()) {
						is2img = true;
						fotovo2 = fotoArrayList.get(m);
						imageLoader.DisplayImage(fotovo2.ter_pacture_path, imageView2, progressBar2);
						textView2.setText(fotovo2.ter_name);
					}
					


					layout.addView(layout2, layoutParams2);
					if (is2img)
						layout.addView(layout3, layoutParams3);

					// *******************************************
					mainlayout.addView(layout, layoutParams);

					imageView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							singleton.SelectedScoole=schoollistArrayList.get(k).ter_name;
							startActivity(new Intent(SchoollistActivity.this,
									ViewFlipperMainActivity.class));

						}
					});

					imageView2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							singleton.SelectedScoole=schoollistArrayList.get(m).ter_name;
							startActivity(new Intent(SchoollistActivity.this,
									ViewFlipperMainActivity.class));
						}
					});


				}// end of for
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}// end of list values
			// **********************************************************

	
}//end of Activity
