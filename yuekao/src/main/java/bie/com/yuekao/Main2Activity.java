package bie.com.yuekao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bie.com.yuekao.Peneter.MyPresenter;
import bie.com.yuekao.Utils.MyNetWorkUtil;
import bie.com.yuekao.adapter.MyAdapter;
import bie.com.yuekao.modle.MyHelper;

/**
 * Created by MSI on 2018/6/1.
 */

public class Main2Activity extends AppCompatActivity implements INews {
    private XRecyclerView rv;

    private MyPresenter presenter;
    private int page = 5010;
    private List<News.Data> data;
    private boolean isRefresh = true;
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
        if (MyNetWorkUtil.hasWifiConnection(this) || MyNetWorkUtil.hasGPRSConnection(this)) {
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

            fillLocalData(json);
        }
    }


    private void fillLocalData(String json) {
        News news = new Gson().fromJson(json,News.class);
        newsAdapter = new MyAdapter(news.data, this,news);
        rv.setAdapter(newsAdapter);
    }

    private void initView() {
        rv = findViewById(R.id.rv);

        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setPullRefreshEnabled(true);
        rv.setLoadingMoreEnabled(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                isRefresh = true;

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

        String json = new Gson().toJson(news);
        System.out.println("size:" + news.data.size());
        data = news.data;
        if (isRefresh) {
            newsAdapter = new MyAdapter(data, this,news);
            rv.setAdapter(newsAdapter);
            rv.refreshComplete();

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("json", json);
            db.insert(MyHelper.NEWS_TABLE_NAME, null, contentValues);
        } else {
            if (newsAdapter != null) {

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





