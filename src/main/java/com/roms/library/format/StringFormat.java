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
        }
        return value;
    }

}
