package bie.com.yuekao.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import bie.com.yuekao.Constants;
import bie.com.yuekao.News;
import bie.com.yuekao.R;

/**
 * Created 朱治文lenovo on 2018/6/1
 * .
 */
//适配器
public class MyAdapter extends com.jcodecraeer.xrecyclerview.XRecyclerView.Adapter<com.jcodecraeer.xrecyclerview.XRecyclerView.ViewHolder> {
    private List<News.Data> list;
    private Context context;
    private  News news;
    public MyAdapter(List<News.Data> list, Context context,News news) {
        this.list = list;
        this.context = context;
        this.news = news;
    }
    public void loadMore(List<News.Data> data) {
        if (list != null) {
            list.addAll(data);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public com.jcodecraeer.xrecyclerview.XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.TYPE1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item2_layout, parent, false);
            return new Type1ViewHolder(view);
        } else {
            View view2 = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
            return new Type2ViewHolder(view2);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull final com.jcodecraeer.xrecyclerview.XRecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("不在关注");
                builder.setMessage("确定取消关注此条新闻");

                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int pos = holder.getLayoutPosition();
                        System.out.println("pos----"+pos);
                        list.remove(position);
                        news.data = list;
                        String json =  new Gson().toJson(news);
                        notifyDataSetChanged();
                    }
                });
                builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
                return true;
            }
        });
        News.Data data = list.get(position);
        if (holder instanceof Type1ViewHolder) {//1张图片
            ((Type1ViewHolder) holder).title.setText(data.topic);
        } else if (holder instanceof Type2ViewHolder) {//三张图片
            ((Type2ViewHolder) holder).title.setText(data.topic);
            if (data.miniimg != null && data.miniimg.size() > 0) {
                if (data.miniimg.size() == 1) {
                    Glide.with(context).load(data.miniimg.get(0).src).into(((Type2ViewHolder) holder).iv1);
                    Glide.with(context).load(data.miniimg.get(0).src).into(((Type2ViewHolder) holder).iv2);
                    Glide.with(context).load(data.miniimg.get(0).src).into(((Type2ViewHolder) holder).iv3);
                } else if (data.miniimg.size() == 2) {
                    Glide.with(context).load(data.miniimg.get(0).src).into(((Type2ViewHolder) holder).iv1);
                    Glide.with(context).load(data.miniimg.get(1).src).into(((Type2ViewHolder) holder).iv2);
                    Glide.with(context).load(data.miniimg.get(1).src).into(((Type2ViewHolder) holder).iv3);
                } else {
                    Glide.with(context).load(data.miniimg.get(0).src).into(((Type2ViewHolder) holder).iv1);
                    Glide.with(context).load(data.miniimg.get(1).src).into(((Type2ViewHolder) holder).iv2);
                    Glide.with(context).load(data.miniimg.get(2).src).into(((Type2ViewHolder) holder).iv3);
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? Constants.TYPE1 : Constants.TYPE2;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Type1ViewHolder extends com.jcodecraeer.xrecyclerview.XRecyclerView.ViewHolder {
        private TextView title;
        public Type1ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title1);
        }
    }

    class Type2ViewHolder extends com.jcodecraeer.xrecyclerview.XRecyclerView.ViewHolder {
        private TextView title;
        private ImageView iv1, iv2, iv3;
        public Type2ViewHolder(View itemView) {
            super(itemView);
            iv1 = itemView.findViewById(R.id.img1);
            iv2 = itemView.findViewById(R.id.img2);
            iv3 = itemView.findViewById(R.id.img3);
            title = itemView.findViewById(R.id.title3);
        }
    }
}




