package com.jd.raiders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.raiders.R;

import java.util.List;

/**
 * Created by houhuang on 18/1/8.
 */
public class FragmentListAdapter extends BaseAdapter {

    private List<String> mList;
    private Context mContext;

    public FragmentListAdapter(Context context, List<String> list)
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
            holder.imageView = (ImageView)view.findViewById(R.id.image);
            holder.textView = (TextView)view.findViewById(R.id.text);
            view.setTag(holder);
        }else
        {
            view = convertView;
            holder = (Holder) view.getTag();
        }

        holder.textView.setText(mList.get(position));

        return view;
    }

    class Holder
    {
        public ImageView imageView;
        public TextView textView;
    }
}
