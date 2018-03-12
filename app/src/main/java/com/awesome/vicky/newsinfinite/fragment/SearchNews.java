package com.awesome.vicky.newsinfinite.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.awesome.vicky.newsinfinite.R;
import com.awesome.vicky.newsinfinite.activity.NewsActivity;
import com.awesome.vicky.newsinfinite.adapter.SearchNewsListAdapter;
import com.awesome.vicky.newsinfinite.pojoSearch.Main2;
import com.awesome.vicky.newsinfinite.pojoSearch.Result;
import com.awesome.vicky.newsinfinite.util.RetrofitApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchNews extends Fragment {


    public SearchNews() {
        // Required empty public constructor
    }

    private int page = 1;
    private List<Result> resultList;
    private ListView searchNewsListView;
    private Boolean isLoading = false;
    private SearchNewsListAdapter searchNewsListAdapter;
    private LinearLayout errorLayout;
    private Button retryButton;
    private ProgressBar progressBar;
    private EditText editText;
    private String query;
    private LinearLayout linearLayout;
    private int resultPages;
    private View footerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        linearLayout = (LinearLayout) getView().findViewById(R.id.linear_layout);
        initProgressBar();
        initErrorLayout();
        initListView();
        initEditText();
    }

    private void initEditText() {
        editText = (EditText) getView().findViewById(R.id.editText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (editText.getText().toString().length() != 0) {
                        page = 1;
                        errorLayout.setVisibility(View.GONE);
                        searchNewsListView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                        query = editText.getText().toString();
                        makeNetworkRequest();
                    }
                    return true;
                }
                return false;
            }
        });
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
        searchNewsListView = (ListView) getView().findViewById(R.id.search_news_list_view);
        footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listview_footer, null, false);
        searchNewsListView.addFooterView(footerView);
        searchNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), NewsActivity.class);
                intent.putExtra("url", resultList.get(position).getWebUrl());
                intent.putExtra("sectionName", resultList.get(position).getSectionName());
                startActivity(intent);
            }
        });
        searchNewsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                    if (page <= resultPages) {
                        makeNetworkRequest();
                        footerView.setVisibility(View.VISIBLE);
                    } else
                        footerView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void makeNetworkRequest() {
        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<Main2> actualSearch = apiInterface.actualSearch(
                query,
                "1e2f5f1c-6f4a-4d9f-9321-c561b7bc9043",
                String.valueOf(page)
        );
        actualSearch.enqueue(new Callback<Main2>() {
            @Override
            public void onResponse(Call<Main2> call, Response<Main2> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    searchNewsListView.setVisibility(View.VISIBLE);
                    if (page == 1) {
                        resultPages = response.body().getResponse().getPages();
                        if (resultPages != 0) {
                            resultList = response.body().getResponse().getResults();
                            //Toast.makeText(getActivity(), resultList.get(0).getWebTitle(), Toast.LENGTH_SHORT).show();
                            searchNewsListAdapter = new SearchNewsListAdapter(getActivity().getApplicationContext(), resultList);
                            searchNewsListView.setAdapter(searchNewsListAdapter);
                        } else {
                            if (resultList != null) {
                                resultList.clear();
                                searchNewsListAdapter.notifyDataSetChanged();
                                searchNewsListView.setVisibility(View.GONE);
                            }
                            Snackbar snackbar = Snackbar.make(linearLayout, "No results found", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    } else {
                        resultList.addAll(response.body().getResponse().getResults());
                        searchNewsListAdapter.notifyDataSetChanged();
                    }
                    ++page;
                    isLoading = false;
                } else {
                    isLoading = false;
                    searchNewsListView.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Main2> call, Throwable t) {
                isLoading = false;
                searchNewsListView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
            }
        });
    }

}
