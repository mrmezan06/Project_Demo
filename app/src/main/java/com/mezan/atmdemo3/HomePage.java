package com.mezan.atmdemo3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomePage extends AppCompatActivity implements View.OnClickListener{

    private EditText adminemail,adminpass;
    private Button adminlog,userlog;
    private String passdflt="224466",email="mrmezan06@gmail.com";
    private AlertDialog.Builder adb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        load();

        adminemail=(EditText) findViewById(R.id.adminemailtxt);
        adminpass=(EditText) findViewById(R.id.adminpasstxt);
        adminlog=(Button) findViewById(R.id.adminlogbtn);
        userlog=(Button) findViewById(R.id.userlogbtn);
        adminlog.setOnClickListener(this);
        userlog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.userlogbtn){
            Intent intent=new Intent(HomePage.this,ShowBalance.class);
            startActivity(intent);
        }else{
            String user=adminemail.getText().toString();
            String pass=adminpass.getText().toString();
            if(user.equals(email) && pass.equals(passdflt)){
                adminemail.setText("");
                adminpass.setText("");
                Intent intent=new Intent(HomePage.this,MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(),"Wrong admin Email or Password",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menulayout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.About){
            //go to about page
            Intent intent=new Intent(HomePage.this,About.class);
            startActivity(intent);

        }else if(item.getItemId()==R.id.shareid){
            //sharing option
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String sub="My New Designed Application";
            String body="How it is look like?";
            intent.putExtra(Intent.EXTRA_SUBJECT,sub);
            intent.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(intent,"Share with "));
        }else if(item.getItemId()==R.id.fgpassadmin){

            //got to forget password page
                Intent intent1=new Intent(HomePage.this,ForgetAdminPassword.class);
                startActivityForResult(intent1,1);
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            passdflt=data.getStringExtra("key1");
            save();
            Toast.makeText(getApplicationContext(),"User Email is mrmezan06@gmail.com and password is "+passdflt,Toast.LENGTH_LONG).show();
            //adminpass.setText(passdflt);
        }
    }

    @Override
    public void onBackPressed() {
        adb = new AlertDialog.Builder(HomePage.this);
        adb.setIcon(R.drawable.question);
        adb.setTitle("Exit");
        adb.setCancelable(false);
        adb.setMessage("Do you want to exit?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog ad=adb.create();
        ad.show();
    }

    public void save(){
        SharedPreferences sharedPreferences=getSharedPreferences("User Details",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit(); //data writing in sharedpreferrence
        editor.putString("ukey",email);
        editor.putString("pkey",passdflt);
        editor.commit();
    }
    public void load(){
        SharedPreferences sharedPreferences=getSharedPreferences("User Details",Context.MODE_PRIVATE);
        if(sharedPreferences.contains("ukey")&&sharedPreferences.contains("pkey")){
            String emails=sharedPreferences.getString("ukey","mrmezan06@gmail.com");
            String password=sharedPreferences.getString("pkey","224466");
            if(password.equals("224466")){
                Toast.makeText(getApplicationContext(),"Last password is saving fail",Toast.LENGTH_LONG).show();
            }else {
                passdflt=password;
            }

        }
    }
}
