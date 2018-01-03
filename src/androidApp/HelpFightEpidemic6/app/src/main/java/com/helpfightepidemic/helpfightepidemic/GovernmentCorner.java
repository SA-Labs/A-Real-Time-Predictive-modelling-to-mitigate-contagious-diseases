package com.helpfightepidemic.helpfightepidemic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.button;

public class GovernmentCorner extends AppCompatActivity {

    EditText medication_rate,vaccination_rate,isolation,zipcode,disease,date;
    String med,vac,iso,zip,dis,dat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_corner);



        medication_rate=(EditText) findViewById(R.id.mr);
        vaccination_rate=(EditText) findViewById(R.id.vr);
        isolation=(EditText) findViewById(R.id.ip);
        zipcode=(EditText) findViewById(R.id.zipcode1);
        disease=(EditText) findViewById(R.id.disease1);
        date=(EditText) findViewById(R.id.date1);


    }
    public void go1(View view)
    {
         med=medication_rate.getText().toString();
         vac=vaccination_rate.getText().toString();
         iso=isolation.getText().toString();
         zip=zipcode.getText().toString();
         dis=disease.getText().toString();
         dat=date.getText().toString();
      //  Toast.makeText(this,med,Toast.LENGTH_LONG).show();


       String method="govt_corner";
        BackgroundTask backgroundTask=new BackgroundTask(this);
        backgroundTask.execute(method,med,vac,iso,zip,dis,dat);

    }


}
