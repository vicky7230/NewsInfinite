package com.awesome.vicky.newsinfinite.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.awesome.vicky.newsinfinite.R;
import com.awesome.vicky.newsinfinite.activity.NewsActivity;
import com.awesome.vicky.newsinfinite.adapter.NewsListAdapter;
import com.awesome.vicky.newsinfinite.pojo.Result;
import com.awesome.vicky.newsinfinite.pojo.Search;
import com.awesome.vicky.newsinfinite.util.RetrofitApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsList extends Fragment {


    public NewsList() {
        // Required empty public constructor
    }

    private int page = 1;
    private List<Result> resultList;
    private ListView newsListView;
    private Boolean isLoading = false;
    private NewsListAdapter newsListAdapter;
    private LinearLayout errorLayout;
    private Button retryButton;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initProgressBar();
        initErrorLayout();
        initListView();
        makeNetworkRequest();
    }

    private void initProgressBar() {
        progressBar = (ProgressBar) getView().findViewById(R.id.loading_progress_bar);
    }

    private void initErrorLayout() {
        errorLayout = (LinearLayout) getView().findViewById(R.id.error_layout);
        retryButton = (Button) getView().findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                makeNetworkRequest();
            }
        });
    }

    private void initListView() {
        newsListView = (ListView) getView().findViewById(R.id.search_list_view);
        View footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listview_footer, null, false);
        newsListView.addFooterView(footerView);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), NewsActivity.class);
                intent.putExtra("url", resultList.get(position).getWebUrl());
                intent.putExtra("sectionName", resultList.get(position).getSectionName());
                startActivity(intent);
            }
        });
        newsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.getAdapter() == null)
                    return;
                if (view.getAdapter().getCount() == 0)
                    return;
                int l = visibleItemCount + firstVisibleItem;
                if (l >= totalItemCount && !isLoading) {
                    // It is time to add new data. We call the listener
                    isLoading = true;
                    makeNetworkRequest();
                }
            }
        });
    }

    private void makeNetworkRequest() {
        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<Search> searchCall = apiInterface.search(
                "1e2f5f1c-6f4a-4d9f-9321-c561b7bc9043",
                String.valueOf(page)
        );
        searchCall.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    newsListView.setVisibility(View.VISIBLE);
                    if (page == 1) {
                        resultList = response.body().getResponse().getResults();
                        newsListAdapter = new NewsListAdapter(getActivity().getApplicationContext(), resultList);
                        newsListView.setAdapter(newsListAdapter);
                    } else {
                        resultList.addAll(response.body().getResponse().getResults());
                        newsListAdapter.notifyDataSetChanged();
                    }
                    ++page;
                    isLoading = false;
                } else {
                    isLoading = false;
                    newsListView.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                isLoading = false;
                newsListView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
            }
        });
    }

}
