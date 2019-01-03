package wangjin.com.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import wangjin.com.beijingnews.base.BasePager;

/**
 * Created by wangjin on 2018/8/24.
 * 主页面
 */

public class HomePager extends BasePager {
    public HomePager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        tv_title.setText("主页面");
        //联网请求数据
        TextView textView = new TextView(context);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        //添加控件
        fl_content.addView(textView);
        //绑定数据
        textView.setText("主页面");
    }
}
