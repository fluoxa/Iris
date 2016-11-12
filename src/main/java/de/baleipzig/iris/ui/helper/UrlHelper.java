package de.baleipzig.iris.ui.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlHelper {

    public static Map<String, String> getParameterMap(String parameterString) {

        if(!isValidParameterString(parameterString)) {
            throw new RuntimeException("Invalid Url Parameter String");
        }

        String[] parameters = parameterString.split("&");

        Map<String, String> parameterMap = new HashMap<>(parameters.length);

        for (String parameter : parameters) {
            if(parameter.isEmpty()) {
                continue;
            }

            String[] keyValue = parameter.split("=");
            parameterMap.put(keyValue[0], keyValue[1]);
        }

        return parameterMap;
    }

    private static boolean isValidParameterString(String parameterString) {

        Pattern pattern = Pattern.compile("(^$)|(^((\\w+)=((\\w|-)+)&)*((\\w+)=((\\w|-)+))$)");
        Matcher matcher = pattern.matcher(parameterString);

        return matcher.matches();
    }
}
