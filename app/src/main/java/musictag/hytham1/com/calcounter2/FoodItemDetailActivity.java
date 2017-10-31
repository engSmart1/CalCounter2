package musictag.hytham1.com.calcounter2;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class FoodItemDetailActivity extends AppCompatActivity {
    TextView  foodName , foodCalories , foodDate;
    Button shareButton;
    private int foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_detail);

        foodName = (TextView) findViewById(R.id.detsFoodName);
        foodCalories = (TextView) findViewById(R.id.detsCaloriesValues);
        foodDate = (TextView) findViewById(R.id.detsDateText);

        shareButton = (Button) findViewById(R.id.detsShareButton);

        Food food = (Food) getIntent().getSerializableExtra("userObj");


        foodName.setText(food.getFoodName());
        foodCalories.setText(String.valueOf(food.getCalories()));
        foodDate.setText(food.getRecordDate());

         foodId = food.getFoodId();

        foodCalories.setTextColor(Color.RED);
        foodCalories.setTextSize(34.9f);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareCals();
            }
        });


    }
    public void shareCals(){
        StringBuilder  dataString = new StringBuilder();
        String name = foodName.getText().toString();
        String cals = foodCalories.getText().toString();
        String date = foodDate.getText().toString();

        dataString.append(" Food : " + name + "\n");
        dataString.append(" Calories : " + cals + "\n");
        dataString.append("Eaten On : " + date + "\n");

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT , "My Caloric Intake");
        intent.putExtra(Intent.EXTRA_EMAIL , new String[] {"recipient@example.com"});
        intent.putExtra(Intent.EXTRA_TEXT , dataString.toString());

        try{
            startActivity(Intent.createChooser(intent , "Send email....."));
        } catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(), "Please install email client before sending", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_food_item_detail , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.deleteItem){

            AlertDialog.Builder alert = new AlertDialog.Builder(FoodItemDetailActivity.this);
            alert.setTitle("Delete?");
            alert.setMessage("Are you Sure you want To Delete That Item ?!");
            alert.setNegativeButton("NO" , null);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                    dba.deleteFood(foodId);

                    Toast.makeText(getApplicationContext(), "Food Item Deleted!", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(FoodItemDetailActivity.this , DisplayFoodActivity.class));


                }
            });
            alert.show();



        }

        return super.onOptionsItemSelected(item);
    }
}
