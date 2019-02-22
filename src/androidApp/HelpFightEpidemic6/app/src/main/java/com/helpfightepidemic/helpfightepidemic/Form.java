package com.helpfightepidemic.helpfightepidemic;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.util.Locale;
import java.util.concurrent.Delayed;

import static android.R.attr.delay;

public class Form extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button submit;
    EditText date;
    Spinner NameOfDisease;


    String ID= null;
    String Id1,zipcode1,disease1,date1;
    String advertId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        NameOfDisease = (Spinner) findViewById(R.id.NameOfDisease);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.NameOfDisease, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        NameOfDisease.setAdapter(adapter);
        NameOfDisease.setOnItemSelectedListener(this);

        submit =(Button) findViewById(R.id.Submit);
        submit.setOnClickListener(this);
        date =(EditText)findViewById(R.id.date);


    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Submit:







                AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        AdvertisingIdClient.Info idInfo = null;
                        try {
                            idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try{
                            advertId = idInfo.getId();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        return advertId;


                    }
                    @Override
                    protected void onPostExecute(String advertId) {

                        ID=advertId;
                        Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();


                    }
                };
                task.execute();


                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                builder.setSmallIcon(R.drawable.abs);
                builder.setContentTitle("warning");
                builder.setContentTitle("form submitted ");
                Intent intent = new Intent(this,MainActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(intent);
                PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NM.notify(0,builder.build());

                SharedPreferences getdata =getSharedPreferences("MyData", Context.MODE_PRIVATE);
                 zipcode1 =getdata.getString("zipcode","00000");



                date1=date.getText().toString();
                String method="register";
                BackgroundTask backgroundTask=new BackgroundTask(this);
                backgroundTask.execute(method,zipcode1,date1,disease1);


                //finish();





                startActivity(new Intent(this, MainActivity.class));

                break;


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String item = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        disease1=item;



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

