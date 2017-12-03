package com.example.actech.digitecsoln;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JidanPC on 7/23/2017.
 */

public class DbHelper extends SQLiteOpenHelper{
    private Context context;
    public static final String Db_name="UserDetails";
    public static final String tbl_name="userTbl";
    public static final String col_name="Name";
    public static final String col_email="Email";
    public static final String col_phn="Phone";
    public static final String col_pswd="Password";



    public static final String col_city="City";

    String create_query="CREATE TABLE "+tbl_name+"("+col_name+" varchar(60),"+col_email+" varchar(60) PRIMARY KEY,"+col_phn+" varchar(60),"+col_pswd+" varchar(60),"+col_city+" varchar(60));";
    public DbHelper(Context context) {

        super(context, Db_name, null, 1);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public long dsignup(String name, String email, String password, String phone, String city){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues cVal=new ContentValues();
        cVal.put(col_name,name);
        cVal.put(col_email,email);
        cVal.put(col_phn,phone);
        cVal.put(col_pswd,password);
        cVal.put(col_city,city);

        return  sqLiteDatabase.insert(tbl_name,null,cVal);
    }

    public Cursor login(String email, String password){

        /*SQLiteDatabase sqLiteDatabase= getWritableDatabase();

        //This is the array of column name
        String[] column ={col_name,col_email,col_pswd,col_gndr,col_dob,col_gndr,col_city};


        //This is the where clause
        String selection = col_email+"=? and "+col_pswd+"=?";
        //this is the argument passed by method where it is called
        String[] argValue ={email,password};


        Cursor cursor = sqLiteDatabase.query(tbl_name,column,selection,argValue,null,null,null);
        return cursor;*/

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String[] columns={col_name,col_email,col_phn,col_pswd,col_city};
        String selection=col_email+"=? and "+col_pswd+"=?";
        String[] selArgs={email,password};
        return sqLiteDatabase.query(tbl_name,columns,selection,selArgs,null,null,null);
    }

    public int update(String nm, String eml,String phn, String pswd, String city){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues cVal=new ContentValues();
        cVal.put(col_name,nm);
        cVal.put(col_phn,phn);
        cVal.put(col_pswd,pswd);
        cVal.put(col_city,city);

        String sel=col_email+"=? ";
        String[] selArg={eml};
        return sqLiteDatabase.update(tbl_name,cVal,sel,selArg);
    }

    public int delete(String eml){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String sel=col_email+"=? ";
        String[] selArg={eml};
        return sqLiteDatabase.delete(tbl_name,sel,selArg);

    }

    public Cursor duplicate_login(String email){

        /*SQLiteDatabase sqLiteDatabase= getWritableDatabase();

        //This is the array of column name
        String[] column ={col_name,col_email,col_pswd,col_gndr,col_dob,col_gndr,col_city};


        //This is the where clause
        String selection = col_email+"=? and "+col_pswd+"=?";
        //this is the argument passed by method where it is called
        String[] argValue ={email,password};


        Cursor cursor = sqLiteDatabase.query(tbl_name,column,selection,argValue,null,null,null);
        return cursor;*/

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String[] columns={col_name,col_email,col_phn,col_pswd,col_city};
        String selection=col_email+"=?";
        String[] selArgs={email};
        return sqLiteDatabase.query(tbl_name,columns,selection,selArgs,null,null,null);
    }
}
