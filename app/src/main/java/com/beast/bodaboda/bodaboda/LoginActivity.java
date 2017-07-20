package com.beast.bodaboda.bodaboda;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.beast.bodaboda.bodaboda.component.AlertPopup;
import com.beast.bodaboda.bodaboda.component.SessionManager;
import com.beast.bodaboda.bodaboda.model.request.LoginRequest;
import com.beast.bodaboda.bodaboda.model.response.Login;
import com.beast.bodaboda.bodaboda.networking.ApiClient;
import com.beast.bodaboda.bodaboda.networking.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import fr.arnaudguyon.perm.Perm;
import fr.arnaudguyon.perm.PermResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by beast on 7/19/17.
 */

public class LoginActivity extends AppCompatActivity {

    private static final int PERMISSION_INTERNET_REQUEST = 1;
    @BindView(R.id.button_register)
    Button buttonRegister;
    private Perm internetPermission;
    private EditText inputPhone, inputPassword;
    private TextInputLayout inputLayoutPhone, inputLayoutPassword;
    private Button btnLogin;
    private AlertPopup alertPopup;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/aller/Aller_Rg.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        internetPermission = new Perm(this, Manifest.permission.INTERNET);
        setupPermissions();


        //Check is login is saved in preferences
        if (sessionManager.isLoggedIn()) {
         Intent splash = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(splash);
        } else {
            //  Snackbar.make(getCurrentFocus(), "Sign in", Snackbar.LENGTH_SHORT).show();

            //Setup Views
            inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);
            inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
            inputPhone = (EditText) findViewById(R.id.input_phone);
            inputPassword = (EditText) findViewById(R.id.input_password);
            btnLogin = (Button) findViewById(R.id.btn_login);
            alertPopup = new AlertPopup();

            //ChangeListener detects changes in text
            inputPhone.addTextChangedListener(new MyTextWatcher(inputPhone));
            inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

            //button login listener
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submitForm();
                }
            });
        }

    }

    //This is for the font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
        //Check phone and password not to be empty
        if (!inputPhone.getText().toString().trim().isEmpty() && !inputPassword.getText().toString().trim().isEmpty()) {

            sendLogin();

        }

    }

    //Prompt login error Dialog
    private void alertLoginError() {
        alertPopup.alertError(this, "Login Error", "Incorrect Username or Password");
    }

    private void alertConnectError() {
        alertPopup.alertConnectError(this);
    }

    private void sendLogin() {

        //Loading Progress Dialog
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Logging in");
        pDialog.setCancelable(false);
        pDialog.show();

        //Retrofit Interface for Endpoints
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //Setup Post Request Object
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(inputPhone.getText().toString().trim());
        loginRequest.setPassword(inputPassword.getText().toString().trim());

        //Retrofit Api call
        Call<Login> call = apiService.postLogin(loginRequest);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                //Response code status check
                if (response.code() == 200) {

                    if (response.body().getUser().getRole().getTitle().equals("user")) {
                        //Change Progress Dialog to alert Success
                        pDialog.setTitleText("Success!")
                                .setContentText("You are now logged in!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .showCancelButton(false)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                        sessionManager.createLoginSession(response.body().getUser().getId(),response.body().getUser().getName(),inputPhone.getText().toString(),inputPassword.getText().toString(),response.body().getUser().getRole().getTitle(), response.body().getUser().getIp(),response.body().getUser().getExpired(),response.body().getToken());
                        //Check role of User to login
                        pDialog.dismiss();
                           Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                           startActivity(mainActivity);

                    } else {
                        pDialog.dismiss();
                        alertLoginError();

                    }
                } else {
                    pDialog.dismiss();
                    alertLoginError();

                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                //Network error or Connectivity
                pDialog.dismiss();
                alertConnectError();
            }
        });


    }

    //validators
    private boolean validateEmail() {
        if (inputPhone.getText().toString().trim().isEmpty()) {
            inputLayoutPhone.setError(getString(R.string.err_msg_phone));
            requestFocus(inputPhone);
            return false;
        } else if (Patterns.PHONE.matcher(inputPhone.getText().toString().trim()).matches()) {

            inputLayoutPhone.setErrorEnabled(false);
            return true;
        } else {
            inputLayoutPhone.setError(getString(R.string.err_msg_phone_valid));
            return false;
        }

    }

    //validate password
    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    //Android M permission Dialog
    private void setupPermissions() {
        if (internetPermission.isGranted()) {

        } else if (internetPermission.isDenied()) {

            internetPermission.askPermission(PERMISSION_INTERNET_REQUEST);
        } else {
            internetPermission.askPermission(PERMISSION_INTERNET_REQUEST);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_INTERNET_REQUEST) {
            PermResult internetPermissionResult = new PermResult(permissions, grantResults);
            if (internetPermissionResult.isGranted()) {

            } else if (internetPermissionResult.isDenied()) {

            } else {

            }
        }
    }


    @OnClick(R.id.button_register)
    public void onClick() {
        Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
       startActivity(intentRegister);
    }


    //Text watcher static class
    private class MyTextWatcher implements TextWatcher {

        private final View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_phone:
                    validateEmail();
                    break;

                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }


    }
}
