package wangjin.com.beijingnews.activity;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import wangjin.com.beijingnews.R;
import wangjin.com.beijingnews.fragment.ContentFragment;
import wangjin.com.beijingnews.fragment.LeftmenuFragment;
import wangjin.com.beijingnews.utils.LogUtil;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


public class MainActivity extends SlidingFragmentActivity {
    private int screeWidth;
    private int screeHeight;
    public static final String MAIN_CONTENT_TAG = "main_content_tag";
    public static final String LEFTMENU_TAG = "leftmenu_tag";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有标题
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        super.onCreate(savedInstanceState);
        initSlidingMenu();
        initFragment();
    }

    private void initSlidingMenu() {
        //1、设置主页面
        setContentView(R.layout.activity_main);

        //2、设置左侧菜单
       setBehindContentView(R.layout.activity_leftmenu);
        SlidingMenu slidingMenu = getSlidingMenu();
        //设置显示模式：左侧+主页，左侧菜单+主页面+右侧菜单，主页面+右侧菜单
        slidingMenu.setMode(SlidingMenu.LEFT);
        //5.设置滑动模式：滑动边缘，全屏滑动，不可以滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        DisplayMetrics outmetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outmetrics);
        screeWidth = outmetrics.widthPixels;
        screeHeight = outmetrics.heightPixels;
        //6.设置主页占据的宽度
//        slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this, 200));
        slidingMenu.setBehindOffset((int) (screeWidth*0.625));
        //初始化Fragment
        initFragment();
    }
    private void initFragment() {
        //1、得到FragmentManger
        FragmentManager fm = getSupportFragmentManager();
        //2、开启事务
        FragmentTransaction ft = fm.beginTransaction();
        //3、替换
        ft.replace(R.id.fl_main_content,new ContentFragment(),MAIN_CONTENT_TAG);
        ft.replace(R.id.fl_leftmenu,new LeftmenuFragment(),LEFTMENU_TAG);
        //4.提交
        ft.commit();
    }
    public LeftmenuFragment getLeftmenuFragment() {
        return (LeftmenuFragment)getSupportFragmentManager().findFragmentByTag(LEFTMENU_TAG);
    }

    /**
     * 得到正文fragment
     * @return
     */
    public ContentFragment getContentFragment() {
        return (ContentFragment)getSupportFragmentManager().findFragmentByTag(MAIN_CONTENT_TAG);
    }
}
