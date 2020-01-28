package eu.galileolanguages.quiz2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
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

public class QuizActivity extends AppCompatActivity {
    private TextView mScore;
    private TextView mArrayLeft;
    private TextView mCharacters;
    private TextView mPinyin;
    private Button mChoice1;
    private Button mChoice2;
    private Button mChoice3;
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
    public int levelNumber;
    private int randomizerB;
    MediaPlayer mediaPlayer;
    public boolean switchOnOff;
    private List<String> characters50;
    private List<String> pinyin50;
    private List<String> translation50;
    private int correctCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mScore = findViewById(R.id.score);
        mArrayLeft = findViewById(R.id.arrayLeft);
        mCharacters = findViewById(R.id.character);
        mPinyin = findViewById(R.id.pinyin);
        mChoice1 = findViewById(R.id.choice1);
        mChoice2 = findViewById(R.id.choice2);
        mChoice3 = findViewById(R.id.choice3);
        mSound = findViewById(R.id.soundOnOff);
        answersArray = new ArrayList<>();
        characters = new ArrayList<>();
        pinyin = new ArrayList<>();
        translation = new ArrayList<>();
        characters50 = new ArrayList<>();
        pinyin50 = new ArrayList<>();
        translation50 = new ArrayList<>();
        wrongTranslation = new ArrayList<>();
        randomizerA = new Random().nextInt(200);
        scoreList = new ArrayList<>();

        loadSoundData();

        Intent intent = getIntent();
        levelNumber = intent.getIntExtra("levelNumber", 1);


        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        populateArrays();
        databaseAccess.close();
        for (int x = 0; x<50; x++){
            characters50.add(characters.get(randomizerA));
            pinyin50.add(pinyin.get(randomizerA));
            translation50.add(translation.get(randomizerA));
        }


        //for (int x=0; x < 20; x++){
        //    scoreList.add(0);
        //}



        mScore.setText("Correct:" + correctCount);
        mArrayLeft.setText("Words left: " + characters50.size());

        mCharacters.setText(characters50.get(randomizerA));
        mPinyin.setText(pinyin50.get(randomizerA));



        wrongAnswer1 = new Random().nextInt(3000);
        wrongAnswer2 = new Random().nextInt(3000);

        answersArray.add(translation50.get(randomizerA));
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
                if(mChoice1.getText() == translation50.get(randomizerA)){
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
                else if(mChoice2.getText() == translation50.get(randomizerA)) {
                    if (switchOnOff == true) playSoundWrong();
                    Animation animShake = AnimationUtils.loadAnimation(QuizActivity.this, R.anim.shake);
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
                else if(mChoice3.getText() == translation50.get(randomizerA)) {
                    if (switchOnOff == true) playSoundWrong();
                    Animation animShake = AnimationUtils.loadAnimation(QuizActivity.this, R.anim.shake);
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
                if(mChoice2.getText() == translation50.get(randomizerA)){
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
                else if(mChoice1.getText() == translation50.get(randomizerA)) {
                    if (switchOnOff == true) playSoundWrong();
                    Animation animShake = AnimationUtils.loadAnimation(QuizActivity.this, R.anim.shake);
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
                else if(mChoice3.getText() == translation50.get(randomizerA)) {
                    if (switchOnOff == true) playSoundWrong();
                    Animation animShake = AnimationUtils.loadAnimation(QuizActivity.this, R.anim.shake);
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
                if(mChoice3.getText() == translation50.get(randomizerA)){
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
                else if(mChoice1.getText() == translation50.get(randomizerA)) {
                    if (switchOnOff == true) playSoundWrong();
                    Animation animShake = AnimationUtils.loadAnimation(QuizActivity.this, R.anim.shake);
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
                else if(mChoice2.getText() == translation50.get(randomizerA)) {
                    if (switchOnOff == true) playSoundWrong();
                    Animation animShake = AnimationUtils.loadAnimation(QuizActivity.this, R.anim.shake);
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

    }





    private void updateQuestionCorrectA() {
        if(switchOnOff==true)playSoundCorrect();
        mChoice1.setEnabled(false);
        mChoice2.setEnabled(false);
        mChoice3.setEnabled(false);
        characters50.remove(randomizerA);
        pinyin50.remove(randomizerA);
        translation50.remove(randomizerA);
        correctCount++;


    }

    private void updateQuestionCorrectB() {
        if (characters50.size() < 1) {
            killActivity();
        } else {

            randomizerA = randomizerA();
            wrongAnswer1 = new Random().nextInt(3000);
            wrongAnswer2 = new Random().nextInt(3000);
            mCharacters.setText(characters50.get(randomizerA));
            mPinyin.setText(pinyin50.get(randomizerA));

            answersArray = new ArrayList<>();
            answersArray.add(translation50.get(randomizerA));
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
            mScore.setText("Correct: " + correctCount);
            mArrayLeft.setText("Words left: " + characters50.size());

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
    }

    private void updateQuestionWrongA() {
        mChoice1.setEnabled(false);
        mChoice2.setEnabled(false);
        mChoice3.setEnabled(false);
        characters50.remove(randomizerA);
        pinyin50.remove(randomizerA);
        translation50.remove(randomizerA);
    }

    private void updateQuestionWrongB(){
        if (characters50.size() < 1) {

            killActivity();
        } else {

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
            mArrayLeft.setText("Words left: " + characters50.size());

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
    }

    private void populateArrays() {
        databaseAccess.c = databaseAccess.db.rawQuery("SELECT character, pinyin, translation FROM Table1 WHERE levelID ="+ levelNumber, null);
        databaseAccess.c.moveToFirst();
        for (int x = 0; x < 200; x++) {
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
        mediaPlayer= MediaPlayer.create(QuizActivity.this, R.raw.correct4);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
            }
        });
    }

    private void playSoundWrong(){
        mediaPlayer= MediaPlayer.create(QuizActivity.this, R.raw.wrong2);
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

    private void killActivity() {
        finish();
    }
    protected void showQuizDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(QuizActivity.this).create();
        alertDialog.setTitle("Your score is: " + (correctCount*2) + "%");
        if (correctCount<=20)
        alertDialog.setMessage("Your result is so so.");
        else if (correctCount>20&&correctCount<=40)
            alertDialog.setMessage("That is pretty good");
        else if (correctCount>40)
            alertDialog.setMessage("You are awesome!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        killActivity();
                    }
                });
        alertDialog.show();
    }

}
