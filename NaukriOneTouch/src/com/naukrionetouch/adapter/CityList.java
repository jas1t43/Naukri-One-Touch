
package com.naukrionetouch.adapter;

import com.example.naukrionetouch.R;
import com.naukrionetouch.model.MyArrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CityList extends Activity {

	ListView listCity;
	String []cities=new String[3];
	int count=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_list);
		listCity=(ListView)findViewById(R.id.listCity);
		listCity.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,MyArrays.cityArray));
		listCity.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listCity.setSmoothScrollbarEnabled(true);
		listCity.setOnItemClickListener(new OnItemClickListener(){
		
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				
			}
			
		});
	}
	
	public void onDone(View v){
		SparseBooleanArray checked=listCity.getCheckedItemPositions();
		if(checked.size()==3){
			for (int i = 0; i < 3; i++)
            {                    
                if(checked.valueAt(i) == true)
                    cities[i]=MyArrays.cityArray[checked.keyAt(i)];

            }     
			Intent intent=getIntent();
			intent.putExtra("selected_cities",cities[0]+","+cities[1]+","+cities[2]);
			this.setResult(RESULT_OK, intent);
			finish();
		}
		else{
			Toast.makeText(this, "Please Select 3 Cities", Toast.LENGTH_SHORT).show();	
		}
	}
	
}
