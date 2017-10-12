package com.vedmitryapps.news.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ProgressBar;

import com.vedmitryapps.news.R;
import com.vedmitryapps.news.model.objects.News;
import com.vedmitryapps.news.model.api.ApiFactory;
import com.vedmitryapps.news.view.ItemDecoration;
import com.vedmitryapps.news.view.adapters.StoriesRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoriesFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindAnim(R.anim.animation_in)
    Animation animIn;

    private StoriesRecyclerAdapter storiesRecyclerAdapter;
    private List<News> news;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stories, container, false);
        ButterKnife.bind(this, view);

        news = new ArrayList<>();

        initRecyclerView();
        loadNews();

        return view;
    }

    private void loadNews() {
        ApiFactory.getService().getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if(response != null)
                storiesRecyclerAdapter.update(response.body());
                recyclerView.startAnimation(animIn);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });
    }

    private void initRecyclerView() {
        storiesRecyclerAdapter = new StoriesRecyclerAdapter(getContext(), news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(storiesRecyclerAdapter);
        recyclerView.addItemDecoration(new ItemDecoration());
    }

}
