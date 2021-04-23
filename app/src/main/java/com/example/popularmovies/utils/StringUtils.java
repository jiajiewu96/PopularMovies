package com.example.popularmovies.utils;

import android.app.Activity;
import android.util.Log;

import com.example.popularmovies.BaseApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StringUtils {
    public static String[] getValue(String lineToSplit){
        if(lineToSplit == null){
            return null;
        }
        String[] kv = lineToSplit.split(":");
        for(int i = 0; i< kv.length; i++){
            kv[i] = kv[i].trim();
        }
        return kv;
    }

    public static ArrayList<String[]> openFile(File file) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String readLine = null;
        ArrayList<String[]> values = new ArrayList<>();
        try {
            // While the BufferedReader readLine is not null
            while ((readLine = br.readLine()) != null) {
                values.add(getValue(readLine));
            }

            // Close the InputStream and BufferedReader
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }
}
