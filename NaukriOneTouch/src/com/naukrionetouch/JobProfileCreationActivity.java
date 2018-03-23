package com.naukrionetouch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.naukrionetouch.R;
import com.naukrionetouch.model.MyArrays;
import com.naukrionetouch.model.PrefManagement;
import com.naukrionetouch.model.UserManagement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class JobProfileCreationActivity extends Activity {

	EditText txtJobTitle,txtJobDescription;
	Spinner spRequiredEducation,spRequiredCourse,spRequiredExperience;
	UserManagement user;
	PrefManagement pref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_jobprofile_creation);
		init();
		user=new UserManagement(this);
		pref=new PrefManagement(this);
	}
	
	public void init(){
		txtJobTitle=(EditText)findViewById(R.id.txtJobTitle);
		txtJobDescription=(EditText)findViewById(R.id.txtJobDescription);
		spRequiredEducation=(Spinner)findViewById(R.id.spRequiredEducation);
		spRequiredCourse=(Spinner)findViewById(R.id.spRequiredCourse);
		spRequiredExperience=(Spinner)findViewById(R.id.spRequiredExperience);
		
		spRequiredEducation.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, MyArrays.highestEducation));
		spRequiredExperience.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, MyArrays.experienceMonths));
		
		spRequiredEducation.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				switch(pos){
				case 0:
					spRequiredCourse.setAdapter(new ArrayAdapter<String>(JobProfileCreationActivity.this,android.R.layout.simple_spinner_item,MyArrays.ugCourses));
					break;
				case 1:
					spRequiredCourse.setAdapter(new ArrayAdapter<String>(JobProfileCreationActivity.this,android.R.layout.simple_spinner_item,MyArrays.pgCourses));
					break;
				case 2:
					spRequiredCourse.setAdapter(new ArrayAdapter<String>(JobProfileCreationActivity.this,android.R.layout.simple_spinner_item,MyArrays.doctorateCourses));
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	@SuppressLint("SimpleDateFormat") public void onPostJob(View v){
		if(txtJobTitle.getText().toString().equals("")||txtJobDescription.getText().toString().equals("")){
			Toast.makeText(this, "Please Fill all required Fields.", Toast.LENGTH_SHORT).show();
		}
		else{
			String profileData[]=new String[8];
			profileData[0]=pref.getUserId();
			profileData[1]=txtJobTitle.getText().toString();
			profileData[2]=txtJobDescription.getText().toString();
			profileData[3]=spRequiredEducation.getSelectedItem().toString();
			profileData[4]=spRequiredCourse.getSelectedItem().toString();
			profileData[5]=spRequiredExperience.getSelectedItem().toString();
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			profileData[6]=dateFormat.format(date).toString();
			profileData[7]="active";
			if(user.createJobProfile(profileData)){
				Toast.makeText(this, "Job Profile Created Successfully", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, RecruiterHomeActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(intent);
				finish();
			}else{
				Toast.makeText(this, "Some Error", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
}
