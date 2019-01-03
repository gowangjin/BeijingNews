package wangjin.com.beijingnews;

import android.app.Application;

import org.xutils.x;
/**
 * Created by wangjin on 2018/8/21.
 */

public class BeijingNesApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
