package com.arvind.wheelrush;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arvind.wheelrush.adapters.TransactionAdapter;
import com.arvind.wheelrush.models.TransactionModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {

    private RecyclerView transactionRecyclerView;
    private List<TransactionModel> transactions = new ArrayList<>();
    private TransactionAdapter adapter;
    private DatabaseReference transactionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        // RecyclerView setup
        transactionRecyclerView = findViewById(R.id.transactionRecyclerView);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter(transactions);
        transactionRecyclerView.setAdapter(adapter);

        // Get current user ID
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) {
            finish();
            return;
        }

        // Reference to user's transaction data
        transactionRef = FirebaseDatabase.getInstance().getReference("Transactions").child(uid);

        // Load transactions
        transactionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactions.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    TransactionModel model = snap.getValue(TransactionModel.class);
                    if (model != null) {
                        transactions.add(model);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // You may show an error Toast here
            }
        });
    }
}
