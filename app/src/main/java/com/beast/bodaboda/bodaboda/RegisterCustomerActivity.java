package com.beast.bodaboda.bodaboda;

/**
 * Created by beast on 7/19/17.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.beast.bodaboda.bodaboda.component.AlertPopup;
import com.beast.bodaboda.bodaboda.component.Validator;
import com.beast.bodaboda.bodaboda.config.Config;
import com.beast.bodaboda.bodaboda.model.request.RegisterCustomerRequest;
import com.beast.bodaboda.bodaboda.model.response.RegisterCustomer;
import com.beast.bodaboda.bodaboda.networking.ApiClient;
import com.beast.bodaboda.bodaboda.networking.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class RegisterCustomerActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.editText_fullName)
    EditText editTextFullName;
    @BindView(R.id.input_fullName)
    TextInputLayout inputFullName;
    @BindView(R.id.editText_email)
    EditText editTextEmail;
    @BindView(R.id.input_email)
    TextInputLayout inputEmail;
    @BindView(R.id.editText_phone)
    EditText editTextPhone;
    @BindView(R.id.input_phone)
    TextInputLayout inputPhone;

    Config config;
    AlertPopup alertPopup;

    private String username;
    private String password;
    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registercustomer);
        ButterKnife.bind(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/aller/Aller_Rg.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        config = new Config();
        alertPopup = new AlertPopup();

        username = getIntent().getStringExtra(config.USERNAME);
        password = getIntent().getStringExtra(config.PASSWORD);
        userid = getIntent().getStringExtra(config.ID);

    }

    private void signUp() {
        Validator validator = new Validator();
        if (validator.validateInput(editTextFullName, inputFullName) && validator.validateInput(editTextEmail, inputEmail) && validator.validateInput(editTextPhone, inputPhone)) {
            sendUserDetails();

        }

    }

    public void sendUserDetails() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        RegisterCustomerRequest registerCustomerRequest = new RegisterCustomerRequest();
        registerCustomerRequest.setFullname(editTextFullName.getText().toString());
        registerCustomerRequest.setUsername(username);
        registerCustomerRequest.setPhone(username);
        registerCustomerRequest.setDeviceId(username);
        registerCustomerRequest.setUserId(userid);
        registerCustomerRequest.setPassword(password);
        Call<RegisterCustomer> call = apiService.postRegisterCustomer(registerCustomerRequest);
        call.enqueue(new Callback<RegisterCustomer>() {
            @Override
            public void onResponse(Call<RegisterCustomer> call, final Response<RegisterCustomer> response) {
                RegisterCustomer registerCustomer = response.body();
                if (response.code() == 200) {
                    //if success code
                    pDialog.setTitleText("Success!")
                            .setContentText("Registered Successfully")
                            .setConfirmClickListener(null)
                            .showCancelButton(false)
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .showCancelButton(false)
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                } else {
                    //if failed code
                    new AlertPopup().alertError(RegisterCustomerActivity.this, "Error", "USER FAILED TO REGISTER");

                }

            }

            @Override
            public void onFailure(Call<RegisterCustomer> call, Throwable t) {
                pDialog.dismiss();
                alertPopup.alertConnectError(RegisterCustomerActivity.this);
            }
        });


    }

    @OnClick(R.id.fab)
    public void onClick() {
        signUp();
    }
}
