package com.wqdata.recycler.demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
/**
 * Created by wangjia on 2016/1/27.
 */
public class SimpleImageAdapter extends FragmentStatePagerAdapter {

    public SimpleImageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return  ImageFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
