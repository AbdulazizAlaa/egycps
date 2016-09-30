package com.egycps.abdulaziz.egycps.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by abdulaziz on 5/25/16.
 */
public class Utils {


    public static boolean hasActiveInternetConnection(Context context) {
        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection url = (HttpURLConnection)
                        (new URL("http://clients3.google.com/generate_204")
                                .openConnection());
                url.setRequestProperty("User-Agent", "Test");
                url.setRequestProperty("Connection", "close");
                url.setConnectTimeout(1500);
                url.connect();
                return (url.getResponseCode() == 204 && url.getContentLength() == 0);
            } catch (IOException e) {
                Log.e(GlobalEntities.UTILS_CLASS_TAG, "Error checking internet connection", e);
            }
        } else {
            Log.d(GlobalEntities.UTILS_CLASS_TAG, "No network available!");
        }
        return false;
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static String saveToInternalStorage(Context context, Bitmap image, String Filename){
        ContextWrapper cw = new ContextWrapper(context);

        File directory = cw.getDir(GlobalEntities.APP_DIR_TAG, Context.MODE_PRIVATE);


        Log.i(GlobalEntities.UTILS_CLASS_TAG, "saveToInternalStorage: "+directory + Filename);

//        final File dir = new File(context.getFilesDir() + Filename);
//        dir.mkdirs(); //create folders where write files
//        final File file = new File(dir, "BlockForTest.txt");
        File imagePath = new File(directory, Filename);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imagePath);
//            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            image.compress(Bitmap.CompressFormat.JPEG, 30, fos);

            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return directory.getAbsolutePath();
    }

    public static Boolean isImageExistInStorage(Context mContext, String filename){
        ContextWrapper cw = new ContextWrapper(mContext);
        File dir = cw.getDir(GlobalEntities.APP_DIR_TAG, Context.MODE_PRIVATE);

        File file = new File(dir, filename);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            return inputStream!=null;
        }
    }

    public static Bitmap loadImageFromStorage(Context mContext, String filename){
        ContextWrapper cw = new ContextWrapper(mContext);
        File dir = cw.getDir(GlobalEntities.APP_DIR_TAG, Context.MODE_PRIVATE);

        Bitmap image = null;
        File file = new File(dir, filename);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            image = BitmapFactory.decodeStream(inputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            return image;
        }
    }

    public static int randInt(int min, int max) {
        Random rand = new Random(System.currentTimeMillis());
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public static String getPath(Context context, Uri uri) {
        // just some safety built in
        if( uri == null ) {
            Log.i(GlobalEntities.UTILS_CLASS_TAG, "Uri is null");
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }

        cursor.close();
        return uri.getPath();
    }
}
