package com.jk.chiti.utils;

public class AmountConverter {

    // Convert a numeric value to a string representation (e.g., 100000 -> "1L")
    public static String convertToString(long amount) {
        if (amount >= 100000) {
            return (amount / 100000) + "L"; // Convert to Lakh if >= 100000
        }
        return String.valueOf(amount); // Return as is if it's not a large number
    }

    // Convert a string representation (e.g., "1L") to a numeric value (e.g., 100000)
    public static long convertToNumeric(String amount) {
        amount = amount.trim().toUpperCase(); // Normalize input

        // Check for "L" for Lakh
        if (amount.endsWith("L")) {
            try {
                // Remove "L" and parse the numeric part
                long value = Long.parseLong(amount.replace("L", "").trim());
                return value * 100000; // 1 L = 100000
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid amount format");
            }
        }

        // If it's not in Lakh, return the numeric value directly
        try {
            return Long.parseLong(amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format");
        }
    }
}
