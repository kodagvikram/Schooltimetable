package com.mobiwebcode.schooltimetable;


import android.app.Activity;
import android.os.Bundle;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
 
public class NOVSelectSubjectActivity extends Activity {
 
    private CheckBox coremaths,physics,chemistry;
    private Button Clickhere;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
     
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novselectsubject_main);
 
        coremaths   = (CheckBox) findViewById(R.id.chechbox_maths);
        physics    = (CheckBox) findViewById(R.id.chechbox_phisics);
        chemistry    = (CheckBox) findViewById(R.id.chechbox_chemistry);
       // Clickhere = (Button) findViewById(R.id.btn_calculate);
         
         
         
        Clickhere.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
 
                // Create string buffer to
 
                StringBuffer OUTPUT = new StringBuffer();
                OUTPUT.append("Android : ")
                        .append(coremaths.isChecked());
 
                OUTPUT.append("\nJava : ").append(
                        physics.isChecked());
 
               OUTPUT.append("\nOpenCV :").append(
                        chemistry.isChecked());
 
                Toast.makeText(NOVSelectSubjectActivity.this, OUTPUT.toString(),
                        Toast.LENGTH_LONG).show();
 
            }
        });
         
    }
}