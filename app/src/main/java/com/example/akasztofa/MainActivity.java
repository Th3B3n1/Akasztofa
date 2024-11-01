package com.example.akasztofa;

import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final List<String> words = new ArrayList<>() {
        {
            add("tarca");
            add("semmi");
            add("asdasdasd");
        }
    };
    private TextView charToTip;
    private ImageView hangImage;
    private LinearLayout wordsLayout;
    private String randomWord;
    private int badTips = 0;
    private HashMap<Character, Boolean> tips = new HashMap<>();
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
        MaterialButton charBeforeButton = findViewById(R.id.charBeforeButton);
        MaterialButton charAfterButton = findViewById(R.id.charAfterButton);
        MaterialButton tipButton = findViewById(R.id.tipButton);
        this.charToTip = findViewById(R.id.charToTip);
        this.hangImage = findViewById(R.id.hangImage);
        this.wordsLayout = findViewById(R.id.words);
        this.randomWord = this.generateWord();
        charBeforeButton.setOnClickListener(view -> updateToTipCharBackWard(this.charToTip.getText().charAt(0)));
        charAfterButton.setOnClickListener(view -> updateToTipCharForward(this.charToTip.getText().charAt(0)));
        tipButton.setOnClickListener(view -> tip(this.charToTip.getText().charAt(0)));
    }
    private void updateToTipCharForward(int currentChar)
    {
        if (currentChar >= 65 && currentChar <= 90)
        {
            if (currentChar == 90)
            {
                currentChar = 65;
            }
            else
            {
                currentChar += 1;
            }
        }
        else
        {
            currentChar = 65;
            Toast.makeText(this, R.string.howDidYouManagedThat, Toast.LENGTH_LONG).show();
        }
        this.charToTip.setText(String.valueOf((char) currentChar));
    }
    private void updateToTipCharBackWard(int currentChar)
    {
        if (currentChar >= 65 && currentChar <= 90)
        {
            if (currentChar == 65)
            {
                currentChar = 90;
            }
            else
            {
                currentChar -= 1;
            }
        }
        else
        {
            currentChar = 65;
            Toast.makeText(this, R.string.howDidYouManagedThat, Toast.LENGTH_LONG).show();
        }
        this.charToTip.setText(String.valueOf((char) currentChar));
    }
    private void tip(char tippedChar)
    {
        if (!tips.containsKey(tippedChar))
        {
            if (this.randomWord.contains(Character.toString(tippedChar)))
            {
                setCharForEveryTextViewInsideALinearLayout(this.wordsLayout, this.randomWord, tippedChar);
                this.tips.put(tippedChar, true);
                if (validateTextViewsInsideALinearLayout(this.wordsLayout, this.randomWord))
                {
                    createDialog(R.string.youWon, R.string.wouldYouLikeToPlayAgain);
                }
            }
            else
            {
                this.badTips += 1;
                this.hangImage.setImageResource(getResources().getIdentifier("akasztofa" + ((this.badTips < 10) ? "0" : "") + this.badTips, "drawable", getPackageName()));
                this.tips.put(tippedChar, false);
                if (this.badTips == 13)
                {
                    createDialog(R.string.youLost, R.string.wouldYouLikeToPlayAgain);
                }
            }
        }
        else
        {
            Toast.makeText(this, getString(R.string.the) + " " + tippedChar + getString(R.string.alreadyTipped) + "\n" + ((Boolean.TRUE.equals(this.tips.get(tippedChar))) ? getString(R.string.itWasAGoodTip) : getString(R.string.itWasABadTip)), Toast.LENGTH_LONG).show();
        }
    }
    private String generateWord()
    {
        Random random = new Random();
        String randomWord = this.words.get(random.nextInt(this.words.size())).toUpperCase();
        generateTextViewsForLinearLayout(randomWord.length(), this.wordsLayout, R.string.starterChar);
        return randomWord;
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
    private void setCharForEveryTextViewInsideALinearLayout(LinearLayout linearLayout, String string, char charToSet)
    {
        List<Integer> indexes = getEveryIndexWhichMatchesEveryCharInAString(string, charToSet);
        for (int i = 0; i < indexes.size(); i++)
        {
            TextView currentTextView = (TextView) linearLayout.getChildAt(indexes.get(i));
            currentTextView.setText(String.valueOf(charToSet));
        }
    }
    private List<Integer> getEveryIndexWhichMatchesEveryCharInAString(String string, char charToMatch)
    {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < string.length(); i++)
        {
            if (string.charAt(i) == charToMatch)
            {
                indexes.add(i);
            }
        }
        return indexes;
    }
    private boolean validateTextViewsInsideALinearLayout(LinearLayout linearLayout, String word)
    {
        for (int i = 0; i < linearLayout.getChildCount(); i++)
        {
            TextView currentTextView = (TextView) linearLayout.getChildAt(i);
            if (currentTextView.getText().charAt(0) != word.charAt(i))
            {
                return false;
            }
        }
        return true;
    }
    private void createDialog(int title, int message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> reset());
        builder.setNegativeButton(R.string.no, (dialogInterface, i) -> finish());
        builder.create();
        builder.show();
    }
    private void reset()
    {
        this.charToTip.setText(R.string.firstChar);
        this.hangImage.setImageResource(R.drawable.akasztofa00);
        this.wordsLayout.removeAllViews();
        this.randomWord = this.generateWord();
        this.tips = new HashMap<>();
    }
}