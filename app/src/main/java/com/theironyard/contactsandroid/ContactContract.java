package com.theironyard.contactsandroid;

import android.provider.BaseColumns;

/**
 * Created by branden on 5/28/16.
 */
public final class ContactContract {
    //empty constructor keeps someone from accidently accesing class
    public ContactContract() {}

    /* inner class defines table structure */
    public static abstract class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contact";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PHONE = "phone";
    }

    /* Create the table logic */
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactEntry.TABLE_NAME + " (" +
                    ContactEntry._ID + " INTEGER PRIMARY KEY," +
                    ContactEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    ContactEntry.COLUMN_NAME_PHONE + TEXT_TYPE +
            " )";

    /* Drop the table */
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContactEntry.TABLE_NAME;



}
