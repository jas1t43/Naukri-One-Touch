package com.naukrionetouch;

import com.example.naukrionetouch.R;
import com.naukrionetouch.adapter.CityList;
import com.naukrionetouch.model.MyArrays;
import com.naukrionetouch.model.UserManagement;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnCheckedChangeListener {

	Spinner spAcType,spIndustry;
	UserManagement user;
	final int CITY_REQUEST_CODE=9634;
	String[] seekerData=new String[15];
	RadioButton rdoFresher,rdoExp;
	Spinner spExperience;
	EditText txtLocations;
	EditText txtName,txtMobile,txtMail,txtPassword;
	Spinner spHigherEducation,spCourse,spPassingYear,spCourseType;
	EditText txtUniversity;
	String workStatus="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		spAcType=(Spinner)findViewById(R.id.spActType);
		user=new UserManagement(this);
	}
	
	
	public void onSecondStep(View v){
		//0 for seeker
		//1 for recruiter
		
		if(spAcType.getSelectedItemPosition()==0){
			setContentView(R.layout.activity_seeker_step1);
		}else{
			setContentView(R.layout.activity_recruiter_register);
		}
	}
	
	
	public void onRecruiterRegister(View v){
		EditText txtRecruiterMail,txtRecruiterPass,txtRecruiterMobile,txtCompanyName,txtDescription;
		
		txtRecruiterMail=(EditText)findViewById(R.id.txtRecruiterMail);
		txtRecruiterPass=(EditText)findViewById(R.id.txtRecruiterPass);
		txtRecruiterMobile=(EditText)findViewById(R.id.txtRecruiterMobile);
		txtCompanyName=(EditText)findViewById(R.id.txtCompanyName);
		txtDescription=(EditText)findViewById(R.id.txtDescription);
		
		if(txtRecruiterMail.getText().toString().equals("")||txtRecruiterPass.getText().toString().equals("")||txtRecruiterMobile.getText().toString().equals("")||txtCompanyName.getText().toString().equals("")||txtDescription.getText().toString().equals("")||txtRecruiterMail.getText().toString().equals("")){
			Toast.makeText(this, "Fill all required information", Toast.LENGTH_SHORT).show();
		}
		else{
			String recruiterData[]=new String[5];
			recruiterData[0]=txtRecruiterMail.getText().toString();
			recruiterData[1]=txtRecruiterPass.getText().toString();
			recruiterData[2]=txtRecruiterMobile.getText().toString();
			recruiterData[3]=txtCompanyName.getText().toString();
			recruiterData[4]=txtDescription.getText().toString();
			if(user.isSeekerRegistered(txtRecruiterMail.getText().toString())||user.isRecruiterRegistered(txtRecruiterMail.getText().toString())){
				Toast.makeText(this, "Email already registered.", Toast.LENGTH_SHORT).show();
			}
			else{
				if(user.createRecruiter(recruiterData)){
					Toast.makeText(this, "Registered Successfully.", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(this,LoginActivity.class));
				}
			}
		}
			
		
	}
	
	public void onSeekerStep2(View v){
		txtName=(EditText)findViewById(R.id.txtName);
		txtMobile=(EditText)findViewById(R.id.txtMobile);
		txtMail=(EditText)findViewById(R.id.txtMail);
		txtPassword=(EditText)findViewById(R.id.txtPass);
		if(txtName.getText().toString().equals("")||txtMobile.getText().toString().equals("")||txtMail.getText().toString().equals("")||txtPassword.getText().toString().equals("")){
			Toast.makeText(this, "Fill all required information", Toast.LENGTH_SHORT).show();
		}
		else{
			if(user.isSeekerRegistered(txtMail.getText().toString())||user.isRecruiterRegistered(txtMail.getText().toString())){
				Toast.makeText(this, "Email already registered.", Toast.LENGTH_SHORT).show();
			}
			else{
				seekerData[0]=txtName.getText().toString();
				seekerData[1]=txtMobile.getText().toString();
				seekerData[2]=txtMail.getText().toString();
				seekerData[3]=txtPassword.getText().toString();
				setContentView(R.layout.activity_seeker_step2);
				
				//for next step controls
				
				spExperience=(Spinner)findViewById(R.id.spExperienceMonths);
				spExperience.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, MyArrays.experienceMonths));
				spExperience.setEnabled(false);
				rdoFresher=(RadioButton)findViewById(R.id.rdoFresher);
				rdoExp=(RadioButton)findViewById(R.id.rdoExp);
				rdoFresher.setOnCheckedChangeListener(this);
				rdoExp.setOnCheckedChangeListener(this);
				txtLocations=(EditText)findViewById(R.id.txtLocations);
				spIndustry=(Spinner)findViewById(R.id.spIndustry);
				spIndustry.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, MyArrays.industryType));
			}
		}
	}
	
	public void onSeekerStep3(View v){
		if(txtLocations.getText().toString().equals("")){
			Toast.makeText(this, "Fill all required information", Toast.LENGTH_SHORT).show();
		}
		else{
			String[] Locations=txtLocations.getText().toString().split(",");
			seekerData[4]=workStatus;
			seekerData[5]=spExperience.getSelectedItem().toString();
			seekerData[6]=Locations[0];
			seekerData[7]=Locations[1];
			seekerData[8]=Locations[2];
			seekerData[9]=spIndustry.getSelectedItem().toString();
			
			setContentView(R.layout.activity_seeker_step3);
			
			txtUniversity=(EditText)findViewById(R.id.txtUniversity);
			
			spHigherEducation=(Spinner)findViewById(R.id.spHigherEducation);
			spHigherEducation.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, MyArrays.highestEducation));
			
			spCourse=(Spinner)findViewById(R.id.spCourse);
			
			spHigherEducation.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int pos, long arg3) {
					// TODO Auto-generated method stub
					switch(pos){
					case 0:
						spCourse.setAdapter(new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_item,MyArrays.ugCourses));
						break;
					case 1:
						spCourse.setAdapter(new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_item,MyArrays.pgCourses));
						break;
					case 2:
						spCourse.setAdapter(new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_item,MyArrays.doctorateCourses));
						break;
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			spPassingYear=(Spinner)findViewById(R.id.spPassingYear);
			spPassingYear.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, MyArrays.passingYear));
			
			spCourseType=(Spinner)findViewById(R.id.spCourseType);
			spCourseType.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, MyArrays.courseType));
		}
		
	}
	
	public void onSeekerFinish(View v){
		
		if(txtUniversity.getText().toString().equals("")){
			Toast.makeText(this, "Fill University Name", Toast.LENGTH_SHORT).show();
		}
		else{
			seekerData[10]=spHigherEducation.getSelectedItem().toString();
			seekerData[11]=spCourse.getSelectedItem().toString();
			seekerData[12]=txtUniversity.getText().toString();
			seekerData[13]=spPassingYear.getSelectedItem().toString();
			seekerData[14]=spCourseType.getSelectedItem().toString();
			//save data into database
			if(user.insertSeeker(seekerData))
			{
				Toast.makeText(this, "Registered Successfully.", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, LoginActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(intent);
				finish();
			}
			else{
				Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public void onCitySelection(View v){
		this.startActivityForResult(new Intent(this,CityList.class), CITY_REQUEST_CODE);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode==CITY_REQUEST_CODE){
			if(resultCode==RESULT_OK){
				txtLocations=(EditText)findViewById(R.id.txtLocations);
				txtLocations.setText("");
				String cities=data.getStringExtra("selected_cities");
				txtLocations.setText(cities);
			}
		}
		
	}


	@Override
	public void onCheckedChanged(CompoundButton btn, boolean arg1) {
		// TODO Auto-generated method stub
		if(rdoFresher.isChecked()){
			spExperience.setEnabled(false);
			workStatus=rdoFresher.getText().toString();
			spExperience.setSelection(0);
		}
		if(rdoExp.isChecked()){
			spExperience.setEnabled(true);
			workStatus=rdoExp.getText().toString();
		}
	}
	
}
