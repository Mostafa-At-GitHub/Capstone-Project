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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.Constants;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.MealEntry;
import com.sarahehabm.carbcalculator.common.model.Meal;
import com.sarahehabm.carbcalculator.main.business.MainBusiness;
import com.sarahehabm.carbcalculator.meal.view.MealDetailsActivity;

import java.util.ArrayList;

import me.drozdzynski.library.steppers.OnItemClickAction;
import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentMain extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private final String TAG = FragmentMain.class.getSimpleName();
    private final int MEALS_LOADER_ID = 11;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SteppersView mealsView;
    private View rootView;
    private SteppersView steppersView;
    private RecyclerView recyclerView_meals;

    private ArrayList<Meal> meals;
    private ArrayList<SteppersItem> mealsList;
    private SimpleCursorAdapter mealsCursorAdapter;


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

        mealsCursorAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                null,
                new String[]{CarbCounterContract.ItemEntry.COLUMN_NAME},
                new int[]{android.R.id.text1},
                0
        );

        initializeMealsView(rootView);

//        viewPager.requestFocus();
//        ((NestedScrollView)rootView.findViewById(R.id.nestedScrollView)).fullScroll(View.FOCUS_UP);


        recyclerView_meals = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView_meals.setLayoutManager(new LinearLayoutManager(getContext()));
        Cursor cursor = getContext().getContentResolver().query(MealEntry.CONTENT_URI, null, null, null, null);
        recyclerView_meals.setAdapter(new MealAdapter(getContext(), cursor));
        recyclerView_meals.setNestedScrollingEnabled(false);

        return rootView;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.v(TAG, "onResume");
//
//    }

    private void initializeMealsView(View rootView) {
        Log.v(TAG, "initializeMealsView");
        steppersView = (SteppersView) rootView.findViewById(R.id.steppersView);
        SteppersView.Config steppersViewConfig = new SteppersView.Config()
                .setOnItemClickAction(new OnItemClickAction() {

                    @Override
                    public void onItemClick(int position, int id) {
                        Toast.makeText(getContext(), "*FRAGMENT* OnClick at position " + position
                                + " with id: " + id, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getContext(), MealDetailsActivity.class);
                        intent.putExtra(Constants.KEY_MEAL_ID, (long)id);
                        startActivity(intent);
                    }
                });
        steppersViewConfig.setFragmentManager(getActivity().getSupportFragmentManager());


//        getActivity().getSupportLoaderManager().initLoader(MEALS_LOADER_ID, null, this);

        meals = MainBusiness.getTodayMeals(getContext());
        mealsList = new ArrayList<>();
        if(meals!=null) {
            for (int i = 0; i < meals.size(); i++) {
                SteppersItem mealItem = new SteppersItem();
                mealItem.setLabel(meals.get(i).getName());
                String description = getString(R.string.meal_description, meals.get(i).getTotalCarbs());
                mealItem.setSubLabel(description);
                mealItem.setTag(meals.get(i).getId());
                mealsList.add(mealItem);
            }
        }

        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(mealsList);
        steppersView.build();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case MEALS_LOADER_ID:
                return MainBusiness.getCursorLoader(getContext());

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mealsCursorAdapter.swapCursor(data);


        if(steppersView == null)
            steppersView = (SteppersView) rootView.findViewById(R.id.steppersView);

        SteppersView.Config steppersViewConfig = new SteppersView.Config();
        steppersViewConfig.setFragmentManager(getActivity().getSupportFragmentManager());

        if(data!=null && data.getCount()>0 ) {
            meals = new ArrayList<>();
            mealsList = new ArrayList<>();

            while (data.moveToNext()) {
                SteppersItem mealItem = new SteppersItem();

                String name = data.getString(data.getColumnIndex(MealEntry.COLUMN_TITLE));
                int totalCarbs = data.getInt(data.getColumnIndex(MealEntry.COLUMN_TOTAL_CARBS));
                String description = getString(R.string.meal_description, totalCarbs);

                mealItem.setLabel(name);
                mealItem.setSubLabel(description);
                mealsList.add(mealItem);
            }

            steppersView.setConfig(steppersViewConfig);
            steppersView.setItems(null);
            steppersView.setItems(mealsList);
//            steppersView.build();
        } else {
            steppersView.setConfig(steppersViewConfig);
            steppersView.setItems(null);
        }
        steppersView.build();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mealsCursorAdapter.swapCursor(null);
    }
}
