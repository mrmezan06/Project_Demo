package com.mezan.atmdemo3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ATMDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME="USER.db";
    private Context context;
    public ATMDatabase(Context context) {
        super(context,DB_NAME , null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ACCOUNT(ACC_NO NUMBER PRIMARY KEY NOT NULL,NAME VARCHAR(40) NOT NULL,PIN NUMBER NOT NULL,BALANCE INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS ACCOUNT;");
            onCreate(db);
    }

    public long InsertData(String acc_no,String name,String pin,String balance){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ACC_NO",acc_no);
        contentValues.put("NAME",name);
        contentValues.put("PIN",pin);
        contentValues.put("BALANCE",balance);
        long row=sqLiteDatabase.insert("ACCOUNT",null,contentValues);
        return row;
    }
    public String FindAccountDetails(String acc_no,String pin_no){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        boolean res=false;
        String details="";
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM ACCOUNT",null);
        if(cursor.getCount()==0){
            Toast.makeText(context,"No Data Found",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                String name=cursor.getString(1);
                String acc=cursor.getString(0);
                String pin=cursor.getString(2);
                int bal=cursor.getInt(3);
                if(acc.equals(acc_no) && pin.equals(pin_no) ){
                    res=true;
                    //Toast.makeText(context,"Account Holder Name : "+name,Toast.LENGTH_LONG).show();
                    details="Name : "+name+"\nAcc_no : "+acc+"\nBalance : "+bal;
                    break;
                }
            }
        }
        if(res){
            return  details;
        }else {
            return null;
        }
    }
    public void  UpdateData(String acc_no,String name,String pin,String balance){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        boolean res=false;

        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM ACCOUNT",null);
        if(cursor.getCount()==0){
            Toast.makeText(context,"No Data Found!",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                String acc=cursor.getString(0);
                if(acc.equals(acc_no)){
                    res=true;
                    contentValues.put("ACC_NO",acc_no);
                    contentValues.put("NAME",name);
                    contentValues.put("PIN",pin);
                    contentValues.put("BALANCE",balance);
                    sqLiteDatabase.update("ACCOUNT",contentValues,"ACC_NO = ?",new String[]{acc_no});
                    Toast.makeText(context,"Account Updated and acc number is "+acc_no,Toast.LENGTH_LONG).show();
                }
            }
        }
        if(res){
            Toast.makeText(context,"Account Updated and acc number is "+acc_no,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context,"No User found on this acc_no : "+acc_no,Toast.LENGTH_LONG).show();
        }
    }
    public void UpdateBalance(String acc_no,String add_bal){

        SQLiteDatabase db=this.getWritableDatabase();
        int bal=0;
        int adbal=Integer.parseInt(add_bal);
        String name="",acc="",pin="";
        Cursor cursor=db.rawQuery("SELECT * FROM ACCOUNT",null);
        if(cursor.getCount()==0){
            Toast.makeText(context,"No Data Found!",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                name=cursor.getString(1);
                acc=cursor.getString(0);
                pin=cursor.getString(2);
                 String balance=cursor.getString(3);
                 bal=Integer.parseInt(balance);
                if(acc.equals(acc_no)){
                    bal +=adbal;
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("ACC_NO",acc);
                    contentValues.put("NAME",name);
                    contentValues.put("PIN",pin);
                    contentValues.put("BALANCE",bal);
                    db.update("ACCOUNT",contentValues,"ACC_NO = ?",new String[]{acc_no});
                    Toast.makeText(context,"Balance added to acc "+acc+" "+add_bal+"TK",Toast.LENGTH_LONG).show();
                    break;
                }
                else {
                    Toast.makeText(context,"User Not Found!",Toast.LENGTH_LONG).show();
                }
            }
        }

    }
    public boolean SendBalance(String acc_no,String pin_no,String send_bal,String sender){
        boolean res=false;
        if(findReceiver(sender)){
            SQLiteDatabase db=this.getWritableDatabase();
            int bal=0;

            int sndbal=Integer.parseInt(send_bal);
            String name="",acc="",pin="";
            Cursor cursor=db.rawQuery("SELECT * FROM ACCOUNT",null);
            if(cursor.getCount()==0){
                Toast.makeText(context,"No Data Found!",Toast.LENGTH_LONG).show();
            }else {
                while (cursor.moveToNext()){
                    name=cursor.getString(1);
                    acc=cursor.getString(0);
                    pin=cursor.getString(2);
                    bal=cursor.getInt(3);
                    if(acc.equals(acc_no) && pin.equals(pin_no)){
                        if(bal>=sndbal){
                            bal -=sndbal;
                            res=true;
                            ContentValues contentValues=new ContentValues();
                            contentValues.put("ACC_NO",acc);
                            contentValues.put("NAME",name);
                            contentValues.put("PIN",pin);
                            contentValues.put("BALANCE",bal);
                            db.update("ACCOUNT",contentValues,"ACC_NO = ?",new String[]{acc_no});
                            Toast.makeText(context,"Balance send to acc no : "+sender+" to "+send_bal+"TK",Toast.LENGTH_LONG).show();
                            ReceivedBal(sender,send_bal);
                            break;
                        }else {
                            Toast.makeText(context,"Insufficient Balance!",Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }

        }else {
            Toast.makeText(context,"Receiver not found in Database!",Toast.LENGTH_LONG).show();
        }

        return res;
    }
    public void ReceivedBal(String acc_no,String add_bal){
        SQLiteDatabase db=this.getWritableDatabase();
        int bal=0;
        boolean res=false;
        int adbal=Integer.parseInt(add_bal);
        String name="",acc="",pin="";
        Cursor cursor=db.rawQuery("SELECT * FROM ACCOUNT",null);
        if(cursor.getCount()==0){
            Toast.makeText(context,"No Data Found!",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                name=cursor.getString(1);
                acc=cursor.getString(0);
                pin=cursor.getString(2);
                bal=cursor.getInt(3);
                if(acc.equals(acc_no)){
                    bal +=adbal;
                    res=true;
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("ACC_NO",acc);
                    contentValues.put("NAME",name);
                    contentValues.put("PIN",pin);
                    contentValues.put("BALANCE",bal);
                    db.update("ACCOUNT",contentValues,"ACC_NO = ?",new String[]{acc_no});
                    Toast.makeText(context,"Balance Received Acc_no : "+acc+"to Balance : "+add_bal+"TK",Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
        if(!res){
            Toast.makeText(context,"Balance Receive Failed!",Toast.LENGTH_LONG).show();
        }

    }
    public int DeleteAcc(String acc_no){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        int row=sqLiteDatabase.delete("ACCOUNT","ACC_NO = ?",new String[]{acc_no});
        return row;
    }
    public Cursor ShowallUser(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM ACCOUNT",null);
        return cursor;
    }
    public boolean findReceiver(String receiver){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        boolean res=false;
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM ACCOUNT",null);

        if(cursor.getCount()==0){
            Toast.makeText(context,"No Data Found",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                String name=cursor.getString(1);
                String acc=cursor.getString(0);
                if(acc.equals(receiver) ){
                    Toast.makeText(context,"Receiver Name : "+name,Toast.LENGTH_LONG).show();
                    res=true;
                    break;
                }
            }
        }

        return res;
    }

}
