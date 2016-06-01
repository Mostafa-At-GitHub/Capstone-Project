package com.sarahehabm.carbcalculator.item.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarahehabm.carbcalculator.R;

/**
 A placeholder fragment containing a simple view.
 */
public class AddNewItemActivityFragment extends Fragment {

    public AddNewItemActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_item, container, false);
    }
}
