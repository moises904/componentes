package com.almeyda.soft.componentstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout  layoutUserName;
    private TextInputEditText inputUserName;
    private TextInputLayout layoutPassword;
    private TextInputEditText inputPassword;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            final View v = getCurrentFocus();
            if (v != null && v instanceof EditText || v instanceof TextInputEditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getX(), (int) ev.getY())) {
                    v.clearFocus();
                    final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }

        }
        return super.dispatchTouchEvent(ev);
    }

    private void initializeView(){

        setContentView(R.layout.activity_main);

        layoutUserName = findViewById(R.id.til_username);
        inputUserName = findViewById(R.id.et_username);
        layoutPassword = findViewById(R.id.til_password);
        inputPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFieldsLogin();
            }
        });
    }

    private void validateFieldsLogin(){

       boolean flagUserName = validateUserName(inputUserName.getText().toString());
       boolean flagPassword = validatePassword(inputPassword.getText().toString());

       if(flagUserName && flagPassword) {
           Toast.makeText(getApplicationContext(), "Datos validos", Toast.LENGTH_LONG);
       }


    }

    private boolean validateUserName(String userName){

        boolean flagValidateUserName = validateExistString(userName);

        if(flagValidateUserName){
            layoutUserName.setError(null);
            flagValidateUserName = true;
        }else{
            layoutUserName.setError(getString(R.string.message_error_empty_username));
            flagValidateUserName = false;
        }

        return flagValidateUserName;
    }

    private boolean validatePassword(String password){

        boolean flagValidatePassword = validateExistString(password);

        if(flagValidatePassword){
            layoutPassword.setError(null);
            flagValidatePassword = true;
        }else{
            layoutPassword.setError(getString(R.string.message_error_empty_password));
            flagValidatePassword = false;
        }

        return flagValidatePassword;
    }

    private boolean validateExistString(String inputData){
        return !TextUtils.isEmpty(inputData);
    }
}
