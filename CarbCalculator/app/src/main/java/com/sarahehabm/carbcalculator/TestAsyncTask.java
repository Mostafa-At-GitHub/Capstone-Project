package com.sarahehabm.carbcalculator;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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
public class TestAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private OnDataRetrieveListener dataRetrieveListener;

    public TestAsyncTask(OnDataRetrieveListener dataRetrieveListener) {
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
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    /*.setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    })*/
                    .setRootUrl("https://carbcalculator.appspot.com/_ah/api/")
                    ;

            myApiService = builder.build();
        }

        context = params[0].first;
        String name = params[0].second;

        try {
            return myApiService.getItems().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        Log.v("TestSyncAActivity", s);

        if (dataRetrieveListener != null)
            dataRetrieveListener.onFinishCall(s, Constants.SERVICE_GET_ITEMS);
    }
}
