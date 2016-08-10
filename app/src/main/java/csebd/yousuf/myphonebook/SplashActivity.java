package csebd.yousuf.myphonebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class SplashActivity extends Activity {

    private static final int TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        workSplash(); // sleep 2 seconds



    }

    private void workSplash() {
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(TIME);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    } // end

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
