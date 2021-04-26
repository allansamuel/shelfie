package com.shelfie.config;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ImageDecoder {

    public static Bitmap decodeBase64(String imageBase64) {
        byte[] decodedImageArray = Base64.decode(imageBase64, Base64.DEFAULT);
        Bitmap bitmapImage = BitmapFactory.decodeByteArray(
                decodedImageArray,
                0,
                decodedImageArray.length);
        return bitmapImage;
    }

}
