package ro.pub.cs.systems.eim.Colocviu1_2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
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

public class Colocviu1_2MainActivity extends AppCompatActivity {

    Button add_button, compute_button;
    TextView all_terms;
    EditText input;

    private ActivityResultLauncher activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practical_test01_2_main);

        add_button = findViewById(R.id.add_button);
        compute_button = findViewById(R.id.compute_button);
        all_terms = findViewById(R.id.all_terms);
        input = findViewById(R.id.input);

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
                        Toast.makeText(this, "OK: The sum is: " + data, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Cancel: No result.", Toast.LENGTH_SHORT).show();
                    }
                });

        compute_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, Colocviul1_2SecondaryActivity.class);
            intent.putExtra("all_terms", all_terms.getText().toString());
            activityResultLauncher.launch(intent);
        });
    }
}