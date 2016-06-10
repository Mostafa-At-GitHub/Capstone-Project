package com.sarahehabm.carbcalculator.item.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract;

/**
 A placeholder fragment containing a simple view.
 */
public class AllItemsActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private final String TAG = AllItemsActivityFragment.class.getSimpleName();
    private final int LOADER_ALL_ITEMS_ID = 12, LOADER_FAVORITE_ITEMS_ID = 13;

    private TabLayout tabLayout;
    private ViewPager viewpager;
    private ItemsPagerAdapter pagerAdapter;

    public AllItemsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_items, container, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
        getLoaderManager().initLoader(LOADER_ALL_ITEMS_ID, null, this);
        pagerAdapter = new ItemsPagerAdapter(getFragmentManager(), null);
        viewpager.setAdapter(pagerAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case ItemsPagerAdapter.TAB_INDEX_ALL:
//                        Toast.makeText(getContext(), "All items page selected", Toast.LENGTH_SHORT).show();
                        getLoaderManager().initLoader(LOADER_ALL_ITEMS_ID, null, AllItemsActivityFragment.this);
                        break;

                    case ItemsPagerAdapter.TAB_INDEX_FAVORITES:
//                        Toast.makeText(getContext(), "Favorite items page selected", Toast.LENGTH_SHORT).show();
                        getLoaderManager().initLoader(LOADER_FAVORITE_ITEMS_ID, null, AllItemsActivityFragment.this);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        viewpager.setCurrentItem(ItemsPagerAdapter.TAB_INDEX_ALL);
        tabLayout.setupWithViewPager(viewpager);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ALL_ITEMS_ID:
                return new CursorLoader(getContext(), CarbCounterContract.ItemEntry.CONTENT_URI, null,
                        null, null,
                        CarbCounterContract.ItemEntry.COLUMN_NAME + " ASC");

            case LOADER_FAVORITE_ITEMS_ID:
                return new CursorLoader(getContext(), CarbCounterContract.ItemEntry.CONTENT_URI, null,
                        CarbCounterContract.ItemEntry.COLUMN_FAVORITE + " = ? ",
                        new String[]{String.valueOf(1)},
                        CarbCounterContract.ItemEntry.COLUMN_NAME + " ASC");

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        pagerAdapter.swapCursor(data);
        pagerAdapter.notifyDataSetChanged();

//        Toast.makeText(getContext(), "onLoadFinished; count= " + data.getCount(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        pagerAdapter.swapCursor(null);
    }
}
