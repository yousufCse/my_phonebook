package csebd.yousuf.myphonebook;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class AboutUs extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF3763CA));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.about);
    }

    public void onButtonAboutUsSentEmail(View v) {

        String emailId = "yousuf.stu@gmail.com";
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{emailId});
        i.setType("message/rfc822");
        startActivity(Intent.createChooser(i, "Sent an email to developer"));

    }


    public void onButtonAboutUsCancel (View v) {
            finish(); // activity finish

    }
}
