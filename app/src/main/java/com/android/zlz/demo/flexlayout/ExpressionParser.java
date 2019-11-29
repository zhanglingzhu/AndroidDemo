package com.android.zlz.demo.flexlayout;

import com.alibaba.fastjson.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhanglingzhu
 * @date 2019/11/28
 */
public class ExpressionParser {

    private final static ExpressionParser instance = new ExpressionParser();

    private Pattern pattern;

    private ExpressionParser() {
        String regEx = "(?<=\\{)(\\S+)(?=\\})";
        //String regEx = "\\{([^}]*)\\}";
        pattern = Pattern.compile(regEx);
    }

    public static ExpressionParser getInstance() {
        return instance;
    }

    public String parse(String text, JSONObject object) {
        text = parseRegEx(text);
        return getData(text, object);
    }

    public String parseRegEx(String text) {
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String tx = matcher.group();
            return tx;
        }
        return text;
    }

    public String getData(String text, JSONObject jsonObject) {
        String[] array = text.split("\\.");
        JSONObject jsonO = jsonObject;
        for (int i = 0; i < array.length; i++) {
            boolean isLast = i == array.length - 1;
            if (isLast) {
                if (jsonO != null && jsonO.containsKey(array[i])) {
                    return jsonO.getString(array[i]);
                }
            } else {
                //FIXME
                String regEx = "(?<=\\[)(\\S+)(?=\\])";
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(array[i]);

                if (matcher.find()) {
                    String index = matcher.group();
                    regEx = "([a-z]+)?";
                    pattern = Pattern.compile(regEx);
                    matcher = pattern.matcher(array[i]);
                    if (matcher.find()) {
                        String list = matcher.group();
                        if (jsonO != null && jsonO.getJSONArray(list) != null) {
                            jsonO = jsonO.getJSONArray(list).getJSONObject(Integer.valueOf(index));
                        }
                    }
                } else {
                    if (jsonO != null && jsonO.containsKey(array[i])) {
                        jsonO = jsonO.getJSONObject(array[i]);
                    }
                }

            }
        }
        return "";
    }

}
