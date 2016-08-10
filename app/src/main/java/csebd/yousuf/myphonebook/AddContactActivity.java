package csebd.yousuf.myphonebook;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

//import csebd.yousuf.myphonebook.db.DbAdapter;
//import csebd.yousuf.myphonebook.phonebook.Phonebook;

public class AddContactActivity extends ActionBarActivity {
    private EditText etAddName;
    private EditText etAddAddress;
    private EditText etAddPhone;
    private EditText etAddEmail;
    private Button btnAddSave;
    private Button btnAddClear;
    private Button btnAddCancel;
    private ImageView ivAddPickImage;

    private String name = "";
    private String phone = "";
    private String address = "";
    private String email = "";
    private String imagePath = null;

    private DbAdapter dbAdapter;

    public static final int CODE_LOAD_IMAGES = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF3763CA));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.s);


        dbAdapter = new DbAdapter(getApplicationContext());
        init();

    }

    private void init() {
        etAddAddress = (EditText) findViewById(R.id.etAddAddress);
        etAddName = (EditText) findViewById(R.id.etAddName);
        etAddEmail = (EditText) findViewById(R.id.etAddEmail);
        etAddPhone = (EditText) findViewById(R.id.etAddPhone);
        btnAddCancel = (Button) findViewById(R.id.btnAddCancel);
        btnAddSave = (Button) findViewById(R.id.btnAddSave);
        btnAddClear = (Button) findViewById(R.id.btnAddClear);
        ivAddPickImage = (ImageView) findViewById(R.id.ivAddPickImage);

    }

    // button save click
    public void onAddSave(View view) {
        getTextData();
        dataInsertIntoDatabase();

    }

    // button save click
    public void onAddClear(View view) {
        clearData();
    }

    // button save click
    public void onAddCancel(View view) {
        finish();
    }

    // image view pick image click
    public void onAddPickImage(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, CODE_LOAD_IMAGES);
    }

    // activity result for pick up image from gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_LOAD_IMAGES && resultCode == RESULT_OK && data != null) {
            Uri image = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor c = getContentResolver().query(image, projection, null, null, null);
            c.moveToFirst();

            imagePath = c.getString(c.getColumnIndex(projection[0]));
//            T.d(imagePath);
            ivAddPickImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));

        }
    }

    private void getTextData() {
        name = etAddName.getText().toString().trim();
        phone = etAddPhone.getText().toString();
        email = etAddEmail.getText().toString();
        address = etAddAddress.getText().toString();

    }

    private void clearData() {
        etAddName.setText("");
        etAddPhone.setText("");
        etAddEmail.setText("");
        etAddAddress.setText("");
        ivAddPickImage.setImageBitmap(null);
        imagePath = null;

    }

    private void dataInsertIntoDatabase() {
        if ((name.length() >= 3 && phone.length() >= 3)){
            checkImagePath(); // if null, imagePath = set first char form editText name
            Phonebook phonebook = new Phonebook(name, phone, email, address, imagePath);

            long inserted = dbAdapter.insertPhonebook(phonebook);
//            T.d("row id = "+ inserted);
            if (inserted > 0) {
                T.t(getApplication(), "Contacts Saved");
//                T.d(imagePath);
                clearData();
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                int a = ((int) inserted);
                i.putExtra("id", a);
                startActivity(i);
                finish();
            }
            else
                T.t(getApplication(), "Contacts Not Saved");

        } else {
//            T.t(getApplication(), "Blank fields is not allowed");
            etAddName.setError("To Short");
            etAddPhone.setError("To Short");
        }

    }


    private void checkImagePath() {
        if (imagePath == null) {
            char firstChar = name.charAt(0);
            imagePath = Character.toString(firstChar).toUpperCase();
        }
    }
}
