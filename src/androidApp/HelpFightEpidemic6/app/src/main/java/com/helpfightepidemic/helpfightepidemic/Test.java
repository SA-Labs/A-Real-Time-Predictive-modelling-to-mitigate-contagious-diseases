package com.helpfightepidemic.helpfightepidemic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Test extends AppCompatActivity implements View.OnClickListener {
    Button done;
    EditText Mowa,Pahand,Sondongari,kotard,dhumartarai,mathpurena;
    public static String Mowa1="0",Pahand1="0",Sondongari1="0",kotard1="0",dhumartarai1="0",mathpurena1="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(this);
        Mowa =(EditText) findViewById(R.id.Samriddhi);
        Pahand =(EditText) findViewById(R.id.Shresth);
        Sondongari =(EditText) findViewById(R.id.Sammy);
        kotard =(EditText) findViewById(R.id.Sammy1);
        dhumartarai =(EditText) findViewById(R.id.sammy2);
        mathpurena =(EditText) findViewById(R.id.sammy3);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.done:
                Mowa1 =Mowa.getText().toString();
                Pahand1 =Pahand.getText().toString();
                Sondongari1=Sondongari.getText().toString();
                kotard1 =kotard.getText().toString();
                dhumartarai1 =dhumartarai.getText().toString();
                mathpurena1 =mathpurena.getText().toString();


                startActivity(new Intent(this, MainActivity.class));



                break;


        }
    }
}