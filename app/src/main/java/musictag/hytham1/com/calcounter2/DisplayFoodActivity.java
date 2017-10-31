package musictag.hytham1.com.calcounter2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.CustomListViewAdapter;
import data.DatabaseHandler;
import model.Food;
import util.Utilitiy;

public class DisplayFoodActivity extends AppCompatActivity {
    DatabaseHandler dba;
    CustomListViewAdapter foodAdapter;
    ListView listView;
    ArrayList<Food> dbfood = new ArrayList<>();
      Food food;
    TextView totalCals , totalFood;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_food);
        listView = (ListView) findViewById(R.id.listView);
        totalCals = (TextView) findViewById(R.id.totalAmountTextView);
        totalFood = (TextView) findViewById(R.id.totalItemTextView);


        refreshDB();

    }

    private void refreshDB() {

        dbfood.clear();
        dba = new DatabaseHandler(getApplicationContext());
        ArrayList<Food> foodsFromDb = dba.getFoods();

        int totalsItems = dba.getTotalItems();
        int calValue = dba.getTotalColories();


        String formattedValue = Utilitiy.dataFormat(calValue);
        String formattedItem = Utilitiy.dataFormat(totalsItems);


        totalCals.setText("Total Calories :" + formattedValue);
        totalFood.setText("Total Food  :" + formattedItem);

        for (int i = 0 ; i < foodsFromDb.size() ; i++){
            String name = foodsFromDb.get(i).getFoodName();
            String dateText = foodsFromDb.get(i).getRecordDate();
            int cals = foodsFromDb.get(i).getCalories();
            int foodId = foodsFromDb.get(i).getFoodId();

            Log.v("FOODS ID" , String.valueOf(foodId));

            food = new Food();
            food.setFoodName(name);
            food.setCalories(cals);
            food.setRecordDate(dateText);
            food.setFoodId(foodId);

            dbfood.add(food);

        }

        dba.close();
        foodAdapter = new CustomListViewAdapter(DisplayFoodActivity.this , R.layout.list_row , dbfood );

        listView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();








    }
}
