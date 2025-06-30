package com.arvind.wheelrush;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    private TextView welcomeText, coinsText;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize views
        welcomeText = findViewById(R.id.welcomeText);
        coinsText = findViewById(R.id.coinsText);

        // Firebase Auth and DB
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
            return;
        }

        uid = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);
                    Integer coins = snapshot.child("coins").getValue(Integer.class);

                    welcomeText.setText("Welcome, " + (name != null ? name : "User") + "!");
                    coinsText.setText("Coins: " + (coins != null ? coins : 0));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "Failed to load user data", Toast.LENGTH_SHORT).show();
            }
        });

        // Click listeners for dashboard cards
        findViewById(R.id.cardSpin).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, SpinActivity.class)));

        findViewById(R.id.cardEarn).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, EarnActivity.class)));

        findViewById(R.id.cardWallet).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, WalletActivity.class)));

        findViewById(R.id.cardRedeem).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, RedeemActivity.class))); // ✅ Added this
    }
}
