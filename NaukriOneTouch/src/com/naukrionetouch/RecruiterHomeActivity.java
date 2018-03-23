package com.naukrionetouch;

import com.example.naukrionetouch.R;
import com.naukrionetouch.model.MyArrays;
import com.naukrionetouch.model.PrefManagement;
import com.naukrionetouch.model.UserManagement;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class RecruiterHomeActivity extends Activity {
	private TabHost tabhost;
	PrefManagement pref;
	Spinner spSearchRequiredEducation,spSearchRequiredCourse,spSearchRequiredExperience;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_recruiterhome);
		tabhost=(TabHost)findViewById(R.id.recruiterTabhost);
		tabhost.setup();
		//adding tabs
		TabSpec spec=tabhost.newTabSpec("tab_creation");
		spec.setIndicator("Job Profiles");
		spec.setContent(R.id.recruiterTab1);
		tabhost.addTab(spec);
		
		tabhost.addTab(tabhost.newTabSpec("tab_insert").setIndicator("Search Candidates").setContent(R.id.recruiterTab2));
		tabhost.setOnTabChangedListener(new OnTabChangeListener(){

			@Override
			public void onTabChanged(String tab) {
				// TODO Auto-generated method stub
				int i=tabhost.getCurrentTab();
				switch(i){
				case 0:
					getJobProfiles();
					break;
				case 1:
					fillSpinners();
					break;
				default:
					Toast.makeText(RecruiterHomeActivity.this, "Error", Toast.LENGTH_SHORT).show();
				}
			}
			
		});
		getJobProfiles();
	}
	
	public void getJobProfiles(){
		Cursor cr=new UserManagement(this).getJobProfiles();
		ListView listJobProfiles=(ListView)findViewById(R.id.listJobProfiles);
		TextView lblError=(TextView)findViewById(R.id.lbljobProfileError);
		lblError.setText("");
		if(cr!=null){
			SimpleCursorAdapter cursorAdapter;
			String[] columns={"jobTitle","jobDescription","date"};
			int[] controls={R.id.lblJobProfileTitle,R.id.lblJobProfileDescription,R.id.lblJobProfileDate};
			cursorAdapter = new SimpleCursorAdapter(
                    this, R.layout.list_job_profiles, cr, columns, controls, 0);
			listJobProfiles.setAdapter(cursorAdapter);
		}
		else{
			lblError.setText("No job profile created by You");
		}
	}
	
	public void fillSpinners(){
		spSearchRequiredEducation=(Spinner)findViewById(R.id.spSearchRequiredEducation);
		spSearchRequiredCourse=(Spinner)findViewById(R.id.spSearchRequiredCourse);
		spSearchRequiredExperience=(Spinner)findViewById(R.id.spSearchRequiredExperience);
		
		spSearchRequiredEducation.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, MyArrays.highestEducation));
		spSearchRequiredExperience.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, MyArrays.experienceMonths));
		
		spSearchRequiredEducation.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				switch(pos){
				case 0:
					spSearchRequiredCourse.setAdapter(new ArrayAdapter<String>(RecruiterHomeActivity.this,android.R.layout.simple_spinner_item,MyArrays.ugCourses));
					break;
				case 1:
					spSearchRequiredCourse.setAdapter(new ArrayAdapter<String>(RecruiterHomeActivity.this,android.R.layout.simple_spinner_item,MyArrays.pgCourses));
					break;
				case 2:
					spSearchRequiredCourse.setAdapter(new ArrayAdapter<String>(RecruiterHomeActivity.this,android.R.layout.simple_spinner_item,MyArrays.doctorateCourses));
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	public void onSearchCandidates(View v){
		if(spSearchRequiredEducation.getSelectedItem()!=null){
			String data[]=new String[3];
			data[0]=spSearchRequiredEducation.getSelectedItem().toString();
			data[1]=spSearchRequiredCourse.getSelectedItem().toString();
			data[2]=spSearchRequiredExperience.getSelectedItem().toString();
			Cursor cr=new UserManagement(this).getCandidates(data);
			if(cr!=null){
				Intent intent=new Intent(this,SearchResultActivity.class);
				intent.putExtra("education", data[0]);
				intent.putExtra("course", data[1]);
				intent.putExtra("experience", data[2]);
				startActivity(intent);
			}
			else{
				Toast.makeText(this, "No match found", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
	}
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
		 	switch(item.getItemId()){
		 	case  R.id.action_create_job_profile:
		 			startActivity(new Intent(this,JobProfileCreationActivity.class));
		 		break;
		 	case R.id.action_recruiter_logout:
		 			pref=new PrefManagement(this);
		 			pref.setLoggedOut();
		 			Intent intent = new Intent(this, LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); 
					startActivity(intent);
					finish();
		 		break;
		 	}
		 	return true;
	    }
	
	
}
