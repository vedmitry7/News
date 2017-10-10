package com.vedmitryapps.news.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vedmitryapps.news.R;
import com.vedmitryapps.news.model.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private DisplayMetrics displayMetrics;
    private List<News> news;
    private Context context;

    public RecyclerViewAdapter(Context context, List<News> news) {
        this.context = context;
        this.news = news;
        displayMetrics = context.getResources().getDisplayMetrics();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
/*      double w = news.get(position).getWidth();
        double h = news.get(position).getHeight();   */
        double w = 1080;
        double h = 1080;
        double k = w/h;

        Glide
                .with(context)
                .load(news.get(position).getCover())
                .placeholder(R.mipmap.ic_launcher)
               //.override(displayMetrics.widthPixels, (int) (displayMetrics.widthPixels/k))
                .into(holder.imageView);

        holder.description.setText(news.get(position).getName());
        String link = news.get(position).getLink();
        link = link.substring(link.indexOf("/")+2, link.lastIndexOf("/"));
        holder.source.setText(link);
        holder.date.setText(String.valueOf(news.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.source)
        TextView source;
        @BindView(R.id.date)
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void update(List<News> videos){
        this.news = videos;
        notifyDataSetChanged();
    }
}
