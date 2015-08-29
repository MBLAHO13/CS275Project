package cblaho.foodtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by maxm on 8/29/15.
 */
public class DbHandler extends SQLiteOpenHelper {
    public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbHandler(Context context) {
        super(context, "ingredients", null, 0);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO: Finish this with Karishma
        db.execSQL("CREATE TABLE ingredients");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
