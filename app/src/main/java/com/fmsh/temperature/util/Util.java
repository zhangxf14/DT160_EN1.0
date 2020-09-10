/*
 * Copyright (c), NXP Semiconductors
 * (C)NXP B.V. 2014-2018
 * All rights are reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright owner. NXP reserves the right to make
 * changes without notice at any time. NXP makes no warranty, expressed, implied or
 * statutory, including but not limited to any implied warranty of merchantability
 * or fitness for any particular purpose, or that the use will not infringe any
 * third party patent, copyright or trademark. NXP must not be liable for any loss
 * or damage arising from its use.
 */


package com.fmsh.temperature.util;

import android.support.v4.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Util {
    public static final int MINIMUM_VALID_TEMPERATURE_VALUE = -400; // deci-degrees celsius
    public static final int MAXIMUM_VALID_TEMPERATURE_VALUE = 850; // deci-degrees celsius - fixed value: see APP_MSG_MAX_TEMPERATURE in firmware
    public static final float PLACEHOLDER_TEMPERATURE_VALUE = 85.1f; // fixed value: see APP_MSG_TEMPERATURE_PLACEHOLDER_VALUE in firmware

    static public String readTextFromResource(Fragment fragment, int resourceID) {
        InputStream raw = fragment.getResources().openRawResource(resourceID);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int i;
        try {
            i = raw.read();
            while (i != -1) {
                stream.write(i);
                i = raw.read();
            }
            raw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

    /**
     * Converts an array of bytes back to a single number
     *
     * @param bytes Array of bytes, of any length. Maximally, only the first 4 bytes are looked at.
     * @return A number. The top bit of the meaningful part of the number is not propagated.
     */
    public static int bytesToInt(byte[] bytes) {
        int[] parts = new int[]{0, 0, 0, 0};
        for (int i = 0; i < Math.min(bytes.length, 4); i++) {
            parts[i] = (bytes[i] >= 0) ? bytes[i] : bytes[i] + 256;
        }
        return parts[0] + (parts[1] << 8) + (parts[2] << 16) + (parts[3] << 24);
    }

    public static String bytesToHexString(byte[] bytes, char separator) {
        String s = "0";
        StringBuilder hexString = new StringBuilder();
        if ((bytes != null) && (bytes.length > 0)) {
            for (byte b : bytes) {
                int n = b & 0xff;
                if (n < 0x10) {
                    hexString.append("0");
                }
                hexString.append(Integer.toHexString(n));
                if (separator != 0) {
                    hexString.append(separator);
                }
            }
            s = hexString.substring(0, hexString.length() - 1);
        }
        return s;
    }

    public static int celsiusToFahrenheit(int deciCelsius) {
        return (int) Math.round((1.8 * (deciCelsius / 10.0) + 32.0) * 10.0);
    }

    public static float celsiusToFahrenheit(float celsius) {
        return (float) ((Math.round((1.8 * celsius + 32.0) * 10.0)) / 10.0);
    }

    public static double celsiusToFahrenheit(double celsius) {
        return ((Math.round((1.8 * celsius + 32.0) * 10.0)) / 10.0);
    }

    public static int fahrenheitToCelsius(int deciFahrenheit) {
        return (int) Math.round(((deciFahrenheit / 10.0) - 32) / 1.8 * 10.0);
    }

    public static float fahrenheitToCelsius(float fahrenheit) {
        return (float) ((Math.round(((fahrenheit - 32) / 1.8) * 10.0)) / 10.0);
    }

    public static double fahrenheitToCelsius(double fahrenheit) {
        return ((Math.round(((fahrenheit - 32) / 1.8) * 10.0)) / 10.0);
    }
}
