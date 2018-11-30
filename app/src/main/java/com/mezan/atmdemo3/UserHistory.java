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

public class UserHistory extends AppCompatActivity {

    private ListView userTransHistory;
    private ShowBalance sb;
    String User;
    private TransactionDatabase TDU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);
        userTransHistory=(ListView) findViewById(R.id.usertransView);
        TDU=new TransactionDatabase(this);
        sb=new ShowBalance();
        User=sb.getUserAcc();
        UserLoadTransaction();

    }
    public void UserLoadTransaction(){
        ArrayList<String> listTrans=new ArrayList<>();
        int id=0;
        Cursor cursor = TDU.FindAccTransactionHistory();
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()) {
                String Sender = cursor.getString(1);
                // String id=cursor.getString(0);
                String Receiver = cursor.getString(2);
                String Trans = cursor.getString(3);
                String DT = cursor.getString(4);
                if (User.equals(Sender)) {

                    listTrans.add("ID: " + id + "\nSender: " + Sender + "\nReceiver: " + Receiver + "\nTransaction: " + Trans + "\nDate&Time: " + DT + "\n\n");
                    id++;
                }
            }
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.sample_user_view,R.id.textid,listTrans);
        userTransHistory.setAdapter(adapter);

        userTransHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value=parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Selected",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
