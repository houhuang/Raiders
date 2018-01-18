package com.jd.raiders.activity;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jd.raiders.R;
import com.jd.raiders.manager.DataManager;
import com.jd.raiders.utils.StatusBarUtils;

public class TextActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        StatusBarUtils.setWindowStatusBarColor(this, R.color.transparent);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(DataManager.getInstance().getCurrentTextTitle());
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);

        ImageView imageView = (ImageView)findViewById(R.id.toolbarimage);
        Glide.with(this).load(R.drawable.title).into(imageView);

        mTextView = (TextView)findViewById(R.id.textView);
        mTextView.setText(DataManager.getInstance().getCurrentTextContent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
