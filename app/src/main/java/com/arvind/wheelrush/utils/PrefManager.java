package com.arvind.wheelrush.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class for managing user preferences like daily spin count and last spin date.
 */
public class PrefManager {

    private static final String PREF_NAME = "spin_earn_pref";
    private static final String KEY_SPINS_TODAY = "spins_today";
    private static final String KEY_LAST_SPIN_DATE = "last_spin_date";

    private final SharedPreferences prefs;

    /**
     * Constructor to initialize the preferences.
     *
     * @param context Application or Activity context
     */
    public PrefManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Store how many spins user did today
    public void setSpinCount(int count) {
        prefs.edit().putInt(KEY_SPINS_TODAY, count).apply();
    }

    // Get how many spins user did today
    public int getSpinCount() {
        return prefs.getInt(KEY_SPINS_TODAY, 0);
    }

    // Save the last spin date (yyyy-MM-dd)
    public void setLastSpinDate(String date) {
        prefs.edit().putString(KEY_LAST_SPIN_DATE, date).apply();
    }

    // Get the last spin date
    public String getLastSpinDate() {
        return prefs.getString(KEY_LAST_SPIN_DATE, "");
    }

    // Reset spin count (e.g., at midnight)
    public void resetSpins() {
        setSpinCount(0);
    }

    // Clear all preferences (if needed on logout)
    public void clearAll() {
        prefs.edit().clear().apply();
    }
}
