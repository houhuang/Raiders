package com.jd.raiders.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jd.raiders.R;
import com.jd.raiders.helper.MyWebView;
import com.jd.raiders.loading.FoldingCirclesDrawable;
import com.jd.raiders.loading.NexusRotationCrossDrawable;
import com.jd.raiders.utils.GeneralUtil;
import com.jd.raiders.utils.StatusBarUtils;

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
        progressDrawable.setLevel(5000);

        mProgressBar = (ProgressBar)findViewById(R.id.google_progress);
        Rect bounds = mProgressBar.getIndeterminateDrawable().getBounds();
        mProgressBar.setIndeterminateDrawable(progressDrawable);
        mProgressBar.getIndeterminateDrawable().setBounds(bounds);

        mWebView = (MyWebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        mWebView.loadUrl("https://www.cnblogs.com/shytong/p/5240077.html");
//        mWebView.getSettings().setBlockNetworkImage(true);

        if (GeneralUtil.checkNetwork(this))
        {
            mWebView.loadUrl("https://www.cnblogs.com/shytong/p/5240077.html");
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
                super.onPageFinished(view, url);

                mProgressBar.setVisibility(View.GONE);

                if(url!=null && url.contains("/p/resource/weapon/iProductID/39")){

                    String fun="javascript:function getClass(parent,sClass) { var aEle=parent.getElementsByTagName('div'); var aResult=[]; var i=0; for(i<0;i<aEle.length;i++) { if(aEle[i].className==sClass) { aResult.push(aEle[i]); } }; return aResult; } ";

                    view.loadUrl(fun);

                    String fun2="javascript:function hideOther() {getClass(document,'nav-sides')[0].style.display='none'; getClass(document,'side-bar')[0].style.display='none'; getClass(document,'area-main')[0].style.display='none'; getClass(document,'home-foot')[0].style.display='none'; getClass(document,'enter')[0].style.display='none'; getClass(document,'crumb')[0].style.display='none';getClass(document,'date-tab clearfix')[0].style.display='none'; document.getElementById('id_sidebar').style.display='none'; document.getElementById('top_nav').style.display='none'; document.getElementById('fix-personal').style.display='none'; document.getElementById('waterlogo').style.display='none';getClass(document,'wrap')[0].style.minWidth=0;getClass(document,'game')[0].style.paddingTop=0;}";

                    view.loadUrl(fun2);

                    view.loadUrl("javascript:hideOther();");

                }


            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //webview content not jump
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
