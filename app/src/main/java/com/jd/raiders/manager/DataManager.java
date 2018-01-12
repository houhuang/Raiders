package com.jd.raiders.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by houhuang on 18/1/8.
 */
public class DataManager {

    private List<List<String>> mFragmentData = new ArrayList<List<String>>();

    private static class DataHolder
    {
        private static final DataManager intance = new DataManager();
    }

    private DataManager(){};
    public static final DataManager getInstance()
    {
        return DataHolder.intance;
    }

    public void initFragmentData()
    {
        for (int i = 0; i < 4; ++i)
        {
            List<String> list = new ArrayList<String>();
            for (int j =0 ; j < 30; ++j)
            {
                Random random = new Random();

                int xx= (i+1)* (j+1)*10000;
                list.add(String.valueOf(xx));
            }
            mFragmentData.add(list);
        }
    }

    public List<List<String>> getFragmentData()
    {
        return mFragmentData;
    }
}
