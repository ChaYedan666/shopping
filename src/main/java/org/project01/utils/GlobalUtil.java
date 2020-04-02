package org.project01.utils;

public class GlobalUtil {
    public static int mustInt(String source, int defaultValue){
        int result = defaultValue;
        try {

            result = Integer.parseInt(source);
        }catch (Exception e){
            System.out.println("转换当前页面数失败");
            return result;
        }
        return result;
    }
}
