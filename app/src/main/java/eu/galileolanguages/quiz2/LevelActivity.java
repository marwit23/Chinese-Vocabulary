package eu.galileolanguages.quiz2;

import androidx.appcompat.app.AppCompatActivity;

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

public class LevelActivity extends AppCompatActivity {
    TextView lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9, lesson10;
    TextView[] lessonTextViews = {lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9, lesson10};

    ImageView tick1, tick2, tick3, tick4, tick5, tick6, tick7, tick8, tick9, tick10;
    ImageView[] tickImageViews = {tick1, tick2, tick3, tick4, tick5, tick6, tick7, tick8, tick9, tick10};

    public int levelNumber;
    Set<String> levelsCompleted = new HashSet<>();
    int finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        for (int i = 0; i < 10; i++) {
            String levelID = "lesson" + (i+1);
            int resID = getResources().getIdentifier(levelID, "id", getPackageName());
            lessonTextViews[i] = ((TextView) findViewById(resID));
            lessonTextViews[i].setText(levelID);
            final int finalI = i;
            lessonTextViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LevelActivity.this, LessonActivity.class);
                    intent.putExtra("lessonNumber", (levelNumber * 10 - 10)+(finalI+1) );
                    startActivity(intent);
                }
            });
        }

        for (int i = 0; i < 10; i++) {
            String levelID = "tick" + (i+1);
            int resID = getResources().getIdentifier(levelID, "id", getPackageName());
            tickImageViews[i] = ((ImageView) findViewById(resID));
            int x = levelNumber * 10 - 10;
            if (levelsCompleted.contains("true" + (x+1))) tick1.setImageResource(android.R.drawable.btn_star_big_on);
        }

        loadData();
        Intent intent = getIntent();
        levelNumber = intent.getIntExtra("levelNumber", 1);
            for(int x = 1; x<=150; x++){
                if(levelsCompleted.contains("true" + x))finish =x;
                else break;
            }
            if(finish==150){
                showDialog();
            }
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        levelsCompleted = sharedPreferences.getStringSet("lessonComplete", levelsCompleted);
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
