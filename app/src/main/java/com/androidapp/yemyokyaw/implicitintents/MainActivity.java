package com.androidapp.yemyokyaw.implicitintents;

import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    static final int REQUEST_IMAGE_OPEN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = (EditText)findViewById(R.id.et_txt_input);
    }

    public void clickShareBtn(View view) {
        String shareText = inputText.getText().toString();
        if(!shareText.isEmpty()) {
            Intent intent = ShareCompat.IntentBuilder.from(MainActivity.this)
                    .setType("text/plain")
                    .setText(shareText)
                    .getIntent();
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else {
            Toast.makeText(MainActivity.this,"Please fill the input first!!!",Toast.LENGTH_LONG).show();
        }
    }

    public void clickLocationBtn(View view) {
        Uri gmmIntentUri = Uri.parse("geo:16.7798166,96.168384");
        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri)
                .setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    public void clickPhCallBtn(View view) {
        String regex = "^[0-9]*$";
        String shareText = inputText.getText().toString();
        if(!shareText.isEmpty() && shareText.matches(regex)){
            Intent intent = new Intent(Intent.ACTION_DIAL)
                .setData(Uri.parse("tel:" + shareText));
            if (intent.resolveActivity(getPackageManager()) != null)
                startActivity(intent);
        }else {
            Toast.makeText(MainActivity.this,"Please fill the correct number to input first!!!",Toast.LENGTH_LONG).show();
        }
    }

    public void clickEmailBtn(View view) {
        String shareText = inputText.getText().toString();
        if(shareText.isEmpty())
            shareText = "Email Body...";
        Intent intent = new Intent(Intent.ACTION_SEND)
                .setType("message/rfc822")
                .putExtra(Intent.EXTRA_EMAIL, "ymksun90@gmail.com")
                .putExtra(Intent.EXTRA_SUBJECT, "Sending Email from Implicit Intent App")
                .putExtra(Intent.EXTRA_TEXT, shareText);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(Intent.createChooser(intent, "Send Email"));

    }

    public void clickCamreaBtn(View view) {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void clickPhotoBtn(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT)
            .setType("image/*")
            .addCategory(Intent.CATEGORY_OPENABLE);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(intent, REQUEST_IMAGE_OPEN);
    }

    public void clickCalendarBtn(View view) {
        String shareText = inputText.getText().toString();
        if(shareText.isEmpty())
            shareText = "Implicit Intent Sample Event";
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, shareText)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Yangon");
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            Toast.makeText(MainActivity.this,"Image from '" + fullPhotoUri + "' is now selected...",Toast.LENGTH_LONG).show();
        }
    }

}
