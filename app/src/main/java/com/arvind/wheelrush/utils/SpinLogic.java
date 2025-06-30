package com.arvind.wheelrush.utils;

import java.util.Random;

/**
 * Utility class to handle spin reward logic.
 */
public class SpinLogic {

    // Predefined rewards on the wheel
    private static final int[] rewards = {0, 5, 10, 15, 20, 25, 50, 100};

    /**
     * Returns a random reward from the rewards array.
     *
     * @return Coins earned from spin
     */
    public static int getRandomReward() {
        Random random = new Random();
        return rewards[random.nextInt(rewards.length)];
    }

    /**
     * Optional: Returns a weighted random reward (for better control over probabilities).
     *
     * Uncomment and use if you want weighted rewards.
     */
    /*
    private static final int[] weightedRewards = {
        0, 0, 5, 5, 10, 10, 15, 20, 25, 50, 100 // More 0s and 5s means higher chance
    };

    public static int getWeightedReward() {
        Random random = new Random();
        return weightedRewards[random.nextInt(weightedRewards.length)];
    }
    */
}
