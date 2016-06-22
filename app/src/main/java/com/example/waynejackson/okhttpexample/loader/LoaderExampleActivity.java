package com.example.waynejackson.okhttpexample.loader;

import java.io.IOException;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.waynejackson.okhttpexample.BaseActivity;
import com.example.waynejackson.okhttpexample.R;
import com.example.waynejackson.okhttpexample.endpoint.EndPointUrl;
import com.example.waynejackson.okhttpexample.endpoint.EndpointUrlProvider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wayne.jackson on 6/22/16.
 */
public class LoaderExampleActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Response> {
    private static final int LOADER_ID = 100;

    private ProgressDialog progressDialog;
    private Button getDataBtn;
    private TextView responseDataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Loader
        getLoaderManager().initLoader(LOADER_ID, null, this);

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
        progressDialog.show();

        // Restart the loader
        getLoaderManager().restartLoader(LOADER_ID, null, this).forceLoad();
    }

    @Override
    public Loader<Response> onCreateLoader(int id, Bundle args) {
        AsyncTaskLoader<Response> loader = new AsyncTaskLoader<Response>(this) {

            @Override
            public Response loadInBackground() {
                OkHttpClient client = new OkHttpClient();

                EndPointUrl endPointUrl = EndpointUrlProvider.getDefaultEndPointUrl();
                String url = endPointUrl.getUrl();

                Request request = new Request.Builder()
                                      .url(url)
                                      .build();
                try {
                    Response response = client.newCall(request).execute();
                    return response;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Response> loader, Response response) {
        progressDialog.dismiss();

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
    }

    @Override
    public void onLoaderReset(Loader<Response> loader) {
        responseDataText.setText(getString(R.string.no_data_loaded));
    }
}
