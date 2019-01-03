package wangjin.com.beijingnews.base;

import android.content.Context;
import android.view.View;

/**
 * Created by wangjin on 2018/9/9.
 * 详情页面的基类
 */

public abstract class MenuDetaiBasePager {
    public Context context;
    //代表各个详情页面的视图
    public View rootView;
    public MenuDetaiBasePager(Context context) {
        this.context = context;
        rootView = initView();
    }

    /**
     * 几个详情页面没有可以共用的部分，强迫子类实现
     * @return
     */
    public abstract View initView();

    /**
     * 当子类需要联网请求数据时，使用该方法
     */
    public void initData() {

    }

}
