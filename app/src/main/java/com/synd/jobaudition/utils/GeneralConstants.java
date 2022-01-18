package com.synd.jobaudition.utils;

public class GeneralConstants {

    public static final String KEY_MENTION = "mentions";
    public static final String KEY_LINK = "links";
    public static final String KEY_URL = "url";
    public static final String KEY_TITLE = "title";

    public static final String REGEX_MENTION = "@([A-Za-z0-9_-]+)";
    public static final String REGEX_LINK = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    public static final String[] regexes = {
            REGEX_MENTION,
            REGEX_LINK
    };
    public static final int JSON_INDENT_SPACE = 4;
}
