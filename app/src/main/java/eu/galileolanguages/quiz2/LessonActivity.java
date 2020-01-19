package eu.galileolanguages.quiz2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import android.os.*;

import eu.galileolanguages.quiz2.R;

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
    private int randomizerA;
    private List<String> characters;
    private List<String> pinyin;
    private List<String> translation;
    private List<String> wrongTranslation;
    private List<Integer> scoreList;
    private Integer score;
    private List<String> answersArray;
    private int wrongAnswer1;
    private int wrongAnswer2;
    DatabaseAccess databaseAccess;
    public int lessonNumber;
    private int randomizerB;
    MediaPlayer mediaPlayer;
    public boolean switchOnOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mScore = findViewById(R.id.score);
        mArrayLeft = findViewById(R.id.arrayLeft);
        mCharacters = findViewById(R.id.character);
        mPinyin = findViewById(R.id.pinyin);
        mChoice1 = findViewById(R.id.choice1);
        mChoice2 = findViewById(R.id.choice2);
        mChoice3 = findViewById(R.id.choice3);
        mHelp = findViewById(R.id.hint);
        mSound = findViewById(R.id.soundOnOff);
        answersArray = new ArrayList<>();
        characters = new ArrayList<>();
        pinyin = new ArrayList<>();
        translation = new ArrayList<>();
        wrongTranslation = new ArrayList<>();
        randomizerA = new Random().nextInt(20);
        scoreList = new ArrayList<>();

        loadSoundData();

        Intent intent = getIntent();
        lessonNumber = intent.getIntExtra("lessonNumber", 1);


        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        populateArrays();
        databaseAccess.close();


        for (int x=0; x < 20; x++){
            scoreList.add(0);
        }



        mScore.setText("This word: " + scoreList.get(randomizerA) +"/3");
        mArrayLeft.setText("Words left: " + characters.size());

        mCharacters.setText(characters.get(randomizerA));
        mPinyin.setText(pinyin.get(randomizerA));



        wrongAnswer1 = new Random().nextInt(3000);
        wrongAnswer2 = new Random().nextInt(3000);

        answersArray.add(translation.get(randomizerA));
        answersArray.add(wrongTranslation.get(wrongAnswer1));
        answersArray.add(wrongTranslation.get(wrongAnswer2));

        if (answersArray.get(1).equals(answersArray.get(0))){
            answersArray.set(1, wrongTranslation.get(new Random().nextInt(3000)));
        }

        if (answersArray.get(2).equals(answersArray.get(0)) || answersArray.get(2).equals(answersArray.get(1))){
            answersArray.set(2, wrongTranslation.get(new Random().nextInt(3000)));
        }

        Collections.shuffle(answersArray);

        mChoice1.setText(answersArray.get(0));
        mChoice2.setText(answersArray.get(1));
        mChoice3.setText(answersArray.get(2));



        mSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switchOnOff = true;
                    saveData();
                }
                else {
                    switchOnOff = false;
                    saveData();
                }
            }
        });


        mChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChoice1.getText() == translation.get(randomizerA)){
                    updateQuestionCorrectA();
                    mChoice1.setBackgroundResource(R.drawable.button2);
                    mChoice1.setTextColor(Color.parseColor("#BE000000"));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionCorrectB();
                        }
                    }, 2000);
                }
                else if(mChoice2.getText() == translation.get(randomizerA)) {
                    if (switchOnOff == true) playSoundWrong();
                    Animation animShake = AnimationUtils.loadAnimation(LessonActivity.this, R.anim.shake);
                    mChoice1.startAnimation(animShake);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongA();
                            mChoice2.setBackgroundResource(R.drawable.button2);
                            mChoice2.setTextColor(Color.parseColor("#BE000000"));
                        }
                    }, 1000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongB();
                        }
                    }, 2500);

                }
                else if(mChoice3.getText() == translation.get(randomizerA)) {
                    if (switchOnOff == true) playSoundWrong();
                    Animation animShake = AnimationUtils.loadAnimation(LessonActivity.this, R.anim.shake);
                    mChoice1.startAnimation(animShake);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongA();
                            mChoice3.setBackgroundResource(R.drawable.button2);
                            mChoice3.setTextColor(Color.parseColor("#BE000000"));
                        }
                    }, 1000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongB();
                        }
                    }, 2500);

                }
                }
        });

        mChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChoice2.getText() == translation.get(randomizerA)){
                    updateQuestionCorrectA();
                    mChoice2.setBackgroundResource(R.drawable.button2);
                    mChoice2.setTextColor(Color.parseColor("#BE000000"));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionCorrectB();
                        }
                    }, 2000);
                }
                else if(mChoice1.getText() == translation.get(randomizerA)) {
                    if (switchOnOff == true) playSoundWrong();
                    Animation animShake = AnimationUtils.loadAnimation(LessonActivity.this, R.anim.shake);
                    mChoice2.startAnimation(animShake);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongA();
                            mChoice1.setBackgroundResource(R.drawable.button2);
                            mChoice1.setTextColor(Color.parseColor("#BE000000"));
                        }
                    }, 1000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongB();
                        }
                    }, 2500);

                }
                else if(mChoice3.getText() == translation.get(randomizerA)) {
                    if (switchOnOff == true) playSoundWrong();
                    Animation animShake = AnimationUtils.loadAnimation(LessonActivity.this, R.anim.shake);
                    mChoice2.startAnimation(animShake);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongA();
                            mChoice3.setBackgroundResource(R.drawable.button2);
                            mChoice3.setTextColor(Color.parseColor("#BE000000"));
                        }
                    }, 1000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongB();
                        }
                    }, 2500);

                }
            }
        });

        mChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChoice3.getText() == translation.get(randomizerA)){
                    updateQuestionCorrectA();
                    mChoice3.setBackgroundResource(R.drawable.button2);
                    mChoice3.setTextColor(Color.parseColor("#BE000000"));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionCorrectB();
                        }
                    }, 2000);
                }
                else if(mChoice1.getText() == translation.get(randomizerA)) {
                    if (switchOnOff == true) playSoundWrong();
                    Animation animShake = AnimationUtils.loadAnimation(LessonActivity.this, R.anim.shake);
                    mChoice3.startAnimation(animShake);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongA();
                            mChoice1.setBackgroundResource(R.drawable.button2);
                            mChoice1.setTextColor(Color.parseColor("#BE000000"));
                        }
                    }, 1000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongB();
                        }
                    }, 2500);

                }
                else if(mChoice2.getText() == translation.get(randomizerA)) {
                    if (switchOnOff == true) playSoundWrong();
                    Animation animShake = AnimationUtils.loadAnimation(LessonActivity.this, R.anim.shake);
                    mChoice3.startAnimation(animShake);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongA();
                            mChoice2.setBackgroundResource(R.drawable.button2);
                            mChoice2.setTextColor(Color.parseColor("#BE000000"));
                        }
                    }, 1000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongB();
                        }
                    }, 2500);

                }
            }
        });

        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChoice1.getText() == translation.get(randomizerA)){
                    mChoice1.setBackgroundResource(R.drawable.button4);
                    mChoice1.setTextColor(Color.parseColor("#000000"));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongB();
                        }
                    }, 2000);
                }
                else if(mChoice2.getText() == translation.get(randomizerA)){
                    mChoice2.setBackgroundResource(R.drawable.button4);
                    mChoice2.setTextColor(Color.parseColor("#000000"));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateQuestionWrongB();
                        }
                    }, 2000);
                }
                else if(mChoice3.getText() == translation.get(randomizerA)){
                    mChoice3.setBackgroundResource(R.drawable.button4);
                    mChoice3.setTextColor(Color.parseColor("#000000"));
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





    private void updateQuestionCorrectA() {
        score = scoreList.get(randomizerA);
        scoreList.set(randomizerA, score += 1);
        mScore.setText("This word: " + scoreList.get(randomizerA) + "/3");
        mArrayLeft.setText("Words left: " + characters.size());
        if(switchOnOff==true)playSoundCorrect();
        mChoice1.setEnabled(false);
        mChoice2.setEnabled(false);
        mChoice3.setEnabled(false);

        if (score == 3) {
            characters.remove(randomizerA);
            pinyin.remove(randomizerA);
            translation.remove(randomizerA);
            scoreList.remove(randomizerA);
        }

    }

    private void updateQuestionCorrectB() {
            if (characters.size() == 0) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
                Set<String> hs = sharedPreferences.getStringSet("lessonComplete", new HashSet<String>());
                Set<String> in = new HashSet<String>(hs);
                in.add("true" + lessonNumber);
                sharedPreferences.edit().putStringSet("lessonComplete", in).commit();
                finishActivity(12345);
            }
            randomizerA = randomizerA();
            wrongAnswer1 = new Random().nextInt(3000);
            wrongAnswer2 = new Random().nextInt(3000);
            mCharacters.setText(characters.get(randomizerA));
            mPinyin.setText(pinyin.get(randomizerA));

            answersArray = new ArrayList<>();
            answersArray.add(translation.get(randomizerA));
            answersArray.add(wrongTranslation.get(wrongAnswer1));
            answersArray.add(wrongTranslation.get(wrongAnswer2));

            if (answersArray.get(1).equals(answersArray.get(0))) {
                answersArray.set(1, wrongTranslation.get(new Random().nextInt(3000)));
            }

            if (answersArray.get(2).equals(answersArray.get(0)) || answersArray.get(2).equals(answersArray.get(1))) {
                answersArray.set(2, wrongTranslation.get(new Random().nextInt(3000)));
            }


            Collections.shuffle(answersArray);


            mChoice1.setText(answersArray.get(0));
            mChoice2.setText(answersArray.get(1));
            mChoice3.setText(answersArray.get(2));
            mScore.setText("This word: " + scoreList.get(randomizerA) + "/3");
            mArrayLeft.setText("Words left: " + characters.size());

            mChoice1.setBackgroundResource(R.drawable.button);
            mChoice1.setTextColor(Color.parseColor("#F3F3F3"));
            mChoice2.setBackgroundResource(R.drawable.button);
            mChoice2.setTextColor(Color.parseColor("#F3F3F3"));
            mChoice3.setBackgroundResource(R.drawable.button);
            mChoice3.setTextColor(Color.parseColor("#F3F3F3"));
            mChoice1.setEnabled(true);
            mChoice2.setEnabled(true);
            mChoice3.setEnabled(true);

    }

    private void updateQuestionWrongA() {
        score = scoreList.get(randomizerA);
        scoreList.set(randomizerA, score = 0);
        mScore.setText("This word: " + scoreList.get(randomizerA) + "/3");
        mChoice1.setEnabled(false);
        mChoice2.setEnabled(false);
        mChoice3.setEnabled(false);
    }

    private void updateQuestionWrongB(){
        randomizerA = randomizerA();
        wrongAnswer1 = new Random().nextInt(3000);
        wrongAnswer2 = new Random().nextInt(3000);
        mCharacters.setText(characters.get(randomizerA));
        mPinyin.setText(pinyin.get(randomizerA));

        answersArray = new ArrayList<>();
        answersArray.add(translation.get(randomizerA));
        answersArray.add(wrongTranslation.get(wrongAnswer1));
        answersArray.add(wrongTranslation.get(wrongAnswer2));

        if (answersArray.get(1).equals(answersArray.get(0))){
            answersArray.set(1, wrongTranslation.get(new Random().nextInt(3000)));
        }

        if (answersArray.get(2).equals(answersArray.get(0)) || answersArray.get(2).equals(answersArray.get(1))){
            answersArray.set(2, wrongTranslation.get(new Random().nextInt(3000)));
        }

        Collections.shuffle(answersArray);



        mChoice1.setText(answersArray.get(0));
        mChoice2.setText(answersArray.get(1));
        mChoice3.setText(answersArray.get(2));
        mScore.setText("This word: " + scoreList.get(randomizerA) + "/3");

        mChoice1.setBackgroundResource(R.drawable.button);
        mChoice1.setTextColor(Color.parseColor("#F3F3F3"));
        mChoice2.setBackgroundResource(R.drawable.button);
        mChoice2.setTextColor(Color.parseColor("#F3F3F3"));
        mChoice3.setBackgroundResource(R.drawable.button);
        mChoice3.setTextColor(Color.parseColor("#F3F3F3"));
        mChoice1.setEnabled(true);
        mChoice2.setEnabled(true);
        mChoice3.setEnabled(true);
    }

    private void populateArrays() {
        databaseAccess.c = databaseAccess.db.rawQuery("SELECT character, pinyin, translation FROM Table1 WHERE lessonID ="+ lessonNumber, null);
        databaseAccess.c.moveToFirst();
        for (int x = 0; x < 20; x++) {
            characters.add(databaseAccess.c.getString(0));
            pinyin.add(databaseAccess.c.getString(1));
            translation.add(databaseAccess.c.getString(2));
            wrongTranslation.add(databaseAccess.c.getString(2));
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

    private int randomizerA(){
        randomizerB = new Random().nextInt(characters.size());
        if (characters.size()>1 && randomizerB == randomizerA) randomizerB = new Random().nextInt(characters.size());
        return randomizerB;
    }

    private void playSoundCorrect(){
        mediaPlayer= MediaPlayer.create(LessonActivity.this, R.raw.correct4);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
            }
        });
    }

    private void playSoundWrong(){
        mediaPlayer= MediaPlayer.create(LessonActivity.this, R.raw.wrong2);
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

    public void loadSoundData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        switchOnOff = sharedPreferences.getBoolean("switch1", true);
        mSound.setChecked(switchOnOff);
    }

}