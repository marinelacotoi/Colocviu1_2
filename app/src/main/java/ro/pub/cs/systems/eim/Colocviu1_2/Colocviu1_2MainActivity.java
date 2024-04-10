package ro.pub.cs.systems.eim.Colocviu1_2;

import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
// import set text
import android.widget.TextView;
// import edit text
import android.widget.EditText;
// import intent
import android.content.Intent;
import android.widget.Toast;
// import activity result launcher
import androidx.activity.result.ActivityResultLauncher;
// import broadcast receiver
import android.content.BroadcastReceiver;

public class Colocviu1_2MainActivity extends AppCompatActivity {

    Button add_button, compute_button;
    TextView all_terms;
    EditText input;

    int calculatedSum = 0;

    private ActivityResultLauncher activityResultLauncher;
    private IntentFilter intentFilter = new IntentFilter();

    private final MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private static class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("Received broadcast!");

            String message = intent.getStringExtra("broadcast");
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();
            System.out.println(message);
        }
    }
    private void startServiceIfConditionMet() {
        System.out.println("Condition:" + calculatedSum);
        if (calculatedSum == 10) {
            System.out.println("Condition met!");
            Intent intent = new Intent(getApplicationContext(), Colocviu1_2Service.class);
            intent.putExtra("input", all_terms.getText().toString());

            getApplicationContext().startService(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("sum", String.valueOf(getSum(all_terms.getText().toString())));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey("sum")) {
            String sum = savedInstanceState.getString("sum");
            all_terms.setText(sum);
//            Toast.makeText(this, "OK: The sum is: " + sum, Toast.LENGTH_SHORT).show();
        }
    }

    public int getSum(String input) {

        if (calculatedSum != 0) {
            return calculatedSum;
        }
        int sum = 0;

        String[] terms = input.split("\\+");
        for (String term : terms) {
            System.out.println(term);
            sum += Integer.parseInt(term);
        }

        return sum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practical_test01_2_main);

        add_button = findViewById(R.id.add_button);
        compute_button = findViewById(R.id.compute_button);
        all_terms = findViewById(R.id.all_terms);
        input = findViewById(R.id.input);

        intentFilter.addAction("ro.pub.cs.systems.eim.colocviu1_2.sum");

        add_button.setOnClickListener(v -> {
            String text = all_terms.getText().toString();
            System.out.println(text);

            // get text from input
            String inputText = input.getText().toString();
            int sum = Integer.parseInt(inputText);

            String[] terms = text.split("\\+");
            for (String term : terms) {
                System.out.println(term);
                sum += Integer.parseInt(term);
            }

            if (!text.equals("")) {
                input.setText(String.valueOf(sum));
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    System.out.println("Result code: " + result.getResultCode());
                    if (result.getResultCode() == RESULT_OK) {
                        String data = result.getData().getExtras().getString("all_terms");

                        calculatedSum = Integer.parseInt(data);
                        startServiceIfConditionMet();
//                        Toast.makeText(this, "OK: The sum is: " + data, Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(this, "Cancel: No result.", Toast.LENGTH_SHORT).show();
                    }
                });

        compute_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, Colocviul1_2SecondaryActivity.class);
            intent.putExtra("all_terms", all_terms.getText().toString());
            activityResultLauncher.launch(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter);
        }
    };

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    };

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, Colocviu1_2Service.class);
        stopService(intent);
        super.onDestroy();
    };
}