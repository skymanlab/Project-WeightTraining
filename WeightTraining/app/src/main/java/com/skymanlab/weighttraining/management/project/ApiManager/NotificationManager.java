package com.skymanlab.weighttraining.management.project.ApiManager;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.skymanlab.weighttraining.MainActivity;
import com.skymanlab.weighttraining.R;
import com.squareup.picasso.Picasso;

public class NotificationManager {

    // constant
    public static final int NOTIFICATION_FITNESS_CENTER = 40000;

    // constant
    private static final String CHANNEL_ID_FITNESS_CENTER = "FitnessCenterNotification";

    // instance
    private Activity activity;

    // constructor
    public NotificationManager(Activity activity) {
        this.activity = activity;
    }

    public void init() {

        // 앱의 알림 채널을 시스템에 등록록
        createNotificationChannel();

    }

    public void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Fitness Center channel =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            // 이름, 설명, 중요도
            CharSequence name = activity.getString(R.string.etc_notification_manager_channel_id_fitness_center_notification_name);
            String description = activity.getString(R.string.etc_notification_manager_channel_id_fitness_center_notification_description);
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;

            // 채널 생성
            NotificationChannel channelFitnessCenterGeofencingNotification = new NotificationChannel(CHANNEL_ID_FITNESS_CENTER, name, importance);
            channelFitnessCenterGeofencingNotification.setDescription(description);

            // 채널 등록
            android.app.NotificationManager notificationManager = activity.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channelFitnessCenterGeofencingNotification);


        }
    }

    public static void sendFitnessCenterNotification(Context context, int notificationId, String title, String message) {

        Intent home = new Intent(context, MainActivity.class);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent homePendingIntent = PendingIntent.getActivity(context, 0, home, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_FITNESS_CENTER)
                .setSmallIcon(R.mipmap.icon)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(homePendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());

    }

    public void sendFitnessCenterNotification(int notificationId, String title, String message) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity.getApplicationContext(), CHANNEL_ID_FITNESS_CENTER)
                .setSmallIcon(R.mipmap.icon)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(activity.getApplicationContext());
        notificationManager.notify(notificationId, builder.build());

    }
}
