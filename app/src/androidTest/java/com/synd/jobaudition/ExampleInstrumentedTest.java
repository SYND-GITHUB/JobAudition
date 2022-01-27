package com.synd.jobaudition;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.util.Log;

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
        Log.d(TAG, "Output 1: " + StringUtils.getOutput("@synd is opening https://olympics.com/tokyo-2020/en/"));
        Log.d(TAG, "Output 2: " + StringUtils.getOutput("@synd is calling @candy"));
        Log.d(TAG, "Output 3: " + StringUtils.getOutput("https://olympics.com/tokyo-2020/en/ and http://tinhte.vn"));
        Log.d(TAG, "Output 4: " + StringUtils.getOutput("test"));
        Log.d(TAG, "Output 5: " + StringUtils.getOutput("@@@"));
        Log.d(TAG, "Output 6: " + StringUtils.getOutput("httphttps"));
    }

    @Test
    public void test_getWebPageTitle() {
        new Thread(() -> Log.d(TAG, "Title 1: " + StringUtils.jsoupParseHtmlTitle("https://olympics.com/tokyo-2020/en/"))).start();
        new Thread(() -> Log.d(TAG, "Title 2: " + StringUtils.jsoupParseHtmlTitle("http://"))).start();
    }
}