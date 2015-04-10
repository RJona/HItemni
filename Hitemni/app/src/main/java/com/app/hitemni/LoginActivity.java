package com.app.hitemni;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by OptiFine on 08/04/2015.
 */
public class LoginActivity extends Activity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private EditText username=null;
    private EditText password=null;
    private TextView usernameTV=null;
    private TextView passwordTV=null;
    private TextView loginTitleTV=null;
    private Button login;
    private LoginButton loginFacebook;
    private UiLifecycleHelper uiHelper;
    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "GoogleSignIn";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private boolean isSignedWithFacebook=false;
    private boolean isSignedWithLogin=false;

    private ConnectionResult mConnectionResult;

    private SignInButton loginGoogle;
    private Button logoutGoogle, revokeGoogle;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;
    private LinearLayout llProfileLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.editText1);
        password = (EditText)findViewById(R.id.editText2);
        login = (Button)findViewById(R.id.button1);
        loginTitleTV = (TextView)findViewById(R.id.textView1);
        usernameTV = (TextView)findViewById(R.id.textView2);
        passwordTV = (TextView)findViewById(R.id.textView3);


        /* Facebook */

        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);


        loginFacebook = (LoginButton) findViewById(R.id.login_button);
        loginFacebook.setReadPermissions(Arrays.asList("email"));
        loginFacebook.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser graphUser) {
                if (graphUser != null) {
                    Toast.makeText(getApplicationContext(), "You are currently connected as " + graphUser.getName(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* Google+ */
        loginGoogle = (SignInButton) findViewById(R.id.sign_in_button);
        logoutGoogle = (Button) findViewById(R.id.btn_sign_out);
        revokeGoogle = (Button) findViewById(R.id.btn_revoke_access);
        loginGoogle.setOnClickListener(this);
        logoutGoogle.setOnClickListener(this);
        revokeGoogle.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState sessionState, Exception e) {
            if (sessionState.isOpened()) {
                isSignedWithFacebook=true;
                updateUI(false);
                Log.d("MainActivity", "Facebook session opened.");
                Toast.makeText(getApplicationContext(), "Facebook session opened.",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Accueil.class);
                startActivity(intent);
            } else if (sessionState.isClosed()) {
                isSignedWithFacebook=false;
                updateUI(false);
                Log.d("MainActivity", "Facebook session closed.");
                Toast.makeText(getApplicationContext(), "Facebook session closed.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
        updateUI(mGoogleApiClient.isConnected());
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
        updateUI(mGoogleApiClient.isConnected());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
        updateUI(mGoogleApiClient.isConnected());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }


    public void login(View view){
        if(username == null || password == null){
            Toast.makeText(getApplicationContext(), "Wrong Credentials",
                    Toast.LENGTH_SHORT).show();
            isSignedWithLogin=false;
            return;
        }
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://192.168.2.50:8080/primavera/api/user/authentication?login=" + username.getText().toString() + "&password=" + password.getText().toString());
        try {
            HttpResponse response = client.execute(request);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line ="";
            String result="";
            while ((line = br.readLine()) != null){
                result += line;
            }
            if(!result.equals("{}")) {
                Toast.makeText(getApplicationContext(), "Redirecting...",
                        Toast.LENGTH_SHORT).show();
                isSignedWithLogin=true;
                Intent intent = new Intent(getApplicationContext(), Accueil.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "Wrong Credentials",
                        Toast.LENGTH_SHORT).show();
                isSignedWithLogin=false;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            isSignedWithLogin=false;
        } catch (IOException e) {
            e.printStackTrace();
            isSignedWithLogin=false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu., menu);
        return true;
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Method to resolve any signin errors
     * */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    @Override
    public void onConnected(Bundle arg0) {
        if(mSignInClicked) {
            mSignInClicked = false;
            Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

            // Get user's information
            getProfileInformation();
        }
        // Update the UI after signin
        updateUI(mGoogleApiClient.isConnected());
    }

    /**
     * Updating the UI, showing/hiding buttons and profile layout
     * */
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            loginGoogle.setVisibility(View.GONE);
            logoutGoogle.setVisibility(View.VISIBLE);
            revokeGoogle.setVisibility(View.VISIBLE);
            username.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            loginFacebook.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
            loginTitleTV.setVisibility(View.GONE);
            usernameTV.setVisibility(View.GONE);
            passwordTV.setVisibility(View.GONE);
        } else if(isSignedWithFacebook) {
            loginGoogle.setVisibility(View.GONE);
            logoutGoogle.setVisibility(View.GONE);
            revokeGoogle.setVisibility(View.GONE);
            username.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            loginFacebook.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
            loginTitleTV.setVisibility(View.GONE);
            usernameTV.setVisibility(View.GONE);
            passwordTV.setVisibility(View.GONE);
        }  else {
            loginGoogle.setVisibility(View.VISIBLE);
            logoutGoogle.setVisibility(View.GONE);
            revokeGoogle.setVisibility(View.GONE);
            username.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
            loginFacebook.setVisibility(View.VISIBLE);
            login.setVisibility(View.VISIBLE);
            loginTitleTV.setVisibility(View.VISIBLE);
            usernameTV.setVisibility(View.VISIBLE);
            passwordTV.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Fetching user's information name, email, profile pic
     * */
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Toast.makeText(getApplicationContext(), "Google session opened.",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Accueil.class);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        updateUI(false);
    }

    /**
     * Button on click listener
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                // Signin button clicked
                signInWithGplus();
                break;
            case R.id.btn_sign_out:
                // Signout button clicked
                signOutFromGplus();
                break;
            case R.id.btn_revoke_access:
                // Revoke access button clicked
                revokeGplusAccess();
                break;
        }
    }

    /**
     * Sign-in into google
     * */
    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    /**
     * Sign-out from google
     * */
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            updateUI(false);
        }
    }

    /**
     * Revoking access from google
     * */
    private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e(TAG, "User access revoked!");
                            mGoogleApiClient.connect();
                            updateUI(false);
                        }

                    });
        }
    }
}
