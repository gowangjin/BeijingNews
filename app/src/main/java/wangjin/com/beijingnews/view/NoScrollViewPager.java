package wangjin.com.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**Viewpager，视图翻页工具，提供了多页面切换的效果。Android 3.0后引入的一个UI控件，位于v4包中。低版本使用需要导入v4包，
 * 但是现在我们开发的APP一般不再兼容3.0及以下的系统版本，另外现在大多数使用Android studio进行开发，默认导入v7包，v7包含了v4
 * Viewpager使用起来就是我们通过创建adapter给它填充多个view，左右滑动时，切换不同的view。
 * Google官方是建议我们使用Fragment来填充ViewPager的，这样 可以更加方便的生成每个Page，以及管理每个Page的生命周期
 * Created by wangjin on 2018/8/26.
 */

public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
