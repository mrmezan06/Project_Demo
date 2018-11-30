package com.mezan.atmdemo3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TransactionDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME="TransactionDatabase.db";
    Context context;

    public TransactionDatabase(Context context) {
        super(context, DB_NAME, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE HISTORY(ID INTEGER  PRIMARY KEY AUTOINCREMENT,SR NUMBER,RR NUMBER,TRANS INTEGER,DATEANDTIME DATETIME);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS HISTORY;");
        onCreate(db);
    }
    public long InsertTransaction(String sender,String receiver,String balance){
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        String date_time=simpleDateFormat.format(calendar.getTime());
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("SR",sender);
        contentValues.put("RR",receiver);
        contentValues.put("TRANS",balance);
        contentValues.put("DATEANDTIME",date_time);
        long row=sqLiteDatabase.insert("HISTORY",null,contentValues);
        return row;
    }
    public Cursor FindAccTransactionHistory(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       // boolean res=false;
       // String details="";
        //int id=0;
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM HISTORY",null);
        return cursor;
        /*if(cursor.getCount()==0){
            Toast.makeText(context,"No Data Found",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                String Sender=cursor.getString(1);
                //String id=cursor.getString(0);
                String Receiver=cursor.getString(2);
                String Trans=cursor.getString(3);
                String DT=cursor.getString(4);
                if(Sender.equals(sender)){
                    res=true;
                    //Toast.makeText(context,"Account Holder Name : "+name,Toast.LENGTH_LONG).show();
                    details +="ID: "+id+"\nSender: "+Sender+"\nReceiver: "+Receiver+"\nTransaction: "+Trans+"\nDate&Time: "+DT+"\n\n";
                    id++;
                }
            }
        }
        if(res){
            return  details;
        }else {
            return null;
        }*/
    }
    public int DeleteTranshistory(String id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        int row=sqLiteDatabase.delete("HISTORY","ID = ?",new String[]{id});
        return row;
    }
}
