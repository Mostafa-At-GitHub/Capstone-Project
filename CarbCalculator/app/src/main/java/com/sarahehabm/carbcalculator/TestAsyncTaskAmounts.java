package com.sarahehabm.carbcalculator;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.sarahehabm.carbcalculator.backend.myApi.MyApi;
import com.sarahehabm.carbcalculator.common.Constants;
import com.sarahehabm.carbcalculator.common.OnDataRetrieveListener;

import java.io.IOException;

/**
 * Created by Sarah E. Mostafa on 20-May-16.
 */
public class TestAsyncTaskAmounts extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private OnDataRetrieveListener dataRetrieveListener;

    public TestAsyncTaskAmounts(OnDataRetrieveListener dataRetrieveListener) {
        this.dataRetrieveListener = dataRetrieveListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (dataRetrieveListener != null)
            dataRetrieveListener.onStartCall();
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://carbcalculator.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        context = params[0].first;
        String name = params[0].second;

        try {
            return myApiService.getAmounts().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (dataRetrieveListener != null)
            dataRetrieveListener.onFinishCall(s, Constants.SERVICE_GET_ITEM_AMOUNTS);
    }
}
