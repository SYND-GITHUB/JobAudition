package com.synd.jobaudition.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class GeneralUtils {

    /**
     * Copy text to clipboard
     *
     * @param context
     * @param text
     */
    public static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(context.getPackageName(), text);
        clipboard.setPrimaryClip(clip);
    }

    /**
     * Check network is connected
     *
     * @param connectivityManager
     * @return
     */
    public static boolean isConnected(ConnectivityManager connectivityManager) {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
