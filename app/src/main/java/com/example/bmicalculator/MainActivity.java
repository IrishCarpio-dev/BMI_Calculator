package com.example.bmicalculator;

import static com.example.bmicalculator.R.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // Class Variables; also are called 'Fields'
    private TextView resultText;
    private Button calculateButton;
    private RadioButton rButtonMale;
    private RadioButton rButtonFemale;
    private EditText ageEditText;
    private EditText feetEditText;
    private EditText inchesEditText;
    private EditText weightEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        findViews();
        setupButtonClickListener();

        String popUpText = "Loading successful!";
        Toast.makeText(this, popUpText, Toast.LENGTH_LONG).show();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void findViews() {
        resultText = findViewById(id.text_view_result);
        rButtonMale = findViewById(id.radio_button_male);
        rButtonFemale = findViewById(id.radio_button_female);
        ageEditText = findViewById(id.edit_text_age);
        feetEditText = findViewById(id.edit_text_feet);
        inchesEditText = findViewById(id.edit_text_inches);
        weightEditText = findViewById(id.edit_text_weight);
        calculateButton = findViewById(id.button_calculate);

    }

    private void setupButtonClickListener() {
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double bmiResult = calculateBmi();

                String ageText = ageEditText.getText().toString();
                int age = Integer.parseInt(ageText);

                if(age >= 18){
                    displayResult(bmiResult);
                } else {
                    displayGuidance(bmiResult);
                }
            }
        });
    }


    private double calculateBmi() {
        String feetText = feetEditText.getText().toString();
        String inchesText = inchesEditText.getText().toString();
        String weightText = weightEditText.getText().toString();

        // Converting the number 'String' into 'int' variables
        int feet = Integer.parseInt(feetText);
        int inches = Integer.parseInt(inchesText);
        int weight = Integer.parseInt(weightText);

        int totalInches =  (feet * 12) + inches;

        // height in meters is the inches multiplied by 0.0254
        double heightInMeters = totalInches * 0.0254;

        // BMI formula = weight in kg divided by height in meters squared
        return weight / (heightInMeters * heightInMeters);

    }
    private void displayResult(double bmi){
        DecimalFormat myDecimalFormatter = new DecimalFormat("0.00");
        String bmiTextResult = myDecimalFormatter.format(bmi);

        String fullResultString;
        if (bmi < 18.5) {
            // Display underweight
            fullResultString = bmiTextResult + " - You are underweight";
        } else if (bmi > 25) {
            // Display Overweight
            fullResultString = bmiTextResult + " - You are overweight";

        } else {
            // Display healthy
            fullResultString = bmiTextResult + " - You are healthy weight";
        }

        resultText.setText(fullResultString);
    }

    private void displayGuidance(double bmi) {
        DecimalFormat myDecimalFormatter = new DecimalFormat("0.00");
        String bmiTextResult = myDecimalFormatter.format(bmi);

        String fullResultString;
        if (rButtonMale.isChecked()){
            // Display boy guidance
            fullResultString = bmiTextResult + " - You're under 18, place consult with your doctor for the healthy range for boys";
        } else if (rButtonFemale.isChecked()) {
            // Display girl guidance
            fullResultString = bmiTextResult + " - You're under 18, place consult with your doctor for the healthy range for girls";
        }else {
            // General guidance
            fullResultString = bmiTextResult + " - You're under 18, place consult with your doctor for the healthy range";
        }
        resultText.setText(fullResultString);
    }

}