package com.jd.raiders2.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.jd.raiders2.R;
import com.jd.raiders2.manager.DataManager;
import com.jd.raiders2.player.YoutubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class VedioActivity extends AppCompatActivity {

    private List<YoutubePlayerView> playerViewList;//一个页面可以播放多个视频，将所有的播放控件收集到这里进行维护，主要是控制离开页面时候的暂停
    //定位到youtube的某个视频有三种方式
    public static final String VideoUrl_normal = "https://youtu.be/iHY7ZjZU32A";//这种是最普通的写在地址栏中的视频地址
    public static final String VideoUrl_embed = "https://www.youtube.com/embed/0xtcWek2tcM";//这种是分享嵌入式的视频地址
    public static final String VideoUrl_short = "https://youtu.be/wQ5Gj0UB_R8";//分享到facebook等社交平台的短url

    private View mVideoProgressView;
    private View mCustomView; //全屏显示的View
    private View mVideoFullScrreenBack;

    private LinearLayout ll_player_container;

    private int mOriginalSystemUiVisibility;
    private int mOriginalOrientation;

    private WebChromeClient.CustomViewCallback mCustomViewCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);

        String videoUrl = DataManager.getInstance().getCurrentTextContent();
        String videoID = YoutubePlayerView.parseIDfromVideoUrl(videoUrl);

        View youtubeView = LayoutInflater.from(this).inflate(R.layout.layout_youtube_player, null);
        YoutubePlayerView youtubePlayerView = (YoutubePlayerView)youtubeView.findViewById(R.id.youtubePlayerView);
        youtubePlayerView.setAutoPlayerHeight(this);
        youtubePlayerView.initialize(videoID, new YoutubePlayerCallBack(youtubePlayerView), mWebChromeClient);
        mVideoFullScrreenBack = findViewById(R.id.detail_video_back);
        if (playerViewList == null)
        {
            playerViewList = new ArrayList<>();
        }

        ll_player_container = (LinearLayout)findViewById(R.id.ll_player_container);
        ll_player_container.addView(youtubeView);
        playerViewList.add(youtubePlayerView);

    }

    private class YoutubePlayerCallBack implements YoutubePlayerView.YouTubeListener
    {
        private YoutubePlayerView mYoutubeView;

        YoutubePlayerCallBack(YoutubePlayerView view)
        {
            this.mYoutubeView = view;
        }

        @Override
        public void onReady() {

        }

        @Override
        public void onStateChange(YoutubePlayerView.STATE state) {
            if (state == YoutubePlayerView.STATE.PLAYING && mYoutubeView != null)
            {
                if (playerViewList != null)
                {
                    for (YoutubePlayerView v : playerViewList)
                    {
                        if (v != null && v != mYoutubeView && (v.getPlayerState() == YoutubePlayerView.STATE.PLAYING || v.getPlayerState() == YoutubePlayerView.STATE.PAUSED))
                        {
                            v.stop();
                        }
                    }
                }
            }
        }

        @Override
        public void onPlaybackQualityChange(String arg) {

        }

        @Override
        public void onPlaybackRateChange(String arg) {

        }

        @Override
        public void onError(String arg) {

        }

        @Override
        public void onApiChange(String arg) {

        }

        @Override
        public void onCurrentSecond(double second) {

        }

        @Override
        public void onDuration(double duration) {

        }

        @Override
        public void logs(String log) {

        }
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient()
    {
        @Override
        public View getVideoLoadingProgressView() {
//            return super.getVideoLoadingProgressView();
            if (mVideoProgressView == null)
            {
                LayoutInflater inflater =LayoutInflater.from(VedioActivity.this);
                mVideoProgressView = inflater.inflate(R.layout.vedio_layout_loading, null);
            }

            return mVideoProgressView;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
//            super.onShowCustomView(view, callback);

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null)
            {
                onHideCustomView();
                return;
            }

            // 1. Stash the current state
            mCustomView = view;
            mOriginalSystemUiVisibility = VedioActivity.this.getWindow().getDecorView().getSystemUiVisibility();
            mOriginalOrientation = VedioActivity.this.getRequestedOrientation();

            // 2. Stash the custom view callback
            mCustomViewCallback = callback;

            // 3. Add the custom view to the view hierarchy
            FrameLayout decor = (FrameLayout)VedioActivity.this.getWindow().getDecorView();
            decor.addView(mCustomView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            if (mVideoFullScrreenBack != null)
            {
                mVideoFullScrreenBack.setVisibility(View.VISIBLE);
            }

            // 4. Change the state of the window
            VedioActivity.this.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE);
            VedioActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }


        @Override
        public void onHideCustomView() {
//            super.onHideCustomView();

            // 1. Remove the custom view
            FrameLayout decor = (FrameLayout)VedioActivity.this.getWindow().getDecorView();
            decor.removeView(mCustomView);
            mCustomView = null;
            if (mVideoFullScrreenBack != null)
            {
                mVideoFullScrreenBack.setVisibility(View.GONE);
            }

            // 2. Restore the state to it's original form
            VedioActivity.this.getWindow().getDecorView().setSystemUiVisibility(mOriginalSystemUiVisibility);
            VedioActivity.this.setRequestedOrientation(mOriginalOrientation);

            // 3. Call the custom view callback
            if (mCustomViewCallback != null)
            {
                mCustomViewCallback.onCustomViewHidden();
                mCustomViewCallback = null;
            }

        }


    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        //视频播放器当页面停止的时候所有的视频播放全部暂停
        if (playerViewList != null)
        {
            for (YoutubePlayerView v : playerViewList)
            {
                if (v.getPlayerState() == YoutubePlayerView.STATE.PLAYING)
                {
                    v.pause();
                }else if (v.getPlayerState() == YoutubePlayerView.STATE.BUFFERING)
                {
                    v.stop();
                }
            }
        }

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerViewList != null)
        {
            for (YoutubePlayerView v : playerViewList)
            {
                if (v != null)
                    v.onDestroy();
            }
        }
    }

    @Override
    public void onBackPressed() {
        closeFullScreen();
        super.onBackPressed();
    }

    public boolean closeFullScreen()
    {
        if (mCustomView != null && mCustomViewCallback != null)
        {
            mWebChromeClient.onHideCustomView();
            return true;
        }
        return false;
    }
}
