package com.mobiwebcode.schooltimetable;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
public class MeritListActivity extends Activity {
  ListView list;
  String[] web = {
    "student name 1          1",
      "student name 2          2",
      "student name 3           3",
      "student name 4            4"
      
  } ;
  Integer[] imageId = {
      R.drawable.photo,
      R.drawable.photo,
      R.drawable.photo,
      R.drawable.photo
        };
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.meritlist);
    CustomList adapter = new
        CustomList(MeritListActivity.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        //list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 }
}
