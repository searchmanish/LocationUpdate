package com.softcodeinfotech.locationupdate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softcodeinfotech.locationupdate.util.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    TextView longi, lati, dateTime, imei;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderApi locationProviderApi = LocationServices.FusedLocationApi;

    private LocationRequest locationRequest;
    private Double myLongi;
    private Double myLati;
    Button attandance, exitTime;
    //
    Retrofit retrofit;
    ServiceInterface serviceInterface;
    private String IMEI, IMSI;
    String currentDateandTime;

    //Employee textView details
    private TextView employeeName;
    private TextView employeeId;
    private TextView employeeEmail;
    private TextView employeePassword;


    //Employee Details
    private String employee_name;
    private String employee_id;
    private String employee_email;
    private String employee_password;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        longi = findViewById(R.id.e_longi);
        lati = findViewById(R.id.e_lati);
        dateTime = findViewById(R.id.e_currentTime);
        //  imei = findViewById(R.id.imei);
        attandance = findViewById(R.id.button2);
        exitTime = findViewById(R.id.button3);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        setUpWidget();


        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000 * 10);
        locationRequest.setFastestInterval(1000 * 5);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //getting employee details from shared Preferences
        employee_email = SharePreferenceUtils.getInstance().getString(Constant.EMPLOYEE_email);
        employee_name = SharePreferenceUtils.getInstance().getString(Constant.EMPLOYEE_name);
        employee_id = SharePreferenceUtils.getInstance().getString(Constant.EMPLOYEE_id);

        employeeName.setText(employee_name);
        employeeId.setText("SOFT00"+employee_id+"IN");

        // Toast.makeText(this, ""+employee_id+" "+employee_name+" "+employee_id, Toast.LENGTH_SHORT).show();

        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceInterface = retrofit.create(ServiceInterface.class);

        attandance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharePreferenceUtils.getInstance().getString(Constant.EMPLOYEE_entrytime) == "true") {
                    Toast.makeText(MainActivity.this, "Already Attandance Recorded Previously", Toast.LENGTH_SHORT).show();
                } else {
                    currentDateandTime = "entry time@" + currentDateandTime;
                    SharePreferenceUtils.getInstance().saveString(Constant.EMPLOYEE_entrytime, "true");
                    attandance.setEnabled(false);
                    saveDataReq();
                }
            }
        });
        exitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharePreferenceUtils.getInstance().getString(Constant.EMPLOYEE_exittime) == "true") {
                    Toast.makeText(MainActivity.this, "Already Exit Time Recorded", Toast.LENGTH_SHORT).show();
                } else {
                    currentDateandTime = "exit  time@" + currentDateandTime;
                    SharePreferenceUtils.getInstance().saveString(Constant.EMPLOYEE_exittime, "true");
                    exitTime.setEnabled(false);
                    saveDataReq();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SharePreferenceUtils.getInstance().deletePref();
                            MainActivity.this.finish();
                            System.exit(0);
                        }
                    }, 3000);
                   /* try {
                        wait(3000);
                        MainActivity.this.finish();
                        System.exit(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }

            }
        });

    }

    private void setUpWidget() {
        employeeName = findViewById(R.id.e_name);
        employeeId = findViewById(R.id.e_id);
        // employeeEmail = findViewById(R.id.e);
        //employeePassword = findViewById(R.id.e_);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onLocationChanged(Location location) {
        myLati = location.getLatitude();
        myLongi = location.getLongitude();

        lati.setText(String.valueOf(myLati));
        longi.setText(String.valueOf(myLongi));

        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        //String currentDateandTime = sdf.format(new Date());
        currentDateandTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        dateTime.setText(currentDateandTime);

        //device information
        // Code For IMEI AND IMSI NUMBER

        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager m_telephonyManager = (TelephonyManager) getSystemService(serviceName);
        //  String IMEI, IMSI;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        IMEI = m_telephonyManager.getDeviceId();
        IMSI = m_telephonyManager.getSubscriberId();
        //imei.setText("Device info=" + IMEI);

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
        if (googleApiClient.isConnected()) {
            requestLocationUpdates();
        }
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            requestLocationUpdates();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googleApiClient.disconnect();
    }

    private void saveDataReq() {
        Call<LocationResponse> call = serviceInterface.saveAttandance(convertPlainString(employee_name)
                , convertPlainString(IMEI), convertPlainString(myLati.toString()), convertPlainString(myLongi.toString()),
                convertPlainString(currentDateandTime.toString()));
        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(MainActivity.this, "" + myLati, Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {

                Log.e("error", t.toString());

            }
        });

    }

    // convert aa param into plain text
    public RequestBody convertPlainString(String data) {
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), data);
        return plainString;
    }

}
