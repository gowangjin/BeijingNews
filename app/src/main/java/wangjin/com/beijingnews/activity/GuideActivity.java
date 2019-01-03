package wangjin.com.beijingnews.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import wangjin.com.beijingnews.R;
import wangjin.com.beijingnews.SplashActivity;
import wangjin.com.beijingnews.utils.CacheUtils;
import wangjin.com.beijingnews.utils.DensityUtil;

import static android.R.attr.imageWellStyle;
import static android.R.attr.left;
import static android.R.attr.visibility;

public class GuideActivity extends AppCompatActivity {
    private ViewPager viewpager;
    private Button btn_start_main;
    private LinearLayout ll_point_group;
    private ArrayList<ImageView> imageViews;
    private ImageView iv_red_point;
    private int leftmax;
    private int widthdpi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        viewpager = (ViewPager)findViewById(R.id.viewpage);
        btn_start_main=(Button)findViewById(R.id.btn_start_main);
        ll_point_group=(LinearLayout)findViewById(R.id.ll_point_group);
        iv_red_point = (ImageView)findViewById(R.id.iv_red_point);

        btn_start_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存进入引导页面的记录
                CacheUtils.putBoolean(GuideActivity.this, SplashActivity.START_MAIN,true);
                //跳转到主页面
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                //关闭此activity
                finish();
            }
        });
        //准备数据
        int [] ids = new int[] {R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};

        imageViews = new ArrayList<ImageView>();
        for(int i=0;i < ids.length;i++) {
            ImageView imageView = new ImageView(this);
            //设置背景
            imageView.setBackgroundResource(ids[i]);
            //添加到集合
            imageViews.add(imageView);
            //创建点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            /*
            **LayoutParams 是子控件用来告诉父控件自己要如何布局时用的。
            * Layoutparmas是ViewGroup的一个内部类，是一个基类，主要描述宽高
             */
            widthdpi = DensityUtil.dip2px(this,10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthdpi,widthdpi);
            if(i != 0) {
                //不包括第0个，每个相距30像素
                params.leftMargin = widthdpi;
            }
            point.setLayoutParams(params);
            //添加到线性布局里面
            ll_point_group.addView(point);
        }

        //设置viewpager适配器
        viewpager.setAdapter(new MypagerAdapter());
        //根据viewde生命周期，当视图执行到onLayout或者onDraw的时候，视图的宽和高，边距都有了
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
        //得到屏幕滑动的百分比
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * 当页面滚动会回调这个方法
         * @param position 当前滑动液面的位置
         * @param positionOffset 页面滑动的百分比
         * @param positionOffsetPixels 滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               //两点间移动的距离 = 屏幕滑动百分比 * 间距
            //int leftmargin =(int)( (positionOffset * leftmax));
            //两点滑动距离对应的坐标 = 原来的起始位置 + 两点移动的距离
            int leftmargin = (int)(position*leftmax + (positionOffset * leftmax));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();
            params.leftMargin = leftmargin;
            iv_red_point.setLayoutParams(params);

        }

        /**
         * 当页面被选中的时候，回调这个方法
         * @param position 被选中页面的对应位置
         */
        @Override
        public void onPageSelected(int position) {
                if(position == imageViews.size() -1) {
                    //最后一个页面
                    btn_start_main.setVisibility(View.VISIBLE);
                } else {
                    btn_start_main.setVisibility(View.GONE);
                }
        }

        /**
         * 当viewpager页面滑动状态发生改变的时候
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
                //执行不止一次，执行完和删除监听
            iv_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            //灰点间的间距
            leftmax = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();
        }
    }

    class MypagerAdapter extends PagerAdapter {
        /*
        **返回数据的总个数
         */
        @Override
        public int getCount() {
            return imageViews.size();
        }

        /**
         * getview
         * @param container Viewpager
         * @param position  要创建页面的位置
         * @return  返回和创建当前页面有关联的值
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            //添加到容器中
            container.addView(imageView);
            // return position;
            return imageView;
        }

        /*
                判断
                *@view 当前创建的视图
                * @object 上面instantiateItem返回的结果值
                 */
        @Override
        public boolean isViewFromObject(View view, Object object) {
           // return view == imageViews.get(Integer.parseInt((String)object));
            return view == object;
        }

        /**
         * 销毁页面
         * @param container viewpager
         * @param position  要销毁页面的位置
         * @param object     要销毁的页面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((ImageView)object);
        }
    }
}
