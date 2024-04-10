package ro.pub.cs.systems.eim.Colocviu1_2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;
import android.content.Intent;

public class Colocviul1_2SecondaryActivity extends AppCompatActivity {
    TextView all_terms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_practical_test01_2_main);

        all_terms = findViewById(R.id.all_terms);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("all_terms")) {
            String all_terms_string = intent.getExtras().getString("all_terms");
            int sum = 0;

            String[] terms = all_terms_string.split("\\+");

            for (String term : terms) {
                System.out.println(term);
                sum += Integer.parseInt(term);
            }

            System.out.println("SUM ::" + sum);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("all_terms", String.valueOf(sum));
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }
}