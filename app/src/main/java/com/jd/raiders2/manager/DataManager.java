package com.jd.raiders2.manager;

import android.content.Context;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.jd.raiders2.activity.HomeActivity;
import com.jd.raiders2.utils.FileUtils;
import com.jd.raiders2.utils.GeneralUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houhuang on 18/1/8.
 */
public class DataManager {
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    private AdRequest   mAdrequest;

    private static final String INTERSTITIAL_ID = "ca-app-pub-9291877653530829/6990179292";
    private static final String REWARD_ID = "ca-app-pub-9291877653530829/3066412004";

    public int current_page = 0;
    public int current_index = 0;

    private List<List<Base>> mFragmentData = new ArrayList<List<Base>>();

//    private List<Base> mBasicList = new ArrayList<Base>();


    private static class DataHolder
    {
        private static final DataManager intance = new DataManager();
    }

    private DataManager(){};
    public static final DataManager getInstance()
    {
        return DataHolder.intance;
    }

    public void initFragmentData(Context context)
    {
//        String jsonStr = GeneralUtil.handleJsonFile(context, "pub.json");

        String jsonStr = FileUtils.readFileFrom_datadata(context, HomeActivity.DATAFILE);

        if (TextUtils.isEmpty(jsonStr))
        {
            jsonStr = GeneralUtil.handleJsonFile(context, "pub.json");
        }

        //基础
        try {
            List<Base> list = new ArrayList<Base>();
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("jichu");
            for (int i = 0; i < jsonArray.length(); ++i)
            {
                BasicData data = new BasicData();
                JSONObject object = jsonArray.getJSONObject(i);
                data.setTitle(object.getString("title"));
                data.setContent(object.getString("con"));
                list.add(data);

            }

            mFragmentData.add(list);

            List<Base> list1 = new ArrayList<>();
            JSONArray jinArray = jsonObject.getJSONArray("jinjie");
            for (int i = 0; i < jinArray.length(); ++i)
            {
                BasicData data = new BasicData();
                JSONObject object = jinArray.getJSONObject(i);
                data.setTitle(object.getString("title"));
                data.setContent(object.getString("con"));
                list1.add(data);

            }

            mFragmentData.add(list1);

            List<Base> list2 = new ArrayList<>();
            JSONArray jinArray2 = jsonObject.getJSONArray("gaoji");
            for (int i = 0; i < jinArray2.length(); ++i)
            {
                BasicData data = new BasicData();
                JSONObject object = jinArray2.getJSONObject(i);
                data.setTitle(object.getString("title"));
                data.setContent(object.getString("con"));
                list2.add(data);

            }

            mFragmentData.add(list2);

            List<Base> list3 = new ArrayList<Base>();
            JSONArray jsonArray3 = jsonObject.getJSONArray("vedio");
            for (int i = 0; i < jsonArray3.length(); ++i)
            {
                BasicData data = new BasicData();
                JSONObject object = jsonArray3.getJSONObject(i);
                data.setTitle(object.getString("title"));
                data.setContent(object.getString("con"));
                list3.add(data);
            }

            mFragmentData.add(list3);

        }catch (JSONException e)
        {
            e.printStackTrace();
        }

        for (int i = 3; i < 4; ++i)
        {
            List<Base> list = new ArrayList<Base>();
            for (int j =0 ; j < 30; ++j)
            {
                Base base = new Base();
                base.setTitle("123456789");

                list.add(base);
            }
            mFragmentData.add(list);
        }
    }

    public List<List<Base>> getFragmentData()
    {
        return mFragmentData;
    }

//    public List<Base> getBasicData()
//    {
//        return mBasicList;
//    }

    public String getCurrentTextContent()
    {
        return ((BasicData)mFragmentData.get(current_page).get(current_index)).getContent();
//        return ((BasicData)mBasicList.get(current_index)).getContent();
    }

    public String getCurrentTextTitle()
    {
        return ((BasicData)mFragmentData.get(current_page).get(current_index)).getTitle();
    }


    public void initAds(Context context)
    {
        mAdrequest = new AdRequest.Builder().build();

        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(INTERSTITIAL_ID);
        mInterstitialAd.loadAd(mAdrequest);
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                mInterstitialAd.loadAd(mAdrequest);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAd.loadAd(mAdrequest);
            }
        });


        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                Log.d("","");
            }

            @Override
            public void onRewardedVideoAdOpened() {
                Log.d("","");
            }

            @Override
            public void onRewardedVideoStarted() {
                Log.d("","");
            }

            @Override
            public void onRewardedVideoAdClosed() {
                mRewardedVideoAd.loadAd(REWARD_ID, mAdrequest);
                Log.d("","");
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                Log.d("","");
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Log.d("","");
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Log.d("","");
                mRewardedVideoAd.loadAd(REWARD_ID, mAdrequest);
            }
        });

        mRewardedVideoAd.loadAd(REWARD_ID, mAdrequest);

    }

    public void showAds()
    {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

    }

    public void showRewardAds()
    {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }else
        {
            showAds();
        }
    }

    public RewardedVideoAd getmRewardedVideoAd()
    {
        return mRewardedVideoAd;
    }
}
