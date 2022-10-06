package com.kasimkartal866.mybookmedia.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kasimkartal866.mybookmedia.common.MyPref;
import com.kasimkartal866.mybookmedia.db.Book;
import com.kasimkartal866.mybookmedia.R;
import com.kasimkartal866.mybookmedia.db.RoomExecutor;

public class AddBookActivity extends AppCompatActivity {
    private ImageView ivPhoto;
    private Button buttonSave;
    private EditText tvExplanation,tvAuthorName,tvBookName;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    private String imageUri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        ivPhoto = findViewById(R.id.ivPhoto);
        buttonSave = findViewById(R.id.buttonSave);
        tvExplanation = findViewById(R.id.tvExplanation);
        tvAuthorName = findViewById(R.id.tvAuthorName);
        tvBookName = findViewById(R.id.tvBookName);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String explanation = tvExplanation.getText().toString();
                String authorName = tvAuthorName.getText().toString();
                String bookName = tvBookName.getText().toString();

                int userId = MyPref.getInstance().getUserId();
                Book book = new Book(bookName, authorName, explanation, imageUri, userId);
                RoomExecutor executor = RoomExecutor.getInstance(AddBookActivity.this);
                executor.addBook(book);
                Intent intent = new Intent(AddBookActivity.this, MainPageActivity.class);
                startActivity(intent);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddBookActivity.this);
                builder.setTitle("Add a book")
                        .setMessage("The Book was added.")
                        .setPositiveButton("OK", (dialog, i) -> {
                            dialog.dismiss();
                            //clearForm();

                        })
                        .create();
                builder.show();
            }
            private void clearForm() {
               tvExplanation.setText("");
                tvAuthorName.setText("");
                tvBookName.setText("");
                tvBookName.requestFocus();
            }


        });

        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                        String [] permissions =  {Manifest.permission.READ_EXTERNAL_STORAGE};

                        requestPermissions(permissions,PERMISSION_CODE);

                    }else {
                        pickImageFromGallery();
                    }
                }
                else {
                    pickImageFromGallery();

                }
            }
        });

    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data.getData().toString();
            ivPhoto.setImageURI(data.getData());
        }
    }

}