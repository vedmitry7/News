package com.vedmitryapps.news.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vedmitryapps.news.R;
import com.vedmitryapps.news.model.News;
import com.vedmitryapps.news.model.api.ApiFactory;
import com.vedmitryapps.news.view.adapters.ItemDecoration;
import com.vedmitryapps.news.view.adapters.MainRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoriesFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    MainRecyclerAdapter mainRecyclerAdapter;
    private ViewPager viewPager;
    private List<News> news;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stories, container, false);
        ButterKnife.bind(this, view);

        news = new ArrayList<>();

/*        viewPager = view.findViewById(R.id.topNewsViewPager);
        viewPager.setAdapter(new TopNewsPagerAdapter(getContext(), news));
        TabLayout tabLayout = view.findViewById(R.id.topNewsTabLayout);
        tabLayout.setupWithViewPager(viewPager, true);*/

        initRecyclerView();
        loadNews();

        return view;
    }

    private void loadNews() {
        ApiFactory.getService().getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                mainRecyclerAdapter.update(response.body());
               // ((TopNewsPagerAdapter)viewPager.getAdapter()).update(response.body());
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });
    }

    private void initRecyclerView() {
        mainRecyclerAdapter = new MainRecyclerAdapter(getContext(), news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainRecyclerAdapter);
        recyclerView.addItemDecoration(new ItemDecoration());
    }

}
