package milos.jovanovic.sopinglista;

import  android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "shared_list_app.db";

    public static final String users_table_name = "USERS";
    public static final String users_username = "username";
    public static final String users_email = "email";
    public static final String users_password = "password";

    public static final String lists_table_name = "LISTS";
    public static final String lists_name = "name";
    public static final String lists_creator_name = "creatorName";
    public static final String lists_shared = "shared";

    public static final String items_table_name = "ITEMS";
    public static final String items_name = "name";
    public static final String items_list_name = "listName";
    public static final String items_checked = "checked";
    public static final String items_id = "id";


    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + users_table_name +
                " (" + users_username + " TEXT, " +
                users_email + " TEXT, " +
                users_password + " TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE " + lists_table_name +
                " (" + lists_name + " TEXT, " +
                lists_creator_name + " TEXT, " +
                lists_shared + " TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE " + items_table_name +
                " (" + items_name + " TEXT, " +
                items_list_name + " TEXT, " +
                items_checked + " TEXT," +
                items_id + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertUser(String username, String email, String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(users_username, username);
        values.put(users_email, email);
        values.put(users_password, password);

        db.insert(users_table_name, null, values);
        close();
    }

    public boolean checkUser(String username, String password){   //PROVERA ZA LOGIN
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(users_table_name, null, users_username + "=? AND " + users_password + "=?", new String[] { username, password }, null, null, null);
        if(cursor.getCount() <= 0) {
            return false;
        }

        close();
        return true;
    }

    public boolean doesUserExist(String username){  //PROVERA ZA REGISTER
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(users_table_name, null, users_username + "=?", new String[] { username }, null, null, null);
        if(cursor.getCount() <= 0) {
            return true;
        }

        close();
        return false;
    }

    public void insertList(Lists l, String user){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(lists_name, l.getName());
        values.put(lists_creator_name,user);
        values.put(lists_shared,l.getShare());

        db.insert(lists_table_name,null, values);
        close();
    }

    public boolean doesListExist(String listname){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(lists_table_name, null, lists_name + "=?", new String[] { listname }, null, null, null);
        if(cursor.getCount() <= 0) {
            return false;
        }

        close();
        return true;
    }

    private Lists createList(Cursor cursor) {
        String mTitle = cursor.getString(cursor.getColumnIndexOrThrow(lists_name));
        String mShared = cursor.getString(cursor.getColumnIndexOrThrow(lists_shared));

        return new Lists(mTitle, mShared);
    }

    public Lists[] readLists(String user) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(lists_table_name, null, lists_creator_name + "=? OR " + lists_shared + "=?", new String[] {user, "Yes"}, null, null, null);
        if(cursor.getCount() <= 0) {
            return null;
        }
        Lists[] lists = new Lists[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            lists[i++] = createList(cursor);
        }

        close();
        return lists;
    }

    public Lists[] readMyLists(String user) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(lists_table_name, null, lists_creator_name + "=?", new String[] {user}, null, null, null);
        if(cursor.getCount() <= 0) {
            return null;
        }
        Lists[] lists = new Lists[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            lists[i++] = createList(cursor);
        }

        close();
        return lists;
    }

    public void deleteList (String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(lists_table_name, lists_name + "=?", new String[] { name });
        //db.delete(items_table_name, items_column_name + "=?", new String[] { name });
        close();
    }

    public void insertItem (Zadatak tm,String uID, String list) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(items_name, tm.getNaslov());
        values.put(items_list_name, list);
        values.put(items_checked, Boolean.toString(tm.getProvera()));
        values.put(items_id, uID);

        db.insert(items_table_name, null, values);
        close();
    }

    public boolean doesTaskExist(String taskname, String uID){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(items_table_name, null, items_name + "=?", new String[] { taskname }, null, null, null);
        if(cursor.getCount() <= 0) {
            return false;
        }

        close();
        return true;
    }

    private Zadatak createItem (Cursor cursor) {
        String mTitle = cursor.getString(cursor.getColumnIndexOrThrow(items_name));
        String mChecked = cursor.getString(cursor.getColumnIndexOrThrow(items_checked));
        String uID = cursor.getString(cursor.getColumnIndexOrThrow(items_id));
        return new Zadatak(mTitle, Boolean.valueOf(mChecked), uID);
    }

    public Zadatak[] readItems (String list) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(items_table_name, null, items_list_name + "=?", new String[] {list}, null, null, null);
        if(cursor.getCount() <= 0) {
            return null;
        }
        Zadatak[] lists = new Zadatak[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            lists[i++] = createItem(cursor);
        }

        close();
        return lists;
    }

    public void deleteItem (String uID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(items_table_name, items_id + " =?", new String[] { uID });
        close();
    }

    public void updateItem(String uID, String mChecked){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(items_table_name, null, items_id + "=?", new String[] {uID}, null, null, null);
        cursor.moveToFirst();
        String title = cursor.getString(cursor.getColumnIndexOrThrow(items_name));
        String listName =  cursor.getString(cursor.getColumnIndexOrThrow(items_list_name));

        ContentValues cv = new ContentValues();
        cv.put(items_name, title);
        cv.put(items_list_name, listName);
        cv.put(items_checked, mChecked);
        cv.put(items_id, uID);

        db.update(items_table_name, cv, items_id + "=?", new String[] {uID});
        close();
    }
}
