package com.naukrionetouch;

import com.example.naukrionetouch.R;
import com.naukrionetouch.model.UserManagement;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

public class LoginActivity extends Activity {

	EditText txtLoginMail,txtLoginPass;
	UserManagement user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		txtLoginMail=(EditText)findViewById(R.id.txtLoginEmail);
		txtLoginPass=(EditText)findViewById(R.id.txtLoginPass);
		user=new UserManagement(this);
	}
	
	public void onRegister(View v){
		startActivity(new Intent(this,RegisterActivity.class));
	}
	
	public void onLogin(View v){
		if(txtLoginMail.getText().toString().equals("")||txtLoginPass.getText().toString().equals("")){
			Toast.makeText(this, "Please Fill all Required Fields", Toast.LENGTH_SHORT).show();
		}
		else{
			if(user.login(txtLoginMail.getText().toString(), txtLoginPass.getText().toString())){
				if(user.acType.equals("Seeker")){
					Intent intent = new Intent(this, SeekerHomeActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); 
					startActivity(intent);
					finish();
				}else{
					Intent intent = new Intent(this, RecruiterHomeActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); 
					startActivity(intent);
					finish();
				}
				finish();
			}
			else{
				Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
}
