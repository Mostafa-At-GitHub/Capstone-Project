package com.sarahehabm.carbcalculator.item.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarahehabm.carbcalculator.R;

/**
 A placeholder fragment containing a simple view.
 */
public class AllItemsActivityFragment extends Fragment {
    private final String TAG = AllItemsActivityFragment.class.getSimpleName();

    private TabLayout tabLayout;
    private ViewPager viewpager;
    public ItemsPagerAdapter pagerAdapter;

    public AllItemsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_items, container, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
        pagerAdapter = new ItemsPagerAdapter(getFragmentManager(), null);
        viewpager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewpager);

        return rootView;
    }
}
