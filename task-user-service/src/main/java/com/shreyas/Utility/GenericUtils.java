package com.shreyas.Utility;

public class GenericUtils {
    public static String encodeKeyFromPath(String path){
        return path.replace("/","_");
    }

    public static String decodeKeyFromPath(String path){
        return path.replace("_","/");
    }
}
