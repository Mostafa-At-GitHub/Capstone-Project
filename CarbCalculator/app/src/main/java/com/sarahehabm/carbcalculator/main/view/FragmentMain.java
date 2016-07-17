package com.sarahehabm.carbcalculator.main.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.Constants;
import com.sarahehabm.carbcalculator.common.Utility;
import com.sarahehabm.carbcalculator.common.model.Meal;
import com.sarahehabm.carbcalculator.main.business.MainBusiness;
import com.sarahehabm.carbcalculator.meal.view.MealDetailsActivity;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentMain extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>,OnMealClickListener {
    private final String TAG = FragmentMain.class.getSimpleName();
    private final int MEALS_LOADER_ID = 11;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View rootView;
    private RecyclerView recyclerView_meals;

    private ArrayList<Meal> meals;
    private MealAdapter mealAdapter;


    public FragmentMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, null, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MainPagerAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        getActivity().getSupportLoaderManager().initLoader(MEALS_LOADER_ID, null, this);

        recyclerView_meals = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView_meals.setLayoutManager(new LinearLayoutManager(getContext()));
        mealAdapter = new MealAdapter(getContext(), null, this);
        recyclerView_meals.setAdapter(mealAdapter);
        recyclerView_meals.setNestedScrollingEnabled(false);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case MEALS_LOADER_ID:
                return MainBusiness.getCursorLoader(getContext(), Utility.getStartOfDayTimestamp(),
                        Utility.getEndOfDayTimestamp());

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mealAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mealAdapter.swapCursor(null);
    }

    @Override
    public void onMealClick(int position, int id) {
        Intent intent = new Intent(getContext(), MealDetailsActivity.class);
        intent.putExtra(Constants.KEY_MEAL_ID, (long)id);
        startActivity(intent);
    }
}
