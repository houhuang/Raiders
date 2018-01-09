package com.jd.raiders.activity;

import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jd.raiders.R;

public class WebActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebView = (WebView)findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.loadUrl("http://www.gamersky.com/handbook/201704/892518.shtml");

        mWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                view.loadUrl("javascript:function setTop(){document.querySelector('.ad-footer').style.display=\"none\";}setTop();");


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


        });
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack())
        {
            mWebView.goBack();
        }else
        {
            super.onBackPressed();
        }
    }


}
