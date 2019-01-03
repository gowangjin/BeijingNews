package wangjin.com.beijingnews.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import wangjin.com.beijingnews.R;
import wangjin.com.beijingnews.activity.MainActivity;

/**
 * Created by wangjin on 2018/8/22.
 * 作用：基类或者说公共类
 */

public  class BasePager {
    public Context context; //MainActivity
    //代表各个不同的页面
    public View rootView;
    @ViewInject(R.id.tv_title)
    public TextView tv_title; //标题
    @ViewInject(R.id.ib_menu)
    public ImageButton ib_menu; //侧滑按钮
    @ViewInject(R.id.fl_content)
    public FrameLayout fl_content; // 子页面数据填充

    public BasePager(Context context) {
        this.context = context;
        this.rootView = initView();
    }
   /*
   **初始化公共布局部分，初始化内容布局FrameLayout
    */
    private View initView() {
        //基类的页面
            View view = View.inflate(context, R.layout.base_pager,null);
        x.view().inject(BasePager.this,view);
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)context;
                mainActivity.getSlidingMenu().toggle();//关<->开
            }
        });
        return view;
    }
   /*
   **需要初始化数据数据时，重写该方法
    */
    public  void initData(){

    };
}
