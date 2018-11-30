package com.mezan.atmdemo3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetAdminPassword extends AppCompatActivity implements View.OnClickListener{

    private EditText email,newpass,adminid;
    private Button Submitbtn,Setbtn;
    private boolean res=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_admin_password);
        email=(EditText) findViewById(R.id.mailtxt);
        newpass=(EditText) findViewById(R.id.newpasstxt);
        Submitbtn=(Button) findViewById(R.id.submit);
        Setbtn=(Button) findViewById(R.id.setbtn);
        adminid=(EditText) findViewById(R.id.adminidtxt);

        Submitbtn.setOnClickListener(this);
        Setbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String mail=email.getText().toString();
        String pass=newpass.getText().toString();
        String id=adminid.getText().toString();

        if(v.getId()==R.id.submit){
            if(id.equals("199801786995549") && mail.equals("mrmezan06@gmail.com")){
                    res=true;
                Toast.makeText(getApplicationContext(),"Admin ID and Email Confirmed!",Toast.LENGTH_LONG).show();
                }else {
                Toast.makeText(getApplicationContext(),"Wrong admin id or email",Toast.LENGTH_LONG).show();
            }
        } else if(v.getId()==R.id.setbtn){
            if(res){
                Intent it1=new Intent(ForgetAdminPassword.this,HomePage.class);
                it1.putExtra("key1",pass);
                setResult(1,it1);
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"Please confirm your admin id and email",Toast.LENGTH_LONG).show();
            }
        }


    }
}
