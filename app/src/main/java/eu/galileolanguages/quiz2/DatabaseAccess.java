package eu.galileolanguages.quiz2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private static DatabaseAccess instance;
    SQLiteDatabase db;
    Cursor c;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    void open() {
        this.db = openHelper.getWritableDatabase();
    }

    void close() {
        if (db != null) {
            this.db.close();
        }
    }


}
