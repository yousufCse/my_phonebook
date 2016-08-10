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
import android.widget.TextView;

//import csebd.yousuf.myphonebook.db.DbAdapter;
//import csebd.yousuf.myphonebook.phonebook.Phonebook;

public class EditActivity extends ActionBarActivity {
    private EditText etEditName;
    private EditText etEditAddress;
    private EditText etEditPhone;
    private EditText etEditEmail;
    private Button btnEditUpdate;
    private Button btnEditClear;
    private Button btnEditCancel;
    private ImageView ivEditPickImage;

    private String name = "";
    private String phone = "";
    private String address = "";
    private String email = "";
    private String imagePath = null;

    private DbAdapter dbAdapter;

    private int id;

    public static final int CODE_LOAD_IMAGES = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF3763CA));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.editing);

        dbAdapter = new DbAdapter(getApplicationContext());

        init();

        setAllDataToField();

    }

    private void init() {
        etEditName = (EditText) findViewById(R.id.etEditName);
        etEditAddress = (EditText) findViewById(R.id.etEditAddress);
        etEditEmail = (EditText) findViewById(R.id.etEditEmail);
        etEditPhone = (EditText) findViewById(R.id.etEditPhone);
        btnEditCancel = (Button) findViewById(R.id.btnEditCancel);
        btnEditUpdate = (Button) findViewById(R.id.btnEditUpdate);
        btnEditClear = (Button) findViewById(R.id.btnEditClear);
        ivEditPickImage = (ImageView) findViewById(R.id.ivEditPickImage);

        Intent i = getIntent();
        id = i.getIntExtra("id", -1);
//        T.d("EditText id : " + id);
//        T.t(getApplication(), "EditText id : " + id);

    }

    // button save click
    public void onEditUpdate(View view) {
        getTextData();
        dataUpdateIntoDatabase();

    }

    // button save click
    public void onEditClear(View view) {
        clearData();
    }

    // button save click
    public void onEditCancel(View view) {
        finish();
    }

    // image view pick image click
    public void onEditPickImage(View view) {
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
            ivEditPickImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));

        }
    }

    private void getTextData() {
        name = etEditName.getText().toString().trim();
        phone = etEditPhone.getText().toString();
        email = etEditEmail.getText().toString();
        address = etEditAddress.getText().toString();

    }

    private void clearData() {
        etEditName.setText("");
        etEditPhone.setText("");
        etEditEmail.setText("");
        etEditAddress.setText("");
        ivEditPickImage.setImageBitmap(null);
        imagePath = null;

    }

    private void dataUpdateIntoDatabase() {
        if ((name.length() >= 3 && phone.length() >= 3)){
            checkImagePath(); // if null imagePath = set first char form editText name
            Phonebook phonebook = new Phonebook(name, phone, email, address, imagePath);

            boolean updated = dbAdapter.updatePhonebook(id, phonebook);
            if (updated) {
                T.t(getApplication(), "Contacts Updated");
//                T.d(imagePath);
                clearData();
                Intent i = new Intent();
                setResult(Activity.RESULT_OK,i);
                finish();
            }
            else
                T.t(getApplication(), "Contacts Not Updated");

        } else {
//            T.t(getApplication(), "Blank fields is not allowed");
            etEditPhone.setError("To Short");
            etEditName.setError("To Short");
        }

    }


    private void checkImagePath() {
        if (imagePath == null) {
            char firstChar = name.charAt(0);
            imagePath = Character.toString(firstChar).toUpperCase();
        }
    }

    private void setAllDataToField() {
        Phonebook phonebook = dbAdapter.queryById(id);

        etEditName.setText(phonebook.getName());
        etEditPhone.setText(phonebook.getPhone());
        etEditAddress.setText(phonebook.getAddress());
        etEditEmail.setText(phonebook.getEmail());
        imagePath = phonebook.getImagePath();
        ivEditPickImage.setImageBitmap(BitmapFactory.decodeFile(phonebook.getImagePath()));
        ivEditPickImage.setVisibility(View.VISIBLE);

    }
}
