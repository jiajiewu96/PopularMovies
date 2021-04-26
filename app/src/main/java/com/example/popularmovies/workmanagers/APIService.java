package com.example.popularmovies.workmanagers;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.popularmovies.BaseApp;
import com.example.popularmovies.R;
import com.example.popularmovies.database.MovieRepository;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieResponse;
import com.example.popularmovies.ui.MainActivity;
import com.example.popularmovies.utils.Consts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIService extends Service {

    private static final String TAG = APIService.class.getSimpleName();
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private static final int ONGOING_NOTIFICATION_ID = 1;

    private ServiceHandler mServiceHandler;
    private Looper serviceLooper;


    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {

            String mSortString = msg.getData().getString(Consts.MOVIE_EXTRA_KEY);

            Log.d(TAG, mSortString);
            Application application = getApplication();
            final MovieRepository movieRepository = ((BaseApp) application).getRepository();
            Call<MovieResponse> responseCall = movieRepository.getMoviesFromAPI(mSortString);

            responseCall.enqueue(new retrofit2.Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (!response.isSuccessful()) {
                        Log.d(TAG, "Failure: Response Unsuccessful" + response.message());
                        stopSelf();
                        return;
                    }

                    ArrayList<Movie> movies;
                    if (response.body() != null) {
                        //movieRepository.deleteAll();
                        movies = (ArrayList<Movie>) response.body().getMovies();
                        //movieRepository.addFavoriteToFavoriteDatabase(movie);
                        Log.d(TAG, "SUCCESS");
                    } else {
                        Log.d(TAG, "Failure: Empty Body");
                    }
                    stopSelf();
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d(TAG, "Failure: Call failed");
                    stopSelf();
                }
            });
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread handlerThread = new HandlerThread(Consts.MOVIE_SERVICE_NAME,
                Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();

        serviceLooper = handlerThread.getLooper();
        mServiceHandler = new ServiceHandler(serviceLooper);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        createNotificationChannel();
        @SuppressLint({"NewApi", "LocalSuppress"}) //TESTING PURPOSES ONLY
                Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(getText(R.string.loading_movies))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(ONGOING_NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Message message = new Message();
        Bundle bundle = intent.getBundleExtra(Consts.MOVIE_SERVICE_TAG);
        message.setData(bundle);
        mServiceHandler.sendMessage(message);

        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
