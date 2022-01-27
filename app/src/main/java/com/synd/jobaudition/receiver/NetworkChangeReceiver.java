package com.synd.jobaudition.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        // Send broadcast to update UI
        Intent dataIntent = new Intent(ConnectivityManager.CONNECTIVITY_ACTION);
        context.sendBroadcast(dataIntent);
    }
}
