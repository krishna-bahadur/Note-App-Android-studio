package com.example.todoapp.dbutil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import com.example.todoapp.Add_Activity;
import com.example.todoapp.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static  final String DATABASE_NAME="itemDB";
    private static SQLiteDatabase db;

    public DbHelper(Context c){
        super(c,DATABASE_NAME,null,1);
        db= getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS item(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title VARCHAR(100)," +
                "description VARCHAR(500)," +
                "time VARCHAR(50)," +
                "flag INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS item");
        onCreate(db);
    }
    public  void insertDataToDB(ItemModel model){
        ContentValues cv = new ContentValues();
        cv.put("title",model.getTitle());
        cv.put("description",model.getDescription());
        cv.put("time",model.getTime());
        cv.put("flag",model.getFlag());
        db.insert("item",null,cv);
    }
    public  ArrayList<ItemModel> retrieveData(){
        ArrayList<ItemModel> data =new ArrayList<>();
        String sql = "SELECT * FROM item WHERE flag = 0";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                ItemModel model = new ItemModel();
                model.setId(cursor.getInt(0));
                model.setTitle(cursor.getString(1));
                model.setDescription(cursor.getString(2));
                model.setTime(cursor.getString(3));
                data.add(model);

            }while (cursor.moveToNext());
        }
    return  data;
    }

    public void updateRecord(ItemModel model,int id){
        Log.e("Display title",""+model.getTitle());
        ContentValues cv = new ContentValues();
        cv.put("title",model.getTitle());
        cv.put("description",model.getDescription());
        Log.e("",""+model.getTitle());
        db.update("item",cv,"Id="+id,null);



    }
    public  void RemoveItemFromDb(int id){
        db.execSQL("DELETE FROM item WHERE id ="+id);
    }


    public void updateFlag(ItemModel model, int id) {
       ContentValues cv = new ContentValues();
       cv.put("flag",model.getFlag());
       db.update("item",cv,"Id="+id,null);
    }

    public ArrayList<ItemModel> retrieveHiddenData() {
        ArrayList<ItemModel> data =new ArrayList<>();
        String sql = "SELECT * FROM item WHERE flag = 1";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                ItemModel model = new ItemModel();
                model.setId(cursor.getInt(0));
                model.setTitle(cursor.getString(1));
                model.setDescription(cursor.getString(2));
                model.setTime(cursor.getString(3));
                data.add(model);

            }while (cursor.moveToNext());
        }
        return  data;
    }
}
