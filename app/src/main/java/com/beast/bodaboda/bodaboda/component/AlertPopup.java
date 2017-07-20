package com.beast.bodaboda.bodaboda.component;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;


import com.beast.bodaboda.bodaboda.LoginActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by simba on 3/3/17.
 */

public class AlertPopup {

    public void alertConnectError(final Context context) {
        final SweetAlertDialog failedAlert = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        failedAlert.setTitleText("Connectivity Error")
                .setContentText("Failed,Check Your Internet Connection!")
                .setCancelText("Retry")
                .setConfirmText("Settings")
                .showCancelButton(true).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                context.startActivity(intent);
                failedAlert.dismiss();

            }
        })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        failedAlert.dismiss();
                    }
                }).show();


    }

    public void alertError(Context context, String title, String message) {
        SweetAlertDialog failedAlert = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        failedAlert.setTitleText(title)
                .setContentText(message)
                .show();
    }

    public void alertSuccess(Context context, String title, String message) {
        SweetAlertDialog failedAlert = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        failedAlert.setTitleText(title)
                .setContentText(message)
                .show();
    }
    public void alertTokenExpired(final SharedPreferences sp, final Context context){
        SweetAlertDialog tokenAlert = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            tokenAlert.setTitleText("Session Expired")
                    .setContentText("Your login Session has Expired Please Login Again")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            SharedPreferences.Editor editor = sp.edit();
                            editor.clear();
                            editor.commit();
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);
                            //finish();
                        }
                    })
                    .showCancelButton(false)
                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
        tokenAlert.show();



    }
}


