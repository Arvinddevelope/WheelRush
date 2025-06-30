package com.arvind.wheelrush;

import android.content.Intent;
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

public class EarnActivity extends AppCompatActivity {

    private Button watchAdBtn, completeOfferBtn, referFriendBtn;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private boolean isRewarding = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn);

        // Initialize UI
        watchAdBtn = findViewById(R.id.watchAdBtn);
        completeOfferBtn = findViewById(R.id.completeOfferBtn); // ✅ Added
        referFriendBtn = findViewById(R.id.referFriendBtn);     // ✅ Added

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        userRef = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid());

        // Watch Ad Button
        watchAdBtn.setOnClickListener(v -> {
            if (isRewarding) return; // prevent double tap
            isRewarding = true;

            userRef.child("coins").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Integer currentCoins = snapshot.getValue(Integer.class);
                    if (currentCoins == null) currentCoins = 0;

                    int newCoins = currentCoins + 10;

                    userRef.child("coins").setValue(newCoins)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(EarnActivity.this, "🎁 You earned 10 coins!", Toast.LENGTH_SHORT).show();
                                isRewarding = false;
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(EarnActivity.this, "Failed to update coins", Toast.LENGTH_SHORT).show();
                                isRewarding = false;
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(EarnActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    isRewarding = false;
                }
            });
        });

        // ✅ Open TaskOfferActivity
        completeOfferBtn.setOnClickListener(v ->
                startActivity(new Intent(EarnActivity.this, TaskOfferActivity.class)));

        // ✅ Open ReferralActivity
        referFriendBtn.setOnClickListener(v ->
                startActivity(new Intent(EarnActivity.this, ReferralActivity.class)));
    }
}
