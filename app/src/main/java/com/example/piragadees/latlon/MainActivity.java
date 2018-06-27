package com.example.piragadees.latlon;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.telephony.TelephonyManager.*;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private TextView textView;
    private LocationManager locationManager;
    private Button btn;
    private EditText editText1;
    private EditText editText11;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.id_textView);
        btn =(Button)findViewById(R.id.okButton);
        editText1= (EditText)findViewById(R.id.editText);
        editText11= (EditText)findViewById(R.id.editText2);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        int Permission_All = 1;

        String[] Permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.SEND_SMS };
        if(!hasPermissions(this, Permissions)){
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
    }

    private void  checkAndRequestPermissions() {


    }

    @Override
    public void onLocationChanged(Location location) {
        Context context = getApplicationContext();
        final double longitude = location.getLongitude();
        final double latitude = location.getLatitude();
        final double accuracy = location.getAccuracy();
        final String problem      =  editText1.getText().toString();
        final String name      =  editText11.getText().toString();

        final String phoneNumber = "+919445780112";
        textView.setText("LONGITUDE:"+longitude+"\n"+"LATITUDE:"+latitude+"\n"+"ACCURACY:"+accuracy);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phoneNumber));
                sendIntent.putExtra("sms_body", "EMGERGENCY"+"\n"+"\n"+"Long:"+Double.toString(longitude)
                        +"\n"+"Lat:"+Double.toString(latitude)+"\n" +"Accuracy:"+ Double.toString(accuracy)+
                        "meters" +"\n"+"Name:"+editText11.getText().toString()+"\n"+"Problem:"+editText1.getText().toString());
                startActivity(sendIntent);
                //https://www.google.com/maps/?q=-latitude,longitude for google map link

            }
        });

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    public static boolean hasPermissions(Context context, String... permissions){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && context!=null && permissions!=null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!=PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }


}
