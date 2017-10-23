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
import android.widget.Button;
import android.widget.EditText;

import com.beast.bodaboda.bodaboda.component.AlertPopup;
import com.beast.bodaboda.bodaboda.component.Validator;
import com.beast.bodaboda.bodaboda.config.Config;
import com.beast.bodaboda.bodaboda.model.request.RegisterRequest;
import com.beast.bodaboda.bodaboda.model.response.Register;
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

public class RegisterActivity extends AppCompatActivity {


    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.editText_phone)
    EditText editTextPhone;
    @BindView(R.id.input_phone)
    TextInputLayout inputPhone;
    @BindView(R.id.editText_password)
    EditText editTextPassword;
    @BindView(R.id.input_password)
    TextInputLayout inputPassword;
    @BindView(R.id.editText_confirmpassword)
    EditText editTextConfirmpassword;
    @BindView(R.id.input_confirmpassword)
    TextInputLayout inputConfirmpassword;
    @BindView(R.id.button_login)
    Button buttonLogin;

    Config config;
    AlertPopup alertPopup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/aller/Aller_Rg.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        config = new Config();
        alertPopup = new AlertPopup();


    }

    private void signUp() {
        Validator validator = new Validator();
        if (validator.validateInput(editTextPhone, inputPhone) && validator.validateInput(editTextPassword, inputPassword)) {
            if (editTextPassword.getText().toString().equals(editTextConfirmpassword.getText().toString())) {
                sendUserDetails();
            } else {
                inputPassword.setError("Password does not match");
            }
        }

    }

    public void sendUserDetails() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(editTextPhone.getText().toString());
        registerRequest.setDeviceId(editTextPhone.getText().toString());
        registerRequest.setPassword(editTextConfirmpassword.getText().toString());
        Call<Register> call = apiService.postRegister(registerRequest);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, final Response<Register> response) {
                final Register register = response.body();
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
                                    Intent intent = new Intent(getApplicationContext(), RegisterCustomerActivity.class);
                                    intent.putExtra(config.PASSWORD, editTextPassword.getText().toString());
                                    intent.putExtra(config.USERNAME, register.getData().getUsername());
                                    intent.putExtra(config.ID, register.getData().getId());
                                    startActivity(intent);
                                }
                            })
                            .showCancelButton(false)
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                } else {
                    //if failed code
                    new AlertPopup().alertError(RegisterActivity.this, "Error", "Phone Already exists");

                }

            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                pDialog.dismiss();
                alertPopup.alertConnectError(RegisterActivity.this);
            }
        });


    }

    @OnClick(R.id.fab)
    public void onClick() {
        signUp();
    }

    @OnClick(R.id.button_login)
    public void onViewClicked() {
        finish();
    }
}
