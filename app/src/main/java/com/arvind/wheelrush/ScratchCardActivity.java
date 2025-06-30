package com.arvind.wheelrush;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.Random;

public class ScratchCardActivity extends AppCompatActivity {

    private Button scratchNowBtn;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private boolean isScratched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_card);

        scratchNowBtn = findViewById(R.id.scratchNowBtn);

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid();

        if (uid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        scratchNowBtn.setOnClickListener(v -> {
            if (isScratched) {
                Toast.makeText(this, "You already scratched today!", Toast.LENGTH_SHORT).show();
                return;
            }

            int reward = new Random().nextInt(21) + 5; // 5–25 coins

            userRef.child("coins").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Integer coins = snapshot.getValue(Integer.class);
                    if (coins == null) coins = 0;

                    userRef.child("coins").setValue(coins + reward);
                    Toast.makeText(ScratchCardActivity.this, "🎉 You won " + reward + " coins!", Toast.LENGTH_SHORT).show();
                    isScratched = true;
                    scratchNowBtn.setEnabled(false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ScratchCardActivity.this, "Failed to update coins", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
