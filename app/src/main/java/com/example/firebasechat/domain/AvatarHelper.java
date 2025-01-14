package com.example.firebasechat.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AvatarHelper {

    private final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    public static String getAvatarUrl(String username) {

        return GRAVATAR_URL + md5(username) + "?s=72";

    }

    private static final String md5(final String string) {

        final String MD5 = "MD5";

        try {

            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(string.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();

            for (byte aMessageDigest: messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {

            noSuchAlgorithmException.printStackTrace();
        }

        return "";
    }

}