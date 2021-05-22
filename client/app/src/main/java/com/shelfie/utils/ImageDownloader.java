package com.shelfie.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.shelfie.R;

import java.io.InputStream;

public class ImageDownloader extends AsyncTask<String, Integer, Bitmap> {

    private static final String baseUrl = "http://10.0.0.103:8080/";
    private ImageView image;

    public ImageDownloader(ImageView image) {
        this.image = image;
    }

    public static boolean isAvatarImage(String avatarParam) {
        return avatarParam != null && avatarParam.equals("Avatar");
    }

    public static Bitmap cropAvatarImage(Bitmap bitmap) {
        return Bitmap.createBitmap(bitmap, 0, 0, 1000, 1000);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... URL) {
        String imageURL = baseUrl.concat(URL[0]);
        boolean isAvatarImage = isAvatarImage(URL[1]);
        Bitmap bitmap = null;
        try {
            InputStream input = new java.net.URL(imageURL).openStream();
            bitmap = BitmapFactory.decodeStream(input);
            if(isAvatarImage) {
                bitmap = cropAvatarImage(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (image != null) {
            image.setImageBitmap(result);
        }
    }
}
