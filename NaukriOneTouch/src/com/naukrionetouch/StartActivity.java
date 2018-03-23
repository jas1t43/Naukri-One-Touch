package com.naukrionetouch;

import com.example.naukrionetouch.R;
import com.naukrionetouch.model.PrefManagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;


public class StartActivity extends Activity {

	PrefManagement pref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		pref=new PrefManagement(this);
		if(pref.getUserStatus()){
			if(pref.getAcType().equals("Seeker")){
				startActivity(new Intent(this,SeekerHomeActivity.class));
			}
			else{
				startActivity(new Intent(this,RecruiterHomeActivity.class));
			}
			finish();
		}else{
			setContentView(R.layout.activity_start);
		}
	}
	
	public void onRegister(View v){
		startActivity(new Intent(this,RegisterActivity.class));
	}
	
	public void onLogin(View v){
		startActivity(new Intent(this,LoginActivity.class));
	}
	
	
}
