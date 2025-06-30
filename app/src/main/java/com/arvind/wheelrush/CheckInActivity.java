package com.arvind.wheelrush;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckInActivity extends AppCompatActivity {

    private Button checkinButton;
    private DatabaseReference userRef;
    private String todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        checkinButton = findViewById(R.id.checkinButton);

        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        checkinButton.setOnClickListener(v -> performDailyCheckIn());
    }

    private void performDailyCheckIn() {
        userRef.child("lastCheckIn").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String lastDate = snapshot.getValue(String.class);
                if (!todayDate.equals(lastDate)) {
                    userRef.child("lastCheckIn").setValue(todayDate);
                    rewardUserForCheckIn();
                } else {
                    Toast.makeText(CheckInActivity.this, "You have already checked in today!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CheckInActivity.this, "Failed to check in.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void rewardUserForCheckIn() {
        userRef.child("coins").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer currentCoins = snapshot.getValue(Integer.class);
                if (currentCoins == null) currentCoins = 0;
                userRef.child("coins").setValue(currentCoins + 15);
                Toast.makeText(CheckInActivity.this, "Check-In Successful! +15 Coins", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CheckInActivity.this, "Error updating coins.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
