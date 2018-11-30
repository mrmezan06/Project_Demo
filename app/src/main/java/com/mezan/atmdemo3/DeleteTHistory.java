package com.mezan.atmdemo3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteTHistory extends AppCompatActivity {

    private Button delT;
    private EditText transID;
    private TransactionDatabase td;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_thistory);
        transID=(EditText)findViewById(R.id.transid);
        delT=(Button) findViewById(R.id.delth);
        td=new TransactionDatabase(this);
        delT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=transID.getText().toString();
                int r=td.DeleteTranshistory(id);
                if(r>0){
                    Toast.makeText(getApplicationContext(),"Deleted Transaction History id : "+id,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Delete Transaction History Failed ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
