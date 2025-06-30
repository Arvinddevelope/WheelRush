package com.arvind.wheelrush;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class ReferralActivity extends AppCompatActivity {

    private TextView referralCodeText;
    private Button shareButton;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);

        referralCodeText = findViewById(R.id.referralCodeText);
        shareButton = findViewById(R.id.shareButton);

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid();

        if (uid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        // Load referral code
        userRef.child("referralCode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String code = snapshot.getValue(String.class);
                if (code != null && !code.isEmpty()) {
                    referralCodeText.setText(code);
                } else {
                    referralCodeText.setText("N/A");
                    Toast.makeText(ReferralActivity.this, "Referral code not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReferralActivity.this, "Failed to load referral code", Toast.LENGTH_SHORT).show();
            }
        });

        // Share referral code
        shareButton.setOnClickListener(v -> {
            String code = referralCodeText.getText().toString();
            if (code.equals("N/A") || code.isEmpty()) {
                Toast.makeText(this, "Referral code is not available", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT, "Join & Earn");
            share.putExtra(Intent.EXTRA_TEXT, "Join this app and earn money! Use my code: " + code);
            startActivity(Intent.createChooser(share, "Share using"));
        });
    }
}
