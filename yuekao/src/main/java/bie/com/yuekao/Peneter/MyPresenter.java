package bie.com.yuekao.Peneter;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Map;

import bie.com.yuekao.INews;
import bie.com.yuekao.News;


/**
 * Created 朱治文lenovo on 2018/6/1
 * .
 */

public class MyPresenter {
    private INews iNews;
    private MyModel model;
    public MyPresenter(INews iNews) {
        model = new MyModel();
        attach(iNews);
    }

    public void attach(INews iNews){
        this.iNews = iNews;
    }

    public void getData(String getUrl, Map<String ,String> params) {
        model.getData(getUrl, params,new MyModel.ResponseCallback() {
            @Override
            public void success(String result) {
                if (!TextUtils.isEmpty(result)) {
                    String s = result.replace("null(","")
                            .replace(")","");

                    News news = new Gson().fromJson(s, News.class);
                    iNews.success(news);
                }
            }
            @Override
            public void fail(String msg) {
            }
        });
    }

    public void detach(){
        this.iNews = null;
    }
}




