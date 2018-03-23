package com.naukrionetouch.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class UserManagement {
	Context context;
	private DBHelper db;
	public String acType="";
	PrefManagement pref;
	public UserManagement(Context context){
		this.context=context;
		db=new DBHelper(context);
	}
	
	public boolean isSeekerRegistered(String mail){
		String sql="select  * from "+Seeker.TABLE_NAME+" where "+
		        "email='"+mail+"'";
		try{
		Cursor cr=db.getData(sql);
		if(cr.getCount()==0)
			return false;
		else
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean isRecruiterRegistered(String mail){
		String sql="select * from "+Recruiter.TABLE_NAME+" where "+
		        "email='"+mail+"'";
		Cursor cr=db.getData(sql);
		try{
		if(cr.getCount()==0)
			return false;
		else
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean createJobProfile(String[] data){
		ContentValues contentValues=new ContentValues();
		contentValues.put("recruiterId",data[0]);
        contentValues.put("jobTitle",data[1]);
        contentValues.put("jobDescription",data[2]);
        contentValues.put("requiredEducation",data[3]);
        contentValues.put("requiredCourse",data[4]);
        contentValues.put("requiredExperience",Integer.parseInt(data[5]));
        contentValues.put("date",data[6]);
        contentValues.put("status",data[7]);
        if(db.insertData(JobProfiles.TABLE_NAME,contentValues))
        	return true;
        else 
        	return false;
	}
	
	public boolean insertSeeker(String[] data){
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",data[0]);
        contentValues.put("mobile",data[1]);
        contentValues.put("email",data[2]);
        contentValues.put("password",data[3]);
        contentValues.put("workStatus",data[4]);
        contentValues.put("experience",data[5]);
        contentValues.put("city1",data[6]);
        contentValues.put("city2",data[7]);
        contentValues.put("city3",data[8]);
        contentValues.put("industryType",data[9]);
        contentValues.put("highestEducation",data[10]);
        contentValues.put("course",data[11]);
        contentValues.put("university",data[12]);
        contentValues.put("passingYear",data[13]);
        contentValues.put("courseType",data[14]);
        if(db.insertData(Seeker.TABLE_NAME,contentValues))
        	return true;
        else 
        	return false;
    }
	
	
	public boolean createRecruiter(String[] data){
		ContentValues contentValues=new ContentValues();
        contentValues.put("email",data[0]);
        contentValues.put("password",data[1]);
        contentValues.put("mobile",data[2]);
        contentValues.put("companyName",data[3]);
        contentValues.put("description",data[4]);
        
        if(db.insertData(Recruiter.TABLE_NAME,contentValues))
        	return true;
        else 
        	return false;
	}
	
	
	public Cursor getSeeker(String mail,String password){
        String sql="select * from "+Seeker.TABLE_NAME+" where "+
        "email='"+mail+"' AND password='"+password+"'";
        Cursor cr=db.getData(sql);
        return cr;
    }
	
	public Cursor getRecruiter(String mail,String password){
        String sql="select * from "+Recruiter.TABLE_NAME+" where "+
        "email='"+mail+"' AND password='"+password+"'";
        Cursor cr=db.getData(sql);
        return cr;
    }
	
	public String[] getSeekerData(){
		String[] data=new String[6];
		Cursor cr=null;
		String sql="";
		pref=new PrefManagement(context);
		int id=Integer.parseInt(pref.getUserId());
		sql="select * from "+Seeker.TABLE_NAME+" where id="+id;
		cr=db.getData(sql);
		
		if(cr!=null){
			cr.moveToFirst();
			data[0]=cr.getString(cr.getColumnIndex("name"));
			data[1]=cr.getString(cr.getColumnIndex("email"));
			data[2]=cr.getString(cr.getColumnIndex("mobile"));
			data[3]=cr.getString(cr.getColumnIndex("highestEducation"));
			data[4]=cr.getString(cr.getColumnIndex("course"));
			data[5]=cr.getString(cr.getColumnIndex("university"));
			cr.close();
		}
		return data; 
	}
	
	public Cursor getRecommendedJobs(){
		String[] seekerData=new String[3];
		Cursor cr=null;
		pref=new PrefManagement(context);
		int id=Integer.parseInt(pref.getUserId());
		String sql="select * from "+Seeker.TABLE_NAME+" where id="+id;
		
		cr=db.getData(sql);
		if(cr!=null){
			cr.moveToFirst();
			seekerData[0]=cr.getString(cr.getColumnIndex("highestEducation"));
			seekerData[1]=cr.getString(cr.getColumnIndex("course"));
			seekerData[2]=cr.getString(cr.getColumnIndex("experience"));
			cr.close();
			sql="select id as _id,jobTitle,jobDescription,date from "+JobProfiles.TABLE_NAME+" where requiredEducation='"+
				seekerData[0]+"' or requiredCourse='"+seekerData[1]+"' or requiredExperience="+Integer.parseInt(seekerData[2]);
			cr=db.getData(sql);
		}
		return cr;
	}
	
	
	public Cursor getJobProfiles(){
		Cursor cr=null;
		int id=Integer.parseInt(new PrefManagement(context).getUserId());
		String sql="select id as _id,jobTitle,jobDescription,date from "+JobProfiles.TABLE_NAME+" where recruiterId="+id;
		cr=db.getData(sql);
		return cr;
	}
	
	public Cursor getCandidates(String[] data){
		String sql="select id as _id,name,email,mobile from "+Seeker.TABLE_NAME+" where highestEducation='"+data[0]+"' and course='"+data[1]+"' or experience='"+data[2]+"'";
		Cursor cr=db.getData(sql);
		return cr;
	}
	
	public Cursor getJobData(int id){
		String sql="select * from "+JobProfiles.TABLE_NAME+" where id="+id;
		Cursor cr=db.getData(sql);
		return cr;
	}
	
	public Cursor getCompanyData(int id){
		String sql="select * from "+Recruiter.TABLE_NAME+" where id="+id;
		Cursor cr=db.getData(sql);
		return cr;
	}
	
	public boolean login(String mail,String password){
		try{
		Cursor c=null;
		if(this.isSeekerRegistered(mail)){
			c=getSeeker(mail,password);
			acType="Seeker";
		}
		if(this.isRecruiterRegistered(mail)){
			c=getRecruiter(mail,password);
			acType="Recruiter";
		}
		
		if(c!=null){
			c.moveToFirst();
			String userId=c.getString(c.getColumnIndex("id"));
			String userMail=c.getString(c.getColumnIndex("email"));
			PrefManagement management=new PrefManagement(context);
			management.setLoggedIn(userId,userMail,acType);
			return true;
		}
		else{
			return false;
		}
		}catch(Exception e){
			return false;
		}
	}
	
}
