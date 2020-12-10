package com.nwpu.bsss.utils;

import java.sql.Timestamp;

/**
 * @author alecHe
 * @desc 一些自定义的小工具可以写在这个类里面
 * @date 2020-12-08 13:06:36
 */
public class Tools {
    public static boolean isEmptyString(String s){
        return s==null || s.isEmpty();
    }
    public static Timestamp timestamp(){
        return new Timestamp(System.currentTimeMillis());
    }
}
