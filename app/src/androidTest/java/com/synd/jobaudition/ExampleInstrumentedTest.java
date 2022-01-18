package com.synd.jobaudition;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.UiThread;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.synd.jobaudition.utils.StringUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private String TAG = "LogTest";

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.synd.jobaudition", appContext.getPackageName());
    }

    @Test
    public void test_showOutput() {
        Log.d(TAG, "" + StringUtils.getOutput("@synd is opening https://olympics.com/tokyo-2020/en/"));
        Log.d(TAG, "" + StringUtils.getOutput("@synd is calling @candy"));
        Log.d(TAG, "" + StringUtils.getOutput("https://olympics.com/tokyo-2020/en/ and http://tinhte.vn"));
        Log.d(TAG, "" + StringUtils.getOutput("test"));
        Log.d(TAG, "" + StringUtils.getOutput("@@@"));
        Log.d(TAG, "" + StringUtils.getOutput("httphttps"));
    }

    @Test
    @UiThread
    public void test_getWebPageTitle() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        new Handler(appContext.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                WebView webView = new WebView(ApplicationProvider.getApplicationContext());
                webView.getSettings().setJavaScriptEnabled(true);
                WebViewClient mockWebViewClient = mock(WebViewClient.class);
                webView.setWebViewClient(mockWebViewClient);
                String url = "https://olympics.com/tokyo-2020/en/";

                webView.loadUrl(url);
            }
        });
    }
}