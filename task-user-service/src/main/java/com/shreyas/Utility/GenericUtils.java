package com.shreyas.Utility;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class GenericUtils {
    public static String encodeKeyFromPath(String path){
        return path.replace("/","_");
    }

    public static String decodeKeyFromPath(String path){
        return path.replace("_","/");
    }


}
