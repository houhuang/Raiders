package com.jd.raiders.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jd.raiders.R;
import com.jd.raiders.activity.TextActivity;
import com.jd.raiders.activity.WebActivity;
import com.jd.raiders.adapter.FragmentListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by houhuang on 18/1/8.
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

    private List<String> mList = new ArrayList<String>();
    private ListView mListVew;
    private Context mContext;
    private FragmentListAdapter adapter;

    public HomeFragment() {
        super();
    }

    public HomeFragment(Context context, List<String> list) {
        super();
        mList = list;
        mContext = context;
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

                Intent intent = new Intent(mContext, TextActivity.class);
                startActivity(intent);

            }
        });

    }
}
