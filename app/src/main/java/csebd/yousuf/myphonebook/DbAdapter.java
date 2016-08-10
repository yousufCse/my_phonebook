package csebd.yousuf.myphonebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Yousuf on 3/9/2016.
 */
public class DbAdapter {

    private DbHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public DbAdapter(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);

    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }
    public void close() {
        if (db != null)
        db.close();
    }
    public void read() {
        db = dbHelper.getReadableDatabase();
    }

    public long insertPhonebook(Phonebook phonebook) {
        open();
        ContentValues values = new ContentValues();
        values.put(PhoneData.Table.FIELD_PHONE_NAME, phonebook.getName() );
        values.put(PhoneData.Table.FIELD_PHONE_PHONE, phonebook.getPhone());
        values.put(PhoneData.Table.FIELD_PHONE_EMAIL, phonebook.getEmail());
        values.put(PhoneData.Table.FIELD_PHONE_ADDRESS, phonebook.getAddress());
        values.put(PhoneData.Table.FIELD_PHONE_IMAGE_PATH, phonebook.getImagePath());

        long inserted = db.insert(PhoneData.Table.TABLE_BOOK_NAME, null, values);

        close();

        return inserted;
    } // end insert

    // get all phonebook data by array
    public ArrayList<Phonebook> getAllPhonebook() {
        read();
        ArrayList<Phonebook> allPhonebooks = new ArrayList<>();
        String sortOrder = PhoneData.Table.FIELD_PHONE_NAME + " ASC";

        Cursor cursor = db.query(PhoneData.Table.TABLE_BOOK_NAME, null, null, null, null, null, sortOrder);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Phonebook phonebook = new Phonebook();
                phonebook.setId(cursor.getInt(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_ID)));
                phonebook.setName(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_NAME)));
                phonebook.setPhone(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_PHONE)));
                phonebook.setEmail(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_EMAIL)));
                phonebook.setAddress(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_ADDRESS)));
                phonebook.setImagePath(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_IMAGE_PATH)));

                allPhonebooks.add(phonebook);
            } while (cursor.moveToNext());
        } else  {

        }

        if (!cursor.isClosed()) {
            cursor.close();
        }
        close(); // db readable close

        return allPhonebooks;
    }

    // query by id
    public Phonebook  queryById(int id) {
        read();
        Phonebook phonebook = new Phonebook();

        String selection = PhoneData.Table.FIELD_PHONE_ID  +" = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(PhoneData.Table.TABLE_BOOK_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            phonebook.setId(cursor.getInt(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_ID)));
            phonebook.setName(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_NAME)));
            phonebook.setPhone(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_PHONE)));
            phonebook.setEmail(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_EMAIL)));
            phonebook.setAddress(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_ADDRESS)));
            phonebook.setImagePath(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_IMAGE_PATH)));

        }
        if (!cursor.isClosed())
            cursor.close();
        close(); // db read close

        return phonebook;
    }

    // delete by an id
    public boolean deletePhonebook(int id) {
        open();
        String whereClause = PhoneData.Table.FIELD_PHONE_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        int deleteIntValue = db.delete(PhoneData.Table.TABLE_BOOK_NAME, whereClause, whereArgs);

        boolean deleted = (deleteIntValue > 0) ? true : false;
        if (db.isOpen()) {
            close();
        }
//        return db.delete(PhoneData.Table.TABLE_BOOK_NAME, whereClause, whereArgs ) > 0;

        return deleted;


    }

    // update
    public boolean updatePhonebook(int id, Phonebook phonebook) {
        open();
        ContentValues values = new ContentValues();
        values.put(PhoneData.Table.FIELD_PHONE_NAME, phonebook.getName());
        values.put(PhoneData.Table.FIELD_PHONE_PHONE, phonebook.getPhone());
        values.put(PhoneData.Table.FIELD_PHONE_EMAIL, phonebook.getEmail());
        values.put(PhoneData.Table.FIELD_PHONE_ADDRESS, phonebook.getAddress());
        values.put(PhoneData.Table.FIELD_PHONE_IMAGE_PATH, phonebook.getImagePath());

        String whereClause = PhoneData.Table.FIELD_PHONE_ID +" = ?";
        String[] whereArgs = {String.valueOf(id)};

        int updateValue = db.update(PhoneData.Table.TABLE_BOOK_NAME, values, whereClause, whereArgs);
        boolean updated = updateValue > 0 ? true : false ;

        close();

        return updated;
    }

    // search phonebook
    public ArrayList<Phonebook> getSearchPhonebookList(String queryText) {
        read();
        ArrayList<Phonebook> searchedPhonebookList = new ArrayList<>();

        String selection = PhoneData.Table.FIELD_PHONE_NAME + " LIKE ? OR "
                + PhoneData.Table.FIELD_PHONE_ADDRESS+"  LIKE ? OR "
                + PhoneData.Table.FIELD_PHONE_EMAIL+ "  LIKE ? OR "
                + PhoneData.Table.FIELD_PHONE_PHONE + " LIKE ?";

        String[] selectionArgs = {"%"+queryText+"%", "%"+queryText+"%", "%"+queryText+"%", "%"+queryText+"%"};
        String sortOrder = PhoneData.Table.FIELD_PHONE_NAME + " ASC";
        Cursor cursor = db.query(PhoneData.Table.TABLE_BOOK_NAME, null, selection, selectionArgs, null, null, sortOrder );

        if (cursor.getCount() > 0 && cursor != null) {
            cursor.moveToFirst();
            do {
                Phonebook phonebook = new Phonebook();
                phonebook.setId(cursor.getInt(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_ID)));
                phonebook.setName(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_NAME)));
                phonebook.setAddress(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_ADDRESS)));
                phonebook.setEmail(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_EMAIL)));
                phonebook.setPhone(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_PHONE)));
                phonebook.setImagePath(cursor.getString(cursor.getColumnIndex(PhoneData.Table.FIELD_PHONE_IMAGE_PATH)));

                searchedPhonebookList.add(phonebook);
            }while (cursor.moveToNext());
        } else {
            // do something.
        }

        if (!cursor.isClosed())
            cursor.close();
        close(); // db closed

        return searchedPhonebookList;
    }

}
