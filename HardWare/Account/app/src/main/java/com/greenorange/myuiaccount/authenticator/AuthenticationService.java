
package com.greenorange.myuiaccount.authenticator;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.greenorange.myuiaccount.Log;

/**
 * Service to handle Account authentication. It instantiates the authenticator
 * and returns its IBinder.
 */
public class AuthenticationService extends Service {
    private Authenticator mAuthenticator;
    @Override
    public void onCreate() {
        Log.d("SampleSyncAdapter Authentication Service started.");
        mAuthenticator = new Authenticator(this);
    }
    @Override
    public void onDestroy() {
        Log.d("SampleSyncAdapter Authentication Service stopped.");
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("getBinder()...  returning the AccountAuthenticator binder for intent " + intent.getAction());
        if(AccountManager.ACTION_AUTHENTICATOR_INTENT.equals(intent.getAction()))
            return mAuthenticator.getIBinder();
        return null;
    }
}
