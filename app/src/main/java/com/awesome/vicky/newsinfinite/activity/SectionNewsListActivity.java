package com.awesome.vicky.newsinfinite.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.awesome.vicky.newsinfinite.R;
import com.awesome.vicky.newsinfinite.adapter.SectionNewsListAdapter;
import com.awesome.vicky.newsinfinite.pojoSections.Main;
import com.awesome.vicky.newsinfinite.pojoSections.Result;
import com.awesome.vicky.newsinfinite.util.RetrofitApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SectionNewsListActivity extends AppCompatActivity {

    private int page = 1;
    private List<Result> resultList;
    private ListView sectionNewsListView;
    private Boolean isLoading = false;
    private SectionNewsListAdapter sectionNewsListAdapter;
    private RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
    private Call<Main> mainCall;
    private Toolbar toolbar;
    private String fontPath;
    private Typeface typeface;
    private TextView pageTitle;
    private int position;
    private LinearLayout errorLayout;
    private Button retryButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_news_list);
        initToolbar();
        initProgressBar();
        initErrorLayout();
        initListView();
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        makeNetworkRequest();
    }

    private void initProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.loading_progress_bar);
    }

    private void initErrorLayout() {
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        retryButton = (Button) findViewById(R.id.retry_button);
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
        sectionNewsListView = (ListView) findViewById(R.id.section_news_list_view);
        View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listview_footer, null, false);
        sectionNewsListView.addFooterView(footerView);
        sectionNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SectionNewsListActivity.this, NewsActivity.class);
                intent.putExtra("url", resultList.get(position).getWebUrl());
                intent.putExtra("sectionName", resultList.get(position).getSectionName());
                startActivity(intent);
            }
        });
        sectionNewsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_section_news);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        pageTitle = (TextView) toolbar.findViewById(R.id.page_title);
        fontPath = "fonts/NimbusRomNo9L-Reg.otf";
        typeface = Typeface.createFromAsset(getAssets(), fontPath);
        pageTitle.setTypeface(typeface);

    }

    private void makeNetworkRequest() {

        switch (position) {
            case 0:
                pageTitle.setText("Technology");
                mainCall = apiInterface.technology(
                        "1e2f5f1c-6f4a-4d9f-9321-c561b7bc9043",
                        String.valueOf(page)
                );
                break;
            case 1:
                pageTitle.setText("Sports");
                mainCall = apiInterface.sport(
                        "1e2f5f1c-6f4a-4d9f-9321-c561b7bc9043",
                        String.valueOf(page)
                );
                break;
            case 2:
                pageTitle.setText("Politics");
                mainCall = apiInterface.politics(
                        "1e2f5f1c-6f4a-4d9f-9321-c561b7bc9043",
                        String.valueOf(page)
                );
                break;
            case 3:
                pageTitle.setText("Travel");
                mainCall = apiInterface.travel(
                        "1e2f5f1c-6f4a-4d9f-9321-c561b7bc9043",
                        String.valueOf(page)
                );
                break;
            case 4:
                pageTitle.setText("World News");
                mainCall = apiInterface.world(
                        "1e2f5f1c-6f4a-4d9f-9321-c561b7bc9043",
                        String.valueOf(page)
                );
                break;
            case 5:
                pageTitle.setText("Business");
                mainCall = apiInterface.business(
                        "1e2f5f1c-6f4a-4d9f-9321-c561b7bc9043",
                        String.valueOf(page)
                );
                break;
            case 6:
                pageTitle.setText("Education");
                mainCall = apiInterface.education(
                        "1e2f5f1c-6f4a-4d9f-9321-c561b7bc9043",
                        String.valueOf(page)
                );
                break;
            case 7:
                pageTitle.setText("Film");
                mainCall = apiInterface.film(
                        "1e2f5f1c-6f4a-4d9f-9321-c561b7bc9043",
                        String.valueOf(page)
                );
                break;
            case 8:
                pageTitle.setText("Football");
                mainCall = apiInterface.football(
                        "1e2f5f1c-6f4a-4d9f-9321-c561b7bc9043",
                        String.valueOf(page)
                );
                break;
        }
        mainCall.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, Response<Main> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    sectionNewsListView.setVisibility(View.VISIBLE);
                    if (page == 1) {
                        resultList = response.body().getResponse().getResults();
                        sectionNewsListAdapter = new SectionNewsListAdapter(SectionNewsListActivity.this, resultList);
                        sectionNewsListView.setAdapter(sectionNewsListAdapter);
                    } else {
                        resultList.addAll(response.body().getResponse().getResults());
                        sectionNewsListAdapter.notifyDataSetChanged();
                    }
                    ++page;
                    isLoading = false;
                } else {
                    isLoading = false;
                    sectionNewsListView.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {
                isLoading = false;
                sectionNewsListView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
