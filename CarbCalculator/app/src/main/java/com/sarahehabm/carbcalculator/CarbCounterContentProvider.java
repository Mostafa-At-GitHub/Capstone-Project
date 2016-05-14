package com.sarahehabm.carbcalculator;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sarahehabm.carbcalculator.CarbCounterContract.AmountEntry;
import com.sarahehabm.carbcalculator.CarbCounterContract.ItemAmountEntry;
import com.sarahehabm.carbcalculator.CarbCounterContract.ItemEntry;
import com.sarahehabm.carbcalculator.CarbCounterContract.MealEntry;

/**
 * Created by Sarah E. Mostafa on 14-May-16.
 */
public class CarbCounterContentProvider extends ContentProvider {
    private static final String TAG = CarbCounterContentProvider.class.getSimpleName();
    private CarbCounterDatabaseHelper databaseHelper;
    private UriMatcher uriMatcher = buildUriMatcher();

    static final int ITEM = 100;
    static final int ITEM_BY_ID = 101;
    static final int AMOUNT = 200;
    static final int AMOUNT_BY_ID = 201;
    static final int ITEMAMOUNT = 300;
    static final int ITEMAMOUNT_BY_ID = 301;
    static final int MEAL = 400;
    static final int MEAL_BY_ID = 401;

    static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = CarbCounterContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, CarbCounterContract.PATH_ITEM, ITEM);
        uriMatcher.addURI(authority, CarbCounterContract.PATH_ITEM + "/#", ITEM_BY_ID);
        uriMatcher.addURI(authority, CarbCounterContract.PATH_AMOUNT, AMOUNT);
        uriMatcher.addURI(authority, CarbCounterContract.PATH_AMOUNT + "/#", AMOUNT_BY_ID);
        uriMatcher.addURI(authority, CarbCounterContract.PATH_ITEM_AMOUNT, ITEMAMOUNT);
        uriMatcher.addURI(authority, CarbCounterContract.PATH_ITEM_AMOUNT + "/#", ITEMAMOUNT_BY_ID);
        uriMatcher.addURI(authority, CarbCounterContract.PATH_MEAL, MEAL);
        uriMatcher.addURI(authority, CarbCounterContract.PATH_MEAL + "/#", MEAL_BY_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        databaseHelper = new CarbCounterDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        final int match = uriMatcher.match(uri);
        Cursor cursor;
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();
        switch (match) {
            case ITEM:
                cursor = readableDatabase.query(ItemEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            case ITEM_BY_ID:
                String itemId = String.valueOf(ContentUris.parseId(uri));
                cursor = readableDatabase.query(ItemEntry.TABLE_NAME, projection,
                        concatenateSelection(selection, ItemEntry.COLUMN_ID + " = ?"),
                        concatenateSelectionArgs(selectionArgs, new String[]{itemId}),
                        null, null, sortOrder);
                break;

            case AMOUNT:
                cursor = readableDatabase.query(AmountEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            case AMOUNT_BY_ID:
                String amountId = String.valueOf(ContentUris.parseId(uri));
                cursor = readableDatabase.query(AmountEntry.TABLE_NAME, projection,
                        concatenateSelection(selection, AmountEntry.COLUMN_ID + " = ?"),
                        concatenateSelectionArgs(selectionArgs, new String[]{amountId}),
                        null, null, sortOrder);
                break;

            case ITEMAMOUNT:
                cursor = readableDatabase.query(ItemAmountEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            case ITEMAMOUNT_BY_ID:
                String itemAmountId = String.valueOf(ContentUris.parseId(uri));
                cursor = readableDatabase.query(ItemAmountEntry.TABLE_NAME, projection,
                        concatenateSelection(selection, ItemAmountEntry.COLUMN_ID + " = ?"),
                        concatenateSelectionArgs(selectionArgs, new String[]{itemAmountId}),
                        null, null, sortOrder);
                break;

            case MEAL:
                cursor = readableDatabase.query(MealEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            case MEAL_BY_ID:
                String mealId = String.valueOf(ContentUris.parseId(uri));
                cursor = readableDatabase.query(MealEntry.TABLE_NAME, projection,
                        concatenateSelection(selection, MealEntry.COLUMN_ID + " = ?"),
                        concatenateSelectionArgs(selectionArgs, new String[]{mealId}),
                        null, null, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case ITEM:
                return ItemEntry.CONTENT_TYPE;

            case ITEM_BY_ID:
                return ItemEntry.CONTENT_ITEM_TYPE;

            case AMOUNT:
                return AmountEntry.CONTENT_TYPE;

            case AMOUNT_BY_ID:
                return AmountEntry.CONTENT_ITEM_TYPE;

            case ITEMAMOUNT:
                return ItemAmountEntry.CONTENT_TYPE;

            case ITEMAMOUNT_BY_ID:
                return ItemAmountEntry.CONTENT_ITEM_TYPE;

            case MEAL:
                return MealEntry.CONTENT_TYPE;

            case MEAL_BY_ID:
                return MealEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Uri returnUri;

        final int match = uriMatcher.match(uri);
        switch (match) {
            case ITEM: {
                long id = database.insert(ItemEntry.TABLE_NAME, null, values);
                if (id > 0)
                    returnUri = ItemEntry.buildItemUri(id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case AMOUNT: {
                long id = database.insert(AmountEntry.TABLE_NAME, null, values);
                if (id > 0)
                    returnUri = AmountEntry.buildItemUri(id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case ITEMAMOUNT: {
                long id = database.insert(ItemAmountEntry.TABLE_NAME, null, values);
                if (id > 0)
                    returnUri = ItemAmountEntry.buildItemUri(id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case MEAL: {
                long id = database.insert(MealEntry.TABLE_NAME, null, values);
                if (id > 0)
                    returnUri = MealEntry.buildItemUri(id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(returnUri, null);
        Log.v(TAG, "Inserted: " + returnUri);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        /*final int match = uriMatcher.match(uri);
        switch (match) {
            case ITEM:
                break;

            case AMOUNT:
                break;

            case ITEMAMOUNT:
                break;

            case MEAL:
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return 0;*/
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        int rowsUpdated = 0;

        final int match = uriMatcher.match(uri);
        switch (match) {
            case ITEM:
                rowsUpdated = database.update(ItemEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            case AMOUNT:
                rowsUpdated = database.update(AmountEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            case ITEMAMOUNT:
                rowsUpdated = database.update(ItemAmountEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            case MEAL:
                rowsUpdated = database.update(MealEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated > 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    private String concatenateSelection(String selection, String newSelection) {
        if (selection == null)
            return newSelection;

        return selection + " AND " + newSelection;
    }

    private String[] concatenateSelectionArgs(String[] selectionArgs, String[] newSelectionArgs) {
        if (selectionArgs == null)
            return newSelectionArgs;

        int size = selectionArgs.length + newSelectionArgs.length;
        String[] concatenatedSelectionArgs = new String[size];
        int i = 0;
        for (; i < size; i++) {
            concatenatedSelectionArgs[i] = selectionArgs[i];
        }

        for (int j = 0; j < newSelectionArgs.length; i++, j++) {
            concatenatedSelectionArgs[i] = newSelectionArgs[j];
        }

        return concatenatedSelectionArgs;
    }
}