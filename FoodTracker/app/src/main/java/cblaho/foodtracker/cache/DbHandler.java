package cblaho.foodtracker.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

import cblaho.foodtracker.data.Food;
import cblaho.foodtracker.data.Ingredient;

/**
 * Created by maxm on 8/29/15.
 * Handler for local SQLite Nutrient Datastore
 */
public class DbHandler {
    private static String table = "ingredients";
    private SQLiteDatabase database;

    public DbHandler(Context context) {
        System.out.println("Connecting to Database");
        database = (new DbHelper(context)).getWritableDatabase();
        System.out.println("Connected Successfully");
    }

    public Ingredient getIngredientById(String id) {
        Cursor c = database.query(table, null, "id=?", new String[] {id}, null, null, null, null);
        if(c != null) {
            String name = null;
            String group_name = null;
            HashMap<String,Double> nutrients = new HashMap<>();
            c.moveToFirst();
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
        try {
            database.insert(table, null, values);
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
        }
    }

    public Map<String,String> getIngredientList() {
        System.out.println("Getting Ingredient list");
        Cursor c = database.query(table, new String[] {"id","name"}, null, null, null, null, null);
        HashMap<String,String> idmap = new HashMap<>();
        if(c != null) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                idmap.put(c.getString(c.getColumnIndex("id")),c.getString(c.getColumnIndex("name")));
                c.moveToNext();
            }
            c.close();
        }
        System.out.println("Returning Ingredient List");
        return idmap;
    }

    private class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, table, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE ingredients(" +
                    "id varchar(30) PRIMARY KEY," +
                    "name varchar(30) default \"\"," +
                    "group_name varchar(30) default \"\"," +
                    "\"22_1_t\" varchar(30) default \"\"," +
                    "\"22_4\" varchar(30) default \"\"," +
                    "\"vitamin_d\" varchar(30) default \"\"," +
                    "\"vitamin_b_12\" varchar(30) default \"\"," +
                    "\"sugars_total\" varchar(30) default \"\"," +
                    "\"18_2_n_6_cc\" varchar(30) default \"\"," +
                    "\"16_1_c\" varchar(30) default \"\"," +
                    "\"phenylalanine\" varchar(30) default \"\"," +
                    "\"14_0 default\" varchar(30) default \"\"," +
                    "\"22_5_n_3_dpa\" varchar(30) default \"\"," +
                    "\"pantothenic_acid\" varchar(30) default \"\"," +
                    "\"fatty_acids_total_trans\" varchar(30) default \"\"," +
                    "\"18_2_undifferentiated\" varchar(30) default \"\"," +
                    "\"sucrose\" varchar(30) default \"\"," +
                    "\"17_1\" varchar(30) default \"\"," +
                    "\"carotene_alpha\" varchar(30) default \"\"," +
                    "\"retinol\" varchar(30) default \"\"," +
                    "\"vitamin_a_iu\" varchar(30) default \"\"," +
                    "\"18_1_c\" varchar(30) default \"\"," +
                    "\"20_0\" varchar(30) default \"\"," +
                    "\"vitamin_d3_cholecalciferol\" varchar(30) default \"\"," +
                    "\"16_0\" varchar(30) default \"\"," +
                    "\"valine\" varchar(30) default \"\"," +
                    "\"22_1_undifferentiated\" varchar(30) default \"\"," +
                    "\"tocopherol_beta\" varchar(30) default \"\"," +
                    "\"vitamin_c_total_ascorbic_acid\" varchar(30) default \"\"," +
                    "\"18_3_n_3_ccc_ala\" varchar(30) default \"\"," +
                    "\"14_1\" varchar(30) default \"\"," +
                    "\"selenium_se\" varchar(30) default \"\"," +
                    "\"leucine\" varchar(30) default \"\"," +
                    "\"20_4_undifferentiated\" varchar(30) default \"\"," +
                    "\"vitamin_b_12_added\" varchar(30) default \"\"," +
                    "\"18_2_clas\" varchar(30) default \"\"," +
                    "\"niacin\" varchar(30) default \"\"," +
                    "\"isoleucine\" varchar(30) default \"\"," +
                    "\"vitamin_e_added\" varchar(30) default \"\"," +
                    "\"lactose\" varchar(30) default \"\"," +
                    "\"ash\" varchar(30) default \"\"," +
                    "\"tocopherol_gamma\" varchar(30) default \"\"," +
                    "\"15_0\" varchar(30) default \"\"," +
                    "\"20_2_n_6_cc\" varchar(30) default \"\"," +
                    "\"folate_food\" varchar(30) default \"\"," +
                    "\"24_1_c\" varchar(30) default \"\"," +
                    "\"folate_dfe\" varchar(30) default \"\"," +
                    "\"18_4\" varchar(30) default \"\"," +
                    "\"22_0\" varchar(30) default \"\"," +
                    "\"phosphorus_p\" varchar(30) default \"\"," +
                    "\"fiber_total_dietary\" varchar(30) default \"\"," +
                    "\"choline_total\" varchar(30) default \"\"," +
                    "\"fatty_acids_total_saturated\" varchar(30) default \"\"," +
                    "\"fructose\" varchar(30) default \"\"," +
                    "\"20_3_n_6\" varchar(30) default \"\"," +
                    "\"16_1_undifferentiated\" varchar(30) default \"\"," +
                    "\"threonine\" varchar(30) default \"\"," +
                    "\"fatty_acids_total_monounsaturated\" varchar(30) default \"\"," +
                    "\"maltose\" varchar(30) default \"\"," +
                    "\"serine\" varchar(30) default \"\"," +
                    "\"18_2_t_not_further_defined\" varchar(30) default \"\"," +
                    "\"20_5_n_3_epa\" varchar(30) default \"\"," +
                    "\"total_lipid_fat\" varchar(30) default \"\"," +
                    "\"protein\" varchar(30) default \"\"," +
                    "\"theobromine\" varchar(30) default \"\"," +
                    "\"galactose\" varchar(30) default \"\"," +
                    "\"18_3_undifferentiated\" varchar(30) default \"\"," +
                    "\"fatty_acids_total_trans_monoenoic\" varchar(30) default \"\"," +
                    "\"20_3_undifferentiated\" varchar(30) default \"\"," +
                    "\"histidine\" varchar(30) default \"\"," +
                    "\"alcohol_ethyl\" varchar(30) default \"\"," +
                    "\"folate_total\" varchar(30) default \"\"," +
                    "\"lysine\" varchar(30) default \"\"," +
                    "\"22_6_n_3_dha\" varchar(30) default \"\"," +
                    "\"cholesterol\" varchar(30) default \"\"," +
                    "\"hydroxyproline\" varchar(30) default \"\"," +
                    "\"16_1_t\" varchar(30) default \"\"," +
                    "\"manganese_mn\" varchar(30) default \"\"," +
                    "\"18_1_undifferentiated\" varchar(30) default \"\"," +
                    "\"carbohydrate_by_difference\" varchar(30) default \"\"," +
                    "\"cystine\" varchar(30) default \"\"," +
                    "\"vitamin_b_6\" varchar(30) default \"\"," +
                    "\"copper_cu\" varchar(30) default \"\"," +
                    "\"potassium_k\" varchar(30) default \"\"," +
                    "\"12_0\" varchar(30) default \"\"," +
                    "\"cryptoxanthin_beta\" varchar(30) default \"\"," +
                    "\"vitamin_d_d2__d3\" varchar(30) default \"\"," +
                    "\"arginine\" varchar(30) default \"\"," +
                    "\"vitamin_a_rae\" varchar(30) default \"\"," +
                    "\"tocopherol_delta\" varchar(30) default \"\" ," +
                    "\"folic_acid\" varchar(30) default \"\"," +
                    "\"fluoride_f\" varchar(30) default \"\"," +
                    "\"methionine\" varchar(30) default \"\"," +
                    "\"6_0\" varchar(30) default \"\"," +
                    "\"glycine\" varchar(30) default \"\"," +
                    "\"thiamin\" varchar(30) default \"\"," +
                    "\"energy\" varchar(30) default \"\"," +
                    "\"15_1\" varchar(30) default \"\"," +
                    "\"proline\" varchar(30) default \"\"," +
                    "\"betaine\" varchar(30) default \"\"," +
                    "\"iron_fe\" varchar(30) default \"\"," +
                    "\"4_0\" varchar(30) default \"\"," +
                    "\"riboflavin\" varchar(30) default \"\"," +
                    "\"vitamin_k_phylloquinone\" varchar(30) default \"\"," +
                    "\"aspartic_acid\" varchar(30) default \"\"," +
                    "\"magnesium_mg\" varchar(30) default \"\"," +
                    "\"tyrosine\" varchar(30) default \"\"," +
                    "\"lutein__zeaxanthin\" varchar(30) default \"\"," +
                    "\"calcium_ca\" varchar(30) default \"\"," +
                    "\"lycopene\" varchar(30) default \"\"," +
                    "\"tryptophan\" varchar(30) default \"\"," +
                    "\"sodium_na\" varchar(30) default \"\"," +
                    "\"8_0\" varchar(30) default \"\"," +
                    "\"caffeine\" varchar(30) default \"\"," +
                    "\"water\" varchar(30) default \"\"," +
                    "\"18_3_n_6_ccc\" varchar(30) default \"\"," +
                    "\"glucose_dextrose\" varchar(30) default \"\"," +
                    "\"22_1_c\" varchar(30) default \"\"," +
                    "\"18_0\" varchar(30) default \"\"," +
                    "\"vitamin_e_alpha_tocopherol\" varchar(30) default \"\"," +
                    "\"17_0\" varchar(30) default \"\"," +
                    "\"18_3i\" varchar(30) default \"\"," +
                    "\"glutamic_acid\" varchar(30) default \"\"," +
                    "\"carotene_beta\" varchar(30) default \"\"," +
                    "\"24_0\" varchar(30) default \"\"," +
                    "\"alanine\" varchar(30) default \"\"," +
                    "\"10_0\" varchar(30) default \"\"," +
                    "\"fatty_acids_total_polyunsaturated\" varchar(30) default \"\"," +
                    "\"20_1\" varchar(30) default \"\"," +
                    "\"18_1_t\" varchar(30) default \"\"," +
                    "\"zinc_zn\" varchar(30) default \"\");"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    }
}
