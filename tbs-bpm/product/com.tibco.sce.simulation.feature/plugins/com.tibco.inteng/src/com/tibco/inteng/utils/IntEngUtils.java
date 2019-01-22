package com.tibco.inteng.utils;

public class IntEngUtils {

    /**
     * This method parses the String objects or Boolean objects and returns the
     * corresponding boolean value.
     * 
     * @param value
     * @return
     */
    public static boolean returnBooleanValue(Object value) {
        boolean toReturn = false;
        if (value == null) {
            return toReturn;
        }
        if (value instanceof Boolean) {
            toReturn = ((Boolean) value).booleanValue();
        }
        if (value instanceof String) {
            String strValue = (String) value;
            Boolean bool = Boolean.valueOf(strValue);
            toReturn = bool.booleanValue();
        }
        return toReturn;
    }
}
