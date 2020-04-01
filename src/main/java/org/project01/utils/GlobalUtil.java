package org.project01.utils;

public class GlobalUtil {
    public static int mustInt(String source, int defaultValue){
        int result = defaultValue;
        try {
            result = Integer.getInteger(source);
        }catch (Exception e){
            return result;
        }
        return result;
    }
}
