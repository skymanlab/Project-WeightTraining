package com.skymanlab.weighttraining.management.project.ApiManager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.common.util.concurrent.ListenableFuture;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FitnessCenterListenableWorker extends ListenableWorker {

    // constant
    private static final String CLASS_NAME = FitnessCenterLocationUpdateWorker.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    public static final int INTERNAL = 10000;         // 선호하는 위치 업데이트 수신 간격
    public static final int FASTEST_INTERNAL = 1000;  // 업데이트를 처리할 수 있는 가장 빠른 간격
    public static final int PRIORITY = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;       // 도시 블록 내의 위치 정밀도를 요청 : 100 [m]
    public static final String WORKER_TAG = "fitnessCenterLocationUpdateWorker";
    // instance varaible
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest.Builder settingsRequester;


    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public FitnessCenterListenableWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        final String METHOD_NAME = "[doWork] ";

        return new ListenableFuture<Result>() {
            @Override
            public void addListener(Runnable listener, Executor executor) {

            }

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Result get() throws ExecutionException, InterruptedException {
                return null;
            }

            @Override
            public Result get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return null;
            }
        };

    }
}
