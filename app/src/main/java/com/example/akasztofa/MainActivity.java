package com.example.akasztofa;

import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final List<String> words = new ArrayList<>() {
        {
            add("tárca");
            add("semmi");
        }
    };
    private MaterialButton charBeforeButton;
    private MaterialButton charAfterButton;
    private TextView charToTip;
    private MaterialButton tipButton;
    private ImageView hangImage;
    private LinearLayout wordsLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.charBeforeButton = findViewById(R.id.charBeforeButton);
        this.charAfterButton = findViewById(R.id.charAfterButton);
        this.charToTip = findViewById(R.id.charToTip);
        this.tipButton = findViewById(R.id.tipButton);
        this.hangImage = findViewById(R.id.hangImage);
        this.wordsLayout = findViewById(R.id.words);
        generateTextViewsForLinearLayout(5, this.wordsLayout, R.string.starterCharacter);

    }
    private String updateToTipChar()
    {
        return "";
    }
    private void tip(char tippedChar)
    {

    }
    private void generateTextViewsForLinearLayout(int amount, LinearLayout linearLayout, int text)
    {
        for (int i = 0; i < amount; i++)
        {
            TextView textView = new TextView(this);
            textView.setId(i);
            textView.setText(text);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            textView.setLayoutParams(params);
            linearLayout.addView(textView);
        }
    }
}