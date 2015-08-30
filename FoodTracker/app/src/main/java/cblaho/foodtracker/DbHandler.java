package cblaho.foodtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maxm on 8/29/15.
 */
public class DbHandler {
    private static String table = "ingredients";
    private SQLiteDatabase database;

    public DbHandler(Context context) {
        database = (new DbHelper(context)).getWritableDatabase();
    }

    public Ingredient getIngredientById(String id) {
        Cursor c = database.query(table, null, "id=?", new String[] {id}, null, null, null, null);
        if(c != null) {
            String name = null;
            String group_name = null;
            HashMap<String,Double> nutrients = new HashMap<>();
            for(String s : c.getColumnNames()) {
                switch (s) {
                    case "id":
                        break;
                    case "name":
                        name = c.getString(c.getColumnIndex(s));
                        break;
                    case "group_name":
                        group_name = c.getString(c.getColumnIndex(s));
                        break;
                    default:
                        nutrients.put(s, c.getDouble(c.getColumnIndex(s)));
                        break;
                }
            }
            c.close();
            return new Ingredient(id, name, group_name, nutrients);
        } else {
            return null;
        }
    }

    public void save(Food f) {
        ContentValues values = new ContentValues();
        values.put("id", f.getID());
        values.put("name", f.getName());
        values.put("group_name", f.getGroup());
        for(Map.Entry<String,Double> p : f.getNutrients().entrySet()) {
            values.put(p.getKey(), p.getValue());
        }
        database.insert(table, null, values);
    }

    public Map<String,String> getIngredientList() {
        Cursor c = database.query(table, new String[] {"id","name"}, null, null, null, null, null);
        if(c != null) {
            HashMap<String,String> idmap = new HashMap<>();
            while(!c.isAfterLast()) {
                idmap.put(c.getString(0),c.getString(1));
            }
            c.close();
            return idmap;
        } else {
            return null;
        }
    }

    private class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, table, null, 0);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO: Finish this with Karishma
            db.execSQL("CREATE TABLE ingredients");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    }
}
