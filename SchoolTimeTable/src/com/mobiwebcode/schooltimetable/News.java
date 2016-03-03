package com.mobiwebcode.schooltimetable;

import android.app.Activity;
import android.app.Dialog;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class News extends Activity {
	 GridView grid;
	  String[] web = {
	        "This is Sample news",
	      "This is Sample news",
	      "This is Sample news",
	      "This is Sample news",
	      "This is Sample news",
	      "This is Sample news",
	      "This is Sample news",
	      "This is Sample news",
	      "This is Sample news",
	      "This is Sample news",
	      "This is Sample news",
	      "This is Sample news",
	      "This is Sample news",
	      "This is Sample news",
	      "This is Sample news"
	  } ;
	  int[] imageId = {
	    
	      
	  };
	  private Button buttonClick;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		
		 buttonClick = (Button) findViewById(R.id.buttonClick);
		 
	        // add listener to button 
	        buttonClick.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View arg0) {
	 
	                // Create custom dialog object
	                final Dialog dialog = new Dialog(News.this);
	                // Include dialog.xml file
	                dialog.setContentView(R.layout.news_dialog);
	                // Set dialog title
	                dialog.setTitle("Add Your News");
	 
	                // set values for custom dialog components - text, image and button
	                TextView text = (EditText) dialog.findViewById(R.id.textDialog);
	                //text.setText("Custom dialog Android example.");
	                //ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
	               // image.setImageResource(R.drawable.image0);
	 
	                dialog.show();
	                 
	                Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
	                // if decline button is clicked, close the custom dialog
	                declineButton.setOnClickListener(new OnClickListener() {
	                    @Override
	                    public void onClick(View v) {
	                        // Close dialog
	                        dialog.dismiss();
	                    }
	                });
	 
	                 
	            }
	 
	        });
		 Customgridtext adapter = new Customgridtext(News.this, web, imageId);
		    grid=(GridView)findViewById(R.id.grid);
		        grid.setAdapter(adapter);
		        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		                @Override
		                public void onItemClick(AdapterView<?> parent, View view,
		                                        int position, long id) {
		                	Intent myintent2 = new Intent(News.this,
		    						Newsdescription.class);
		    				startActivity(myintent2);
		                }
		            });
		        
		        
		        
	}
}
