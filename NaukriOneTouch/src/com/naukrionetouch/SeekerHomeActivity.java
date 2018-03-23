package com.naukrionetouch;

import com.example.naukrionetouch.R;
import com.naukrionetouch.model.PrefManagement;
import com.naukrionetouch.model.UserManagement;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class SeekerHomeActivity extends Activity {

	private TabHost tabhost;
	private UserManagement user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_seekerhome);
		tabhost=(TabHost)findViewById(R.id.tabhost);
		tabhost.setup();
		//adding tabs
		TabSpec spec=tabhost.newTabSpec("tab_creation");
		spec.setIndicator("Recommended Jobs");
		spec.setContent(R.id.tab1);
		tabhost.addTab(spec);
		
		tabhost.addTab(tabhost.newTabSpec("tab_insert").setIndicator("Profile").setContent(R.id.tab2));
		tabhost.setOnTabChangedListener(new OnTabChangeListener(){

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				int i=tabhost.getCurrentTab();
				switch(i){
				case 0:
					getRecommendedJobs();
					break;
				case 1:
					getSeekerProfileData();
					break;
				default:
					Toast.makeText(SeekerHomeActivity.this, "Error", Toast.LENGTH_SHORT).show();
				}
			}
			
		});
		
		getRecommendedJobs();
	}
	
	public void getSeekerProfileData(){
		TextView lblName,lblEmail,lblMobile,lblEducation,lblCourse,lblUniversity;
		lblName=(TextView)findViewById(R.id.lblSeekerName);
		lblEmail=(TextView)findViewById(R.id.lblSeekerMail);
		lblMobile=(TextView)findViewById(R.id.lblMobile);
		lblEducation=(TextView)findViewById(R.id.lblEducation);
		lblCourse=(TextView)findViewById(R.id.lblCourse);
		lblUniversity=(TextView)findViewById(R.id.lblUniversity);
		
		user=new UserManagement(this);
		String data[]=user.getSeekerData();
		lblName.setText(data[0]);
		lblEmail.setText(data[1]);
		lblMobile.setText(data[2]);
		lblEducation.setText(data[3]);
		lblCourse.setText(data[4]);
		lblUniversity.setText(data[5]);
	}
	
	public void getRecommendedJobs(){
		Cursor cr=new UserManagement(this).getRecommendedJobs();
		ListView listRecommendedJobs=(ListView)findViewById(R.id.listRecommendedJobs);
		TextView lblError=(TextView)findViewById(R.id.lblError);
		lblError.setText("");
		if(cr!=null){
			SimpleCursorAdapter cursorAdapter;
			String[] columns={"jobTitle","jobDescription","_id"};
			int[] controls={R.id.lblJobTitle,R.id.lblJobDescription,R.id.lblId};
			cursorAdapter = new SimpleCursorAdapter(
		    this, R.layout.list_recommended_jobs, cr, columns, controls, 0);
			listRecommendedJobs.setAdapter(cursorAdapter);
			listRecommendedJobs.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int arg2, long arg3) {
					TextView lblJobId=(TextView)view.findViewById(R.id.lblId);
					//Toast.makeText(SeekerHomeActivity.this, lblJobId.getText().toString(), Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(SeekerHomeActivity.this,JobDetailsActivity.class);
					intent.putExtra("jobId", lblJobId.getText().toString());
					startActivity(intent);
				}
				
			});
		}
		else{
			lblError.setText("No job match found");
		}
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.seeker_menu, menu);
        return true;
	}
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
		 	switch(item.getItemId()){
		 	case R.id.action_seeker_logout:
		 			new PrefManagement(this).setLoggedOut();
		 			Intent intent = new Intent(this, LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); 
					startActivity(intent);
					finish();
		 		break;
		 	}
		 	return true;
	    }
	
}
