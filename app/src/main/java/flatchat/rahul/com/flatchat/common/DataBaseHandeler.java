package flatchat.rahul.com.flatchat.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rahul on 5/21/15.
 */
public class DataBaseHandeler  extends SQLiteOpenHelper {

    public static final String _id="_id";
    public static final String MESSAGE_TYPE="MESSAGE_TYPE";
    public static final String MESSAGE_DATA="MESSAGE_DATA";
    public static final String MESSAGE_TIMESTAMP="MESSAGE_TIMESTAMP";

    private static final String DB_NAME="chat";
    private static final String TABLE_NAME="messages";

    private static final int DB_VERSION=6;

    private static final String CREATE_TABLE="create table "+TABLE_NAME+" ( "+_id+" text,"+MESSAGE_TYPE+" text," +
            MESSAGE_DATA+" text, "+MESSAGE_TIMESTAMP+" text);";



    public DataBaseHandeler(Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Long insertData(ContentValues values){
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME,null,values);


    }


    public Cursor selectData(){
        Log.i("TAG","Select data");
        return (getReadableDatabase().rawQuery("select * from  " + TABLE_NAME+" ;", null));}}
       /* while (cursor!=null && cursor.moveToFirst()){
            Log.i("TAG", "cursor is noy null");
            Log.e("TAG", cursor.getString(cursor.getColumnIndex(MESSAGE_DATA)));
            s=cursor.getCount();
            cursor.moveToNext();
        }
    }*/

