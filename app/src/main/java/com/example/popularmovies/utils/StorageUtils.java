package com.example.popularmovies.utils;

import android.os.Environment;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.popularmovies.BaseApp;
import com.example.popularmovies.model.Movie;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class StorageUtils {

    private static final String TAG = StorageUtils.class.getName();

    public static Movie getMovieFromStorage(){
        Movie currentMovie = null;
        File fileToRead;
        if(isExternalStorageWritable() && isExternalStorageReadable()) {
            fileToRead = new File(BaseApp.getAppContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),  "godzilla_vs_kong.txt")  ;
            if (fileToRead == null || !fileToRead.mkdirs()) {
                Log.e(TAG, "Directory not created" + fileToRead.mkdir() + fileToRead.toString());
                return currentMovie;
            }
            Log.d(TAG, fileToRead.toString());
        }
        else {
            Log.d(TAG, "External Storage not readable or writeable");
            return currentMovie;
        }

        if(!fileToRead.exists() || fileToRead.isHidden()){
            Log.d(TAG, String.valueOf(fileToRead.exists()) + fileToRead.canRead() + fileToRead.isHidden());
            return new Movie();
        }

        Log.d(TAG, fileToRead.toString());
        try{

            ArrayList<String[]> values = StringUtils.openFile(fileToRead);
            Log.d(TAG, values.toString());
//            currentMovie = new Movie(Integer.parseInt(values.get(0)[1]), values.get(0)[1],values.get(0)[1],values.get(0)[1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Movie();
        }
        return currentMovie;
    }

    private static boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    // Checks if a volume containing external storage is available to at least read.
    private static boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }
}
