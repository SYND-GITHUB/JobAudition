package com.synd.jobaudition.utils;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.Patterns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * Convert input string to Json
     *
     * @param input
     * @return
     */
    public static JSONObject getOutput(String input) {
        JSONObject jsonObject = new JSONObject();

        Matcher mentionMatcher = Pattern.compile(GeneralConstants.REGEX_MENTION).matcher(input);
        Matcher linkMatcher = Patterns.WEB_URL.matcher(input);

        try {
            while (mentionMatcher.find()) {
                JSONArray jsonArray = jsonObject.optJSONArray(GeneralConstants.KEY_MENTION);
                if (jsonArray == null) {
                    jsonArray = new JSONArray();
                }
                jsonArray.put(mentionMatcher.group().replaceAll("@", "").trim());
                jsonObject.put(GeneralConstants.KEY_MENTION, jsonArray);
            }
            while (linkMatcher.find()) {
                JSONArray jsonArray = jsonObject.optJSONArray(GeneralConstants.KEY_LINK);
                if (jsonArray == null) {
                    jsonArray = new JSONArray();
                }
                JSONObject urlJson = new JSONObject();
                urlJson.put(GeneralConstants.KEY_URL, linkMatcher.group().trim());
                jsonArray.put(urlJson);
                jsonObject.put(GeneralConstants.KEY_LINK, jsonArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Convert raw data to Json
     *
     * @param mentions
     * @param linkMap
     * @return
     */
    public static String formatJson(List<String> mentions, Map<String, String> linkMap) {
        try {
            JSONObject jsonObject = new JSONObject();
            // Mentions
            if (!mentions.isEmpty()) {
                JSONArray mentionArray = new JSONArray();
                for (String mention : mentions) {
                    mentionArray.put(mention);
                }
                jsonObject.put(GeneralConstants.KEY_MENTION, mentionArray);
            }

            // Links
            if (!linkMap.isEmpty()) {
                JSONArray linkArray = new JSONArray();
                for (String url : linkMap.keySet()) {
                    JSONObject urlJson = new JSONObject();
                    urlJson.put(GeneralConstants.KEY_URL, url);
                    urlJson.put(GeneralConstants.KEY_TITLE, linkMap.get(url));
                    linkArray.put(urlJson);
                }
                jsonObject.put(GeneralConstants.KEY_LINK, linkArray);
            }

            return jsonObject.toString(GeneralConstants.JSON_INDENT_SPACE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Format Input by mentions
     *
     * @param input
     * @param mentions
     * @return
     */
    public static SpannableString formatSpannableInput(String input, List<String> mentions) {
        SpannableString spannableString = new SpannableString(input);
        for (String mention : mentions) {
            int start = input.indexOf(mention);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),
                    start, start + mention.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // No need to format programmatically
//        for (String s : ioModel.getLinks()) {
//            int start = ioModel.getInput().indexOf(s);
//            spannableString.setSpan(new UnderlineSpan(), start, start + s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
        return spannableString;
    }

    /**
     * Parse Html title by Jsoup
     *
     * @param url
     * @return
     */
    public static String jsoupParseHtmlTitle(String url) {
        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Chrome")
                    .timeout(60000)
                    .ignoreHttpErrors(true)
                    .get();
            return document.title();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
