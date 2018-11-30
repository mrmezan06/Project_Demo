package com.mezan.atmdemo3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

public class Startup extends AppCompatActivity {

    private ProgressBar pr;
    int p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_startup);

        pr=(ProgressBar) findViewById(R.id.prgrss);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                Progreess();
                startApp();
            }
        });
        thread.start();
    }
    public void Progreess(){
        for(p=20;p<=100;p+=20){
            try {
                Thread.sleep(1000);
                pr.setProgress(p);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void startApp(){

        Intent intent=new Intent(Startup.this,HomePage.class);
        startActivity(intent);
        finish(); //no back
    }
}
