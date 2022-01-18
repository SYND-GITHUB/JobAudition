package com.synd.jobaudition.ui.io;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.synd.jobaudition.model.IOModel;

public class IOViewModel extends ViewModel {

    private Application application;

    private MutableLiveData<IOModel> liveData = new MutableLiveData<>();

    public MutableLiveData<IOModel> getLiveData() {
        return liveData;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * New input
     * @param input
     */
    public void newInput(String input) {
        if (!TextUtils.isEmpty(input)) {
            IOModel ioModel = new IOModel(input);

            if (ioModel.isValid()) {
                liveData.postValue(ioModel);
                getPageTitle(ioModel);
            }
        }
    }

    /**
     * Retrieve webpage title
     * @param ioModel
     */
    private void getPageTitle(IOModel ioModel) {
        if (application == null || ioModel == null) {
            return;
        }
        // We also retrieve Page Title by getting HTML content then pick from the tag <title>...</title>
        for (final String link : ioModel.getLinkMap().keySet()) {
            WebView webView = new WebView(application);
            webView.getSettings().setJavaScriptEnabled(true);
            if (Build.VERSION.SDK_INT >= 21) {
                webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    // Update model
                    ioModel.updateLink(link, view.getTitle());
                    liveData.postValue(ioModel);

                    view.destroyDrawingCache();
                    view.destroy();
                }
            });
            webView.loadUrl(link);
        }
    }
}
