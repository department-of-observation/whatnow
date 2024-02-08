package com.sp.whatnow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ForumHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="forumlist.db";
    private static final int SCHEMA_VERSION = 1;

    public ForumHelper(Context context){
        super(context,DATABASE_NAME,null,SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE  forums_table ( _id UUID  PRIMARY KEY,"+
                " forumtitle TEXT, forumuser TEXT , forumcontent TEXT," +
                " forumdate TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public Cursor getAll(){
        return (getReadableDatabase().rawQuery(
                " SELECT _ID, forumtitle, forumuser, forumcontent," +
                        "forumdate FROM  forums_table ORDER BY _ID",null));
    }

    public Cursor getById(String id) {
        String[] args = {id};

        return (getReadableDatabase().rawQuery(
                " SELECT _ID, forumtitle, forumuser, forumcontent," +
                        "forumdate FROM  forums_table WHERE _ID = ?", args));
    }

    public void insert(String uuid ,String forumtitle,String forumuser, String forumcontent,
                       String forumdate){
        ContentValues cv = new ContentValues();
        cv.put("_id", uuid);
        cv.put("forumtitle",forumtitle);
        cv.put("forumuser",forumuser);
        cv.put("forumcontent",forumcontent);
        cv.put("forumdate",forumdate);

        getWritableDatabase().insert(" forums_table","forumtitle",cv);
    }

    public void update(String id, String forumtitle,String forumuser, String forumcontent,
                       String forumdate){
        ContentValues cv = new ContentValues();
        String[] args = {id};
        cv.put("forumtitle",forumtitle);
        cv.put("forumuser",forumuser);
        cv.put("forumcontent",forumcontent);
        cv.put("forumdate",forumdate);

        getWritableDatabase().update(" forums_table",cv," _ID = ?",args);
    }
    public String getID(Cursor c){
        return(c.getString(0));
    }
    public String getforumtitle(Cursor c){
        return(c.getString(1));
    }
    public String getforumuser(Cursor c){
        return(c.getString(2));
    }
    public String getforumcontent(Cursor c){
        return(c.getString(3));
    }
    public String getforumdate(Cursor c){
        return(c.getString(4));
    }
}
