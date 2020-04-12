package com.example.hwa52external;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;


public class SettingActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_READ_STORAGE = 10;
    private Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_READ_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_READ_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    btnOK = findViewById(R.id.btnOK);
                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadImage();
                        }
                    });
                }
        }
    }

    private void loadImage() {
        if (isExternalStorageWritable()) {

            EditText editText = findViewById(R.id.btnEditPath);
            String nameImage = editText.getText().toString();

            File picFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), nameImage);
            if (picFile.canRead())  {
                Intent intent = new Intent();
                intent.putExtra("name", nameImage);
                setResult(RESULT_OK, intent);
                finish();
            }else {
                Toast.makeText(SettingActivity.this, R.string.messageEditTextToImage, Toast.LENGTH_LONG).show();
            }



        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
