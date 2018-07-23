package com.example.administrator.myrestfulapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myrestfulapp.fragment.PageOneFragment;

/**
 * Created by Administrator on 2018/7/23.
 */

public class ViewPagerActivity  extends AppCompatActivity{

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] mTitles = {"Page1", "Page2", "Page3"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("name", "page_" + (position + 1));
            switch (position) {
                case 0:
                    PageOneFragment pageOneFragment = new PageOneFragment();

                    pageOneFragment.setArguments(bundle);
                    return pageOneFragment;
                case 1:
                    PageOneFragment pageTwoFragment = new PageOneFragment();
                    pageTwoFragment.setArguments(bundle);
                    return pageTwoFragment;
                case 2:
                    PageOneFragment pageThreeFragment = new PageOneFragment();

                    pageThreeFragment.setArguments(bundle);
                    return pageThreeFragment;
                case 3:
                    PageOneFragment pageFourFragment = new PageOneFragment();
                    pageFourFragment.setArguments(bundle);
                    return pageFourFragment;
                default:
                    PageOneFragment pageFiveFragment = new PageOneFragment();

                    pageFiveFragment.setArguments(bundle);
                    return pageFiveFragment;
            }

        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
