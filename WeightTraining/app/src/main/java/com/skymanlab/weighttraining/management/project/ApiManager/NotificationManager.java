package com.skymanlab.weighttraining.management.project.ApiManager;

import android.app.Activity;
import android.app.NotificationChannel;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.skymanlab.weighttraining.R;

public class NotificationManager {

    // constant
    private static final String CHANNEL_ID_ = "FitnessCenterNotification";

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
            CharSequence name = activity.getString(R.string.etc_notification_manager_fitness_center_channel_name);
            String description = activity.getString(R.string.etc_notification_manager_fitness_center_channel_description);
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;

            // 채널 생성
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_, name, importance);
            channel.setDescription(description);

            // 채널 등록
            android.app.NotificationManager notificationManager = activity.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }

    public static void showNotification(Context context, int notificationId, String title, String text) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());

    }

    public void showNotification(int notificationId, String title, String text) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity.getApplicationContext(), CHANNEL_ID_)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(activity.getApplicationContext());
        notificationManager.notify(notificationId, builder.build());

    }
}
