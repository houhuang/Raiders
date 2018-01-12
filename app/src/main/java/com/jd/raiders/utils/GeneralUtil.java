package com.jd.raiders.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
}
