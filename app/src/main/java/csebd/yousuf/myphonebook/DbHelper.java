package csebd.yousuf.myphonebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Yousuf on 3/9/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_PHONE_NAME = "phone_book_db";
    private static final int DB_PHONE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_PHONE_NAME, null, DB_PHONE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(PhoneData.Table.PHONE_CREATE_QUERY);
//        Toast.makeText(context, "db created", Toast.LENGTH_SHORT).show();
//        Log.d("www.d.com", "table created : " + PhoneData.Table.PHONE_CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PhoneData.Table.PHONE_DELETE_QUERY);
        onCreate(db);
    }
}
