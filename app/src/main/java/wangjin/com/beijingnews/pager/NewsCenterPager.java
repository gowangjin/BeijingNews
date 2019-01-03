package wangjin.com.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import wangjin.com.beijingnews.activity.MainActivity;
import wangjin.com.beijingnews.base.BasePager;
import wangjin.com.beijingnews.base.MenuDetaiBasePager;
//import wangjin.com.beijingnews.domain.NewsCenterPagerBean;
import wangjin.com.beijingnews.domain.NewsCenterPagerBean2;
import wangjin.com.beijingnews.fragment.LeftmenuFragment;
import wangjin.com.beijingnews.menudetailpager.InteracMenuDetailPager;
import wangjin.com.beijingnews.menudetailpager.NewMenuDetailPager;
import wangjin.com.beijingnews.menudetailpager.PhotosMenuDetailPager;
import wangjin.com.beijingnews.menudetailpager.TopicMenuDetailPager;
import wangjin.com.beijingnews.utils.CacheUtils;
import wangjin.com.beijingnews.utils.Constans;
import wangjin.com.beijingnews.utils.LogUtil;

/**
 * Created by wangjin on 2018/8/24.
 */

public class NewsCenterPager extends BasePager {
//    private List<NewsCenterPagerBean.DataEntity> data;
    private List<NewsCenterPagerBean2.DetailPagerData> data2;
    /**
     * 详情页面集合
     */
    private ArrayList<MenuDetaiBasePager> detaiBasePagers;
    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        ib_menu.setVisibility(View.VISIBLE);
        tv_title.setText("新闻中心");
        //联网请求数据，创建视图
        TextView textView = new TextView(context);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        //把子视图添加到BasePager的FrameLayout中
        fl_content.addView(textView);
        //绑定数据
        textView.setText("新闻中心内容");
        //获取缓存数据
        String saveJson = CacheUtils.getString(context,Constans.NEWSCENTER_PAGER);
        if(!TextUtils.isEmpty(saveJson)) {
            processData(saveJson);
        }
        //联网请求数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constans.NEWSCENTER_PAGER);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                long endTime = SystemClock.uptimeMillis();
                LogUtil.e("使用想Utils3联网请求成功=="+result);
                //缓存数据
                CacheUtils.putString(context,Constans.NEWSCENTER_PAGER,result);
                processData(result);
                //设置适配器
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("使用想Utils3联网请求成功=="+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("使用想Utils3联网请求成功=="+cex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 解析json数据和显示数据
     * @param json
     */
    private void processData(String json) {
        //NewsCenterPagerBean bean = parsedJson(json);
        NewsCenterPagerBean2 bean2 = parsedJson(json);
        //data = bean.getData();
        data2 = bean2.getData();
        String title2 = bean2.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e("使用GSON解析json数据成功-title==" + title2);
        MainActivity mainActivity = (MainActivity) context;
        //得到左侧菜单
        LeftmenuFragment leftmenuFragment = mainActivity.getLeftmenuFragment();
        //leftmenuFragment.setData(data);
        //添加详情页面
        detaiBasePagers = new ArrayList<>();
        detaiBasePagers.add(new NewMenuDetailPager(context,data2.get(0)));
        detaiBasePagers.add(new TopicMenuDetailPager(context));
        detaiBasePagers.add(new PhotosMenuDetailPager(context));
        detaiBasePagers.add(new InteracMenuDetailPager(context));
        //把数据传递给左侧菜单
        leftmenuFragment.setData2(data2);
    }

    /**
     * 使用系统的API解析json
     * @param json
     * @return
     */
    private NewsCenterPagerBean2 parsedJson2(String json) {
        NewsCenterPagerBean2 bean2 = new NewsCenterPagerBean2();
        try {
            JSONObject object = new JSONObject(json);
            int retcode = object.optInt("retcode");
            bean2.setRetcode(retcode);  //retcode字段解析成功
            JSONArray data = object.optJSONArray("data");

            if(data != null && data.length()>0) {
                List<NewsCenterPagerBean2.DetailPagerData> dataPagerDatas = new ArrayList<>();
                //设置列表数据
                bean2.setData(dataPagerDatas);
                //for循环，解析每条数据
                for(int i=0;i<data.length();i++) {
                    LogUtil.e("data长度为：" + data.length());
                    JSONObject jsonObject = (JSONObject) data.get(i);
                    NewsCenterPagerBean2.DetailPagerData dataPagerData = new NewsCenterPagerBean2.DetailPagerData();
                    dataPagerDatas.add(dataPagerData);
                    int id = jsonObject.optInt("id");
                    dataPagerData.setId(id);
                    LogUtil.e("id为--------"+ id);
                    String title = jsonObject.optString("title");
                    LogUtil.e("标题为--------"+ title);
                    dataPagerData.setTitle(title);
                    int type = jsonObject.optInt("type");
                    dataPagerData.setType(type);
                    String url = jsonObject.optString("url");
                    dataPagerData.setUrl(url);
                    String url1 = jsonObject.optString("url1");
                    dataPagerData.setUrl1(url1);
                    String dayurl = jsonObject.optString("dayurl");
                    dataPagerData.setDayurl(dayurl);
                    String excurl = jsonObject.optString("excurl");
                    dataPagerData.setExcurl(excurl);
                    String weekurl = jsonObject.optString("weekurl");
                    dataPagerData.setWeekurl(weekurl);

                    //解析第一个单元中的children
                    /**
                     * 在这个地方曾出现过问题，当时使用getJSONArray来获取children， 但只出现到新闻和专题，而且专题一点即报错
                     * 要牢记，使用getJSONArray获取，对象不能为空
                     */
                    JSONArray children = jsonObject.optJSONArray("children");
                    if(children != null && children.length()>0) {
                        List<NewsCenterPagerBean2.DetailPagerData.ChildrenData> childrenDates = new ArrayList<>();
                        //设置集合-childrenDates
                        dataPagerData.setChildren(childrenDates);
                        for(int j=0;j<children.length();j++) {
                            NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData = new NewsCenterPagerBean2.DetailPagerData.ChildrenData();
                            childrenDates.add(childrenData);
                            JSONObject childrenItem = (JSONObject)children.get(j);
                            int childId = childrenItem.optInt("id");
                            childrenData.setId(childId);
                            String childTitle = childrenItem.optString("title");
                            childrenData.setTitle(childTitle);
                            int childType = childrenItem.optInt("type");
                            childrenData.setType(childType);
                            String childUrl = childrenItem.optString("url");
                            childrenData.setUrl(childUrl);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean2;
    }
    /**
     * 解析json数据：1、使用系统的API解析json；2,使用第三方框架解析json数据，例如Gson,fastjson
     * @param json
     * @return
     */
//    private NewsCenterPagerBean parsedJson(String json) {
//        return new Gson().fromJson(json,NewsCenterPagerBean.class);
//    }
    private NewsCenterPagerBean2 parsedJson(String json) {
        return new Gson().fromJson(json,NewsCenterPagerBean2.class);
    }
    /**
     * 根据位置切换详情页面
     */
    public void switchPager(int position) {
        //1、设置标题
        tv_title.setText(data2.get(position).getTitle());
        //2、移除之前内容
        fl_content.removeAllViews();
        //3、添加新内容
        MenuDetaiBasePager detaiBasePager = detaiBasePagers.get(position);
        View rootView = detaiBasePager.rootView;
        detaiBasePager.initData(); //初始化数据
        fl_content.addView(rootView);
    }
}
