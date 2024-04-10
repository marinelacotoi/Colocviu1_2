package ro.pub.cs.systems.eim.Colocviu1_2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class Colocviu1_2Service extends Service {
    private ProcessingThread processingThread = null;

    @Override
    public void onCreate() {
        super.onCreate();

        String CHANNEL_ID = "my_channel_01";
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT);

        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("")
                .setContentText("").build();

        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Started service!");
        String input = intent.getStringExtra("input");
        processingThread = new ProcessingThread(Colocviu1_2Service.this, input);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}