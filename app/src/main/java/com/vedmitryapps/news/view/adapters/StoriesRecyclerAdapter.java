package com.vedmitryapps.news.view.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vedmitryapps.news.Constants;
import com.vedmitryapps.news.R;
import com.vedmitryapps.news.model.objects.News;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StoriesRecyclerAdapter extends RecyclerView.Adapter<StoriesRecyclerAdapter.ViewHolder> {

    private DisplayMetrics displayMetrics;
    private List<News> news;
    private Context context;

    public StoriesRecyclerAdapter(Context context, List<News> news) {
        this.context = context;
        this.news = news;
        displayMetrics = context.getResources().getDisplayMetrics();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == 2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stories_recycler_view_news, parent, false);
        }
        if (viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stories_recycler_view_top_news_pager, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        position = position -1;

        if(holder.getItemViewType()== 1){
            holder.viewPager.setAdapter(new TopNewsPagerAdapter(context, news));
            holder.tabLayout.setupWithViewPager(holder.viewPager, true);
        }

        if(holder.getItemViewType() == 2) {

            Glide
                    .with(context)
                    .load(news.get(position).getCover())
                    .into(holder.imageView);

            holder.description.setText(news.get(position).getName());
            holder.source.setText(siteName(news.get(position).getLink()));
            holder.date.setText(toDuration(news.get(position).getCreatedAt()));
        }
    }

    @Override
    public int getItemCount() {
        return news.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return 1;
        }
        else {
            return 2;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.imageView)
        ImageView imageView;
        @Nullable
        @BindView(R.id.description)
        TextView description;
        @Nullable
        @BindView(R.id.source)
        TextView source;
        @Nullable
        @BindView(R.id.date)
        TextView date;
        @Nullable
        @BindView(R.id.topNewsViewPager)
        ViewPager viewPager;
        @Nullable
        @BindView(R.id.topNewsTabLayout)
        TabLayout tabLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void update(List<News> videos){
        this.news = videos;
        notifyDataSetChanged();
    }

    private String siteName(String link){
        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String site = url != null ? url.getAuthority().substring(4, url.getAuthority().length()) : null;

        return site == null ? null : site.substring(0, 1).toUpperCase() + site.substring(1);
    }

    private String toDuration(String date) {

        String dateFormat = Constants.DATE_FORMAT;
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date date1 = null;
        try {
            date1 = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long duration = System.currentTimeMillis() - date1.getTime();


        List<Long> times = Arrays.asList(
                TimeUnit.DAYS.toMillis(365),
                TimeUnit.DAYS.toMillis(30),
                TimeUnit.DAYS.toMillis(1),
                TimeUnit.HOURS.toMillis(1),
                TimeUnit.MINUTES.toMillis(1),
                TimeUnit.SECONDS.toMillis(1) );
        List<String> timesString = Arrays.asList(context.getResources().getStringArray(R.array.periods));

        StringBuffer res = new StringBuffer();
        for(int i = 0; i < times.size(); i++) {
            Long current = times.get(i);
            long temp = duration/current;
            if(temp > 0) {
                res.append(temp).append(" ").append(timesString.get(i) ).append(temp != 1 ? "s" : "").append(" " + context.getString(R.string.ago));
                break;
            }
        }
        if("".equals(res.toString()))
            return " " + context.getString(R.string.zero_second_ago);
        else
            return " - " + res.toString();
    }
}
