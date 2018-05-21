package com.example.user.dreamtreeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Budget extends AppCompatActivity {

    TextView textResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        textResult = (TextView) findViewById(R.id.Foodtext);
    }

    public void onInputBudgetButtonClicked(View v)
    {
        Intent intent = new Intent(getApplicationContext(),InputBudget.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1)
        {
            if(resultCode == RESULT_OK)
            {
                String result = data.getStringExtra("result");
                textResult.setText(result);
            }
        }
    }
}
