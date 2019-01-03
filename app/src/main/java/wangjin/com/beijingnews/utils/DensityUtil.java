package wangjin.com.beijingnews.utils;

import android.content.Context;

/**
 * Created by wangjin on 2018/8/12.
 */

public class DensityUtil {
    /**
     * 根据手机的分辨率从dip的单位转为px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context,float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context,float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale+ 0.5f);
    }
}
