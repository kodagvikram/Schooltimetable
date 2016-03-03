package com.mobiwebcode.schooltimetable;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class Quotes extends Activity {
	 GridView grid;
	  String[] web = {
	        "This is Sample quotes",
	      "This is Sample quotes",
	      "This is Sample quotes",
	      "This is Sample quotes",
	      "This is Sample quotes",
	      "This is Sample quotes",
	      "This is Sample quotes",
	      "This is Sample quotes",
	      "This is Sample quotes",
	      "This is Sample quotes",
	      "This is Sample quotes",
	      "This is Sample quotes",
	      "This is Sample quotes",
	      "This is Sample quotes",
	      "This is Sample quotes"
	  } ;
	  int[] imageId = {
	    
	      
	  };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quotes);
		
		 Button buttonClick = (Button) findViewById(R.id.buttonClick);
		 
	        // add listener to button 
	        buttonClick.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View arg0) {
	 
	                // Create custom dialog object
	                final Dialog dialog = new Dialog(Quotes.this);
	                // Include dialog.xml file
	                dialog.setContentView(R.layout.news_dialog);
	                // Set dialog title
	                dialog.setTitle("Add Your Quotes");
	 
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
		
		
		 Customgridtext adapter = new Customgridtext(Quotes.this, web, imageId);
		    grid=(GridView)findViewById(R.id.grid);
		        grid.setAdapter(adapter);
		        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		                @Override
		                public void onItemClick(AdapterView<?> parent, View view,
		                                        int position, long id) {
		                    Toast.makeText(Quotes.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
		                }
		            });
	}
}
