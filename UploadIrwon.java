package org.foodbankmarket;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class UploadIrwon extends AppCompatActivity {

    private static final int GALLERY_CODE = 10;
    private FirebaseStorage storage;
    private FirebaseDatabase database;

    String imagePath;
    EditText edit_description;
    ImageView imageView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_upload);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();


        imageView = findViewById(R.id.ivPoster);
    }

    public void home_view(View view) {
        onBackPressed();
    }

    public void album(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, GALLERY_CODE);
    }

    public void upload(View View) {
        Toast.makeText(getApplicationContext(), "업로드 완료", Toast.LENGTH_SHORT).show();
        uploading(imagePath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {


            imagePath = getPath(data.getData());
            File f = new File(imagePath);
            imageView.setImageURI(Uri.fromFile(f));
        }
    }

    public String getPath(Uri uri) {
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        assert cursor != null;
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

    private void uploading(String uri) {
        StorageReference storageRef = storage.getReferenceFromUrl("gs://foodbankmarket.appspot.com");

        if (uri == null) {
            Toast.makeText(getApplicationContext(), "선택된 파일이 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            File dir = new File(uri);
            final Uri file = Uri.fromFile(dir);
            StorageReference mRef = storageRef.child("irwon/"+file.getLastPathSegment());
            UploadTask uploadTask = mRef.putFile(file);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String image_u = uri.toString();

                            ImageDTOIrwon imageDTOIrwon = new ImageDTOIrwon();

                            edit_description = findViewById(R.id.edit_description);

                            imageDTOIrwon.imageUrl = image_u;
                            imageDTOIrwon.description = edit_description.getText().toString();
                            imageDTOIrwon.imageName = file.getLastPathSegment();

                            database.getReference().child("irwon").push().setValue(imageDTOIrwon);

                            imageView.setImageURI(null);
                            edit_description.setText("");
                        }
                    });
                }
            });
        }
    }
}