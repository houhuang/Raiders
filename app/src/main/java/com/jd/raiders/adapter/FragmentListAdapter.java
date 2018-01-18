package com.jd.raiders.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.raiders.R;
import com.jd.raiders.activity.TextActivity;
import com.jd.raiders.helper.TrigonView;
import com.jd.raiders.manager.Base;

import java.util.List;

/**
 * Created by houhuang on 18/1/8.
 */
public class FragmentListAdapter extends BaseAdapter {

    private List<Base> mList;
    private Context mContext;

    public FragmentListAdapter(Context context, List<Base> list)
    {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        Holder holder = new Holder();
        if (convertView == null)
        {
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_list_item, parent, false);
            holder.imageView = (TrigonView) view.findViewById(R.id.image_trigon);
            holder.textView = (TextView)view.findViewById(R.id.text);
            view.setTag(holder);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, TextActivity.class);
//                    mContext.startActivity(intent);
//
//                }
//            });
        }else
        {
            view = convertView;
            holder = (Holder) view.getTag();
        }

        holder.textView.setText(mList.get(position).getTitle());

        boolean isShow = mContext.getSharedPreferences("userdata", Activity.MODE_PRIVATE).getBoolean(mList.get(position).getTitle(), false);
        if (!isShow)
        {
            holder.imageView.setVisibility(View.VISIBLE);
        }else
        {
            holder.imageView.setVisibility(View.INVISIBLE);
        }


        return view;
    }

    class Holder
    {
        public TrigonView imageView;
        public TextView textView;
    }
}
