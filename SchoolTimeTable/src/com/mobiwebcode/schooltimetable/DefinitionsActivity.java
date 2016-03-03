package com.mobiwebcode.schooltimetable;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListView;
import android.widget.Toast;

import com.mobiwebcode.schooltimetable.VO.AppUtils;
import com.mobiwebcode.schooltimetable.VO.Constant;
import com.mobiwebcode.schooltimetable.VO.DefinationVo;
import com.mobiwebcode.schooltimetable.VO.DefinitionListVO;
import com.mobiwebcode.schooltimetable.VO.SchoolSingleton;

public class DefinitionsActivity extends Activity {
	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
	String responseString = null;
	SchoolSingleton schoolSingleton;
	XMLParser parser = new XMLParser();
	String xml = "";
	NodeList nl;
	Document doc;
	String DEFINATION_TOPIC_URL = "http://millionairesorg.com/schooltimetable/definitiondetail.php?topic="
			+ DefinationTopic.selectedtopic;
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	ArrayList<DefinationVo> definationList = new ArrayList<DefinationVo>();
	ListView homeworkListview;
	ArrayList<String> homeworkArrayList = new ArrayList<String>();
	boolean isFirstViewClick = false;
	boolean isSecondViewClick = false;
	private ProgressDialog mProgressDialog;
	
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
		setContentView(R.layout.definitions_main);
		
		schoolSingleton=SchoolSingleton.getinstance(DefinitionsActivity.this);

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

		DisplayDefinitions(); //call to method
	}//end of oncreate()
	
	public void DisplayDefinitions()
	{
		//ArrayList<DefinitionListVO> defArrayList=(ArrayList<DefinitionListVO>) schoolSingleton.dbhelper.getDefinitionList(schoolSingleton.topicSubjectArrayList);
		
		if(schoolSingleton.definitionsArrayList.size()>0)
		{
			final ExpandableListAdapter listAdapter = new ExpandableListAdapter(
					DefinitionsActivity.this, schoolSingleton.definitionsArrayList);
			expListView.setAdapter(listAdapter);

		}
		else
		{
			AppUtils.ShowAlertDialog(DefinitionsActivity.this, "No Definition Provided for Selected Topic.");
		}
		
	}
	
	
	

	public void onBackPressed() {
//		Intent i = new Intent(this, DefinationTopic.class);
//		i.putExtra("exit", true);
//		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivity(i);
		super.onBackPressed();
	}
}
