package com.sarahehabm.carbcalculator.login;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.Preferences;
import com.sarahehabm.carbcalculator.main.view.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private GoogleApiClient googleApiClient;
    private int mSignInProgress, mSignInError;
    private final int STATE_SIGNED_IN = 0, STATE_SIGN_IN = 1, STATE_PROGRESS = 2;
    private static final int RC_SIGN_IN = 0;
    private static final int DIALOG_PLAY_SERVICES_ERROR = 0;

    private TextView textViewStatus;
    private SignInButton buttonSignIn;
    private PendingIntent mSignInIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewStatus = (TextView) findViewById(R.id.textView_status);
        buttonSignIn = (SignInButton) findViewById(R.id.button_google_signIn);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInClick(v);
            }
        });

        googleApiClient = buildApiClient();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(googleApiClient.isConnected())
            googleApiClient.disconnect();
    }

    private GoogleApiClient buildApiClient() {
        return new GoogleApiClient.Builder(this, this, this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(new Scope(Scopes.PROFILE))
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e(TAG, "onConnected");

        mSignInProgress = STATE_SIGNED_IN;
        buttonSignIn.setEnabled(false);

        Person person = Plus.PeopleApi.getCurrentPerson(googleApiClient);
        if(person!=null) {
            String username = person.getDisplayName();
            String imageUrl = person.getImage().getUrl();
            if (imageUrl.endsWith("?sz=50"))
                imageUrl = imageUrl.substring(0, imageUrl.lastIndexOf("?sz=50"));

            String birthday = person.getBirthday();
            long birthdayTimestamp = -1;
            try {
                birthdayTimestamp = (new SimpleDateFormat("yyyy-MM-dd").parse(birthday)).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                birthdayTimestamp = -1;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                birthdayTimestamp = -1;
            }

            Preferences preferences = new Preferences(this);
            boolean nameSaved = preferences.putString(Preferences.KEY_NAME, username);
            boolean urlSaved = preferences.putString(Preferences.KEY_IMAGE_URL, imageUrl);

            boolean birthdaySaved = false;
            if (birthdayTimestamp != -1)
                birthdaySaved = preferences.putLong(Preferences.KEY_BIRTHDAY, birthdayTimestamp);


            Log.v(TAG, "Name saved: " + nameSaved);
            Log.v(TAG, "URL saved: " + urlSaved);
            Log.v(TAG, "Birthday saved: " + birthdaySaved);

            textViewStatus.setText(getString(R.string.welcome_username, username));

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.failed_to_login), Toast.LENGTH_LONG).show();
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.e(TAG, "onConnectionSuspended");

        Log.e(TAG, "Cause: " + cause);
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed");

        if(mSignInProgress != STATE_PROGRESS) {
            mSignInIntent = connectionResult.getResolution();
            mSignInError = connectionResult.getErrorCode();

            if(mSignInProgress == STATE_SIGN_IN) {
                resolveSignInError();
            }
        }

        onSignedOut();
    }

    private void onSignedOut() {
        buttonSignIn.setEnabled(true);
    }

    private void resolveSignInError() {
        if(mSignInIntent != null) {
            try {
                mSignInProgress = STATE_PROGRESS;
                startIntentSenderForResult(mSignInIntent.getIntentSender(), RC_SIGN_IN,
                        null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                Log.i(TAG, "Sign in intent could not be sent: " + e.getLocalizedMessage());
                mSignInProgress = STATE_SIGN_IN;
                googleApiClient.connect();
            }
        } else {
            showDialog(DIALOG_PLAY_SERVICES_ERROR);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_SIGN_IN:
                switch (resultCode) {
                    case RESULT_OK:
                        mSignInProgress = STATE_SIGN_IN;
                        break;

                    default:
                        mSignInProgress = STATE_SIGNED_IN;
                        break;
                }

                if(!googleApiClient.isConnecting()) {
                    googleApiClient.connect();
                }
                break;
        }
    }

    public void onSignInClick(View view) {
        if(!googleApiClient.isConnecting()) {
            resolveSignInError();
        }
    }

    public void onSignOutClick(View view) {
        if(!googleApiClient.isConnecting()) {
            Plus.AccountApi.clearDefaultAccount(googleApiClient);
            googleApiClient.disconnect();
            googleApiClient.connect();
        }
    }

    public void onRevokeAccessClick(View view) {
        boolean isConnecting = googleApiClient.isConnecting();
        Log.e(TAG, "onRevokeAccessClick; client.isConnecting()= " + isConnecting);

        if(!isConnecting) {
            Plus.AccountApi.clearDefaultAccount(googleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(googleApiClient);
            googleApiClient = buildApiClient();
            googleApiClient.connect();
        }
    }
}
