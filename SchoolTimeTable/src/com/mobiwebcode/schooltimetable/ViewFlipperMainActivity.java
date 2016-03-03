package com.mobiwebcode.schooltimetable;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;
import app.MWC.AsyncImageload.ImageLoader;
import app.tabsample.SmartImageView.NormalSmartImageView;

import com.mobiwebcode.schooltimetable.VO.DefinationVo;
import com.mobiwebcode.schooltimetable.VO.SchoolSingleton;
import com.mobiwebcode.schooltimetable.VO.SchoolinfoVo;
import com.mobiwebcode.schooltimetable.VO.TertieryinfodetailVO;

public class ViewFlipperMainActivity extends Activity {
	private ViewFlipper viewFlipper;
	private float lastX;
	ExpandableListView expListView;
	ExpandableInfoListAdapter listAdapter;
	Button Next, Previous;
	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
	private ProgressDialog mProgressDialog;
	ImageLoader imageLoader;
	public SchoolSingleton singleton;
	ProgressBar myProgressBar;
	XMLParser parser = new XMLParser();
	String xml = "";
	NodeList nl;
	Document doc;
	String SCHOOL_INFO_URL = "http://millionairesorg.com/schooltimetable/tertieryinfotabdetails.php?schoolname="
			+ SchoollistActivity.schoolVO.schoolname
			+ "&schooltype="
			+ SchoollistActivity.schooltype;
	ArrayList<SchoolinfoVo> infolist = new ArrayList<SchoolinfoVo>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_flipper_main);
		try {

		singleton=SchoolSingleton.getinstance(this);
		imageLoader = new ImageLoader(getApplicationContext());
		
		ImageView image1 = (ImageView) findViewById(R.id.img);
		TextView university = (TextView) findViewById(R.id.txtname);
		TextView description = (TextView) findViewById(R.id.txtdescription);
		myProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		singleton.dbhelper.getTertiaryschoolList(this);
		
		if(singleton.TertieryinfoArrayList.size()>0)
		{
			TertieryinfodetailVO terVo=singleton.TertieryinfoArrayList.get(0);
			myProgressBar.setVisibility(View.VISIBLE);
			imageLoader.DisplayImage(terVo.picture_path, image1, myProgressBar);
			university.setText(terVo.ter_name);
			description.setText(terVo.ter_description);
			
			infolist.clear();
			
				SchoolinfoVo sVo = new SchoolinfoVo();
				sVo.infoheading ="Diploma";
				sVo.infodescription = terVo.prog_diploma;
				infolist.add(sVo);
				
				SchoolinfoVo sVo2 = new SchoolinfoVo();
				sVo2.infoheading ="Certificate";
				sVo2.infodescription = terVo.prog_certificate;
				infolist.add(sVo2);
				
				SchoolinfoVo sVo3 = new SchoolinfoVo();
				sVo3.infoheading ="Undergraduate";
				sVo3.infodescription = terVo.prog_undergraduate;
				infolist.add(sVo3);

		    viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
			Next = (Button) findViewById(R.id.next);
			Previous = (Button) findViewById(R.id.previous);
			TextView schooldescriptionTextView = (TextView) findViewById(R.id.discription);
			schooldescriptionTextView
					.setText(terVo.ter_info);
			Next.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					viewFlipper.setDisplayedChild(1);
				}
			});
			Previous.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					viewFlipper.setDisplayedChild(0);

				}
			});
			
			expListView = (ExpandableListView) findViewById(R.id.expandListView);

			expListView.setAdapter(listAdapter);

			// Listview Group click listener
			expListView.setOnGroupClickListener(new OnGroupClickListener() {

				@Override
				public boolean onGroupClick(ExpandableListView parent, View v,
						int groupPosition, long id) {
					return false;
				}
			});

			// Listview Group expanded listener
			expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

				@Override
				public void onGroupExpand(int groupPosition) {
				}
			});

			// Listview Group collasped listener
			expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

				@Override
				public void onGroupCollapse(int groupPosition) {
				}
			});

			// Listview on child click listener
			expListView.setOnChildClickListener(new OnChildClickListener() {

				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
					// TODO Auto-generated method stub
					return false;
				}

			});
			
			final ExpandableInfoListAdapter listAdapter = new ExpandableInfoListAdapter(
					ViewFlipperMainActivity.this, infolist);
			    expListView.setAdapter(listAdapter);

			
		}//end of if
		else
			AppUtils.MyShowAlertDialog("No Data Present For This Type.");
		
		
		//new myTask_UniversityActivity_call().execute();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
