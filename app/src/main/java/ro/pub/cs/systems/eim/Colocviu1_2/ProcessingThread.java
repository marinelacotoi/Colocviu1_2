package ro.pub.cs.systems.eim.Colocviu1_2;

import android.content.Context;

import java.util.Random;
import java.util.Date;
import android.content.Intent;

public class ProcessingThread extends Thread {
    private Context context = null;
    private boolean isRunning = true;
    private int sum;
    final public static String actionTypes = "ro.pub.cs.systems.eim.colocviu1_2.sum";
    public int getSum(String input) {
        int sum = 0;

        String[] terms = input.split("\\+");
        for (String term : terms) {
            System.out.println(term);
            sum += Integer.parseInt(term);
        }

        return sum;
    }

    public ProcessingThread(Context context, String input) {
        this.context = context;

        sum = getSum(input);
        System.out.println("sum " + sum);
    }

    @Override
    public void run() {
        System.out.println("Run!");
        while (isRunning) {
            sendMessage();
            sleep();
        }
    }

    private void sendMessage() {
        System.out.println("send Message!");
        Intent intent = new Intent();
        intent.setAction(actionTypes);
        intent.putExtra("broadcast", " " + "BROADCAST: " +
                new Date(System.currentTimeMillis()) + " Sum: " + sum);

        intent.setPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
