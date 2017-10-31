package model;

import java.io.Serializable;

/**
 * Created by Hytham on 10/31/2017.
 */

public class Food implements Serializable{
    public static final long seriaVersionUID = 10L;
    private String foodName;
    private int foodId , calories;
    private String recordDate;

    public Food (String foodName , int cal , int id , String date){
        this.foodName = foodName;
        this.calories = cal;
        this.foodId = id;
        this.recordDate = date;
    }
    public Food(){

    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public static long getSeriaVersionUID() {
        return seriaVersionUID;
    }
}
