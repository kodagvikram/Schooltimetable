package com.mobiwebcode.schooltimetable.VO;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Constant {
	public static SQLiteDatabase schooltimetable;
	public static String DB_PATH = "/data/data/com.mobiwebcode.schooltimetable/databases/";
	public static final String DB_NAME = "SchoolTimeTable.sqlite";
	public static String path = DB_PATH + DB_NAME;
	public static String MyTimeTable = "generatetimetable";
	public static String Subject_Topic = "subjecttopic";
	public static String Defination = "defination";
	
	public String KEY_ID = "id";
	public String KEY_TOPICID = "topicid";
	public static String KEY_TOPICNAME = "topicname";
	public static String KEY_SUBJECT = "subjectname";
	public String KEY_DEFINATIONID = "definationid";
	public static String KEY_DEFINATIONNAME = "definationname";
	public static String KEY_DEFINATIONDETAIL = "definationdetail";
	public static String KEY_IMAGE = "definationimage";
	
	public static String MENU_ITEM_SELECTED = "";
	Activity mContext = null;
	public Constant(Activity context) {
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	public boolean validateText(EditText editText, ImageView validateImageView) {
		if (editText.getText().toString().equals("")) {
			validateImageView.setVisibility(View.VISIBLE);
			return false;
		} else {
			validateImageView.setVisibility(View.GONE);
			return true;
		}
	}

}
