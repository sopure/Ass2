package database.DBStructure;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "users.db";
    private final Context context;

    private static final String SQL_CREATE_TABLE_NATIONALITY =
            "CREATE TABLE " + DBStructure.tableNationality.TABLE_NAME + " (" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBStructure.tableNationality.COLUMN_NAME +
                    " )";
    private static final String SQL_CREATE_TABLE_NATIVELANGUAGE =
            "CREATE TABLE " + DBStructure.tableNativeLanguage.TABLE_NAME + " (" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBStructure.tableNativeLanguage.COLUMN_NAME +
                    " )";

    private static final String SQL_DELETE_TABLE_NATIONALITY =
            "DROP TABLE IF EXISTS " + DBStructure.tableNationality.TABLE_NAME;
    private static final String SQL_DELETE_TABLE_NATIVELANGUAGE =
            "DROP TABLE IF EXISTS " + DBStructure.tableNativeLanguage.TABLE_NAME;

    private NationalityOpenHelper myDBHelper;
    private SQLiteDatabase db;

    private String[] projection1 = {
            DBStructure.tableNationality.COLUMN_NAME};
    private String[] projection2 = {
            DBStructure.tableNativeLanguage.COLUMN_NAME};

    // we can obtain a reference to it and call it
    // in the DBManager constructor
    public DBManager(Context ctx) {
        this.context = ctx;
        myDBHelper = new NationalityOpenHelper(context);
    }

    // open db
    public DBManager open() throws SQLException {
        db = myDBHelper.getWritableDatabase();
        return this;
    }
    // close db
    public void close() {
        myDBHelper.close();
    }

    /**
     * Getting all labels
     * returns list of labels
     * */
    public List<String> getAllNation(){
        List<String> labels = new ArrayList<String>();

//        SQLiteDatabase db = myDBHelper.getReadableDatabase();
        Cursor cursor = db.query(DBStructure.tableNationality.TABLE_NAME, projection1, null, null,
                null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //add first entry into labels
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
    public List<String> getAllNativeLanguage(){
        List<String> labels = new ArrayList<String>();

//        SQLiteDatabase db = myDBHelper.getReadableDatabase();
        Cursor cursor = db.query(DBStructure.tableNativeLanguage.TABLE_NAME, projection2, null, null,
                null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //add first entry into labels
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


    private class NationalityOpenHelper extends SQLiteOpenHelper {


        public NationalityOpenHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // read nation txt from assets and insert db
        private void readNationFromAssets(SQLiteDatabase db) {
            try {
                ContentValues values = new ContentValues();
                InputStream is = context.getAssets().open("nationData.txt");
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(reader);
                String s1;
                System.out.println("***********************");
                System.out.println("***********************");
                System.out.println("***********************");
                // read line by line
                while ((s1 = br.readLine()) != null) {
                    System.out.println("---------------------");
                    //add key-value pairs in values
                    values.put(DBStructure.tableNationality.COLUMN_NAME, s1);
                    //insert record
                    db.insert(DBStructure.tableNationality.TABLE_NAME, null, values);
                }
                br.close();
                reader.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // read nation txt from assets and insert db
        private void readNativeLanguageFromAssets(SQLiteDatabase db) {
            try {
                ContentValues values = new ContentValues();
                InputStream is = context.getAssets().open("nativeLanguageData.txt");
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(reader);
                String s1;

                // read line by line
                while ((s1 = br.readLine()) != null) {
                    //add key-value pairs in values
                    values.put(DBStructure.tableNativeLanguage.COLUMN_NAME, s1);
                    //insert record
                    db.insert(DBStructure.tableNativeLanguage.TABLE_NAME, null, values);
                }
                br.close();
                reader.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(SQL_CREATE_TABLE_NATIONALITY);
            db.execSQL(SQL_CREATE_TABLE_NATIVELANGUAGE);
            // call this method here
            // onCreate will only be called once(only when there didn't exist a user.db)
            readNationFromAssets(db);
            System.out.println("xxxxxxxxxxxx");
            System.out.println("xxxxxxxxxxxx");
            System.out.println("xxxxxxxxxxxx");
            readNativeLanguageFromAssets(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            // This database is only a cache for online data, so its upgrade policy is
// to simply to discard the data and start over
            db.execSQL(SQL_DELETE_TABLE_NATIONALITY);
            db.execSQL(SQL_DELETE_TABLE_NATIVELANGUAGE);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
