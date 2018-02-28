package com.jd.raiders2.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jd.raiders2.R;
import com.jd.raiders2.activity.TextActivity;
import com.jd.raiders2.activity.VedioActivity;
import com.jd.raiders2.activity.WebActivity;
import com.jd.raiders2.adapter.FragmentListAdapter;
import com.jd.raiders2.manager.Base;
import com.jd.raiders2.manager.DataManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by houhuang on 18/1/8.
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

    private List<Base> mList = new ArrayList<Base>();
    private ListView mListVew;
    private Context mContext;
    private FragmentListAdapter adapter;

    private int page;

    public HomeFragment() {
        super();
    }

    public HomeFragment(Context context, List<Base> list, int page) {
        super();
        mList = list;
        mContext = context;
        this.page = page;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initListView(view);
        return view;
    }


    private void initListView(View view)
    {
        adapter = new FragmentListAdapter(mContext, mList);
        mListVew = (ListView)view.findViewById(R.id.listView);
        mListVew.setAdapter(adapter);
        mListVew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataManager.getInstance().current_page = page;
                DataManager.getInstance().current_index = position;

                SharedPreferences preferences = mContext.getSharedPreferences("userdata", Activity.MODE_PRIVATE);
                int adsCount = preferences.getInt("adscount", 0);
                adsCount ++;

                SharedPreferences.Editor editor = mContext.getSharedPreferences("userdata", Activity.MODE_PRIVATE).edit();
                editor.putBoolean(mList.get(position).getTitle(), true);
                editor.putInt("adscount", adsCount);
                editor.commit();

                if (page == 0 || page == 1)
                {
                    Intent intent = new Intent(mContext, TextActivity.class);
                    startActivity(intent);
                }else if (page == 2)
                {
                    Intent intent = new Intent(mContext, WebActivity.class);
                    startActivity(intent);
                }else if (page == 3)
                {
                    Intent intent = new Intent(mContext, VedioActivity.class);
                    startActivity(intent);
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       adapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.fragment_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //download data
            }
        });


    }
}
