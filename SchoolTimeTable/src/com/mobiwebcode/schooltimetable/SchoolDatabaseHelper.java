package com.mobiwebcode.schooltimetable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobiwebcode.schooltimetable.VO.AllDefinitionsVO;
import com.mobiwebcode.schooltimetable.VO.DefinitionListVO;
import com.mobiwebcode.schooltimetable.VO.SchoolSingleton;
import com.mobiwebcode.schooltimetable.VO.TertiarySchoollistVO;
import com.mobiwebcode.schooltimetable.VO.TertieryinfodetailVO;
import com.mobiwebcode.schooltimetable.VO.Topic_SubjectVO;

public class SchoolDatabaseHelper extends SQLiteOpenHelper {
	private String DB_Path="data/data/com.mobiwebcode.schooltimetable/databases/";
	private static String DB_Name="SchoolTimeTable.sqlite";
	
	private SQLiteDatabase myDatabase;
	private Context mycontext;
	
	public String KEY_DEFID="def_id";
	public String KEY_DEFNAME="def_name";
	public String KEY_DEFEXPL="def_explaination";
	public String KEY_DEFIMAGE="image";
	public String KEY_TOPIC="topic";
	public String KEY_SUBJECT="subject";
	public SchoolSingleton singleton;
	public String TOPICANDSUBJECT_TABLE="alldefinitions";
	
	
	public String KEY_TERID="ter_id";
	public String KEY_TERNAME="ter_name";
	public String KEY_TERDESCRIPTION="ter_description";
	public String KEY_PICTUREPATH="picture_path";
	public String KEY_TERINFO="ter_info";
	public String KEY_TERTYPE="ter_type";
	public String KEY_PRGDIPLOMA="prog_diploma";
	public String KEY_PROGCERTIFICATE="prog_certificate";
	public String KEY_PROGUNDERGRADUATE="prog_undergraduate";
	public String TERTIARYINFOTABLE="Tertiaryinfo";
	
	
	public SchoolDatabaseHelper(Context context)  {
		// TODO Auto-generated constructor stub
		super(context,DB_Name,null,1);
		this.mycontext=context;
		
		if(checkdatabase())
		{
			opendatabase();
		}
		else
		{
			createdatabase();
		}
	}
	
		
	public boolean checkdatabase()
	{
		boolean chkdadabase=false;
		
		try {
			
			File dbfile=new File(DB_Path+DB_Name);
			
			if(dbfile.exists())
			{
				chkdadabase=true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return chkdadabase;
	}
	
	
	public void createdatabase()
	{
	
		if(checkdatabase())
		{
		}
		else
		{
			this.getReadableDatabase();
			
			try {
				
				copydatabase();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
	
	
	public void copydatabase() throws IOException
	{
		InputStream myinputfile=mycontext.getAssets().open(DB_Name, 0);
		
		OutputStream myoutputfile=new FileOutputStream(DB_Path+DB_Name);
		
		byte[] buffer=new byte[1024];
		
		int length;
		while((length=myinputfile.read(buffer))>0)
		{
			myoutputfile.write(buffer, 0, length);
		}
		
		myoutputfile.flush();
		myoutputfile.close();
		myinputfile.close();
		
	}
	
	
	public void opendatabase()
	{
		 myDatabase=SQLiteDatabase.openDatabase(DB_Path+DB_Name,null, SQLiteDatabase.OPEN_READWRITE);
		
	}
	
	public void updateSubjects(AllDefinitionsVO subVo)
	{
		
//		if(subVo.topic!=null&&!subVo.topic.equals("")||subVo.topicname!=null||subVo.topicname!=""||subVo.subject!=null||subVo.subject!="")
//		{
		myDatabase=this.getWritableDatabase();
		
		if(subVo.definitionlilstArraylist.size()>0)
		{
			for(int i=0;i<subVo.definitionlilstArraylist.size();i++)
			{
				ContentValues values=new ContentValues();
				values.put(KEY_DEFID,subVo.definitionlilstArraylist.get(i).def_id);
				values.put(KEY_DEFNAME,subVo.definitionlilstArraylist.get(i).def_name);
				values.put(KEY_DEFEXPL,subVo.definitionlilstArraylist.get(i).def_expl);
				values.put(KEY_DEFIMAGE, subVo.definitionlilstArraylist.get(i).imagename);
				values.put(KEY_TOPIC,subVo.topic);
				values.put(KEY_SUBJECT, subVo.subject.trim());
				myDatabase.insert(TOPICANDSUBJECT_TABLE, null, values);
				
			}//end of for
		}//end of if
		else
		{
			ContentValues values=new ContentValues();
			values.put(KEY_DEFID,"");
			values.put(KEY_DEFNAME,"");
			values.put(KEY_DEFEXPL,"");
			values.put(KEY_DEFIMAGE,"");
			values.put(KEY_TOPIC,subVo.topic);
			values.put(KEY_SUBJECT, subVo.subject.trim());
			myDatabase.insert(TOPICANDSUBJECT_TABLE, null, values);
		}//end of else
					
		myDatabase.close();	
		
		//}
		
	}
	
	
	
	public void updateTertiaryinfo(TertieryinfodetailVO subVo)
	{
		
//		if(subVo.topic!=null&&!subVo.topic.equals("")||subVo.topicname!=null||subVo.topicname!=""||subVo.subject!=null||subVo.subject!="")
//		{
		  myDatabase=this.getWritableDatabase();
		
				ContentValues values=new ContentValues();
				
				values.put(KEY_TERID,subVo.ter_id);
				values.put(KEY_TERNAME,subVo.ter_name);
				values.put(KEY_TERDESCRIPTION,subVo.ter_description);
				values.put(KEY_PICTUREPATH, subVo.picture_path);
				values.put(KEY_TERINFO,subVo.ter_info);
				values.put(KEY_TERTYPE, subVo.ter_type);
				values.put(KEY_PRGDIPLOMA,subVo.prog_diploma);
				values.put(KEY_PROGCERTIFICATE, subVo.prog_certificate);
				values.put(KEY_PROGUNDERGRADUATE,subVo.prog_undergraduate);
				
				myDatabase.insert(TERTIARYINFOTABLE, null, values);
				
		     myDatabase.close();	
		
		//}
		     
		     System.out.println("\nDatabase Tertiary updated");
		
	}
	
	
	public List<String > getSubjects()
	{
		List<String> sublist=new ArrayList<String>();
		try {
			
		
		myDatabase=this.getReadableDatabase();
		String qry="SELECT distinct  "+KEY_SUBJECT+" FROM  "+TOPICANDSUBJECT_TABLE;
		//String qry="SELECT distinct subject from subjecttopic";
		
		Cursor cursor=myDatabase.rawQuery(qry, null);
		if(cursor.moveToFirst())
		{
			do
			{
//				Topic_SubjectVO subVO=new Topic_SubjectVO();
//				//subVO.topicid=cursor.getString(1);
//			    //subVO.topicname =cursor.getString(2);
//				subVO.subject=cursor.getString(0);
				sublist.add(cursor.getString(0));
			}while(cursor.moveToNext());
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sublist;
	}
	
	public List<TertiarySchoollistVO > getSchoollist(String TERTIARYTYPE)
	{
		List<TertiarySchoollistVO> sublist=new ArrayList<TertiarySchoollistVO>();
		try {
			
		
		myDatabase=this.getReadableDatabase();
		String qry="SELECT  "+KEY_TERNAME+" , "+KEY_PICTUREPATH+" FROM  "+TERTIARYINFOTABLE+" where ter_type='"+TERTIARYTYPE+"'";
		//String qry="SELECT distinct subject from subjecttopic";
		
		Cursor cursor=myDatabase.rawQuery(qry, null);
		if(cursor.moveToFirst())
		{
			do
			{
				TertiarySchoollistVO subVO=new TertiarySchoollistVO();
				subVO.ter_name=cursor.getString(0);
			    subVO.ter_pacture_path =cursor.getString(1);
				
			    sublist.add(subVO);
			}while(cursor.moveToNext());
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sublist;
	}
	
	
	public List<Topic_SubjectVO > getTopics(String subject)
	{
		List<Topic_SubjectVO> sublist=new ArrayList<Topic_SubjectVO>();
		myDatabase=this.getReadableDatabase();
		String qry="SELECT topic,subject FROM  "+TOPICANDSUBJECT_TABLE+"  where "+KEY_SUBJECT+"  = '"+subject+"' ";
		Cursor cursor=myDatabase.rawQuery(qry, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Topic_SubjectVO subVO=new Topic_SubjectVO();
				subVO.topic=cursor.getString(0);
				subVO.subject=cursor.getString(1);
				sublist.add(subVO);
			}while(cursor.moveToNext());
		}
		return sublist;
	}
	
	public void getTertiaryschoolList(Activity activity)
	{
		
		try {
			singleton=SchoolSingleton.getinstance(activity);
			singleton.TertieryinfoArrayList.clear();
				
				myDatabase=this.getReadableDatabase();
				String qry="SELECT picture_path,ter_name,ter_info,prog_diploma,prog_certificate,prog_undergraduate,ter_description FROM  "+TERTIARYINFOTABLE+"  where  "+KEY_TERNAME+"  = '"+singleton.SelectedScoole+"'";
				//String qry="SELECT distinct subject from subjecttopic";
	
				Cursor cursor=myDatabase.rawQuery(qry, null);
				if(cursor.moveToFirst())
				{
					do
					{
						TertieryinfodetailVO subVO=new TertieryinfodetailVO();
						
						subVO.picture_path=cursor.getString(0);
					    subVO.ter_name=cursor.getString(1);
						subVO.ter_info=cursor.getString(2);
						subVO.prog_diploma=cursor.getString(3);
						subVO.prog_certificate=cursor.getString(4);
						subVO.prog_undergraduate=cursor.getString(5);
						subVO.ter_description=cursor.getString(6);
						
						singleton.TertieryinfoArrayList.add(subVO);
					}while(cursor.moveToNext());
				}
				
			
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//return sublist;
	}
	
	public void getDefinitionList(Activity activity)
	{
		
		try {
			singleton=SchoolSingleton.getinstance(activity);
			
			if(singleton.TopicArrayList.size()>0)
			{
			
			for(int i=0;i<singleton.TopicArrayList.size();i++)
			{
				if(singleton.TopicArrayList.get(i).ischecked)
				{
				
				String subject=singleton.TopicArrayList.get(i).subject;
				String topic=singleton.TopicArrayList.get(i).topic;
				myDatabase=this.getReadableDatabase();
				String qry="SELECT def_id,def_name,def_explaination,image FROM  "+TOPICANDSUBJECT_TABLE+"  where "+KEY_SUBJECT+"  = '"+subject+"' AND "+KEY_TOPIC+"  = '"+topic+"' ";
				//String qry="SELECT distinct subject from subjecttopic";
				
				Cursor cursor=myDatabase.rawQuery(qry, null);
				if(cursor.moveToFirst())
				{
					do
					{
						DefinitionListVO subVO=new DefinitionListVO();
						subVO.def_id=cursor.getString(0);
					    subVO.def_name=cursor.getString(1);
						subVO.def_expl=cursor.getString(2);
						subVO.imagename=cursor.getString(3);
						singleton.definitionsArrayList.add(subVO);
					}while(cursor.moveToNext());
				}
				
				}//end of if
			}//end of for
			}//end of if
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//return sublist;
	}
	
	
	public void getAlldata(Activity activity)
	{
		try {
			singleton=SchoolSingleton.getinstance(activity);
			
				myDatabase=this.getReadableDatabase();
				String qry="SELECT subject,def_id,def_name,def_explaination,image FROM  "+TOPICANDSUBJECT_TABLE;
				//String qry="SELECT distinct subject from subjecttopic";
				
				Cursor cursor=myDatabase.rawQuery(qry, null);
				if(cursor.moveToFirst())
				{
					do
					{
						DefinitionListVO subVO=new DefinitionListVO();
						subVO.subject=cursor.getString(0);
						subVO.def_id=cursor.getString(1);
					    subVO.def_name=cursor.getString(2);
						subVO.def_expl=cursor.getString(3);
						subVO.imagename=cursor.getString(4);
						singleton.AlldefinitionsArrayList.add(subVO);
					}while(cursor.moveToNext());
				}
				
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//return sublist;
	}
	
	
	
	public void DeleteAllData()
	{
		try {
			SQLiteDatabase db = this.getWritableDatabase();
	        db.execSQL("DELETE FROM  "+TOPICANDSUBJECT_TABLE); //delete all rows in a table
	       db.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public void DeleteAllTertiaryData()
	{
		try {
			SQLiteDatabase db = this.getWritableDatabase();
	        db.execSQL("DELETE FROM  "+TERTIARYINFOTABLE); //delete all rows in a table
	       db.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
