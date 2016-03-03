package com.mobiwebcode.schooltimetable;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.MediaController;
import android.widget.VideoView;
public class Videos extends Activity {
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
    setContentView(R.layout.videos);
    
    Button buttonClick = (Button) findViewById(R.id.buttonClick);
	 
    // add listener to button 
    buttonClick.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View arg0) {

            // Create custom dialog object
            AlertDialog.Builder adb = new AlertDialog.Builder(Videos.this);



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

    CustomGrid adapter = new CustomGrid(Videos.this, web, imageId);
    grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                	 try {
							final Dialog dialog = new Dialog(Videos.this);
							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.videoview);
							dialog.show();
							VideoView videoView = (VideoView) dialog
									.findViewById(R.id.VideoView);
							WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT);
							lp.copyFrom(dialog.getWindow().getAttributes());
							dialog.getWindow().setAttributes(lp);
					       
					        String link="http://mobiwebcode.com/communication/admin/userMedia/big_buck_bunny.mp4";
				       
					        MediaController mediaController = new MediaController(Videos.this);
					        mediaController.setAnchorView(videoView);
					        Uri video = Uri.parse(link);
					        videoView.setMediaController(mediaController);
					        videoView.setVideoURI(video);
					        videoView.start();
					    } catch (Exception e) {
					        // TODO: handle exception
					       
					    }

                }
            });
  }
}