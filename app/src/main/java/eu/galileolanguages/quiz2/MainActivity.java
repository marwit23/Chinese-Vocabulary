package eu.galileolanguages.quiz2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import hotchemi.android.rate.AppRate;


public class MainActivity extends AppCompatActivity {
    TextView level1, level2, level3, level4, level5, level6, level7, level8, level9, level10, level11, level12, level13, level14, level15;
    TextView[] levelTextViews = {level1, level2, level3, level4, level5, level6, level7, level8, level9, level10, level11, level12, level13, level14, level15};
    TextView rateThisApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        rateThisApp = findViewById(R.id.ratemyapp);


        AppRate.with(this)
                .setInstallDays(3)
                .setLaunchTimes(7)
                .setRemindInterval(8)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);

        for (int i = 0; i < 15; i++) {
            String levelID = "level" + (i+1);
            int resID = getResources().getIdentifier(levelID, "id", getPackageName());
            levelTextViews[i] = ((TextView) findViewById(resID));
            final int finalI = i;
            levelTextViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", (finalI+1));
                    startActivity(intent);
                }
            });
        }

        rateThisApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateMe(v);
            }
        });
    }

    public void rateMe(View v) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }
}


