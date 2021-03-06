package wangjin.com.beijingnews.menudetailpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import wangjin.com.beijingnews.R;
import wangjin.com.beijingnews.activity.MainActivity;
import wangjin.com.beijingnews.base.MenuDetaiBasePager;
import wangjin.com.beijingnews.domain.NewsCenterPagerBean2;
import wangjin.com.beijingnews.menudetailpager.tabdetailpager.TabDetailPager;
import wangjin.com.beijingnews.utils.LogUtil;

/**
 * Created by wangjin on 2018/9/9.
 * 新闻详情页面
 */

public class NewMenuDetailPager extends MenuDetaiBasePager {
    @ViewInject(R.id.tabPageIndicator)
    private TabPageIndicator tabPageIndicator;
    @ViewInject(R.id.viewpage)
    private ViewPager viewPager;
    @ViewInject(R.id.ib_tab_next)
    private ImageButton ib_tab_next;
    //数据
    private List<NewsCenterPagerBean2.DetailPagerData.ChildrenData> children;
    //页签页面的集合
    private ArrayList<TabDetailPager> tabDetailPagers;
    public NewMenuDetailPager(Context context, NewsCenterPagerBean2.DetailPagerData detailPagerData) {
        super(context);
        children = detailPagerData.getChildren();
    }

    @Override
    public View initView() {
        final View view = View.inflate(context, R.layout.newsmenu_detail_pager,null);
        x.view().inject(NewMenuDetailPager.this,view);
        ib_tab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("新闻详情页面数据被初始化了..");
        //准备新闻详情页面的数据
        tabDetailPagers = new ArrayList<>();
        for(int i=0;i<children.size();i++) {
            tabDetailPagers.add(new TabDetailPager(context,children.get(i)));
        }
        //设置viewpager的适配器
        viewPager.setAdapter(new MyNewsMenuDetailPagerAdapter());
        //ViewPager和TabPageIndicator关联
        tabPageIndicator.setViewPager(viewPager);
        // 注意以后监听页面的变化，TabPageIndicator监听页面的变化
        tabPageIndicator.setOnPageChangeListener(new MyOnPageChangeListener());
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

   class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
       @Override
       public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

       }

       @Override
       public void onPageSelected(int position) {
           if (position == 0) {
               //SlidingMenu可以全屏滑动
               isEnableSlidingMenu(true);
           } else {
               //SlidingMenu不可以全屏滑动
               isEnableSlidingMenu(false);
           }
       }

       @Override
       public void onPageScrollStateChanged(int state) {

       }
   }
    private class MyNewsMenuDetailPagerAdapter extends PagerAdapter {
        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).getTitle();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager tabDetailPager = tabDetailPagers.get(position);
            View rootView = tabDetailPager.rootView;
            tabDetailPager.initData(); //初始化数据
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
