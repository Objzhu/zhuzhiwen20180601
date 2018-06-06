package com.bie.zhuzhiwen20180601.Peneter;


import com.bie.zhuzhiwen20180601.Utils.OkhttpUtils;

import java.util.Map;


/**
 * Created 朱治文lenovo on 2018/6/1
 * .
 */

public class MyModel {
    /**
     * 请求数据
     * @param getUrl
     */
    public void getData(String getUrl, Map<String,String> params, final ResponseCallback responseCallback) {
        OkhttpUtils.getInstance().postData(getUrl,params, new OkhttpUtils.ICallback() {
            @Override
            public void success(String result) {
                responseCallback.success(result);
            }
            @Override
            public void fail(String msg) {
                responseCallback.fail(msg);
            }
        });
    }
    public interface  ResponseCallback{
        void success(String result);
        void fail(String msg);
    }
}

