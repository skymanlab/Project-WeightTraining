package com.skymanlab.weighttraining.management.project.ApiManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

import java.util.List;

public class FitnessCenterGeofenceBroadcastReceiver extends BroadcastReceiver {

    // constant
    private static final String CLASS_NAME = FitnessCenterGeofenceBroadcastReceiver.class.getSimpleName();
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

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

            if (geofenceTransitionType == Geofence.GEOFENCE_TRANSITION_DWELL) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Request Id > '안에서 머무름' 해당 Geofence 는 ? = " + geofence.getRequestId());

            } else if (geofenceTransitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Request Id > '밖으로 나감' 해당 Geofence 는 ? = " + geofence.getRequestId());

            } else if (geofenceTransitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Request Id > '들어감' 해당 Geofence 는 ? = " + geofence.getRequestId());
            }

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< transition type > dwell 과 exit 에 속하지 않습니다.");
        }


    }
}
