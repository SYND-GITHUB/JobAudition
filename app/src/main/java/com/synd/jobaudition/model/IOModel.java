package com.synd.jobaudition.model;

import android.text.TextUtils;

import com.synd.jobaudition.utils.GeneralConstants;
import com.synd.jobaudition.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IOModel {
    private long id;
    private String input;
    private String output;
    private List<String> mentions;
    private HashMap<String, String> linkMap;
    private int totalLinks, count;
    private boolean isFinishedLoadTitles;

    public IOModel(String input) {
        setInput(input);
    }

    public long getId() {
        return id;
    }

    public void setInput(String input) {
        this.id = System.currentTimeMillis();
        this.input = input;
        this.isFinishedLoadTitles = false;
        this.totalLinks = 0;
        this.count = 0;

        try {
            mentions = new ArrayList<>();
            linkMap = new HashMap<>();

            JSONObject jsonObject = StringUtils.getOutput(input);

            // Mentions
            JSONArray mentionsArray = jsonObject.optJSONArray(GeneralConstants.KEY_MENTION);
            for (int i = 0; mentionsArray != null && i < mentionsArray.length(); i++) {
                mentions.add(mentionsArray.optString(i));
            }
            // Links
            JSONArray linksArray = jsonObject.optJSONArray(GeneralConstants.KEY_LINK);
            for (int i = 0; linksArray != null && i < linksArray.length(); i++) {
                JSONObject urlJson = linksArray.optJSONObject(i);
                linkMap.put(urlJson.optString(GeneralConstants.KEY_URL), urlJson.optString(GeneralConstants.KEY_TITLE));
            }
            output = jsonObject.toString(GeneralConstants.JSON_INDENT_SPACE);
            totalLinks = linkMap.size();
            isFinishedLoadTitles = (totalLinks == 0);
        } catch (Exception e) {
            e.printStackTrace();
            this.output = null;
            this.mentions = null;
            this.linkMap = null;
        }
    }

    public String getInput() {
        return input;
    }

    public List<String> getMentions() {
        return mentions;
    }

    public HashMap<String, String> getLinkMap() {
        return linkMap;
    }

    public String getOutput() {
        return output;
    }

    public synchronized void updateLink(String link, String title) {
        if (link == null || linkMap == null) {
            return;
        }
        linkMap.put(link, title);
        count++;
        if (count == totalLinks) {
            output = StringUtils.formatJson(mentions, linkMap);
            isFinishedLoadTitles = true;
        }
    }

    public boolean isValid() {
        return !TextUtils.isEmpty(input) && !TextUtils.isEmpty(output);
    }

    public boolean isFinishedLoadTitles() {
        return isFinishedLoadTitles;
    }
}
