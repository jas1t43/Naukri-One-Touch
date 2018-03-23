package com.naukrionetouch.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME="dbNaukriOneTouch";	
	public DBHelper(Context context){
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(Seeker.SQL);
		db.execSQL(Recruiter.SQL);
		db.execSQL(JobProfiles.SQL);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE  IF EXISTS "+Seeker.TABLE_NAME);
		db.execSQL("DROP TABLE  IF EXISTS "+Recruiter.TABLE_NAME);
		db.execSQL("DROP TABLE  IF EXISTS "+JobProfiles.TABLE_NAME);
	}
	public boolean insertData(String table,ContentValues values){
		
		try{
			SQLiteDatabase db=this.getWritableDatabase();
			db.insertOrThrow(table, null, values);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	
	
	public Cursor getData(String sql){
		SQLiteDatabase db=this.getReadableDatabase();
		try{
	        Cursor cr=db.rawQuery(sql,null);
	        return cr;
		}catch(Exception e){
			return null;
		}
	}
	
}



interface Seeker{
	String TABLE_NAME="tblSeeker";

	String SQL="create table tblSeeker"+ 
			"(id INTEGER PRIMARY KEY,"+
			"name text,"+
			"mobile text,"+
			"email text,"+
			"password text,"+
			"workStatus text,"+
			"experience text,"+
			"city1 text,"+
			"city2 text,"+
			"city3 text,"+
			"industryType text,"+
			"highestEducation text,"+
			"course text,"+
			"university text,"+
			"passingYear text,"+
			"courseType text)";
}

interface Recruiter{
	String TABLE_NAME="tblRecruiter";
	String SQL="create table tblRecruiter"+
			"(id INTEGER PRIMARY KEY,"+
			"email text,"+
			"password text,"+
			"mobile text,"+
			"companyName text,"+
			"description text)";
			
}
interface JobProfiles{
	String TABLE_NAME="tblJobProfiles";
	String SQL="create table tblJobProfiles"+
				"(id INTEGER PRIMARY KEY,"+
				"recruiterId integer,"+
				"jobTitle text,"+
				"jobDescription text,"+
				"requiredEducation text,"+
				"requiredCourse text,"+
				"requiredExperience integer,"+
				"date text,"+
				"status text)";
}