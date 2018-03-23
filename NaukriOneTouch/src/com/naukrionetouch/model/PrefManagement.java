package com.naukrionetouch.model;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManagement {
	Context context;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	public PrefManagement(Context context){
		this.context=context;
	}
	
	private void openPref(){
		pref=context.getSharedPreferences("NaukriOneTouch", Context.MODE_PRIVATE);
	}
	public void setLoggedIn(String userId,String userMail,String acType){
		openPref();
		editor=pref.edit();
        editor.putString("userId",userId);
        editor.putString("userMail",userMail);
        editor.putBoolean("status", true);// true=loggedIn
        editor.putString("acType", acType);
        editor.commit();
    }
	public void setLoggedOut(){
		openPref();
		editor=pref.edit();
		editor.putBoolean("status", false);
		editor.commit();
	}
	public String getUserId(){
        openPref();
        return pref.getString("userId","");
    }
    public String getUserMail(){
    	openPref();
        return pref.getString("userMail","");
    }
    public String getAcType(){
    	openPref();
        return pref.getString("acType","");
    }
    public boolean getUserStatus(){
    	openPref();
        return pref.getBoolean("status",false);
    }
}
