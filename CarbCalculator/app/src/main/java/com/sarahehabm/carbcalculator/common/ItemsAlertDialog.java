package com.sarahehabm.carbcalculator.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.sarahehabm.carbcalculator.R;

/**
 Created by Sarah E. Mostafa on 03-Jun-16.
 */

public class ItemsAlertDialog extends AlertDialog {
    public static final int DIALOG_ALL = 1;
    public static final int DIALOG_FAVORITE = 2;
    public static final int OPTION_FAVORITE = 0;
    public static final int OPTION_UN_FAVORITE = 0;
    public static final int OPTION_EDIT = 1;

    private int dialogType, itemId;
    private AlertDialog.Builder builder;
    private ItemsAlertDialogInterface alertDialogInterface;

    public ItemsAlertDialog(Context context, int dialogType, int itemId,
                            ItemsAlertDialogInterface alertDialogInterface) {
        super(context);
        this.dialogType = dialogType;
        this.itemId = itemId;
        this.alertDialogInterface = alertDialogInterface;
        builder = createBuilder();
    }

    private AlertDialog.Builder createBuilder() {
        String[] options = new String[0];
        DialogInterface.OnClickListener onClickListener = null;
        switch (dialogType) {
            case DIALOG_ALL:
                options = getContext().getResources().getStringArray(R.array.dialog_options_all);
                onClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case OPTION_FAVORITE:
                                alertDialogInterface.onFavoriteItemClick(itemId);
                                break;

                            case OPTION_EDIT:
                                alertDialogInterface.onEditItemClick(itemId);
                                break;
                        }
                    }
                };
                break;

            case DIALOG_FAVORITE:
                options = getContext().getResources().getStringArray(R.array.dialog_options_favorites);
                onClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case OPTION_UN_FAVORITE:
                                alertDialogInterface.onUnFavoriteItemClick(itemId);
                                break;

                            case OPTION_EDIT:
                                alertDialogInterface.onEditItemClick(itemId);
                                break;
                        }
                    }
                };
                break;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setItems(options, onClickListener);
        return builder;
    }

    public void showDialog() {
        if(builder!=null)
            builder.show();
    }
}
