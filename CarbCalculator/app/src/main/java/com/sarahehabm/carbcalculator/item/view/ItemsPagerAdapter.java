package com.sarahehabm.carbcalculator.item.view;

import android.database.Cursor;
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

    private Cursor cursor;
    private Fragment currentFragment;

    public ItemsPagerAdapter(FragmentManager fm, Cursor cursor) {
        super(fm);
        this.cursor = cursor;
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
                currentFragment = new AllItemsPagerFragment();
                break;

            case TAB_INDEX_FAVORITES:
                currentFragment = new FavoritesItemsPagerFragment();
                break;
        }

        return currentFragment;
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }

    public void swapCursor(Cursor cursor) {
        /*if (currentFragment instanceof AllItemsPagerFragment) {
            ((AllItemsPagerFragment) currentFragment).setCursor(cursor);
        } else if (currentFragment instanceof FavoritesItemsPagerFragment) {
            ((FavoritesItemsPagerFragment) currentFragment).setCursor(cursor);
        }*/
        if(cursor!=null) {
            ((ItemsBasePagerFragment) currentFragment).setCursor(cursor);
            notifyDataSetChanged();
        }
    }
}
