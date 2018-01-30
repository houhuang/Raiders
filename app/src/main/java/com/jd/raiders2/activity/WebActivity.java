package com.jd.raiders2.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jd.raiders2.R;
import com.jd.raiders2.helper.MyWebView;
import com.jd.raiders2.loading.FoldingCirclesDrawable;
import com.jd.raiders2.manager.DataManager;
import com.jd.raiders2.utils.GeneralUtil;
import com.jd.raiders2.utils.StatusBarUtils;

public class WebActivity extends AppCompatActivity {

    private MyWebView mWebView;
    private ProgressBar mProgressBar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mContext = this;
        StatusBarUtils.setWindowStatusBarColor(this, R.color.homeStatusBarColorBlack);
//        StatusBarUtils.StatusBarLightMode(this);

        Drawable progressDrawable = new FoldingCirclesDrawable.Builder(this)
                .colors(getProgressDrawableColors())
                .build();
//        progressDrawable.setLevel(5000);

        mProgressBar = (ProgressBar)findViewById(R.id.google_progress);
        Rect bounds = mProgressBar.getIndeterminateDrawable().getBounds();
        mProgressBar.setIndeterminateDrawable(progressDrawable);
        mProgressBar.getIndeterminateDrawable().setBounds(bounds);

        mWebView = (MyWebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        mWebView.loadUrl("https://www.cnblogs.com/shytong/p/5240077.html");
//        mWebView.getSettings().setBlockNetworkImage(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setUseWideViewPort(true);

        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        if (GeneralUtil.checkNetwork(this))
        {
            mWebView.loadUrl(DataManager.getInstance().getCurrentTextContent());
        }else
        {

            Toast.makeText(this, "网络连接错误！", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   WebActivity.this.finish();
                }
            }, 2000);
        }

        mWebView.setWebViewClient(new WebViewClient()
        {


            @Override
            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);

                mProgressBar.setVisibility(View.GONE);


                super.onPageFinished(view, url);


            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {



                String s1 = request.getUrl().toString();
                int len1 = s1.length();
                String newS1 = s1.substring(len1 - 9, len1-5);

                String s2 = DataManager.getInstance().getCurrentTextContent();
                int len2 = s2.length();
                String newS2 = s2.substring(len2 - 10, len2-6);

//                Log.d("000000", newS1 + "    " + newS2);

                if (newS1.equals(newS2))
                {
                    view.loadUrl(request.getUrl().toString());
                }else
                {
                    WebActivity.this.finish();
                }

                return true;
            }
        });
    }

    private int[] getProgressDrawableColors() {
        int[] colors = new int[4];
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        colors[0] = ContextCompat.getColor(this, R.color.red);
        colors[1] = ContextCompat.getColor(this, R.color.blue);;
        colors[2] = ContextCompat.getColor(this, R.color.yellow);;
        colors[3] = ContextCompat.getColor(this, R.color.green);;

        return colors;
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = this.getSharedPreferences("userdata", Activity.MODE_PRIVATE);
        int adsCount = preferences.getInt("adscount", 0);
        if (adsCount >= 5)
        {
            SharedPreferences.Editor editor = this.getSharedPreferences("userdata", Activity.MODE_PRIVATE).edit();
            editor.putInt("adscount", 0);
            editor.commit();

            DataManager.getInstance().showRewardAds();
        }else
        {
            DataManager.getInstance().showAds();
        }



    }

    @Override
    public void onBackPressed() {
//        if(mWebView.canGoBack())
//        {
//            mWebView.goBack();
//        }else
//        {
//            super.onBackPressed();
//        }

        super.onBackPressed();
    }


}
