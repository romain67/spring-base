package com.roms.library.format;

import org.apache.commons.lang3.StringUtils;

public class StringFormat {

    /**
     * Clean a StringFormat
     * @param value
     * @return
     */
    public static String canonicalize(String value) {
        if (value != null) {
            value = StringUtils.stripAccents(value);
            value = value.replaceAll("\\P{Alnum}","-");
            value = value.toLowerCase();
            value = value.replaceAll("-+", "-");

            if (value.length() > 0 && value.substring(value.length() - 1).equals("-")) { // Remove last "-" chars
                value = value.substring(0, value.length() - 1);
            }

            if (value.length() > 0 && value.substring(0, 1).equals("-")) { // Remove first "-" chars
                value = value.substring(1);
            }
        }
        return value;
    }

}
