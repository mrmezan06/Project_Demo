package com.mezan.atmdemo3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShowBalance extends AppCompatActivity {

    ATMDatabase atmDatabase;
    private TransactionDatabase TD;
    public static String UserAcc="";
    private EditText accno,pinno,sender,sendbal;
    private Button showbal,sendbalancebtn,history;
    private TextView baltxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_balance);
        accno=(EditText) findViewById(R.id.showaccnotxt);
        pinno=(EditText) findViewById(R.id.showpinnotxt);
        sender=(EditText) findViewById(R.id.sendaccnotxt);
        sendbal=(EditText) findViewById(R.id.sendbaltxt);
        showbal=(Button) findViewById(R.id.showbalbtn);
        sendbalancebtn=(Button) findViewById(R.id.sendbalbtn);
        history=(Button) findViewById(R.id.historybtn);
        baltxt=(TextView) findViewById(R.id.showbaltxt);
        atmDatabase=new ATMDatabase(this);
        TD=new TransactionDatabase(this);
        showbal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String acc=accno.getText().toString();
                    //  int account=Integer.parseInt(acc);
                    String pin=pinno.getText().toString();
                    String details_user=atmDatabase.FindAccountDetails(acc,pin);
                    if(details_user != null){
                    baltxt.setText(details_user);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Wrong acc_no or pin_no",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Please insert acc_no and pin",Toast.LENGTH_LONG).show();
                }

            }
        });
        sendbalancebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String acc=accno.getText().toString();
                    String sendracc=sender.getText().toString();
                    String sendbl=sendbal.getText().toString();
                    String pin=pinno.getText().toString();
                    boolean r=atmDatabase.SendBalance(acc,pin,sendbl,sendracc);
                    if(r){
                        TD.InsertTransaction(acc,sendracc,sendbl);
                    }else {
                        Toast.makeText(getApplicationContext(),"Transaction Fails",Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Please insert acc_no,sender,pin and ammount",Toast.LENGTH_LONG).show();
                }

            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String acc=accno.getText().toString();
                    //  int account=Integer.parseInt(acc);
                    String pin=pinno.getText().toString();
                    String details_user=atmDatabase.FindAccountDetails(acc,pin);
                    if(details_user != null){
                        UserAcc=acc;
                        getUserAcc();
                        Intent intent=new Intent(ShowBalance.this,UserHistory.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Wrong acc_no or pin_no",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Please insert acc_no and pin",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public String getUserAcc(){
        return UserAcc;
    }
}
