package com.naukrionetouch;

import com.example.naukrionetouch.R;
import com.naukrionetouch.model.UserManagement;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class JobDetailsActivity extends Activity {

	TextView lblDetailJobTitle,lblDetailJobDescription,lblDetailEducation,lblDetailCourse,lblDetailJobCompanyName,lblDetailCompanyDescription,lblDetailEmail,lblDetailMobile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_details);
		init();
		getData();
		
	}
	
	public void init(){
		lblDetailJobTitle=(TextView)findViewById(R.id.lblDetailJobTitle);
		lblDetailJobDescription=(TextView)findViewById(R.id.lblDetailJobDescription);
		lblDetailEducation=(TextView)findViewById(R.id.lblDetailEducation);
		lblDetailCourse=(TextView)findViewById(R.id.lblDetailCourse);
		//Company Details and Contact Details
		lblDetailJobCompanyName=(TextView)findViewById(R.id.lblDetailJobCompanyName);
		lblDetailCompanyDescription=(TextView)findViewById(R.id.lblDetailCompanyDescription);
		lblDetailEmail=(TextView)findViewById(R.id.lblDetailEmail);
		lblDetailMobile=(TextView)findViewById(R.id.lblDetailMobile);
	}
	
	public void getData(){
		String jobId=this.getIntent().getStringExtra("jobId");
		
		try{
		
		String recruiterId="";
		Cursor companyData=null;
		Cursor jobData=new UserManagement(this).getJobData(Integer.parseInt(jobId));
		if(jobData!=null){
			jobData.moveToFirst();
			lblDetailJobTitle.setText(jobData.getString(jobData.getColumnIndex("jobTitle")));
			lblDetailJobDescription.setText(jobData.getString(jobData.getColumnIndex("jobDescription")));
			lblDetailEducation.setText(jobData.getString(jobData.getColumnIndex("requiredEducation")));
			lblDetailCourse.setText(jobData.getString(jobData.getColumnIndex("requiredCourse")));
			
			recruiterId=jobData.getString(jobData.getColumnIndex("recruiterId"));
			
			companyData=new UserManagement(this).getCompanyData(Integer.parseInt(recruiterId));
		}
		
		if(companyData!=null)
			companyData.moveToFirst();
		lblDetailJobCompanyName.setText(companyData.getString(companyData.getColumnIndex("companyName")));
		lblDetailCompanyDescription.setText(companyData.getString(companyData.getColumnIndex("description")));
		lblDetailEmail.setText(companyData.getString(companyData.getColumnIndex("email")));
		lblDetailMobile.setText(companyData.getString(companyData.getColumnIndex("mobile")));
		}catch(Exception e){
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
}
