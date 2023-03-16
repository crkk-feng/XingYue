package com.example.xingyue.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.xingyue.utils.FragmentsCreator;

/**
 * 给ViewPager设置的Adapter
 */
public class MainContentAdapter extends FragmentPagerAdapter {
    public MainContentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FragmentsCreator.getFragment(position);
    }

    @Override
    public int getCount() {
        return FragmentsCreator.PAGE_COUNT;
    }
}
