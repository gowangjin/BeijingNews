package wangjin.com.beijingnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import wangjin.com.beijingnews.SplashActivity;

/**
 * Created by wangjin on 2018/8/8.
 * 缓存软件的一些参数和数据
 */

public class CacheUtils {
    /**
     * @param context
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        return  sp.getBoolean(key,false);
    }
    public static void putBoolean (Context context,String key,boolean value) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();

    }

    /**
     * 缓存数据文本
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context,String key,String value) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    /**
     * 获取数据文本
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context,String key) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }
}
