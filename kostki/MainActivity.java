package com.example.inf04_kostki;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static int diceAmount;
    private Button throwDice;
    private Button resetGame;
    private TextView gameScore;
    private TextView throwScore;
    private ImageView dice1, dice2, dice3, dice4, dice5;
    private int gameScoreInt = 0;
    private int throwScoreInt = 0;
    private static ArrayList<Integer> throwResults = new ArrayList<>();
    private static int[] imageViewIds = {
            R.id.imageView2,
            R.id.imageView3,
            R.id.imageView4,
            R.id.imageView5,
            R.id.imageView6
    };
    private static int[] diceImages = {
        R.drawable.diceone,
            R.drawable.dicetwo,
            R.drawable.dicethree,
            R.drawable.dicefour,
            R.drawable.dicefive,
            R.drawable.dicesix
    };
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

        throwDice = findViewById(R.id.throwDice);
        resetGame = findViewById(R.id.resetScore);
        dice1 = findViewById(R.id.imageView2);
        dice2 = findViewById(R.id.imageView3);
        dice3 = findViewById(R.id.imageView4);
        dice4 = findViewById(R.id.imageView5);
        dice5 = findViewById(R.id.imageView6);
        throwScore = findViewById(R.id.resultView);
        gameScore = findViewById(R.id.summaryView);

        throwDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throwResults = drawing(5);
                System.out.println(throwResults);
                throwScoreInt = countScore(throwResults);
                gameScoreInt += throwScoreInt;
                System.out.println(throwScoreInt);
                System.out.println(gameScoreInt);
                throwScore.setText("Wynik tego losowania: " + throwScoreInt);
                gameScore.setText("Wynik gry: " + gameScoreInt);
                for(int i=0; i<5; i++){
                    int diceValue = throwResults.get(i);
                    System.out.println(diceValue);
                    ImageView diceImageView = findViewById(imageViewIds[i]);
                    diceImageView.setImageResource(diceImages[diceValue-1]);
                }
            }
        });

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dice1.setImageResource(R.drawable.question);
                dice2.setImageResource(R.drawable.question);
                dice3.setImageResource(R.drawable.question);
                dice4.setImageResource(R.drawable.question);
                dice5.setImageResource(R.drawable.question);
                throwScoreInt = 0;
                gameScoreInt =0;
                throwScore.setText("Wynik tego losowania: " + throwScoreInt);
                gameScore.setText("Wynik gry: " + gameScoreInt);

            }
        });
    }

    public static ArrayList<Integer> drawing(int diceAmount){
        ArrayList<Integer> draws = new ArrayList<>();
        Random rand = new Random();
        for(int i=0; i<diceAmount; i++){
            draws.add(rand.nextInt(6)+1);
        }
        return draws;
    }

    public static int countScore(ArrayList<Integer> draws){
        System.out.println("zliczanie");
        int score = 0;
        int count = 0;
        ArrayList<Integer> used = new ArrayList<>();

        for(int i = 0; i<5; i++){
            for(int j=1; j<5; j++){
                if(draws.get(i)==draws.get(j) && !used.contains(draws.get(i)) && i!=j){
                    if(count<1) score += (draws.get(i) * 2);
                    else score += draws.get(i);
                    count++;
                }

            }

            used.add(draws.get(i));
            count = 0;
        }
        return score;
    }
}