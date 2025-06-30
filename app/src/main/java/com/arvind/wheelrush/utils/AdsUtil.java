package com.arvind.wheelrush.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Utility class to simulate rewarded ads.
 * This can be replaced with actual ad network integration like AdMob or Unity Ads.
 */
public class AdsUtil {

    /**
     * Simulates loading and showing a rewarded ad, and then triggers callback with coin reward.
     *
     * @param context  Application or Activity context
     * @param listener Callback to notify when the ad is "completed"
     */
    public static void loadRewardedAd(Context context, OnAdCompleteListener listener) {
        // Simulated ad delay of 2 seconds
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Simulate reward - 10 coins
            if (listener != null) {
                listener.onAdCompleted(10);
            }
        }, 2000);
    }

    /**
     * Callback interface for ad completion
     */
    public interface OnAdCompleteListener {
        void onAdCompleted(int coinsEarned);
    }
}
