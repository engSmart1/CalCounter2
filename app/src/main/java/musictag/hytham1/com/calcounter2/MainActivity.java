package musictag.hytham1.com.calcounter2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class MainActivity extends AppCompatActivity {
    private EditText foodName , foodCals;
    private Button saveButton;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foodName = (EditText) findViewById(R.id.foodEditText);
        foodCals = (EditText) findViewById(R.id.caloriesEditText);

        saveButton = (Button) findViewById(R.id.submitButton);

        dba = new DatabaseHandler(MainActivity.this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToDB();
            }
        });
    }
    private void saveDataToDB(){
        Food food = new Food();
        String name = foodName.getText().toString().trim();
        String calsString = foodCals.getText().toString().trim();

        int cals = Integer.parseInt(calsString);


        if (name.equals("") || calsString.equals("")){
            Toast.makeText(getApplicationContext(), "No empty required ...", Toast.LENGTH_SHORT).show();

        } else {

            food.setFoodName(name);
            food.setCalories(cals);

            dba.addFood(food);
            dba.close();
            // clear the set

            foodName.setText("");
            foodCals.setText("");

            startActivity( new Intent(MainActivity.this , DisplayFoodActivity.class));
        }
    }
}
