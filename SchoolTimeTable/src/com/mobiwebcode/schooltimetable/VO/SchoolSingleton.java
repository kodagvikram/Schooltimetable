package com.mobiwebcode.schooltimetable.VO;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mobiwebcode.schooltimetable.SchoolDatabaseHelper;
import com.mobiwebcode.schooltimetable.SelectsubjectDefination;
import com.mobiwebcode.schooltimetable.XMLParser;

public class SchoolSingleton {

	private static SchoolSingleton schoolobj=null;
	public Activity context;
	public static ArrayList<AllDefinitionsVO> alldefArrayList=new ArrayList<AllDefinitionsVO>();
	public ArrayList<Topic_SubjectVO> topicSubjectArrayList=new ArrayList<Topic_SubjectVO>();
	
	public ArrayList<Topic_SubjectVO> TopicArrayList=new ArrayList<Topic_SubjectVO>();
	
	public ArrayList<DefinitionListVO> definitionsArrayList=new ArrayList<DefinitionListVO>();
	public ArrayList<DefinitionListVO> AlldefinitionsArrayList=new ArrayList<DefinitionListVO>();
	
	public ArrayList<TertieryinfodetailVO> AllTertieryinfodetailArrayList=new ArrayList<TertieryinfodetailVO>();
	public ArrayList<TertieryinfodetailVO> TertieryinfoArrayList=new ArrayList<TertieryinfodetailVO>();
	
	
	public String SelectedSchooltype="",SelectedScoole="";
	public  String selectedSubject = "";
	public SchoolDatabaseHelper  dbhelper;
	XMLParser parser = new XMLParser();
	String xml = "";
	NodeList nl;
	Document doc;
	public String DEFINATION_SUBJECT_URL ="http://millionairesorg.com/schooltimetable/definitiondetail.php";
	
	public SchoolSingleton(Activity context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		dbhelper=new SchoolDatabaseHelper(context);
	}
	
	public static SchoolSingleton getinstance(Activity context)
	{
		if(schoolobj==null)
		{
			schoolobj=new SchoolSingleton(context);
		}
		
		return schoolobj;
	}//end of getInstance
	
	public void CalltoUpdateDatabase()
	{
		
		if (AppUtils.isNetworkAvailable(context))
			new myTask_DefinationsubjectList_call().execute();
		else
			AppUtils.ShowAlertDialog(context, "No Internet Connection is Avelable.");
		
	}//end of call to update database
	
	// DownloadJSON AsyncTask for activityDetails
		class myTask_DefinationsubjectList_call extends
				AsyncTask<String, Void, String> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
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
					if (doc != null) {
						alldefArrayList.clear();
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
							alldefArrayList.add(subvo);
						}
					}
				           
					         updateDatabaseTable(alldefArrayList);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
			}

		}//end of Async task

	public void updateDatabaseTable(ArrayList<AllDefinitionsVO> serverArrayList)
	{
		if(serverArrayList.size()>0)
		{
			dbhelper.DeleteAllData();
			for(int i=0;i<serverArrayList.size();i++)
			{
				dbhelper.updateSubjects(serverArrayList.get(i));
			}
			//DisplaySubjects();
		
		}//end of if
		else
		{
			Toast.makeText(context.getApplicationContext(), "NO UPDATES ARE AVELABLE..", Toast.LENGTH_LONG).show();
		}//end of else
			
	}//end of function updataDataBase
	
	public void updateTertiaryDatabaseTable(ArrayList<TertieryinfodetailVO> serverArrayList)
	{
		if(serverArrayList.size()>0)
		{
			dbhelper.DeleteAllTertiaryData();
			for(int i=0;i<serverArrayList.size();i++)
			{
				dbhelper.updateTertiaryinfo(serverArrayList.get(i));
			}
			//DisplaySubjects();
		
		}//end of if
		else
		{
			Toast.makeText(context.getApplicationContext(), "NO UPDATES ARE AVELABLE..", Toast.LENGTH_LONG).show();
		}//end of else
			
	}//end of function updataDataBase

}//end of class
