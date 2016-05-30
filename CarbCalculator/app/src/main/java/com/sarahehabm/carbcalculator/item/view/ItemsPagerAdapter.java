package com.sarahehabm.carbcalculator.item.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 Created by Sarah E. Mostafa on 30-May-16.
 */

public class ItemsPagerAdapter extends FragmentPagerAdapter {
    private final int NUM_TABS = 2;

    public static final int TAB_INDEX_ALL = 0;
    public static final int TAB_INDEX_FAVORITES = 1;

    public static final String TAB_TITLE_ALL = "All";
    public static final String TAB_TITLE_FAVORITES = "Favorites";

    public ItemsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case TAB_INDEX_ALL:
                return TAB_TITLE_ALL;

            case TAB_INDEX_FAVORITES:
                return TAB_TITLE_FAVORITES;

            default:
                return "";
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_INDEX_ALL:
            default:
                return new AllItemsPagerFragment();

            case TAB_INDEX_FAVORITES:
                return new FavoritesItemsPagerFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}
