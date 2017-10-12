package com.vedmitryapps.news.view.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
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

public class TopNewsPagerAdapter extends PagerAdapter {

    private Context context;
    private List<News> news;
    private int heightPixels;
    private int widthPixels;

    public TopNewsPagerAdapter(Context context, List<News> news) {
        this.context = context;
        this.news = news;

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float logicalDensity = displayMetrics.density;
        heightPixels = (int) Math.ceil(Constants.TOP_NEWS_PAGER_HEIGHT * logicalDensity);
        widthPixels = displayMetrics.widthPixels;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_top_news_pager_adapter,  null);

        ImageView imageView = view.findViewById(R.id.imageViewTopNews);
        TextView textView = view.findViewById(R.id.descriptionTopNews);
        TextView source = view.findViewById(R.id.source);
        TextView date = view.findViewById(R.id.dateTopNews);

        Glide
                .with(context)
                .load(news.get(position).getCover())
                .centerCrop()
                .override(widthPixels, heightPixels)
                .into(imageView);

        textView.setText(news.get(position).getName());
        source.setText(siteName(news.get(position).getLink()));
        date.setText(toDuration(news.get(position).getCreatedAt()));

        collection.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int position, Object view) {
        viewGroup.removeView((View) view);
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
