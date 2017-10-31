package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Food;

/**
 * Created by Hytham on 10/31/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    ArrayList<Food> foodList = new ArrayList<>();
    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = " CREATE TABLE " + Constants.TABLE_NAME + "(" + Constants.KEY_ID + " INTEGER PRIMARY KEY, " +
                Constants.FOOD_NAME +  " TEXT, " + Constants.FOOD_CALORIES_NAME + " INT, " + Constants.DATE_NAME + " LONG); ";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);

    }

    // get total items
    public int getTotalItems(){
        int totalItem = 0;
        String query = " SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query ,null);
        totalItem = cursor.getCount();
        cursor.close();
        return totalItem;
    }
    // get total calories consumed
    public int getTotalColories(){
        int totalCal = 0;
        String query = " SELECT SUM( " + Constants.FOOD_CALORIES_NAME + ") " + " FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);

        if (cursor.moveToFirst()){
            totalCal = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return totalCal;
    }
    // deleted ...........
    public void deleteFood(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME , Constants.KEY_ID + " = ? " , new String[] { String.valueOf(id)});
        db.close();
    }

    // add food to list

    public void addFood(Food food){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.FOOD_NAME , food.getFoodName());
        values.put(Constants.FOOD_CALORIES_NAME , food.getCalories());
        values.put(Constants.DATE_NAME , System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME , null , values);
        Log.v("Added Food item " , "Yesssss");
        db.close();
    }
    // get all lists
    public ArrayList<Food> getFoods(){
        foodList.clear();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME , new String[] {Constants.KEY_ID , Constants.FOOD_NAME ,Constants.FOOD_CALORIES_NAME,
        Constants.DATE_NAME} , null , null , null ,null , Constants.DATE_NAME + " DESC ");

        if (cursor.moveToFirst()){

            do {

                Food food = new Food();
                food.setFoodName(cursor.getString(cursor.getColumnIndex(Constants.FOOD_NAME)));
                food.setCalories(cursor.getInt(cursor.getColumnIndex(Constants.FOOD_CALORIES_NAME)));
                food.setFoodId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String date = dateFormat.format( new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());
                food.setRecordDate(date);

                foodList.add(food);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return foodList;
    }
}
