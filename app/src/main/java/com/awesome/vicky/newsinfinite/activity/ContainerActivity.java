package com.awesome.vicky.newsinfinite.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.awesome.vicky.newsinfinite.R;
import com.awesome.vicky.newsinfinite.fragment.About;
import com.awesome.vicky.newsinfinite.fragment.NewsList;
import com.awesome.vicky.newsinfinite.fragment.SearchNews;
import com.awesome.vicky.newsinfinite.fragment.SectionList;

import java.util.ArrayList;
import java.util.List;

public class ContainerActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] TAB_ICONS_UNSELECTED = {
            R.drawable.ic_dashboard_white_24dp,
            R.drawable.ic_explore_white_24dp,
            R.drawable.ic_search_white_24dp,
            R.drawable.ic_info_outline_white_24dp
    };
    private int[] TAB_ICONS_SELECTED = {
            R.drawable.ic_dashboard_black_24dp,
            R.drawable.ic_explore_black_24dp,
            R.drawable.ic_search_black_24dp,
            R.drawable.ic_info_outline_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        for (int i = 0; i < tabLayout.getTabCount(); ++i)
            tabLayout.getTabAt(i).setIcon(i != viewPager.getCurrentItem() ? TAB_ICONS_UNSELECTED[i] : TAB_ICONS_SELECTED[i]);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(TAB_ICONS_UNSELECTED[0]);
        tabLayout.getTabAt(1).setIcon(TAB_ICONS_UNSELECTED[1]);
        tabLayout.getTabAt(2).setIcon(TAB_ICONS_UNSELECTED[2]);
        tabLayout.getTabAt(3).setIcon(TAB_ICONS_UNSELECTED[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(3);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsList(), "S");
        adapter.addFragment(new SectionList(), "S");
        adapter.addFragment(new SearchNews(), "S");
        adapter.addFragment(new About(), "S");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < tabLayout.getTabCount(); ++i)
                    tabLayout.getTabAt(i).setIcon(i != position ? TAB_ICONS_UNSELECTED[i] : TAB_ICONS_SELECTED[i]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
