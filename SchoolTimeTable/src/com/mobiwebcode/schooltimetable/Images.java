package com.mobiwebcode.schooltimetable;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
public class Images extends Activity {
  GridView grid;
  String[] web = {
        "school1",
      "school1",
      "school1",
      "school1",
      "school1",
      "school1",
      "school1",
      "school1",
      "school1",
      "school1",
      "school1",
      "school1",
      "school1",
      "school1",
      "school1"
  } ;
  int[] imageId = {
      R.drawable.image1,
      R.drawable.image1,
      R.drawable.image1,
      R.drawable.image1,
      R.drawable.image1,
      R.drawable.image1,
      R.drawable.image1,
      R.drawable.image1,
      R.drawable.image1,
      R.drawable.image1,
      R.drawable.image1,
      R.drawable.image1,
      R.drawable.image1,
      R.drawable.image1,
      
  };
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.images);
    
    Button buttonClick = (Button) findViewById(R.id.buttonClick);
	 
    // add listener to button 
    buttonClick.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View arg0) {

            // Create custom dialog object
            AlertDialog.Builder adb = new AlertDialog.Builder(Images.this);



            adb.setTitle("Choose Option");


         //   adb.setIcon(android.R.drawable.ic_dialog_alert);


            adb.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {




              } });


            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    finish();
              } });
            
            adb.setNeutralButton("Gallery", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    finish();
              } });
            adb.show();             
        }

    });

    
    Customgridimages adapter = new Customgridimages(Images.this, web, imageId);
    grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                	Intent myintent2 = new Intent(Images.this,
    						SchoolDescription.class);
    				startActivity(myintent2);

                }
            });
  }
}