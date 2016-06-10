package com.sarahehabm.carbcalculator.main.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Sarah E. Mostafa on 09-May-16.
 */
public class MainPagerAdapter extends FragmentPagerAdapter{
    private final int NUM_TABS = 3;

    public static final int TAB_INDEX_TODAY = 0;
    public static final int TAB_INDEX_WEEK = 1;
    public static final int TAB_INDEX_MONTH = 2;

    public static final String TAB_TITLE_TODAY = "Today";
    public static final String TAB_TITLE_WEEK = "Week";
    public static final String TAB_TITLE_MONTH = "Month";

    private TodayPagerFragment todayPagerFragment;
    private WeekPagerFragment weekPagerFragment;
    private MonthPagerFragment monthPagerFragment;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case TAB_INDEX_TODAY:
                return TAB_TITLE_TODAY;

            case TAB_INDEX_WEEK:
                return TAB_TITLE_WEEK;

            case TAB_INDEX_MONTH:
                return TAB_TITLE_MONTH;

            default:
                return "";
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_INDEX_TODAY:
                if(todayPagerFragment == null)
                    todayPagerFragment = new TodayPagerFragment();
                return todayPagerFragment;

            case TAB_INDEX_WEEK:
                if(weekPagerFragment == null)
                    weekPagerFragment = new WeekPagerFragment();
                return weekPagerFragment;

            case TAB_INDEX_MONTH:
                if(monthPagerFragment == null)
                    monthPagerFragment = new MonthPagerFragment();
                return monthPagerFragment;

            default:
                return new BasePagerFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}
