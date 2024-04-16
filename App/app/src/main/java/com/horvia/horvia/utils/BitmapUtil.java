package com.horvia.horvia.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class BitmapUtil {

    public static Bitmap StringToBitmap(String imageString) {
        try {
            byte[] imagesBytes = Base64.getDecoder().decode(imageString);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imagesBytes, 0, imagesBytes.length);
            return decodedImage;

        } catch (Exception e) {
            return null;

        }
    }

    public static String BitmapToString(Bitmap bitmap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            String temp = Base64.getEncoder().encodeToString(bytes);
            return temp;
        } catch (Exception e) {
            return null;
        }
    }

}
