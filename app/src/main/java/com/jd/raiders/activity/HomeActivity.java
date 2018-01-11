package com.jd.raiders.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jd.raiders.R;
import com.jd.raiders.adapter.HomeFragmentAdapter;
import com.jd.raiders.fragment.HomeFragment;
import com.jd.raiders.helper.ViewPagerScroller;
import com.jd.raiders.manager.DataManager;
import com.jd.raiders.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    private ImageView img_show;
    private Animation animation = null;
    private ViewPager mViewPager;

    private TextView[] mTextView = new TextView[4];
    private List<HomeFragment> mFragmentList = new ArrayList<HomeFragment>();

    private int current_page = 0;
    private int screenWidth;

    private int orignalLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mContext = this;

        StatusBarUtils.setWindowStatusBarColor(this, R.color.homeStatusBarColor);
        StatusBarUtils.StatusBarLightMode(this);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;

        bindViews();
        initViewPager();


    }

    private void initViewPager()
    {

        DataManager.getInstance().initFragmentData();

        HomeFragment f1 = new HomeFragment(this, DataManager.getInstance().getFragmentData().get(0));
        HomeFragment f2 = new HomeFragment(this, DataManager.getInstance().getFragmentData().get(1));
        HomeFragment f3 = new HomeFragment(this, DataManager.getInstance().getFragmentData().get(2));
        HomeFragment f4 = new HomeFragment(this, DataManager.getInstance().getFragmentData().get(3));

        mFragmentList.add(f1);
        mFragmentList.add(f2);
        mFragmentList.add(f3);
        mFragmentList.add(f4);

        mViewPager = (ViewPager)findViewById(R.id.viewPager);
        mViewPager.setAdapter(new HomeFragmentAdapter(getSupportFragmentManager(), mFragmentList));
        mViewPager.setCurrentItem(0, false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                int prePage = current_page;
                current_page = position;

                if (current_page != prePage)
                {
                    setTopMenuEnable(false);
                    imageRunAnimation(300, (current_page-prePage)*screenWidth/4);
                    mViewPager.setCurrentItem(current_page);
                }else
                {
                    setTopMenuEnable(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        /**
         *ViewPager Animation Duration
         **/

//        ViewPagerScroller scroller = new ViewPagerScroller(this);
//        scroller.setScrollDuration(1000);
//        scroller.initViewPagerScroll(mViewPager);

//        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
//            @Override
//            public void transformPage(View page, float position) {
//                final float offset = Math.abs(Math.abs(position) - 1);
////                page.setAlpha(offset);
//
////                page.setScaleX(offset/2 + 0.5f);
////                page.setScaleY(offset/2 + 0.5f);
//
//                page.setRotationY(position * -45);
//            }
//        });

    }

    private void bindViews() {
        img_show = (ImageView)findViewById(R.id.img_show);

        mTextView[0] = (TextView)findViewById(R.id.t1);
        mTextView[1] = (TextView)findViewById(R.id.t2);
        mTextView[2] = (TextView)findViewById(R.id.t3);
        mTextView[3] = (TextView)findViewById(R.id.t4);

        for (int i=0; i < mTextView.length; ++i) {
            mTextView[i].setOnClickListener(this);
        }

        orignalLeft = img_show.getLeft();

    }

    @Override
    public void onClick(View view) {
        setTopMenuEnable(false);
        int prePage = current_page;
        switch (view.getId())
        {
            case R.id.t1:
                current_page = 0;
                break;
            case R.id.t2:
                current_page = 1;
                break;
            case R.id.t3:
                current_page = 2;
                break;
            case R.id.t4:
                current_page = 3;
                break;
            default:
        }
        if (current_page != prePage)
        {
            imageRunAnimation(0, (current_page-prePage)*screenWidth/4);
            mViewPager.setCurrentItem(current_page, false);

        }else
        {
            setTopMenuEnable(true);
        }


    }

    private void imageRunAnimation(int time, final int offset)
    {
        for (int i = 0; i < mTextView.length; ++i)
        {
            mTextView[i].setTextColor(ContextCompat.getColor(this, R.color.home_topBar_textRelease));
            mTextView[i].setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }

        animation = new TranslateAnimation(0, offset, 0, 0);
        animation.setDuration(time);
        animation.setFillAfter(true);
        animation.setInterpolator(new DecelerateInterpolator());

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img_show.clearAnimation();


                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) img_show.getLayoutParams();
                params.setMargins(img_show.getLeft() + offset, 0, 0, 0);
                img_show.setLayoutParams(params);

                mTextView[current_page].setTextColor(ContextCompat.getColor(mContext, R.color.home_topBar_textPress));
                mTextView[current_page].setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                setTopMenuEnable(true);

//                new Handler().postDelayed(new Runnable(){
//                    public void run() {
//                        //execute the task
//                        setTopMenuEnable(true);
//                    }
//                }, 100);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        img_show.startAnimation(animation);
    }

    private void setTopMenuEnable(boolean isEnable)
    {
        for (int i = 0; i < mTextView.length; ++i)
        {
            mTextView[i].setEnabled(isEnable);
        }
    }

    private boolean isQuit = false;

    @Override
    public void onBackPressed() {
        if (!isQuit) {
            Toast.makeText(HomeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            isQuit = true; //这段代码意思是,在两秒钟之后isQuit会变成false
             new Thread(new Runnable() {
                 @Override public void run() {
                     try {
                         Thread.sleep(2000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     } finally {
                         isQuit = false;
                     } } }).start();

        } else {
            System.exit(0);
        }

    }
}
