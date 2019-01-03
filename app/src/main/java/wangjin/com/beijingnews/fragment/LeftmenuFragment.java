package wangjin.com.beijingnews.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;
import wangjin.com.beijingnews.R;
import wangjin.com.beijingnews.activity.MainActivity;
import wangjin.com.beijingnews.base.BaseFragment;
//import wangjin.com.beijingnews.domain.NewsCenterPagerBean;
import wangjin.com.beijingnews.domain.NewsCenterPagerBean2;
import wangjin.com.beijingnews.pager.NewsCenterPager;
import wangjin.com.beijingnews.utils.DensityUtil;
import wangjin.com.beijingnews.utils.LogUtil;

import static android.R.attr.data;

/**
 * Created by wangjin on 2018/8/12.
 */

public class LeftmenuFragment extends BaseFragment {
   // List<NewsCenterPagerBean.DataEntity> data;
    List<NewsCenterPagerBean2.DetailPagerData> data2;
    //适配器
    private LeftmenuFragmentAdapter adapter;
    private ListView listView;
    /**
     * 点击的位置
     */
    private int prePosition;
    @Override
    public View initView() {
        LogUtil.e("左侧菜单视图被初始化了");
        listView = new ListView(context);
        listView.setPadding(0, DensityUtil.dip2px(context,40),0,0);
        listView.setDividerHeight(0);  //设置分割线为0
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(android.R.color.transparent);  //设置按下不变色
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1、记录点击位置，变成红色
                prePosition = position;
                adapter.notifyDataSetChanged();
                //2、把左侧菜单关闭
                MainActivity mainActivity = (MainActivity)context;
                mainActivity.getSlidingMenu().toggle();//关<->开
                //3、切换到对应的详情页面：新闻详情页面，专题详情页面，图组详情页面，互动详情页面
               swichPager(prePosition);
            }
        });
        return listView;
    }
    /**
     * 根据位置切换不同详情页面
     * @param position
     */
    private void swichPager(int position) {
        MainActivity mainActivity = (MainActivity) context;
        ContentFragment contentFragment = mainActivity.getContentFragment();
        NewsCenterPager newsCenterPager = contentFragment.getNewsCenterPager();
        newsCenterPager.switchPager(position);
    }
    @Override
    public void initData() {
        super.initData();
    }

//    public void setData(List<NewsCenterPagerBean.DataEntity> data) {
//      //  this.data = data;
//       //设置适配器
//        adapter = new LeftmenuFragmentAdapter();
//        listView.setAdapter(adapter);
//    }
    public void setData2(List<NewsCenterPagerBean2.DetailPagerData> data2) {
        this.data2 = data2;
        //设置适配器
        adapter = new LeftmenuFragmentAdapter();
        listView.setAdapter(adapter);
        //设置默认页面
        swichPager(prePosition);
    }
    class LeftmenuFragmentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data2.size();
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
            TextView textView =(TextView) View.inflate(context, R.layout.item_leftmenu,null);
            textView.setText(data2.get(position).getTitle());
            textView.setEnabled(position==prePosition);
            LogUtil.e("LeftmenuFragmentAdapter初始化了"+data2.get(position).getTitle());
            return textView;
        }
    }
}
