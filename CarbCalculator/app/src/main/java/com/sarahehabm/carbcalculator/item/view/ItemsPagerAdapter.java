package com.sarahehabm.carbcalculator.item.view;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/**
 Created by Sarah E. Mostafa on 30-May-16.
 */

public class ItemsPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    private final int NUM_TABS = 2;

    public static final int TAB_INDEX_ALL = 0;
    public static final int TAB_INDEX_FAVORITES = 1;

    public static final String TAB_TITLE_ALL = "All";
    public static final String TAB_TITLE_FAVORITES = "Favorites";

    private Cursor cursor;
    private AllItemsPagerFragment allItemsPagerFragment;
    private FavoritesItemsPagerFragment favoritesItemsPagerFragment;
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
                if(allItemsPagerFragment == null)
                    allItemsPagerFragment = new AllItemsPagerFragment();
                return allItemsPagerFragment;
//                break;

            case TAB_INDEX_FAVORITES:
                if(favoritesItemsPagerFragment==null)
                    favoritesItemsPagerFragment = new FavoritesItemsPagerFragment();
                return favoritesItemsPagerFragment;
//                break;
        }

//        return currentFragment;
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
        if(cursor!=null && currentFragment!=null) {
            ((ItemsBasePagerFragment) currentFragment).setCursor(cursor);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case TAB_INDEX_ALL:
            default:
                currentFragment = allItemsPagerFragment;
                break;

            case TAB_INDEX_FAVORITES:
                currentFragment = favoritesItemsPagerFragment;
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
