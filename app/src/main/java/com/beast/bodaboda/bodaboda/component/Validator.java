package com.beast.bodaboda.bodaboda.component;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * Created by simba on 3/6/17.
 */

public class Validator {
    AlertPopup alertPopup= new AlertPopup();

    public boolean validateInput(EditText editText,TextInputLayout inputLayout) {
        if (editText.getText().toString().trim().isEmpty()) {
            inputLayout.setError("Field required");

            return false;
        } else {
            inputLayout.setErrorEnabled(false);
        }

        return true;
    }
    public boolean validateEditText(EditText editText) {
        if (editText.getText().toString().trim().isEmpty()) {

            return true;
        } else {
            return false;
        }


    }

}
