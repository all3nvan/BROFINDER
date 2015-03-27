package com.example.allen.brofinder.activity;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.allen.brofinder.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class LoginActivity extends ActionBarActivity implements ConnectionCallbacks, OnConnectionFailedListener, OnClickListener {
    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient googleApiClient;
    private boolean signInClicked;
    private ConnectionResult connectionResult;
    private boolean intentInProgress;
    private ViewSwitcher viewSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        viewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO: Remove google account for testing
        Plus.AccountApi.clearDefaultAccount(googleApiClient);
        Plus.AccountApi.revokeAccessAndDisconnect(googleApiClient);

        if(googleApiClient.isConnected())
            googleApiClient.disconnect();
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                signInClicked = false;
                viewSwitcher.showNext();
            }

            intentInProgress = false;

            if (!googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("derp", "SIGNED IN YEAHHHHHHH");

        // TODO: Maybe store username here
        Person currentUser = Plus.PeopleApi.getCurrentPerson(googleApiClient);
        ((TextView) findViewById(R.id.derp)).setText(currentUser.getDisplayName());

        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
        finish();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!intentInProgress) {
            viewSwitcher.showNext();
            connectionResult = result;
            if (signInClicked) {
                resolveSignInError();
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button && !googleApiClient.isConnecting()) {
            signInClicked = true;
            resolveSignInError();
        }
    }

    private void resolveSignInError() {
        if (connectionResult.hasResolution()) {
            try {
                intentInProgress = true;
                startIntentSenderForResult(connectionResult.getResolution().getIntentSender(), RC_SIGN_IN, null, 0, 0, 0);
            } catch (SendIntentException e) {
                intentInProgress = false;
                googleApiClient.connect();
            }
        }
    }
}
