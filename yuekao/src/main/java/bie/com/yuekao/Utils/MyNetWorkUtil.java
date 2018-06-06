package bie.com.yuekao.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created 朱治文lenovo on 2018/6/1
 * .
 */

public class MyNetWorkUtil {


    public final static boolean hasNetWorkConnection(Context context){

        final ConnectivityManager connectivityManager= (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return (networkInfo!= null && networkInfo.isAvailable());
    }
   //是否为wifi网络
    public final static boolean hasWifiConnection(Context context) {
        final ConnectivityManager connectivityManager= (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return (networkInfo!=null&& networkInfo.isConnectedOrConnecting());
    }

    //判断网络是否可用,是否为移动网络

    public final static boolean hasGPRSConnection(Context context){

        final ConnectivityManager connectivityManager= (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return (networkInfo!=null && networkInfo.isAvailable());
    }
      //判断网络是否可用，并返回网络类型

    public static final int getNetWorkConnectionType(Context context){
        final ConnectivityManager connectivityManager=(ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo wifiNetworkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo mobileNetworkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifiNetworkInfo!=null &&wifiNetworkInfo.isAvailable())
        {
            return ConnectivityManager.TYPE_WIFI;
        }
        else if(mobileNetworkInfo!=null &&mobileNetworkInfo.isAvailable())
        {
            return ConnectivityManager.TYPE_MOBILE;
        }
        else {
            return -1;
        }
    }
}