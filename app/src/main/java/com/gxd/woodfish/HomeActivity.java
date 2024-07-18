package com.gxd.woodfish;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private List<Fragment> fragmentList;
    private List<String> tabList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        WeTabLayout tabLayout = findViewById(R.id.tab);
        ViewPager viewPager = findViewById(R.id.view_pager);

        tabList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        tabList.add("MAIN");
        tabList.add("MINE");
        fragmentList.add(new HomeFragment());
        fragmentList.add(new MeFragment());

        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    HomeFragment fragment = (HomeFragment) fragmentList.get(position);
                    fragment.refreshNum();
                } else if (position == 1) {
                    MeFragment fragment = (MeFragment) fragmentList.get(position);
                    fragment.refreshNum();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setCurrentTab(0);
        tabLayout.attachToViewPager(viewPager, tabList);
        viewPager.setOffscreenPageLimit(tabList.size());
    }

    class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabList.get(position);
        }
    }
}
