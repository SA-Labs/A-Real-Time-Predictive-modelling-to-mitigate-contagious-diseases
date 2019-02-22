package com.helpfightepidemic.helpfightepidemic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login_govt extends AppCompatActivity  implements View.OnClickListener {
    Button login1;
    EditText govt_id,password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_login_govt);
        login1=(Button)findViewById(R.id.login1);
        login1.setOnClickListener(this);
         govt_id=(EditText)findViewById(R.id.user);
        password=(EditText)findViewById(R.id.pass);

    }

    @Override
    public void onClick(View view) {
        //
        switch (view.getId()) {
            case R.id.login1:
             String id=govt_id.getText().toString();
                String pass=password.getText().toString();
               // Toast.makeText(this,id,Toast.LENGTH_LONG).show();
               //if (pass=="toor")
                { //   Toast.makeText(this,id,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, GovernmentCorner.class));


                }

                break;
        }




    }

}
