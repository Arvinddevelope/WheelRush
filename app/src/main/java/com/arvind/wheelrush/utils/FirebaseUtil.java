package com.arvind.wheelrush.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

/**
 * Utility class for common Firebase database operations.
 */
public class FirebaseUtil {

    /**
     * Returns reference to the current user's node.
     */
    public static DatabaseReference getUserRef(String uid) {
        return FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(uid);
    }

    /**
     * Returns reference to a user's transaction node.
     */
    public static DatabaseReference getTransactionRef(String uid) {
        return FirebaseDatabase.getInstance()
                .getReference("Transactions")
                .child(uid);
    }

    /**
     * Adds coins to the user's current coin balance using a transaction.
     *
     * @param uid         User ID
     * @param coinsToAdd  Number of coins to add
     */
    public static void addCoins(String uid, int coinsToAdd) {
        getUserRef(uid).child("coins").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                Integer currentCoins = currentData.getValue(Integer.class);
                if (currentCoins == null) {
                    currentData.setValue(coinsToAdd); // if coins node doesn't exist
                } else {
                    currentData.setValue(currentCoins + coinsToAdd);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                // Optional: Log or trigger callback
                if (error != null) {
                    System.err.println("Coin update failed: " + error.getMessage());
                }
            }
        });
    }
}
