package com.naukrionetouch;

import com.example.naukrionetouch.R;
import com.naukrionetouch.model.UserManagement;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class SearchResultActivity extends Activity {

	ListView listSearchedCandidates;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		listSearchedCandidates=(ListView)findViewById(R.id.listSearchedCandidates);
		getData();
	}
	
	private void getData(){
		String[] data=new String[3];
		data[0]=getIntent().getStringExtra("education");
		data[1]=getIntent().getStringExtra("course");
		data[2]=getIntent().getStringExtra("experience");
		Cursor cr=new UserManagement(this).getCandidates(data);
		if(cr!=null){
			SimpleCursorAdapter cursorAdapter;
			String[] columns={"name","email","mobile"};
			int[] controls={R.id.lblCandidateName,R.id.lblCandidateEmail,R.id.lblCandidateMobile};
			cursorAdapter = new SimpleCursorAdapter(
		    this, R.layout.list_search_candiates, cr, columns, controls, 0);
			listSearchedCandidates.setAdapter(cursorAdapter);
		}
	}
	
	
	
}
