package wangjin.com.beijingnews.menudetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import wangjin.com.beijingnews.base.MenuDetaiBasePager;
import wangjin.com.beijingnews.utils.LogUtil;

/**
 * Created by wangjin on 2018/9/9.
 */

public class TopicMenuDetailPager extends MenuDetaiBasePager {
    TextView textView;
    public TopicMenuDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("专题详情页面实现了");
        textView.setText("专题详情页面");
    }
}
