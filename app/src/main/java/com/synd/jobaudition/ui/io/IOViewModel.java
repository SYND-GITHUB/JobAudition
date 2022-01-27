package com.synd.jobaudition.ui.io;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.synd.jobaudition.model.IOModel;
import com.synd.jobaudition.utils.StringUtils;

public class IOViewModel extends ViewModel {

    private MutableLiveData<IOModel> liveData = new MutableLiveData<>();

    public MutableLiveData<IOModel> getLiveData() {
        return liveData;
    }

    /**
     * New input
     *
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
     *
     * @param ioModel
     */
    private void getPageTitle(IOModel ioModel) {
        if (ioModel == null) {
            return;
        }
        for (String url : ioModel.getLinkMap().keySet()) {
            new Thread(() -> {
                String title = StringUtils.jsoupParseHtmlTitle(url);
                ioModel.updateLink(url, title);
                liveData.postValue(ioModel);
            }).start();
        }
    }
}
