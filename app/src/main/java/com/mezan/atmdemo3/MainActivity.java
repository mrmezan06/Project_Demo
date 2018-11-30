package com.mezan.atmdemo3;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ATMDatabase atmDatabase;
    private TransactionDatabase td;

    private EditText acc,name,pin,balance;
    private Button create,updata,upbalance,delacc,show,TH,delTh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acc=(EditText) findViewById(R.id.accnotxt);
        name=(EditText) findViewById(R.id.nametxt);
        pin=(EditText) findViewById(R.id.pintxt);
        balance=(EditText) findViewById(R.id.baltxt);
        create=(Button) findViewById(R.id.createbtn);
        updata=(Button) findViewById(R.id.updtdatabtn);
        upbalance=(Button) findViewById(R.id.updtbalancebtn);
        delacc=(Button) findViewById(R.id.deleteaccbtn);
        show=(Button) findViewById(R.id.showbtn);
        TH=(Button) findViewById(R.id.transactionhistory);
        delTh=(Button) findViewById(R.id.delthistory);


        atmDatabase=new ATMDatabase(this);
        td=new TransactionDatabase(this);
        create.setOnClickListener(this);
        updata.setOnClickListener(this);
        upbalance.setOnClickListener(this);
        delacc.setOnClickListener(this);
        show.setOnClickListener(this);
        TH.setOnClickListener(this);
        delTh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.createbtn){
            //database created
            try {
                String accnt = acc.getText().toString();
                String nam = name.getText().toString();
                String pinn = pin.getText().toString();
                String bal = balance.getText().toString();
                if (accnt.equals("") || nam.equals("") || pinn.equals("") || bal.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please input all above info", Toast.LENGTH_LONG).show();
                } else {
                    long row = atmDatabase.InsertData(accnt, nam, pinn, bal);
                    if (row > 0) {
                        Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Data Insertion Fail", Toast.LENGTH_LONG).show();
                    }
                }
            }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Please input all above info",Toast.LENGTH_LONG).show();
                }

            acc.setText("");
            name.setText("");
            pin.setText("");
            balance.setText("");
        }else if(v.getId()==R.id.showbtn){
            //show all user
          //  Cursor res=atmDatabase.ShowallUser();

           /* if(res.getCount()==0){
                showMessage("Error","No User Found!");
                return;
            }else {
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Acc_no :"+res.getString(0)+"\n");
                    buffer.append("Name :"+res.getString(1)+"\n");
                    buffer.append("PIN :"+res.getString(2)+"\n");
                    buffer.append("Current Balance :"+res.getString(3)+"\n\n");
                }
                showMessage("Data",buffer.toString());
            }*/
            Intent intent=new Intent(MainActivity.this,LoadAll.class);
            startActivity(intent);
        }else if (v.getId()==R.id.updtbalancebtn){
            try{
                String accnt=acc.getText().toString();
                String nam=name.getText().toString();
                String pinn=pin.getText().toString();
                String bal=balance.getText().toString();
                if (accnt.equals("") || bal.equals("")){
                    Toast.makeText(getApplicationContext(),"Please insert acc_no & balance",Toast.LENGTH_LONG).show();
                }else{
                    atmDatabase.UpdateBalance(accnt,bal);
                    td.InsertTransaction("111222333444555",accnt,bal);
                }

            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Please insert acc_no & balance",Toast.LENGTH_LONG).show();
            }
            acc.setText("");
            name.setText("");
            pin.setText("");
            balance.setText("");

        }
        else if(v.getId()==R.id.deleteaccbtn){
            //acc deletion
            try{
                String accnt=acc.getText().toString();
                String nam=name.getText().toString();
                String pinn=pin.getText().toString();
                String bal=balance.getText().toString();
                if(accnt.equals("")){
                    Toast.makeText(getApplicationContext(),"Please input acc_no",Toast.LENGTH_LONG).show();
                }else {
                    int row=atmDatabase.DeleteAcc(accnt);
                    if(row>0)
                        Toast.makeText(getApplicationContext(),"Deleted acc_no "+accnt,Toast.LENGTH_LONG).show();
                    else {
                        Toast.makeText(getApplicationContext(),"Deletion failed at acc_no "+accnt,Toast.LENGTH_LONG).show();
                    }
                }

            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Please input acc_no",Toast.LENGTH_LONG).show();
            }
            acc.setText("");
            name.setText("");
            pin.setText("");
            balance.setText("");

        }
        else if(v.getId()==R.id.updtdatabtn){
            //user data update
            try{
                String accnt=acc.getText().toString();
                String nam=name.getText().toString();
                String pinn=pin.getText().toString();
                String bal=balance.getText().toString();
                if (accnt.equals("") || nam.equals("") || pinn.equals("") || bal.equals("")){
                    Toast.makeText(getApplicationContext(),"Please insert all info above text box",Toast.LENGTH_LONG).show();
                }else {
                    atmDatabase.UpdateData(accnt,nam,pinn,bal);
                   // Toast.makeText(getApplicationContext(),"Account Updated and acc number is "+accnt,Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Please insert all info above text box",Toast.LENGTH_LONG).show();
            }
            acc.setText("");
            name.setText("");
            pin.setText("");
            balance.setText("");

        }
        else if(v.getId()==R.id.transactionhistory){
            Intent intent=new Intent(MainActivity.this,TransactionShow.class);
            startActivity(intent);
        }else if(v.getId()==R.id.delthistory){
            Intent intent=new Intent(MainActivity.this,DeleteTHistory.class);
            startActivity(intent);
        }
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
