package csebd.yousuf.myphonebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;





public class DetailActivity extends ActionBarActivity {
    private DbAdapter dbAdapter;

    private TextView tvDetailName;
    private TextView tvDetailPhone;
    private TextView tvDetailEmail;
    private TextView tvDetailAddress;
    private TextView tvDetailMain;
    private ImageView ivDetailImage;
    private Button btnDetailCall;

    public static final int CODE_EDIT  = 5;

    private int id;
    private Phonebook phonebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF3763CA));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.card);

        init();

    }

    private void init() {
        assign();  // ie... Button, TextView etc assign;
        dbAdapter = new DbAdapter(getApplicationContext());

        Intent i = getIntent();
        id = i.getIntExtra("id", -1);
//        T.d("Detail id: "+id);
//        T.t(getApplication(), "Detail id : "+id);

        phonebook = dbAdapter.queryById(id);
//        T.d(phonebook.toString());
//        T.t(getApplicationContext(), phonebook.toString());

        tvDetailName.setText(phonebook.getName());
        tvDetailPhone.setText(phonebook.getPhone());
        tvDetailEmail.setText(phonebook.getEmail());
        tvDetailAddress.setText(phonebook.getAddress());
        if (phonebook.getImagePath().length() <= 1) {
            tvDetailMain.setVisibility(View.VISIBLE);
            tvDetailMain.setText(phonebook.getImagePath());
        } else {
            ivDetailImage.setVisibility(View.VISIBLE);
            ivDetailImage.setImageBitmap(BitmapFactory.decodeFile(phonebook.getImagePath()));
        }

    }

    private  void assign() {
        tvDetailName = (TextView) findViewById(R.id.tvDetailName);
        tvDetailAddress = (TextView) findViewById(R.id.tvDetailsAddress);
        tvDetailEmail = (TextView) findViewById(R.id.tvDetailsEmail);
        tvDetailPhone = (TextView) findViewById(R.id.tvDetailsPhone);
        tvDetailMain = (TextView) findViewById(R.id.tvDetailMain);
        btnDetailCall = (Button) findViewById(R.id.btnDetailsCall);
        ivDetailImage = (ImageView) findViewById(R.id.ivDetailImage);
    } // end assign

    // call button click
    public void onDetailCall(View view) {
        String phoneNo = phonebook.getPhone();
        Intent call = new Intent(Intent.ACTION_CALL);
//        T.d(phoneNo);  // check phone no
        call.setData(Uri.parse("tel:" + phoneNo));
        startActivity(call);

    }


    //  menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
    // menu item click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int m_id = item.getItemId();

        // contact edit
        if (m_id == R.id.m_detail_edit) {
            startActivityForResult(new Intent(getApplicationContext(), EditActivity.class).putExtra("id", id), CODE_EDIT); // go edit activity
//            T.d("menu click ok");
            return true;
        }
        // contacts delete
        if (m_id == R.id.m_detail_delete) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Contacts Delete");
            builder.setMessage("Do you want to delete, " + phonebook.getName() + " ?");
            builder.setIcon(android.R.drawable.ic_delete);

            builder.setNegativeButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    boolean deleted = dbAdapter.deletePhonebook(id);
                    String s = deleted ? "Contacts Deleted " : "Contacts Not Deleted";
                    T.t(getApplicationContext(),s);
                    finish();
                }
            });

            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_EDIT && resultCode == RESULT_OK ) {
            init();
        }
    }

    // onDetailsButtonPhone
    public void onDetailsButtonPhone(View v) {
        String phoneNo = phonebook.getPhone();
        Intent call = new Intent(Intent.ACTION_DIAL);
//        T.d(phoneNo);  // check phone no
        call.setData(Uri.parse("tel:" + phoneNo));
        startActivity(call);

    }

    // onDetailsButtonPhone
    public void onDetailsButtonEmail(View v) {
        String emailId = phonebook.getEmail();
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailId});
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Send mail to "+emailId));

    }
    // message send
    public void onBtnDetailMessage(View v) {
        String sms = phonebook.getPhone();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setType("vnd.android-dir/mms-sms");
        i.setData(Uri.parse("smsto:"+ sms));
        startActivity(Intent.createChooser(i, "Send SMS to"+ sms));

    }
}
