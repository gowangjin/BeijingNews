package wangjin.com.beijingnews.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wangjin.com.beijingnews.activity.MainActivity;

/**
 * Created by wangjin on 2018/8/12.
 * 基本的Fragment，LeftMenuFragment和ContentFragment将继承它
 */

public abstract class BaseFragment extends Fragment {
    public Activity context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 让孩子实现自己的布局
     * @return
     */
    public abstract View initView();
    /**
     * 初始化数据，如果没有数据，就联网获取
     */
    public void initData() {

    }
}
