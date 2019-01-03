package wangjin.com.beijingnews.fragment;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import wangjin.com.beijingnews.R;
import wangjin.com.beijingnews.activity.MainActivity;
import wangjin.com.beijingnews.base.BaseFragment;
import wangjin.com.beijingnews.base.BasePager;
import wangjin.com.beijingnews.pager.GovaffairPager;
import wangjin.com.beijingnews.pager.HomePager;
import wangjin.com.beijingnews.pager.NewsCenterPager;
import wangjin.com.beijingnews.pager.SettingPager;
import wangjin.com.beijingnews.pager.SmartServicePager;
import wangjin.com.beijingnews.view.NoScrollViewPager;


/**
 * Created by wangjin on 2018/8/12.
 */

public class ContentFragment extends BaseFragment {
    //初始化ID
    @ViewInject(R.id.viewpage)
    private NoScrollViewPager viewPager;
    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;
    //添加viewpager集合
    private ArrayList<BasePager> basePagers;
    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.content_fragment,null);
//        viewPager = view.findViewById(R.id.viewpage);
//        rg_main = view.findViewById(R.id.rg_main);
        //将视图注入到框架中，让ContentFragment和view关联起来
        x.view().inject(ContentFragment.this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        basePagers = new ArrayList<>();
        basePagers.add(new HomePager(context));
        basePagers.add(new NewsCenterPager(context));
        basePagers.add(new GovaffairPager(context));
        basePagers.add(new SmartServicePager(context));
        basePagers.add(new SettingPager(context));
        viewPager.setAdapter(new ContentFragmentAdapter());
        //rg_main.check(0);
        //初始化第一个页面数据
        basePagers.get(0).initData();
        //默认设置为左边菜单不能侧滑
        isEnableSlidingMenu(false);
       rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //监听某个页面被选中，初始化该页面数据
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 得到新闻中心
     * @return
     */
    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) basePagers.get(1);
    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        /*
        **当页面被选中，回调该方法
         */
        @Override
        public void onPageSelected(int position) {
              basePagers.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:
                    viewPager.setCurrentItem(0,false);
                    isEnableSlidingMenu(false);
                    break;
                case R.id.rb_newscenter:
                    viewPager.setCurrentItem(1,false);
                    isEnableSlidingMenu(true);
                    break;
                case R.id.rb_smartservices:
                    viewPager.setCurrentItem(2,false);
                    isEnableSlidingMenu(false);
                    break;
                case R.id.rb_gvaffair:
                    viewPager.setCurrentItem(3,false);
                    isEnableSlidingMenu(false);
                    break;
                case R.id.rb_setting:
                    viewPager.setCurrentItem(4,false);
                    isEnableSlidingMenu(false);
                    break;
            }
        }
    }

    /**
     * 用来设置侧滑菜单是否能够滑动
     * @param flag true时可以全屏滑动
     */
    private void isEnableSlidingMenu(boolean flag) {
        MainActivity mainActivity = (MainActivity) context;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        if(flag) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
    /*
    **ViewPager的PagerAdapter适配器
     */
    class ContentFragmentAdapter extends PagerAdapter{
        /*
        **返回viewpager要显示几页
         */
        @Override
        public int getCount() {
            return basePagers.size();
        }
        /**
         * 该函数用来判断instantiateItem(ViewGroup, int)
         * 函数所返回来的Key与一个页面视图是否是代表的同一个视图(即它俩是否是对应的，对应的表示同一个View)
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            //几乎是固定的写法,
            return view == object;
        }

        /**
         * 返回要显示的view,即要显示的视图
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = basePagers.get(position);
            View rootView = basePager.rootView;
            container.addView(rootView);   //这一步很重要
        //    basePager.initData();   不在此处调用viewpager的initData，避免数据预加载
            return rootView;
        }
        /**
         * 销毁条目
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
