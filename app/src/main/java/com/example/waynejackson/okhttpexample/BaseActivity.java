package com.example.waynejackson.okhttpexample;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by wayne.jackson on 6/22/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public void showErrorMessage() {
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }
}
