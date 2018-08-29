package card.developer.lln.database.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManagerHelper {

    private DataBaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerHelper(Context c) {
        context = c;
    }

    public DBManagerHelper open() throws SQLException {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DataBaseHelper.NOME, name);
        contentValue.put(DataBaseHelper.SOBRENOME, desc);
        database.insert(DataBaseHelper.TABLE_NAME, null, contentValue);
    }

    public void clearDatabase() {

        SQLiteDatabase db = dbHelper.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(DataBaseHelper.TABLE_NAME, null, null);

    }

    public Cursor fetch() {
        String[] columns = new String[] { DataBaseHelper._ID, DataBaseHelper.NOME, DataBaseHelper.SOBRENOME };
        Cursor cursor = database.query(DataBaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String sobrenome) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.NOME, name);
        contentValues.put(DataBaseHelper.SOBRENOME, sobrenome);
        int i = database.update(DataBaseHelper.TABLE_NAME, contentValues, DataBaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DataBaseHelper.TABLE_NAME, DataBaseHelper._ID + "=" + _id, null);
    }

}
