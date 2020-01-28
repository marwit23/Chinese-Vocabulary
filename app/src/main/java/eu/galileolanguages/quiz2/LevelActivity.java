package eu.galileolanguages.quiz2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class LevelActivity extends AppCompatActivity {
    TextView lesson1;
    TextView lesson2;
    TextView lesson3;
    TextView lesson4;
    TextView lesson5;
    TextView lesson6;
    TextView lesson7;
    TextView lesson8;
    TextView lesson9;
    TextView lesson10;
    TextView quiz;

    ImageView tick1;
    ImageView tick2;
    ImageView tick3;
    ImageView tick4;
    ImageView tick5;
    ImageView tick6;
    ImageView tick7;
    ImageView tick8;
    ImageView tick9;
    ImageView tick10;

    public int levelNumber;
    Set<String> set2 = new HashSet<>();
    int finish;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);



        lesson1 = findViewById(R.id.lesson1);
        lesson2 = findViewById(R.id.lesson2);
        lesson3 = findViewById(R.id.lesson3);
        lesson4 = findViewById(R.id.lesson4);
        lesson5 = findViewById(R.id.lesson5);
        lesson6 = findViewById(R.id.lesson6);
        lesson7 = findViewById(R.id.lesson7);
        lesson8 = findViewById(R.id.lesson8);
        lesson9 = findViewById(R.id.lesson9);
        lesson10 = findViewById(R.id.lesson10);
        quiz = findViewById(R.id.quiz);

        tick1 = findViewById(R.id.tick1);
        tick2 = findViewById(R.id.tick2);
        tick3 = findViewById(R.id.tick3);
        tick4 = findViewById(R.id.tick4);
        tick5 = findViewById(R.id.tick5);
        tick6 = findViewById(R.id.tick6);
        tick7 = findViewById(R.id.tick7);
        tick8 = findViewById(R.id.tick8);
        tick9 = findViewById(R.id.tick9);
        tick10 = findViewById(R.id.tick10);

        loadData();

        Intent intent = getIntent();
        levelNumber = intent.getIntExtra("levelNumber", 1);
        int i = levelNumber * 10 - 10;

        lesson1.setText("Lesson " + (i+1));
        lesson2.setText("Lesson " + (i+2));
        lesson3.setText("Lesson "+ (i+3));
        lesson4.setText("Lesson " + (i+4));
        lesson5.setText("Lesson "+(i+5));
        lesson6.setText("Lesson "+(i+6));
        lesson7.setText("Lesson "+(i+7));
        lesson8.setText("Lesson "+(i+8));
        lesson9.setText("Lesson "+(i+9));
        lesson10.setText("Lesson "+(i+10));

            if (set2.contains("true" + (i+1))) tick1.setImageResource(android.R.drawable.btn_star_big_on);
            if (set2.contains("true" + (i+2))) tick2.setImageResource(android.R.drawable.btn_star_big_on);
            if (set2.contains("true" + (i+3))) tick3.setImageResource(android.R.drawable.btn_star_big_on);
            if (set2.contains("true" + (i+4))) tick4.setImageResource(android.R.drawable.btn_star_big_on);
            if (set2.contains("true" + (i+5))) tick5.setImageResource(android.R.drawable.btn_star_big_on);
            if (set2.contains("true" + (i+6))) tick6.setImageResource(android.R.drawable.btn_star_big_on);
            if (set2.contains("true" + (i+7))) tick7.setImageResource(android.R.drawable.btn_star_big_on);
            if (set2.contains("true" + (i+8))) tick8.setImageResource(android.R.drawable.btn_star_big_on);
            if (set2.contains("true" + (i+9))) tick9.setImageResource(android.R.drawable.btn_star_big_on);
            if (set2.contains("true" + (i+10))) tick10.setImageResource(android.R.drawable.btn_star_big_on);

            for(int x = 1; x<=150; x++){
                if(set2.contains("true" + x))finish =x;
                else break;
            }

            if(finish==150){
                showDialog();
            }


        lesson1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, LessonActivity.class);
                    intent.putExtra("lessonNumber", (levelNumber * 10 - 10)+1 );
                startActivity(intent);
            }
        });

        lesson2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, LessonActivity.class);
                intent.putExtra("lessonNumber", (levelNumber * 10 - 10)+2 );
                startActivity(intent);
            }
        });

        lesson3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, LessonActivity.class);
                intent.putExtra("lessonNumber", (levelNumber * 10 - 10)+3 );
                startActivity(intent);
            }
        });

        lesson4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, LessonActivity.class);
                intent.putExtra("lessonNumber", (levelNumber * 10 - 10)+4 );
                startActivity(intent);
            }
        });

        lesson5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, LessonActivity.class);
                intent.putExtra("lessonNumber", (levelNumber * 10 - 10)+5 );
                startActivity(intent);
            }
        });

        lesson6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, LessonActivity.class);
                intent.putExtra("lessonNumber", (levelNumber * 10 - 10)+6 );
                startActivity(intent);
            }
        });

        lesson7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, LessonActivity.class);
                intent.putExtra("lessonNumber", (levelNumber * 10 - 10)+7 );
                startActivity(intent);
            }
        });

        lesson8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, LessonActivity.class);
                intent.putExtra("lessonNumber", (levelNumber * 10 - 10)+8 );
                startActivity(intent);
            }
        });

        lesson9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, LessonActivity.class);
                intent.putExtra("lessonNumber", (levelNumber * 10 - 10)+9 );
                startActivity(intent);
            }
        });

        lesson10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, LessonActivity.class);
                intent.putExtra("lessonNumber", (levelNumber * 10 - 10)+10 );
                startActivity(intent);
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, QuizActivity.class);
                intent.putExtra("levelNumber", levelNumber);
                startActivity(intent);
            }
        });


    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        set2 = sharedPreferences.getStringSet("lessonComplete", set2);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
    protected void showDialog(){
    AlertDialog alertDialog = new AlertDialog.Builder(LevelActivity.this).create();
    alertDialog.setTitle("Congratulations!");
    alertDialog.setMessage("You finished all lessons. You are awesome!");
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
    alertDialog.show();
    }

}
