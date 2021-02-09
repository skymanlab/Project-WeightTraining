package com.skymanlab.weighttraining.management.project.ApiManager;

import android.content.Context;
import android.content.Intent;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

import java.util.List;

public class FitnessCenterGeofencingService extends JobIntentService {

    // constant
    private static final String CLASS_NAME = FitnessCenterGeofencingService.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;
    private static final int JOB_ID = 573;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, FitnessCenterGeofencingService.class, JOB_ID, intent);
    }


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        final String METHOD_NAME = "[onHandleWork] ";

        // GeofencingEvent 가져오기
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        // GeofencingEvent 에러 감지
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.getErrorCode());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< GeofencingEvent > 에러 발생 = " + errorMessage);
            return;
        }
        // GeofencingEvent 로 부터 transition type 가져오기
        int geofenceTransitionType = geofencingEvent.getGeofenceTransition();

        // transition type 검사
        if (geofenceTransitionType == Geofence.GEOFENCE_TRANSITION_ENTER
                || geofenceTransitionType == Geofence.GEOFENCE_TRANSITION_EXIT
                || geofenceTransitionType == Geofence.GEOFENCE_TRANSITION_DWELL) {

            // Geofence 가져오기
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            Geofence geofence = triggeringGeofences.get(0);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< geofence request id > 리퀘스트 아이디는 ? = " + geofence.getRequestId());

            // Geofence 알림
            NotificationManager.sendFitnessCenterNotification(
                    getApplicationContext(),
                    NotificationManager.NOTIFICATION_FITNESS_CENTER,
                    getString(R.string.etc_fitness_center_geofencing_notification_title),
                    getTransitionTypeMessage(geofenceTransitionType));

        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< transition type > dwell 과 exit 에 속하지 않습니다.");
        }
    }


    private String getNotificationMessage() {
        return new StringBuilder()
                .append("").toString();
    }

    private String getTransitionTypeMessage(int type) {

        switch (type) {
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                return getString(R.string.etc_fitness_center_geofencing_transition_type_dwell);
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.etc_fitness_center_geofencing_transition_type_enter);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.etc_fitness_center_geofencing_transition_type_exit);
            default:
                return getString(R.string.etc_fitness_center_geofencing_transition_type_default);
        }
    }
}
