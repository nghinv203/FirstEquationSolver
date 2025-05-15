package com.example.firstequationsolver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class Calculator extends AppCompatActivity {

    EditText numberA;
    EditText numberB;
    Button addBtn;
    Button subtractBtn;
    Button multipleBtn;
    Button divideBtn;
    TextView result;

    double a;
    double b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        init();
        divideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleClick();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void HandleClick() {
        APIService apiService = NetworkProvider.self().apiService();
        Call<List<Customer>> call = apiService.getAll();
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()) {
                    List<Customer> customers = response.body();
                    result.setText(customers.toString()); // Or handle/display better
                } else {
                    result.setText("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                t.printStackTrace();
                result.setText("Failure: " + t.getMessage());
            }
        });
    }

    private void init() {
        numberA = findViewById(R.id.editTextNumber);
        numberB = findViewById(R.id.editTextNumber2);
        addBtn = findViewById(R.id.button);
        subtractBtn = findViewById(R.id.button2);
        multipleBtn = findViewById(R.id.button3);
        divideBtn = findViewById(R.id.button4);
        result = findViewById(R.id.textView2);
    }

    private void getValue() {
        try {
            a = Double.parseDouble(numberA.getText().toString());
            b = Double.parseDouble(numberB.getText().toString());
        } catch (Exception e) {
        }
    }

    private void handleClick() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValue();
                add();
            }
        });

        subtractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValue();
                subtract();
            }
        });

        multipleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValue();
                multiple();
            }
        });

        divideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValue();
                divide();
            }
        });
    }

    private void add() {
        result.setText(String.format("%f + %f = %f", a, b, a+b));
    }

    private void subtract() {
        result.setText(String.format("%f - %f = %f", a, b, a-b));
    }

    private void multiple() {
        result.setText(String.format("%f * %f = %f", a, b, a*b));
    }

    private void divide() {
        result.setText(String.format("%f / %f = %f", a, b, a/b));
    }
}