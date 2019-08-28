package com.example.cmproject.Firebaseotp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.cmproject.R;

public class LogIn extends AppCompatActivity {

    EditText editPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editPhoneNumber = findViewById(R.id.editTextMobile);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = editPhoneNumber.getText().toString().trim();
                if(mobile.isEmpty() || mobile.length() < 10){
                    editPhoneNumber.setError("Enter a valid mobile");
                    editPhoneNumber.requestFocus();
                    return;
                }
                Intent intent = new Intent(LogIn.this, VerifyPhone.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
            }
        });
    }
}
