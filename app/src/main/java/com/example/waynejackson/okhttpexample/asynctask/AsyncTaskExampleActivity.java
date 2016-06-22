package com.example.waynejackson.okhttpexample.asynctask;

import java.io.IOException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waynejackson.okhttpexample.BaseActivity;
import com.example.waynejackson.okhttpexample.R;
import com.example.waynejackson.okhttpexample.endpoint.EndPointUrl;
import com.example.waynejackson.okhttpexample.endpoint.EndpointUrlProvider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AsyncTaskExampleActivity extends BaseActivity {

    private ProgressDialog progressDialog;
    private Button getDataBtn;
    private TextView responseDataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        new GetDataTask().execute();
    }


    private class GetDataTask extends AsyncTask<Void, Void, Response> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Response doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();

            EndPointUrl endPointUrl = EndpointUrlProvider.getDefaultEndPointUrl();
            String url = endPointUrl.getUrl();

            Request request = new Request.Builder()
                .url(url).build();

            try {
                Response response = client.newCall(request).execute();
                return response;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Response response) {
            if (response != null && response.isSuccessful()) {
                try {
                    String responseData = response.body().string();
                    responseDataText.setText(responseData);
                } catch (IOException e) {
                    showErrorMessage();
                }
            } else {
                showErrorMessage();
            }

            progressDialog.dismiss();
        }
    }
}
