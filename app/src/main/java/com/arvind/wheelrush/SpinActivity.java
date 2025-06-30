package com.arvind.wheelrush;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class SpinActivity extends AppCompatActivity {

    private Button spinButton;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private boolean isSpinning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);

        // Initialize UI
        spinButton = findViewById(R.id.spinButton);

        // Firebase Auth and DB
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        userRef = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid());

        // Spin button click
        spinButton.setOnClickListener(v -> {
            if (isSpinning) return; // prevent double tap
            isSpinning = true;

            int earned = new Random().nextInt(51); // Earn between 0-50 coins

            userRef.child("coins").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Integer currentCoins = snapshot.getValue(Integer.class);
                    if (currentCoins == null) currentCoins = 0;

                    int newTotal = currentCoins + earned;

                    userRef.child("coins").setValue(newTotal)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(SpinActivity.this, "🎉 You won " + earned + " coins!", Toast.LENGTH_SHORT).show();
                                isSpinning = false;
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(SpinActivity.this, "Failed to update coins", Toast.LENGTH_SHORT).show();
                                isSpinning = false;
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(SpinActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    isSpinning = false;
                }
            });
        });
    }
}