package wangjin.com.beijingnews;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import wangjin.com.beijingnews.activity.GuideActivity;
import wangjin.com.beijingnews.activity.MainActivity;
import wangjin.com.beijingnews.utils.CacheUtils;

public class SplashActivity extends Activity {
    public static final String START_MAIN = "start_main";
    private RelativeLayout rl_splash_root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rl_splash_root = (RelativeLayout) findViewById(R.id.rl_splash_root);
        //渐变动画、缩放动画、旋转动画
        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setDuration(500);
        aa.setFillAfter(true);

        ScaleAnimation sa = new ScaleAnimation(0,1,0,1,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        sa.setDuration(500);
        sa.setFillAfter(true);

        RotateAnimation ra = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        ra.setFillAfter(true);
        ra.setDuration(500);

        AnimationSet set = new AnimationSet(false);
        //添加三个动画，没有先后顺序
        set.addAnimation(aa);
        set.addAnimation(sa);
        set.addAnimation(ra);
        set.setDuration(2000); //设置所有动画的时常，这个为准
       rl_splash_root.setAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
            /*
            *动画开始时回调这个方法
             */
            @Override
            public void onAnimationStart(Animation animation) {

            }
            /*
            *动画结束时回调这个方法
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                //判断是否进入过主页面
                boolean isStartMain = CacheUtils.getBoolean(SplashActivity.this, START_MAIN);
                if(isStartMain) {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                } else {
                    Intent intent = new Intent(SplashActivity.this,GuideActivity.class);
                    startActivity(intent);
                }
                finish();
            }
            /*
            *动画重复时回调这个方法
             */
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
