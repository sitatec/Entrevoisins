package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.utils.NeighbourListProvider;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        final Fragment neighbourFragment = NeighbourFragment.newInstance();
        final Bundle fragmentArgBundle = new Bundle();
        fragmentArgBundle.putInt(NeighbourFragment.NEIGHBOUR_PAGE_ARG_KEY, position);
        neighbourFragment.setArguments(fragmentArgBundle);
        Log.i("ListNeighbourPagerAdpte", "Page Swithced");
        return neighbourFragment;
    }


    /**
     * get the number of pages
     * @return
     */
    @Override
    public int getCount() {
        return 2;
    }
}