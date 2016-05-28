package com.sarahehabm.carbcalculator.main.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.main.business.MainBusiness;

import java.util.ArrayList;

import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentMain extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SteppersView mealsView;

    private ArrayList<SteppersItem> mealsList;


    public FragmentMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, null, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MainPagerAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        initializeMealsView(rootView);

//        viewPager.requestFocus();
//        ((NestedScrollView)rootView.findViewById(R.id.nestedScrollView)).fullScroll(View.FOCUS_UP);

        return rootView;
    }

    private void initializeMealsView(View rootView) {
        SteppersView steppersView = (SteppersView) rootView.findViewById(R.id.steppersView);
        SteppersView.Config steppersViewConfig = new SteppersView.Config();
        steppersViewConfig.setFragmentManager(getActivity().getSupportFragmentManager());
        ArrayList<String> meals = MainBusiness.getMeals();
        mealsList = new ArrayList<>();
        for (int i = 0; i < meals.size(); i++) {
            SteppersItem mealItem = new SteppersItem();
            mealItem.setLabel(meals.get(i));
            mealItem.setSubLabel(meals.get(i) + "'s description");
            mealsList.add(mealItem);
        }

        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(mealsList);
        steppersView.build();
    }
}
