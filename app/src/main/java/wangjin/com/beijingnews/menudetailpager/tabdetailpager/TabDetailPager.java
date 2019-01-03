package wangjin.com.beijingnews.menudetailpager.tabdetailpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import wangjin.com.beijingnews.R;
import wangjin.com.beijingnews.base.MenuDetaiBasePager;
import wangjin.com.beijingnews.domain.NewsCenterPagerBean2;
import wangjin.com.beijingnews.domain.TabDetailPagerBean;
import wangjin.com.beijingnews.utils.CacheUtils;
import wangjin.com.beijingnews.utils.Constans;
import wangjin.com.beijingnews.utils.LogUtil;


/**
 * Created by wangjin on 2018/10/7.
 * 页签详情页面
 */

public class TabDetailPager extends MenuDetaiBasePager {
    private ViewPager viewPager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private ListView listView;
    private TabDetailPagerListAdapter adapter;
    private ImageOptions imageOptions;
    private NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData;
    private String url;
    // 顶部轮播图
    private List<TabDetailPagerBean.DataBean.TopnewsBean> topnews;
    //新闻列表数据集合
    private List<TabDetailPagerBean.DataBean.NewsBean> news;
    public TabDetailPager(Context context, NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData) {
        super(context);
        this.childrenData = childrenData;
        imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(100),DensityUtil.dip2px(100))
                .setRadius(DensityUtil.dip2px(5))
                .setCrop(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.news_pic_default)
                .setFailureDrawableId(R.drawable.news_pic_default)
                .build();
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.tabdetail_pager,null);
        listView = view.findViewById(R.id.listview);

        View topNewsView = View.inflate(context,R.layout.topnews,null);
        viewPager = topNewsView.findViewById(R.id.viewpage);
        tv_title = topNewsView.findViewById(R.id.tv_title);
        ll_point_group = topNewsView.findViewById(R.id.ll_point_group);

        //把顶部轮播图部分视图，以头的方式添加到List VIew中
        listView.addHeaderView(topNewsView);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url = Constans.BASE_URL + childrenData.getUrl();
        //取出缓存数据
        String saveJson = CacheUtils.getString(context,url);
        if(!TextUtils.isEmpty(saveJson)) {
            //解析数据
            processDdate(saveJson);
        }
        LogUtil.e(childrenData.getTitle()+"的联网请求地址==" + url);
        //联网请求数据
        getDataFormNet();
    }
    private void getDataFormNet() {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //缓存数据
                CacheUtils.putString(context,url,result);
                LogUtil.e(childrenData.getTitle()+"页面数据请求成功==" + result);
                processDdate(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(childrenData.getTitle()+"页面数据请求失败==" + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e(childrenData.getTitle()+"页面数据请求onCancelled==" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e(childrenData.getTitle()+"页面数据请求onFinished==");
            }
        });
    }

    /**
     * 之前点高亮的位置
     */
    private int prePositon;

    private void processDdate(String json) {
        TabDetailPagerBean bean = parsedJson(json);
        LogUtil.e("解析成功"+bean.getData().getNews().get(0).getTitle());
        //顶部轮播图数据
        topnews = bean.getData().getTopnews();
        //设置适配器
        viewPager.setAdapter(new TabDetailPagerTopNewsAdapter());
        //添加红点和灰点
        addPoint();

        //监听页面的改变，设置红点的变化和文本变化
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        tv_title.setText(topnews.get(0).getTitle());

        //准备Listview对应的集合数据
        news = bean.getData().getNews();
        //设置listview的适配器
        adapter = new TabDetailPagerListAdapter();
        listView.setAdapter(adapter);
    }

    class TabDetailPagerListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null) {
                convertView = View.inflate(context,R.layout.item_tabdetail_pager,null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon =  convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
                viewHolder.tv_time =  convertView.findViewById(R.id.tv_time);
                convertView.setTag(viewHolder);
            } else {
               viewHolder =(ViewHolder) convertView.getTag();
            }

            //根据位置得到数据
            TabDetailPagerBean.DataBean.NewsBean newsData = news.get(position);
            String imageUrl = Constans.BASE_URL + newsData.getListimage();
            //请求图片
            x.image().bind(viewHolder.iv_icon,imageUrl,imageOptions);
            //设置标题
            viewHolder.tv_title.setText(newsData.getTitle());
            //设置时间
            viewHolder.tv_time.setText(newsData.getPubdate());
            return convertView;
        }
    }

    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;
    }
    private void addPoint() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(8),DensityUtil.dip2px(8));
        ll_point_group.removeAllViews(); //移除所有的红点
        for(int i = 0;i < topnews.size();i++) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.point_selector);
            imageView.setLayoutParams(params);
            if(i == 0) {
                imageView.setEnabled(true);
            } else {
                imageView.setEnabled(false);
                params.leftMargin = DensityUtil.dip2px(8);
            }
            ll_point_group.addView(imageView);
        }
    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //1、设置文本
            tv_title.setText(topnews.get(position).getTitle());
            //2、对应页面的点高亮-红色
            //把之前的变为黑色，当前为红色
            ll_point_group.getChildAt(prePositon).setEnabled(false);
            ll_point_group.getChildAt(position).setEnabled(true);
            prePositon = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    class TabDetailPagerTopNewsAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(R.drawable.home_scroll_default);
            container.addView(imageView);

            TabDetailPagerBean.DataBean.TopnewsBean topnewsData = topnews.get(position);
            String imageUrl = Constans.BASE_URL+topnewsData.getTopimage();
            //联网请求图片
           x.image().bind(imageView,imageUrl);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           // super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
    private TabDetailPagerBean parsedJson(String json) {
        return new Gson().fromJson(json,TabDetailPagerBean.class);
    }
}
