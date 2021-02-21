package com.skymanlab.weighttraining.management.project.ApiManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

import java.util.List;

public class FitnessCenterGeofencingBroadcastReceiver extends BroadcastReceiver {

    // constant
    private static final String CLASS_NAME = FitnessCenterGeofencingBroadcastReceiver.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    @Override
    public void onReceive(Context context, Intent intent) {
        final String METHOD_NAME = "[onReceive] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Context > 객체는 ? = " + context);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Intent > 객체는 ?  = " + intent);

        // GeofencingEvent 가져오기
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        // GeofencingEvent 에러 감지
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.getErrorCode());
            return;
        }

        // geofence 가 트리거된 transition
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
                    context,
                    NotificationManager.NOTIFICATION_FITNESS_CENTER,
                    context.getString(R.string.etc_fitness_center_geofencing_notification_title),
                    getTransitionTypeMessage(context, geofenceTransitionType));


        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< transition type > dwell 과 exit 에 속하지 않습니다.");
        }

    }

    public String getMessage(int type, Location location) {

        switch (type) {
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                return "< DWELL > " + "<위도 : " + location.getLatitude() + ", 경도 : " + location.getLongitude() + ">";
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "< ENTER > " + "<위도 : " + location.getLatitude() + ", 경도 : " + location.getLongitude() + ">";
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return "< EXIT > " + "<위도 : " + location.getLatitude() + ", 경도 : " + location.getLongitude() + ">";
            default:
                return "< 뭐냐? > " + "<위도 : " + location.getLatitude() + ", 경도 : " + location.getLongitude() + ">";
        }
    }

    private String getTransitionTypeMessage(Context context, int type) {

        switch (type) {
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                return context.getString(R.string.etc_fitness_center_geofencing_transition_type_dwell);
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return context.getString(R.string.etc_fitness_center_geofencing_transition_type_enter);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return context.getString(R.string.etc_fitness_center_geofencing_transition_type_exit);
            default:
                return context.getString(R.string.etc_fitness_center_geofencing_transition_type_default);
        }
    }
}
