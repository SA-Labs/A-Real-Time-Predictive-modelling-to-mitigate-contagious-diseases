package com.helpfightepidemic.helpfightepidemic;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private GoogleMap mMap;

    FloatingActionButton fab_plus,fab_infected,fab_cyberquarantine,fab_recovered;
    Animation FabOpen,FabClose,FabRClockwise,FabRAntiClockwise;
    boolean isOpen = false;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
      SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
               .findFragmentById(R.id.map);
         mapFragment.getMapAsync(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }

    @Override
    protected void onStart() {
        super.onStart();

        String method="result";
        BackgroundTask backgroundTask=new BackgroundTask(this);
       backgroundTask.execute(method);



    }







    public void Fab(View view)
    {

        fab_plus =(FloatingActionButton)findViewById(R.id.fab_plus);
        fab_infected=(FloatingActionButton)findViewById(R.id.fab_infected);
        fab_cyberquarantine=(FloatingActionButton)findViewById(R.id.fab_cyberquarantine);
        fab_recovered=(FloatingActionButton)findViewById(R.id.fab_recovered);
        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        FabRAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);

        if(isOpen)
        {
            fab_infected.startAnimation(FabClose);
            fab_cyberquarantine.startAnimation(FabClose);
            fab_plus.startAnimation(FabRAntiClockwise);
            fab_cyberquarantine.setClickable(false);
            fab_infected.setClickable(false);
            fab_recovered.startAnimation(FabClose);
            fab_recovered.setClickable(false);
            isOpen=false;

        }
        else {
            SharedPreferences getdata =getSharedPreferences("MyData", Context.MODE_PRIVATE);
            boolean infected =getdata.getBoolean("infected",false);

            fab_infected.startAnimation(FabOpen);
            fab_cyberquarantine.startAnimation(FabOpen);
            fab_plus.startAnimation(FabRClockwise);
            fab_cyberquarantine.setClickable(true);

            if(infected==true)
            {
                fab_recovered.setClickable(true);
                fab_infected.setClickable(false);
            }
            else
            {   fab_recovered.setClickable(false);
                fab_infected.setClickable(true);
            }

            fab_recovered.startAnimation(FabOpen);


            isOpen = true;

        }


    }
    public void Fab_infected(View view)
    {

   ///////////////////notify
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.abs);
        builder.setContentTitle("Warning");
        builder.setContentTitle("U R INFECTED :-[");
        Intent intent = new Intent(this,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(0,builder.build());

        startActivity( new Intent(this, Form.class));


    }

    public void Fab_cyberquarantine(View view)
    {

        ///////////////////notify
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.abs);
        builder.setContentTitle("Warning");
        builder.setContentTitle("you are cyber quarantined,please be safe.you can log on to helpfightepidemic.com for further updates.");
        Intent intent = new Intent(this,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(0,builder.build());

        SharedPreferences getdata =getSharedPreferences("MyData", Context.MODE_PRIVATE);
       String  zipcode =getdata.getString("zipcode","00000");
        String method="cyber_quarantine";
        BackgroundTask backgroundTask=new BackgroundTask(this);
        backgroundTask.execute(method,zipcode);


    }

    public void Fab_recovered(View view)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.abs);
        builder.setContentTitle("Warning");
        builder.setContentTitle("You are recovered");
        Intent intent = new Intent(this,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(0,builder.build());

        SharedPreferences getdata =getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String  id =getdata.getString("id","00000");
        SharedPreferences putdata =getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor edito =putdata.edit();
        edito.putBoolean("infected",false);
        edito.commit();

        String method="recovered";
        BackgroundTask backgroundTask=new BackgroundTask(this);
        backgroundTask.execute(method,id);






    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.hybrid) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            return true;
        }
        else if (id == R.id.satellite){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        }
        else if (id == R.id.Normal){
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        }
        else if (id == R.id.terrain){
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        }
        else if (id == R.id.none){
            mMap.setMapType(GoogleMap.MAP_TYPE_NONE);

        }




        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.maps) {

        }   else if (id == R.id.AboutUs) {
            startActivity(new Intent(this, AboutUs.class));
        }    else if (id == R.id.website) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://www.helpfightepidemic.com"));
            startActivity(intent);

        }    else if (id == R.id.test) {

            startActivity( new Intent(this, Test.class));

        } else if (id == R.id.facebook) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://www.facebook.com/killepidemicbeforeitkillsyou"));
            startActivity(intent);



        } else if (id == R.id.Instagram) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://www.instagram.com/kill_epidemic_before_it_kill_u/"));
            startActivity(intent);

        }
        else if (id == R.id.govern) {
            startActivity(new Intent(this, login_govt.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        int a = GoogleMap.MAP_TYPE_NORMAL;
        mMap.setMapType(a);
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
              //  buildGoogleApiClient();
             mMap.setMyLocationEnabled(true);
            }
        }
        else {
           // buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {



            mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Ur Probablity Of getting Infected Is 0.0000");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

         double lat = location.getLatitude();
         double lng =location.getLongitude();


        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat,lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

///////////zip///to//latlng////////





        SharedPreferences getdata =getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String new_zipcode =getdata.getString("zipcode","00000");
       // Toast.makeText(this,new_zipcode,Toast.LENGTH_LONG).show();
        Toast.makeText(this,postalCode,Toast.LENGTH_LONG).show();

        if(new_zipcode.equals(postalCode)  )
        {}
        else
        { if(postalCode.equals("00000")){
            Toast.makeText(this, "Unable To get Ur Location", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Location change", Toast.LENGTH_LONG).show();
            SharedPreferences getdata1 = getSharedPreferences("MyData", Context.MODE_PRIVATE);
            String id = getdata1.getString("id", "00000");
            SharedPreferences putdata = getSharedPreferences("MyData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = putdata.edit();
            editor.putString("zipcode", postalCode);
            editor.putBoolean("migrant", true);
            editor.commit();


            String method = "migrant";
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method, postalCode, id);
        }

        }











       // SharedPreferences getdata =getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String result =getdata.getString("result","00000");
        int len= result.length();
        int posi[]=new int[100000];

        int index=0;
        for(int x=0;x<len;x++) {
            if(result.charAt(x)=='-')
            {
                posi[index]=x;
                index++;

            }

        }
       // Toast.makeText(this,result.substring(posi[0]+1,posi[1]),Toast.LENGTH_LONG).show();
       int number=Integer.parseInt(result.substring(posi[0]+1,posi[1]));
        String zipcode[]=new String[number];
        String disease[]=new String[number];
        float tp_th_ratio[]=new float[100000];
        for (int x=0;x<number;x++)
        {
            zipcode[x]=result.substring(posi[1+(3*x)]+1,posi[2+(3*x)]);
            //Toast.makeText(this,zipcode[x],Toast.LENGTH_LONG).show();

            disease[x]=result.substring(posi[2+(3*x)]+1,posi[3+(3*x)]);
            //Toast.makeText(this,disease[x],Toast.LENGTH_LONG).show();

            tp_th_ratio[x]=Float.parseFloat(result.substring(posi[3+(3*x)]+1,posi[4+(3*x)]));

        }


      //LatLng latLng1[] = new LatLng[number];
       // MarkerOptions markerOptions1[] = new MarkerOptions[number];
        //Circle circle[]=new Circle[number];

        //move map camera
             for(int z=0;z<number;z++)
             {

        final Geocoder geocoder1 = new Geocoder(this);
        final String zip = zipcode[z];
                double lat1=0,lng1=0;
        try {
            List<Address> addresses1 = geocoder1.getFromLocationName(zip, 1);
            if (addresses != null && !addresses1.isEmpty()) {
                Address address1 = addresses1.get(0);
                // Use the address as needed
                String message = String.format("Latitude: %f, Longitude: %f",
                        address1.getLatitude(), address1.getLongitude());
                lat1=address1.getLatitude();
                lng1=address1.getLongitude();

                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            } else {
                // Display appropriate message when Geocoder services are not available
               // Toast.makeText(this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            // handle exception
        }


                 LatLng latLng2 = new LatLng(lat1,lng1);

                 MarkerOptions markerOptions2 = new MarkerOptions();
                 markerOptions2.position(latLng2);
                 String b= "2";

                 markerOptions2.title("Hrs="+b);
                 float sr = Float.valueOf(b);
                 markerOptions2.icon(BitmapDescriptorFactory.defaultMarker((5-sr)*60));
                 mCurrLocationMarker = mMap.addMarker(markerOptions2);
                  Circle circle = mMap.addCircle(new CircleOptions()
                         .center(latLng2)
                         .radius(2000*Integer.parseInt(b))
                         .fillColor(0x88ff0000).strokeColor(0x68ffffff));
                 circle = mMap.addCircle(new CircleOptions()
                         .center(latLng2)
                         .radius(4000*Integer.parseInt(b))
                         .fillColor(0x68ffff00).strokeColor(0x68ffffff));



/*
                 latLng1[z]=new LatLng(lat1,lng1);
                 markerOptions1[z] = new MarkerOptions();
                 markerOptions1[z].position(latLng1[z]);


        markerOptions1[z].title("Hrs="+Float.toString(tp_th_ratio[z]));
        markerOptions1[z].icon(BitmapDescriptorFactory.defaultMarker((5-tp_th_ratio[z])*60));
        mCurrLocationMarker = mMap.addMarker(markerOptions1[1]);
        circle[z] = mMap.addCircle(new CircleOptions()
                .center(latLng1[z])
                .radius(2000*(tp_th_ratio[z]))
                .fillColor(0x88ff0000).strokeColor(0x68ffffff));
        circle[z] = mMap.addCircle(new CircleOptions()
                .center(latLng1[1])
                .radius(4000*tp_th_ratio[z])
                .fillColor(0x68ffff00).strokeColor(0x68ffffff));
                */
             }
       /*
        /////////NOTIFICATION///////
        if(sy>0)
        {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.drawable.abs);
            builder.setContentTitle("Warning");
            builder.setContentTitle("Infection Around U- Pls Take Precaution...");
            Intent intent = new Intent(this,MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NM.notify(0,builder.build());

        }



        LatLng latLng2 = new LatLng(21.209509,81.543187);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(latLng2);
        String b= Test.Pahand1;

        markerOptions2.title("Hrs="+b);
        float sr = Float.valueOf(b);
        markerOptions2.icon(BitmapDescriptorFactory.defaultMarker((5-sr)*60));
        mCurrLocationMarker = mMap.addMarker(markerOptions2);
        circle = mMap.addCircle(new CircleOptions()
                .center(latLng2)
                .radius(2000*Integer.parseInt(b))
                .fillColor(0x88ff0000).strokeColor(0x68ffffff));
        circle = mMap.addCircle(new CircleOptions()
                .center(latLng2)
                .radius(4000*Integer.parseInt(b))
                .fillColor(0x68ffff00).strokeColor(0x68ffffff));

        LatLng latLng3 = new LatLng(21.283826,81.603032);

        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(latLng3);
        String c= Test.Sondongari1;
        markerOptions3.title("Hrs="+c);
        float st = Float.valueOf(c);
        markerOptions3.icon(BitmapDescriptorFactory.defaultMarker((5-st)*60));
        mCurrLocationMarker = mMap.addMarker(markerOptions3);
        circle = mMap.addCircle(new CircleOptions()
                .center(latLng3)
                .radius(2000*Integer.parseInt(c))
                .fillColor(0x88ff0000).strokeColor(0x68ffffff));
        circle = mMap.addCircle(new CircleOptions()
                .center(latLng3)
                .radius(4000*Integer.parseInt(c))
                .fillColor(0x68ffff00).strokeColor(0x68ffffff));

        LatLng latLng4 = new LatLng(21.213248,81.632962);

        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.position(latLng4);
        String d= Test.mathpurena1;
        markerOptions4.title("Hrs="+d);
        float su= Float.valueOf(d);
        markerOptions4.icon(BitmapDescriptorFactory.defaultMarker((5-su)*60));
        mCurrLocationMarker = mMap.addMarker(markerOptions4);
        circle = mMap.addCircle(new CircleOptions()
                .center(latLng4)
                .radius(2000*Integer.parseInt(d))
                .fillColor(0x88ff0000).strokeColor(0x68ffffff));
        circle = mMap.addCircle(new CircleOptions()
                .center(latLng4)
                .radius(4000*Integer.parseInt(d))
                .fillColor(0x68ffff00).strokeColor(0x68ffffff));


        LatLng latLng5 = new LatLng(21.252017,81.624516);

        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5.position(latLng5);
        String e= Test.kotard1;
        markerOptions5.title("Hrs="+e);
        float sq = Float.valueOf(e);
        markerOptions5.icon(BitmapDescriptorFactory.defaultMarker((5-sq)*60));
        mCurrLocationMarker = mMap.addMarker(markerOptions5);
        circle = mMap.addCircle(new CircleOptions()
                .center(latLng5)
                .radius(2000*Integer.parseInt(e))
                .fillColor(0x88ff0000).strokeColor(0x68ffffff));
        circle = mMap.addCircle(new CircleOptions()
                .center(latLng5)
                .radius(4000*Integer.parseInt(e))
                .fillColor(0x68ffff00).strokeColor(0x68ffffff));


        LatLng latLng6 = new LatLng(21.202479,81.699368);

        MarkerOptions markerOptions6 = new MarkerOptions();
        markerOptions6.position(latLng6);
        String f= Test.dhumartarai1;
        markerOptions6.title("Hrs="+f);
        float se = Float.valueOf(f);
        markerOptions6.icon(BitmapDescriptorFactory.defaultMarker((5-se)*60));
        mCurrLocationMarker = mMap.addMarker(markerOptions6);

        circle = mMap.addCircle(new CircleOptions()
                .center(latLng6)
                .radius(2000*Integer.parseInt(f))
                .fillColor(0x88ff0000).strokeColor(0x68ffffff));
        circle = mMap.addCircle(new CircleOptions()
                .center(latLng6)
                .radius(4000*Integer.parseInt(f))
                .fillColor(0x68ffff00).strokeColor(0x68ffffff));



*/


        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


}


