package com.softcodeinfotech.locationupdate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softcodeinfotech.locationupdate.util.Constant;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SigninActivity extends AppCompatActivity {
    TextInputEditText email, password;
    Button signin;
    String mEmail, mPassword;

    Retrofit retrofit;
    ServiceInterface serviceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);

        getData();
        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceInterface = retrofit.create(ServiceInterface.class);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                signinReq();
            }
        });


    }

    private void signinReq() {
        Call<SigninResponse> call = serviceInterface.siginReq(convertPlainString(mEmail), convertPlainString(mPassword));
        call.enqueue(new Callback<SigninResponse>() {
            @Override
            public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                // Toast.makeText(SigninActivity.this, "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                if (response.body().getStatus() == 1) {
                    SharePreferenceUtils.getInstance().saveString(Constant.EMPLOYEE_email, response.body().getInformation().getEmployeeEmail());
                    SharePreferenceUtils.getInstance().saveString(Constant.EMPLOYEE_id, String.valueOf(response.body().getInformation().getEmployeeId()));
                    SharePreferenceUtils.getInstance().saveString(Constant.EMPLOYEE_name, response.body().getInformation().getEmployeeName());
                    SharePreferenceUtils.getInstance().saveString(Constant.EMPLOYEE_password, response.body().getInformation().getEmployeePassword());

                    Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(SigninActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SigninResponse> call, Throwable t) {

            }
        });
    }

    private void getData() {
        mEmail = email.getText().toString().trim();
        mPassword = password.getText().toString().trim();

    }

    // convert aa param into plain text
    public RequestBody convertPlainString(String data) {
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), data);
        return plainString;
    }
}
