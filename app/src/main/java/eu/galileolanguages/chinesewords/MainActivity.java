package eu.galileolanguages.chinesewords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import hotchemi.android.rate.AppRate;


public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {
TextView level1;
TextView level2;
TextView level3;
TextView level4;
TextView level5;
TextView level6;
TextView level7;
TextView level8;
TextView level9;
TextView level10;
TextView level11;
TextView level12;
TextView level13;
TextView level14;
TextView level15;

ImageView lock1;
ImageView lock2;
ImageView lock3;
ImageView lock4;
ImageView lock5;
ImageView lock6;
ImageView lock7;
ImageView lock8;
ImageView lock9;
ImageView lock10;
ImageView lock11;
ImageView lock12;
ImageView lock13;
ImageView lock14;
ImageView lock15;
TextView rateThisApp;


public static int levelNumber;
BillingProcessor bp;
public boolean purchased;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        purchased = sharedPreferences.getBoolean("purchased", false);

        AppRate.with(this)
                .setInstallDays(3)
                .setLaunchTimes(7)
                .setRemindInterval(8)
                .monitor();

        AppRate.showRateDialogIfMeetsConditions(this);

        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        level4 = findViewById(R.id.level4);
        level5 = findViewById(R.id.level5);
        level6 = findViewById(R.id.level6);
        level7 = findViewById(R.id.level7);
        level8 = findViewById(R.id.level8);
        level9 = findViewById(R.id.level9);
        level10 = findViewById(R.id.level10);
        level11 = findViewById(R.id.level11);
        level12 = findViewById(R.id.level12);
        level13 = findViewById(R.id.level13);
        level14 = findViewById(R.id.level14);
        level15 = findViewById(R.id.level15);
        lock1 = findViewById(R.id.lock1);
        lock2 = findViewById(R.id.lock2);
        lock3 = findViewById(R.id.lock3);
        lock4 = findViewById(R.id.lock4);
        lock5 = findViewById(R.id.lock5);
        lock6 = findViewById(R.id.lock6);
        lock7 = findViewById(R.id.lock7);
        lock8 = findViewById(R.id.lock8);
        lock9 = findViewById(R.id.lock9);
        lock10 = findViewById(R.id.lock10);
        lock11 = findViewById(R.id.lock11);
        lock12 = findViewById(R.id.lock12);
        lock13 = findViewById(R.id.lock13);
        lock14 = findViewById(R.id.lock14);
        lock15 = findViewById(R.id.lock15);
        rateThisApp = findViewById(R.id.ratemyapp);


        if (!purchased){
            bp = BillingProcessor.newBillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhHZ4JLJZKojeXmB0cRHR4QB1KydgSTptTh421oikDZLqNeSNpJFtNhvbItq+4Pz3Eyuj2jXFuHbaNiNQhhuyAWneDzTyOTiFJ9zrc6d3iidCOCM1vY9rASQNdJMM7VSfJsh2ZPOMgoNfIB/I7L/OSNpIXmmFRvR23ln/6S1nq6BM2cr5YM/AryV04F179URqftosPAMwiQ0u28Ks7JWNHWTYwi9or+DGvt3fEnE6L3ZK+7KRSqWN39kpYBkcFy8ur2m+6qLaS4qDDReVuHMtNlcdlD7Quv2Fra7yDJqrj20K/LOtWaHBKLjpb+Wu9eoQxUZqjQTbOwiVXAhXXtuRfQIDAQAB", this);
            bp.initialize();
        }

        if (purchased){
            lock2.setImageResource(R.drawable.empty);
            lock3.setImageResource(R.drawable.empty);
            lock4.setImageResource(R.drawable.empty);
            lock5.setImageResource(R.drawable.empty);
            lock6.setImageResource(R.drawable.empty);
            lock7.setImageResource(R.drawable.empty);
            lock8.setImageResource(R.drawable.empty);
            lock9.setImageResource(R.drawable.empty);
            lock10.setImageResource(R.drawable.empty);
            lock11.setImageResource(R.drawable.empty);
            lock12.setImageResource(R.drawable.empty);
            lock13.setImageResource(R.drawable.empty);
            lock14.setImageResource(R.drawable.empty);
            lock15.setImageResource(R.drawable.empty);

        }




        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                intent.putExtra("levelNumber", 1 );
                startActivity(intent);
            }
        });


        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!purchased) bp.purchase(MainActivity.this, "all_levels");
                else if (purchased){
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", 2 );
                    startActivity(intent);
                }

            }
        });

        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!purchased) bp.purchase(MainActivity.this, "all_levels");
                else if (purchased){
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", 3 );
                    startActivity(intent);
                }

            }
        });

        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!purchased) bp.purchase(MainActivity.this, "all_levels");
                else if (purchased){
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", 4 );
                    startActivity(intent);
                }

            }
        });

        level5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!purchased) bp.purchase(MainActivity.this, "all_levels");
                else if (purchased){
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", 5 );
                    startActivity(intent);
                }

            }
        });

        level6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!purchased) bp.purchase(MainActivity.this, "all_levels");
                else if (purchased){
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", 6 );
                    startActivity(intent);
                }

            }
        });

        level7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!purchased) bp.purchase(MainActivity.this, "all_levels");
                else if (purchased){
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", 7 );
                    startActivity(intent);
                }

            }
        });

        level8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!purchased) bp.purchase(MainActivity.this, "all_levels");
                else if (purchased){
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", 8 );
                    startActivity(intent);
                }

            }
        });

        level9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!purchased) bp.purchase(MainActivity.this, "all_levels");
                else if (purchased){
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", 9 );
                    startActivity(intent);
                }

            }
        });

        level10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!purchased) bp.purchase(MainActivity.this, "all_levels");
                else if (purchased){
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", 10 );
                    startActivity(intent);
                }

            }
        });

        level11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!purchased) bp.purchase(MainActivity.this, "all_levels");
                else if (purchased){
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", 11 );
                    startActivity(intent);
                }
            }
        });


        level12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!purchased) bp.purchase(MainActivity.this, "all_levels");
                else if (purchased){
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", 12 );
                    startActivity(intent);
                }

            }
        });

        level13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!purchased) bp.purchase(MainActivity.this, "all_levels");
                else if (purchased){
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", 13 );
                    startActivity(intent);
                }

            }
        });

        level14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!purchased) bp.purchase(MainActivity.this, "all_levels");
                else if (purchased){
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", 14 );
                    startActivity(intent);
                }

            }
        });

        level15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!purchased) bp.purchase(MainActivity.this, "all_levels");
                else if (purchased){
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra("levelNumber", 15 );
                    startActivity(intent);
                }

            }
        });


        rateThisApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateMe(v);
            }
        });



    }


    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        purchased = true;
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("purchased", purchased=true);

        editor.apply();

        lock2.setImageResource(R.drawable.empty);
        lock3.setImageResource(R.drawable.empty);
        lock4.setImageResource(R.drawable.empty);
        lock5.setImageResource(R.drawable.empty);
        lock6.setImageResource(R.drawable.empty);
        lock7.setImageResource(R.drawable.empty);
        lock8.setImageResource(R.drawable.empty);
        lock9.setImageResource(R.drawable.empty);
        lock10.setImageResource(R.drawable.empty);
        lock11.setImageResource(R.drawable.empty);
        lock12.setImageResource(R.drawable.empty);
        lock13.setImageResource(R.drawable.empty);
        lock14.setImageResource(R.drawable.empty);
        lock15.setImageResource(R.drawable.empty);

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
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

/*
payment, ok
shared prefs, ok
result, ok
first open app, olać
warning on press back, (without double open super) olać
"show" button ok
animation, okać
correctly end activity, jeszcze nie wiem, ale chyba tak
manifest allow deep reading ok
end congratulations jeszcze nie ma
rate ok
sound on / off ok
sounds ok
allow updates after installation ok
disable action bar on main ok

Jak błąd to najpierw shake 1s, potem pokazuje poprawna odpowiedź 2s ok
może wrongAnswer dać z całej bazy danych a nie tylko z tabelki
le, guo, suo, , yu, znaleźć powtarzające się ok
Treść w dialogu "rate this app" ok
10 wyrazów
switch feedback
 */

