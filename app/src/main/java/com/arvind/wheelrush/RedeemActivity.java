package com.arvind.wheelrush;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;

public class RedeemActivity extends AppCompatActivity {

    private EditText upiEditText, amountEditText;
    private TextView currentCoinsText;
    private Button redeemBtn;
    private DatabaseReference userRef;
    private int currentCoins = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        // Initialize views
        upiEditText = findViewById(R.id.upiIdEditText);
        amountEditText = findViewById(R.id.amountEditText);
        currentCoinsText = findViewById(R.id.currentCoinsText);
        redeemBtn = findViewById(R.id.redeemBtn);

        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Firebase reference
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        // Listen for live coins
        userRef.child("coins").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer coins = snapshot.getValue(Integer.class);
                currentCoins = (coins != null) ? coins : 0;
                currentCoinsText.setText("Current Coins: " + currentCoins);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RedeemActivity.this, "Failed to load coins", Toast.LENGTH_SHORT).show();
            }
        });

        // Redeem button click
        redeemBtn.setOnClickListener(v -> {
            String upi = upiEditText.getText().toString().trim();
            String amountStr = amountEditText.getText().toString().trim();

            if (upi.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(this, "Please enter UPI and amount", Toast.LENGTH_SHORT).show();
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            if (amount <= 0) {
                Toast.makeText(this, "Amount must be greater than zero", Toast.LENGTH_SHORT).show();
                return;
            }

            int coinCost = amount * 10; // ₹1 = 10 coins
            if (currentCoins >= coinCost) {
                // Deduct coins
                userRef.child("coins").setValue(currentCoins - coinCost);

                // Create redeem request
                String key = FirebaseDatabase.getInstance().getReference("RedeemRequests").push().getKey();
                if (key != null) {
                    Map<String, Object> redeemData = new HashMap<>();
                    redeemData.put("upi", upi);
                    redeemData.put("amount", amount);
                    redeemData.put("status", "Pending");
                    redeemData.put("uid", uid);
                    redeemData.put("timestamp", ServerValue.TIMESTAMP);

                    FirebaseDatabase.getInstance().getReference("RedeemRequests")
                            .child(key)
                            .setValue(redeemData)
                            .addOnSuccessListener(unused -> Toast.makeText(this, "Redeem request submitted!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(this, "Failed to submit request", Toast.LENGTH_SHORT).show());
                }
            } else {
                Toast.makeText(this, "Not enough coins", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
