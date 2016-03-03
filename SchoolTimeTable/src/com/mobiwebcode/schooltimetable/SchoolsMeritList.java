package com.mobiwebcode.schooltimetable;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
public class SchoolsMeritList extends Activity {
  ListView list;
  String[] web = {
    "School name 1          1",
      "School name 2          2",
      "School name 3           3",
      "School name 4            4"
      
  } ;
  Integer[] imageId = {
      R.drawable.image1,
      R.drawable.image1,
      R.drawable.image1,
      R.drawable.image1
        };
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.schoolmeritlist);
    
   
    CustomList adapter = new
            CustomList(SchoolsMeritList.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                       
                    	Intent myintent2 = new Intent(SchoolsMeritList.this,
            					MeritListActivity.class);
            			startActivity(myintent2);	
                    }
                });
  }}