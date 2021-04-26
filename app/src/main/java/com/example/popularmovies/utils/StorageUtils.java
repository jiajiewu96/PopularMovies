package com.example.popularmovies.utils;

import android.os.Environment;
import android.util.Log;


import com.example.popularmovies.BaseApp;
import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class StorageUtils {

    private static final String TAG = StorageUtils.class.getName();

    public static Movie getMovieFromStorage() {
        Movie currentMovie = null;
        File fileToRead, downloadDir;

        downloadDir = BaseApp.getAppContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if(!downloadDir.isDirectory()){
            Log.d(TAG, "not a directory");
            return movieTestFallback();
        }
        if (isExternalStorageWritable() && isExternalStorageReadable()) {
            fileToRead = new File(downloadDir, "godzilla_vs_kong.txt");
            if (fileToRead == null || !fileToRead.mkdirs()) {
                Log.e(TAG, "Directory not created" + fileToRead.mkdir() + fileToRead.toString());
                return movieTestFallback();
            }
            Log.d(TAG, fileToRead.toString());
        } else {
            Log.d(TAG, "External Storage not readable or writeable");
            return currentMovie;
        }

        try {
            ArrayList<String[]> values = StringUtils.openFile(fileToRead);
            Log.d(TAG, values.toString());
            currentMovie = new Movie(Integer.parseInt(values.get(0)[1]), values.get(1)[1], values.get(2)[1], values.get(3)[1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Movie();
        }
        return currentMovie;
    }

    private static Movie movieTestFallback() {
        InputStream inputStream = BaseApp.getAppContext().getResources().openRawResource(R.raw.godzilla_vs_kong);
        Movie currentMovie;

        ArrayList<String[]> values = StringUtils.openFile(inputStream);
        Log.d(TAG, values.toString());
        currentMovie = new Movie(Integer.parseInt(values.get(0)[1]), values.get(1)[1], values.get(2)[1], values.get(3)[1]);
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
