package com.skymanlab.weighttraining.management.project.ApiManager;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

public class FitnessCenterGeofenceService extends IntentService {

    // constant
    private static final String CLASS_NAME = FitnessCenterGeofenceService.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FitnessCenterGeofenceService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final String METHOD_NAME = "[onHandleIntent] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "================================================================================================================");

    }
}
