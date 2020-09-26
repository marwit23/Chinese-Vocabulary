package eu.galileolanguages.quiz2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class LessonActivity extends AppCompatActivity {
    private TextView mScore;
    private TextView mArrayLeft;
    private TextView mCharacters;
    private TextView mPinyin;
    private Button mChoice1;
    private Button mChoice2;
    private Button mChoice3;
    private Button mHelp;
    private Switch mSound;
    private int randomizerA = new Random().nextInt(20);
    private List<String> characters = new ArrayList<>();
    private List<String> pinyin = new ArrayList<>();
    private List<String> translation = new ArrayList<>();
    private List<String> wrongTranslation = new ArrayList<>();
    private List<Integer> scoreList = new ArrayList<>();
    private Integer score;
    DatabaseAccess databaseAccess;
    public int lessonNumber;
    MediaPlayer mediaPlayer;
    public boolean switchOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // General setup------------------------------------------------------------------------------------

        setContentView(R.layout.activity_lesson);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        Intent intent = getIntent();
        lessonNumber = intent.getIntExtra("lessonNumber", 1);
        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        populateArrays();
        databaseAccess.close();
        mSound = findViewById(R.id.soundOnOff);
        loadSoundData();
        mSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchOnOff = true;
                    saveData();
                } else {
                    switchOnOff = false;
                    saveData();
                }
            }
        });

        // Setting views----------------------------------------------------------------------------

        mScore = findViewById(R.id.score);
        mArrayLeft = findViewById(R.id.arrayLeft);
        mCharacters = findViewById(R.id.character);
        mPinyin = findViewById(R.id.pinyin);
        mChoice1 = findViewById(R.id.choice1);
        mChoice2 = findViewById(R.id.choice2);
        mChoice3 = findViewById(R.id.choice3);
        mHelp = findViewById(R.id.hint);

        // Loading data-----------------------------------------------------------------------------

        for (int x = 0; x < 20; x++) { scoreList.add(0); }
        updateQuestion();

        // Setting listeners------------------------------------------------------------------------

        final Button[] choiceButtons = {mChoice1, mChoice2, mChoice3};
        for (int i = 0; i < choiceButtons.length; i++) {
            final int finalI = i;
            choiceButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if(choiceButtons[finalI].getText() == translation.get(randomizerA)){
                            updateQuestionCorrectA();
                            choiceButtons[finalI].setBackgroundResource(R.drawable.button2);
                            choiceButtons[finalI].setTextColor(Color.parseColor("#BE000000"));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestionCorrectB();
                                }
                            }, 2000);

                        }else {
                            if (switchOnOff == true) playSoundWrong();
                            updateQuestionWrongA();
                            Animation animShake = AnimationUtils.loadAnimation(LessonActivity.this, R.anim.shake);
                            choiceButtons[finalI].startAnimation(animShake);
                            for(int j = 0; j < 3; j++) {
                                final int finalJ = j;
                                if(choiceButtons[j].getText() == translation.get(randomizerA)){
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            choiceButtons[finalJ].setBackgroundResource(R.drawable.button2);
                                            choiceButtons[finalJ].setTextColor(Color.parseColor("#BE000000"));
                                        }
                                    }, 1000);
                                }
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateQuestionWrongB();
                                }
                            }, 2500);
                        }
                }
            });
        }
        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelp.setEnabled(false);
                for(int i = 0; i < choiceButtons.length; i++)
                if (choiceButtons[i].getText() == translation.get(randomizerA)) {
                    choiceButtons[i].setBackgroundResource(R.drawable.button4);
                    choiceButtons[i].setTextColor(Color.parseColor("#000000"));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongB();
                        }
                    }, 2000);
                }
            }
        });
    }

    // Quiz methods---------------------------------------------------------------------------------

    private void updateQuestionCorrectA() {
        score = scoreList.get(randomizerA);
        scoreList.set(randomizerA, score += 1);
        mScore.setText("This word: " + scoreList.get(randomizerA) + "/3");
        mArrayLeft.setText("Words left: " + characters.size());
        if (switchOnOff == true) playSoundCorrect();
        mChoice1.setEnabled(false);
        mChoice2.setEnabled(false);
        mChoice3.setEnabled(false);
        mHelp.setEnabled(false);
        if (score == 3) {
            characters.remove(randomizerA);
            pinyin.remove(randomizerA);
            translation.remove(randomizerA);
            scoreList.remove(randomizerA);
        }
    }

    private void updateQuestionCorrectB() {
        if (characters.size() < 1) {
            SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
            Set<String> hs = sharedPreferences.getStringSet("lessonComplete", new HashSet<String>());
            Set<String> in = new HashSet<>(hs);
            in.add("true" + lessonNumber);
            sharedPreferences.edit().putStringSet("lessonComplete", in).commit();
            killActivity();
        } else {
            updateQuestion();
        }
    }

    private void updateQuestionWrongA() {
        score = scoreList.get(randomizerA);
        scoreList.set(randomizerA, score = 0);
        mScore.setText("This word: " + scoreList.get(randomizerA) + "/3");
        mChoice1.setEnabled(false);
        mChoice2.setEnabled(false);
        mChoice3.setEnabled(false);
        mHelp.setEnabled(false);
    }

    private void updateQuestionWrongB() {
        updateQuestion();
    }

    private int randomizerA() {
        int randomizerB = new Random().nextInt(characters.size());
        if (characters.size() > 1 && randomizerB == randomizerA)
            randomizerB = new Random().nextInt(characters.size());
        return randomizerB;
    }

    void updateQuestion() {
        randomizerA = randomizerA();
        List<String> answersArray = new ArrayList<>();
        answersArray.add(translation.get(randomizerA));
        answersArray.add(wrongTranslation.get(new Random().nextInt(3000)));
        answersArray.add(wrongTranslation.get(new Random().nextInt(3000)));
        if (answersArray.get(1).equals(answersArray.get(0))) {
            answersArray.set(1, wrongTranslation.get(new Random().nextInt(3000)));
        }
        if (answersArray.get(2).equals(answersArray.get(0)) || answersArray.get(2).equals(answersArray.get(1))) {
            answersArray.set(2, wrongTranslation.get(new Random().nextInt(3000)));
        }
        Collections.shuffle(answersArray);
        mScore.setText("This word: " + scoreList.get(randomizerA) + "/3");
        mArrayLeft.setText("Words left: " + characters.size());
        mCharacters.setText(characters.get(randomizerA));
        mPinyin.setText(pinyin.get(randomizerA));
        mChoice1.setText(answersArray.get(0));
        mChoice1.setBackgroundResource(R.drawable.button);
        mChoice1.setTextColor(Color.parseColor("#F3F3F3"));
        mChoice1.setEnabled(true);
        mChoice2.setText(answersArray.get(1));
        mChoice2.setBackgroundResource(R.drawable.button);
        mChoice2.setTextColor(Color.parseColor("#F3F3F3"));
        mChoice2.setEnabled(true);
        mChoice3.setText(answersArray.get(2));
        mChoice3.setBackgroundResource(R.drawable.button);
        mChoice3.setTextColor(Color.parseColor("#F3F3F3"));
        mChoice3.setEnabled(true);
        mHelp.setEnabled(true);
    }

    // Other methods------------------------------------------------------------------------------

    private void populateArrays() {
        databaseAccess.c = databaseAccess.db.rawQuery("SELECT character, pinyin, translation FROM Table1 WHERE lessonID =" + lessonNumber, null);
        databaseAccess.c.moveToFirst();
        for (int x = 0; x < 20; x++) {
            characters.add(databaseAccess.c.getString(0));
            pinyin.add(databaseAccess.c.getString(1));
            translation.add(databaseAccess.c.getString(2));
            databaseAccess.c.moveToNext();
        }
        databaseAccess.c = databaseAccess.db.rawQuery("SELECT character, pinyin, translation FROM Table1", null);
        databaseAccess.c.moveToFirst();
        for (int x = 0; x < 3000; x++) {
            wrongTranslation.add(databaseAccess.c.getString(2));
            databaseAccess.c.moveToNext();
        }
        databaseAccess.c.close();
    }

    private void playSoundCorrect() {
        mediaPlayer = MediaPlayer.create(LessonActivity.this, R.raw.correct4);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
            }
        });
    }

    private void playSoundWrong() {
        mediaPlayer = MediaPlayer.create(LessonActivity.this, R.raw.wrong2);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
            }
        });
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("switch1", mSound.isChecked());
        editor.apply();
    }

    public void loadSoundData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        switchOnOff = sharedPreferences.getBoolean("switch1", true);
        mSound.setChecked(switchOnOff);
    }

    private void killActivity() {
        finish();
    }
}