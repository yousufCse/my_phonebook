package csebd.yousuf.myphonebook;

import android.provider.BaseColumns;

/**
 * Created by Yousuf on 3/9/2016.
 */
public class PhoneData {

    public static abstract class Table implements BaseColumns {

        public static final String TABLE_BOOK_NAME = "phone_book";
        public static final String FIELD_PHONE_ID = "_id";
        public static final String FIELD_PHONE_NAME = "name";
        public static final String FIELD_PHONE_PHONE = "phone";
        public static final String FIELD_PHONE_EMAIL = "email";
        public static final String FIELD_PHONE_ADDRESS = "address";
        public static final String FIELD_PHONE_IMAGE_PATH = "image_path";

        public static final String PHONE_CREATE_QUERY = "CREATE TABLE " + Table.TABLE_BOOK_NAME
                + "( "
                + Table.FIELD_PHONE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Table.FIELD_PHONE_NAME + " TEXT NOT NULL, "
                + Table.FIELD_PHONE_PHONE + " TEXT NOT NULL, "
                + Table.FIELD_PHONE_EMAIL + " TEXT, "
                + Table.FIELD_PHONE_ADDRESS + " TEXT, "
                + Table.FIELD_PHONE_IMAGE_PATH + " TEXT"
                + " )";

        public static final String PHONE_DELETE_QUERY = "DROP TABLE IF  EXISTS " + Table.TABLE_BOOK_NAME ;

    }

}
