package com.mezan.atmdemo3;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TransactionShow extends AppCompatActivity {

    private TransactionDatabase TDB;
    private ListView tdv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_show);
        tdv=(ListView) findViewById(R.id.transView);

        TDB=new TransactionDatabase(this);
        LoadTransaction();
    }
    public void LoadTransaction(){
        ArrayList<String> listTrans=new ArrayList<>();
        Cursor cursor = TDB.FindAccTransactionHistory();
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                String Sender=cursor.getString(1);
                String id=cursor.getString(0);
                String Receiver=cursor.getString(2);
                String Trans=cursor.getString(3);
                String DT=cursor.getString(4);

                    listTrans.add("ID: "+id+"\nSender: "+Sender+"\nReceiver: "+Receiver+"\nTransaction: "+Trans+"\nDate&Time: "+DT+"\n\n");
            }
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.sample_user_view,R.id.textid,listTrans);
        tdv.setAdapter(adapter);

        tdv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value=parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Selected",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
