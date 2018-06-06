package bie.com.yuekao.Peneter;


import java.util.Map;

import bie.com.yuekao.Utils.MyOkhttpUtils;


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
        MyOkhttpUtils.getInstance().postData(getUrl,params, new MyOkhttpUtils.ICallback() {
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

