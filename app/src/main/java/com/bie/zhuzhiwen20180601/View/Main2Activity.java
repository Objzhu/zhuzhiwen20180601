package com.bie.zhuzhiwen20180601.View;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.bie.zhuzhiwen20180601.Peneter.MyPresenter;
import com.bie.zhuzhiwen20180601.R;
import com.bie.zhuzhiwen20180601.Utils.NetWorkUtil;
import com.bie.zhuzhiwen20180601.V2.Constants;
import com.bie.zhuzhiwen20180601.V2.INews;
import com.bie.zhuzhiwen20180601.V2.News;
import com.bie.zhuzhiwen20180601.adapter.MyAdapter;
import com.bie.zhuzhiwen20180601.modle.MyHelper;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃ 神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 *
 * ━━━━━━感觉萌萌哒━━━━━━
 */
public class Main2Activity extends AppCompatActivity implements INews {
    private XRecyclerView rv;
    //使用xRecyClerView做列表展示页面，并实现下拉刷新，上拉加载更多的功能
    private MyPresenter presenter;
    private int page = 5010;
    private List<News.Data> data;
    private boolean isRefresh = true;//判断是下拉刷新还是上拉加载
    private MyAdapter newsAdapter;
    private MyHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initData();
    }
    private void initData() {
        if (getIntent().getExtras() != null) {
            page = 5010 + Integer.parseInt(getIntent().getExtras().getString("id"));
        }
        dbHelper = new MyHelper(this);
        presenter = new MyPresenter(this);
        data = new ArrayList<>();
        request();
    }
    public void request() {
        if (NetWorkUtil.hasWifiConnection(this) || NetWorkUtil.hasGPRSConnection(this)) {
            Map<String, String> p = new HashMap<>();
            p.put("type", page + "");
            presenter.getData(Constants.GET_URL, p);
        } else {
            Toast.makeText(this, "网络不通畅，请稍后再试！", Toast.LENGTH_SHORT).show();
            String json = null;


            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("select * from news", null);
            while (cursor.moveToNext()){
                json = cursor.getString(cursor.getColumnIndex("json"));
            }
            //本地列表刷新
            fillLocalData(json);
        }
    }

    // 本地列表刷新
    private void fillLocalData(String json) {
        News news = new Gson().fromJson(json,News.class);
        newsAdapter = new MyAdapter(news.data, this,news);
        rv.setAdapter(newsAdapter);
    }

    private void initView() {
        rv = findViewById(R.id.rv);
        //设置局部刷新动画
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setPullRefreshEnabled(true);
        rv.setLoadingMoreEnabled(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                isRefresh = true;
                //下拉刷新
                page = 5010;
                request();
            }
            @Override
            public void onLoadMore() {
                isRefresh = false;
                page++;
                request();

            }
        });
    }
    @Override
    public void success(News news) {
        //json串转换
        String json = new Gson().toJson(news);
        System.out.println("size:" + news.data.size());
        data = news.data;
        if (isRefresh) {
            newsAdapter = new MyAdapter(data, this,news);
            rv.setAdapter(newsAdapter);
            rv.refreshComplete();
            //保存json串数据
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("json", json);
            db.insert(MyHelper.NEWS_TABLE_NAME, null, contentValues);
        } else {
            if (newsAdapter != null) {
                //刷新
                newsAdapter.loadMore(news.data);
            }
            rv.loadMoreComplete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}





