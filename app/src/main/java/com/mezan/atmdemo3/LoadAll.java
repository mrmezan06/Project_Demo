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

public class LoadAll extends AppCompatActivity {

    private ATMDatabase database;
    private ListView listView;
    private int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_all);
        listView=(ListView) findViewById(R.id.listviewid);

        database=new ATMDatabase(this);
        loadData();

    }

    public void loadData(){
        ArrayList<String> lisdata=new ArrayList<>();
        Cursor cursor = database.ShowallUser();
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(),"No Data found in the database ",Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                lisdata.add("Acc_no: "+cursor.getString(0)+"\nName: "+cursor.getString(1)+"\nPin_no: "+cursor.getString(2)+"\nBalance: "+cursor.getString(3)+"\n\n");
            }
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.sample_user_view,R.id.textid,lisdata);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value=parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item is Selected",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
