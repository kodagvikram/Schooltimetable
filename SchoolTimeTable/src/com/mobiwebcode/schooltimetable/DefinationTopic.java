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
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobiwebcode.schooltimetable.VO.AppUtils;
import com.mobiwebcode.schooltimetable.VO.DefinationTopicVo;
import com.mobiwebcode.schooltimetable.VO.DefinitionListVO;
import com.mobiwebcode.schooltimetable.VO.SchoolSingleton;
import com.mobiwebcode.schooltimetable.VO.Topic_SubjectVO;

public class DefinationTopic extends Activity implements OnItemClickListener {
	public static String selectedtopic = "";
	ListView listView;
	EditText inputSearch;
	public static String searchTopic = "";
	ArrayList<Topic_SubjectVO> topicArrayList;
	public SchoolSingleton schoolSingleton;
	public EditText SearchEdittext;
	CustomTopicList adapter;
	ArrayList<DefinationTopicVo> topiclist = new ArrayList<DefinationTopicVo>();
	ArrayList<DefinationTopicVo> searchtopiclist = new ArrayList<DefinationTopicVo>();
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

	void filtertopic(String searchTopic) {
		searchtopiclist = new ArrayList<DefinationTopicVo>();
		for (int count = 0; count < topiclist.size(); count++) {
			DefinationTopicVo dtvo = topiclist.get(count);
			if (dtvo.topicname.toLowerCase()
					.contains(searchTopic.toLowerCase())) {
				searchtopiclist.add(dtvo);
			}
		}
		//adapter = new CustomTopicList(DefinationTopic.this, searchtopiclist);
		//listView.setAdapter(adapter);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.definationtopic);

		schoolSingleton = SchoolSingleton.getinstance(DefinationTopic.this);

		listView = (ListView) findViewById(R.id.listView);
		listView.setItemsCanFocus(false);
		listView.setTextFilterEnabled(true);
		//inputSearch = (EditText) findViewById(R.id.inputSearch);

         SearchEdittext=(EditText)findViewById(R.id.inputSearch);
		
		SearchEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
		          
		        	try {
		        		schoolSingleton.definitionsArrayList.clear();
					
		        	if(!SearchEdittext.getText().toString().equalsIgnoreCase(""))
		        	{
		        	for(int i=0;i<schoolSingleton.AlldefinitionsArrayList.size();i++)
		        	{
		        		DefinitionListVO svo=schoolSingleton.AlldefinitionsArrayList.get(i);
		          		if(schoolSingleton.selectedSubject.equals(svo.subject))
		        		if((svo.def_name.toLowerCase().contains(SearchEdittext.getText().toString().toLowerCase().trim())||svo.def_expl.toLowerCase().contains(SearchEdittext.getText().toString().toLowerCase().trim())))
		        		{
		        			schoolSingleton.definitionsArrayList.add(schoolSingleton.AlldefinitionsArrayList.get(i));
		        		}
		        		
		        	}//end of for
		        	
		        	Intent myintent2 = new Intent(DefinationTopic.this,
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
		
		
		
		
		Button btnselecttopic = (Button) findViewById(R.id.btn_Select);
		btnselecttopic.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				schoolSingleton.definitionsArrayList.clear();
				
				if(schoolSingleton.TopicArrayList.size()>0)
				{
					schoolSingleton.dbhelper.getDefinitionList(DefinationTopic.this);
				}
				
				schoolSingleton.definitionsArrayList.size();
				
				Intent myintent2 = new Intent(DefinationTopic.this,
						DefinitionsActivity.class);
				startActivity(myintent2);
			}
		});
		
		DisplayTopics();

	}// end of oncreate()


	public void DisplayTopics() {
		try {
		 topicArrayList = (ArrayList<Topic_SubjectVO>) schoolSingleton.dbhelper
				.getTopics(schoolSingleton.selectedSubject);
		 
		 schoolSingleton.TopicArrayList.clear();
		 
		if(topicArrayList.size()>0)
		{
			adapter = new CustomTopicList(DefinationTopic.this, topicArrayList);
			listView.setAdapter(adapter);
		}//end of if
		else
			AppUtils.ShowAlertDialog(DefinationTopic.this, "NO Topic Present For Selected Subject.");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}// end of display topic

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		adapter.toggle(arg2);
	}

	class CustomTopicList extends BaseAdapter implements
			CompoundButton.OnCheckedChangeListener {
		private final Activity context;
		private String[] web;
		private Integer[] imageId;
		//public ArrayList<Topic_SubjectVO> TopicArrayList;
		TextView definationtopicTextView;
		//CheckBox checkbox;
		public SparseBooleanArray mCheckStates;

		public CustomTopicList(Activity context,
				ArrayList<Topic_SubjectVO> mainTimetableList_) {
			mCheckStates = new SparseBooleanArray(mainTimetableList_.size());
			this.context = context;
			schoolSingleton.TopicArrayList = mainTimetableList_;
		}

		@Override
		public View getView(final int position, final View view,
				ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.definationtopic_iteminfo,
					null, true);
			// DefinationTopicVo dtVo = adapterTimetableList.get(position);
			definationtopicTextView = (TextView) rowView
					.findViewById(R.id.Subject);
			definationtopicTextView
					.setText(schoolSingleton.TopicArrayList.get(position).topic);
			
			final CheckBox checkbox = (CheckBox) rowView.findViewById(R.id.checkBox1);
			checkbox.setTag(position);
			checkbox.setChecked(mCheckStates.get(position, false));
		
			checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if(isChecked)
					{
						schoolSingleton.TopicArrayList.get(position).ischecked=true;
					}
					else
					{
						schoolSingleton.TopicArrayList.get(position).ischecked=false;
					}//end of else
				}
			});
			
			RelativeLayout layout=(RelativeLayout)rowView.findViewById(R.id.TOPICLAYOUT);
			
			layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(checkbox.isChecked())
					checkbox.setChecked(false);
					else
						checkbox.setChecked(true);
				}
			});
			
			
			return rowView;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return schoolSingleton.TopicArrayList.size();
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

		public boolean isChecked(int position) {
			return mCheckStates.get(position, false);
		}

		public void setChecked(int position, boolean isChecked) {
			mCheckStates.put(position, isChecked);
		}

		public void toggle(int position) {
			setChecked(position, !isChecked(position));
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			mCheckStates.put((Integer) buttonView.getTag(), isChecked);

		}

		public void setmCheckStates(SparseBooleanArray mCheckStates) {
			// TODO Auto-generated method stub
			this.mCheckStates = mCheckStates;

		}

	}

}
