package com.example.heychat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String Database_Name = "HeyChat database";
    public static final String Table_Name = "UserAccounts";

    public static final String Col_1 = "EMAIL";
    public static final String Col_2 = "PASS";
    public static final String Col_3 = "USERNAME";

    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Table_Name + "("+
                Col_1 + " TEXT PRIMARY KEY, "+
                Col_2 + " TEXT, "+
                Col_3 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }

    public boolean insertData(String email, String pass, String username){
        SQLiteDatabase  db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Col_1, email);
        values.put(Col_2, pass);
        values.put(Col_3, username);

        long var = db.insert(Table_Name, null, values);

        if(var == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean verifyLogin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Table_Name + " WHERE " + Col_1 + "=? AND " + Col_2 + " =?";
        String[] selectionArgs = {email,password};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean result = cursor.getCount() > 0;

        cursor.close();

        return result;
    }
    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name,null);

        if(cursor.moveToFirst()){
            do{
                String email = cursor.getString(cursor.getColumnIndexOrThrow(Col_1));
                String pass = cursor.getString(cursor.getColumnIndexOrThrow(Col_2));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(Col_3));

                UserModel user = new UserModel(email, pass, username);
                userList.add(user);
            }while(cursor.moveToNext());
        }

        cursor.close();
        return userList;
    }
}
