package com.example.waynejackson.okhttpexample.handler;

import java.io.IOException;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.waynejackson.okhttpexample.BaseActivity;
import com.example.waynejackson.okhttpexample.R;
import com.example.waynejackson.okhttpexample.endpoint.EndPointUrl;
import com.example.waynejackson.okhttpexample.endpoint.EndpointUrlProvider;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wayne.jackson on 6/22/16.
 */
public class HandlerExampleActivity extends BaseActivity {

    private Handler uiThreadHandler;

    private ProgressDialog progressDialog;
    private Button getDataBtn;
    private TextView responseDataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a handler for the UI thread
        uiThreadHandler = new Handler(Looper.getMainLooper());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.getting_data));
        getDataBtn = (Button) findViewById(R.id.get_data_btn);
        responseDataText = (TextView) findViewById(R.id.response_data_text);


        getDataBtn.setOnClickListener(getDataClickListener);
    }

    private View.OnClickListener getDataClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            responseDataText.setText(getString(R.string.no_data_loaded));
            startDataRequest();
        }
    };

    private void startDataRequest() {
        // Show progress
        progressDialog.show();

        // We need a client
        OkHttpClient client = new OkHttpClient();

        // Get the end-point url
        EndPointUrl endPointUrl = EndpointUrlProvider.getDefaultEndPointUrl();
        String url = endPointUrl.getUrl();

        // Create the request
        Request request = new Request.Builder()
                              .url(url)
                              .build();

        // For asynchronous calls in OkHttp a Call object needs to be created and used
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                showErrorMessage();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (!response.isSuccessful()) {
                    // Something went wrong
                    showErrorMessage();
                } else {
                    // Handle the response on the UI thread
                    uiThreadHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                String responseData = response.body().string();
                                responseDataText.setText(responseData);
                            } catch (IOException e) {
                                showErrorMessage();
                            }

                            // Hide the progress
                            progressDialog.hide();
                        }
                    });
                }
            }
        });
    }
}
