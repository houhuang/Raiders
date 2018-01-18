package com.jd.raiders.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by houhuang on 18/1/11.
 */
public class GeneralUtil {
    public static boolean checkNetwork(Context context)
    {
        if (context != null)
        {
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null)
            {
                return networkInfo.isAvailable();
            }
        }

        return false;
    }

    public static String handleJsonFile(Context context, String fileName)
    {
        StringBuilder builder = new StringBuilder();
        try{
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open("pub.json"),"UTF-8"));
            String line;
            while ((line = bf.readLine()) != null)
            {
                builder.append(line);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
