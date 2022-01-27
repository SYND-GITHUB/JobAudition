package com.synd.jobaudition;

import com.synd.jobaudition.utils.StringUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ExampleUnitTest {

    private String TAG = "LogTest";

    @Test
    public void test_showOutput() {
        System.out.println("Output 1: " + StringUtils.getOutput("@synd is opening https://olympics.com/tokyo-2020/en/"));
        System.out.println("Output 2: " + StringUtils.getOutput("@synd is calling @candy"));
        System.out.println("Output 3: " + StringUtils.getOutput("https://olympics.com/tokyo-2020/en/ and http://tinhte.vn"));
        System.out.println("Output 4: " + StringUtils.getOutput("test"));
        System.out.println("Output 5: " + StringUtils.getOutput("@@@"));
        System.out.println("Output 6: " + StringUtils.getOutput("httphttps"));
    }

    @Test
    public void test_getWebPageTitle() {
        String title1 = StringUtils.jsoupParseHtmlTitle("https://olympics.com/tokyo-2020/en/");
        String title2 = StringUtils.jsoupParseHtmlTitle("http://");
        System.out.println("Title 1: " + title1);
        System.out.println("Title 2: " + title2);
    }
}