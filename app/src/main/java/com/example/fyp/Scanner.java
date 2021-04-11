package com.example.fyp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.fyp.classifier.ImageClassifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Scanner extends Login_MainActivity {
    //Initialize variable
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1000;
    private static final int CAMERA_REQUEST_CODE = 10001;
    private static final int PERMISSIONS_REQUEST = 1;
    private ImageView imageView;
    private ListView listView;
    private ImageClassifier imageClassifier;
    DrawerLayout drawerLayout;

    //Login
    TextView nameTv;
    TextView emailTv;
    TextView logoutbtn;

    String ZXC = "a";
    int X = 0;
    String A = "Green tea $10";
    String B = "Milk tea $12";
    String C = "Oolong tea $13";
    TextView messgae;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        //logout

        context = this;
        init();


        //Show the user name and email
        nameTv = findViewById(R.id.nameTv);
        nameTv.setText(sharedPreferences.getString("name",""));
        emailTv = findViewById(R.id.emailTv);
        emailTv.setText(sharedPreferences.getString("email",""));


        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
        initializeUIElements();

        messgae = (TextView) findViewById(R.id.message2);
    }
    private void initializeUIElements() {
        imageView = findViewById(R.id.iv_capture);
        listView = findViewById(R.id.lv_probabilities);
        Button takepicture = findViewById(R.id.bt_take_picture);

        try {
            imageClassifier = new ImageClassifier(this);
        } catch (IOException e) {
            Log.e("EImage Classifier Error", "ERROR: " + e);
        }


        takepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermission()) {
                    openCamera();
                } else {
                    requestPermission();
                }
            }
        });
    }
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap photo = (Bitmap) Objects.requireNonNull(Objects.requireNonNull(data).getExtras()).get("data");
            imageView.setImageBitmap(photo);
            //pass this bitmap to classifier
            List<ImageClassifier.Recognition> predictions = imageClassifier.recognizeImage(photo, 0);
            final List<String> predictionsList = new ArrayList<>();
            for (ImageClassifier.Recognition recog : predictions) {
                predictionsList.add("Drink: " + recog.getName() + " Similarity rate: " + recog.getConfidence() + "%");
                ZXC = recog.getName();
                X = X+1;
                if (X == 1) {
                    A = ZXC;
                }
                if (X == 2) {
                    B = ZXC;
                }
                if (X == 3) {
                    C = ZXC;
                    X = 0;
                }
            }
            ArrayAdapter<String> predictionsAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, predictionsList);
            listView.setAdapter(predictionsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0){
                        Toast.makeText(Scanner.this,  A, Toast.LENGTH_LONG).show();
                        if (A.equals("Green tea $10")) {
                            String y = "0";
                            messgae.setText(y);
                            Intent intent = new Intent(Scanner.this, ProductInfo.class);
                            String keytext = y;
                            intent.putExtra("keytext", keytext);
                            startActivity(intent);
                        }else if (A.equals("Milk tea $12")) {
                            String y = "1";
                            messgae.setText(y);
                            Intent intent = new Intent(Scanner.this, ProductInfo.class);
                            String keytext = y;
                            intent.putExtra("keytext", keytext);
                            startActivity(intent);
                        }else if (A.equals("Oolong tea $13")) {
                            String y = "2";
                            messgae.setText(y);
                            Intent intent = new Intent(Scanner.this, ProductInfo.class);
                            String keytext = y;
                            intent.putExtra("keytext", keytext);
                            startActivity(intent);
                        }
                    }
                    if (i == 1){
                        Toast.makeText(Scanner.this,  B, Toast.LENGTH_LONG).show();
                        if (B.equals("Green tea $10")) {
                            String y = "0";
                            messgae.setText(y);
                            Intent intent = new Intent(Scanner.this, ProductInfo.class);
                            String keytext = y;
                            intent.putExtra("keytext", keytext);
                            startActivity(intent);
                        }else if (B.equals("Milk tea $12")) {
                            String y = "1";
                            messgae.setText(y);
                            Intent intent = new Intent(Scanner.this, ProductInfo.class);
                            String keytext = y;
                            intent.putExtra("keytext", keytext);
                            startActivity(intent);
                        }else if (B.equals("Oolong tea $13")) {
                            String y = "2";
                            messgae.setText(y);
                            Intent intent = new Intent(Scanner.this, ProductInfo.class);
                            String keytext = y;
                            intent.putExtra("keytext", keytext);
                            startActivity(intent);
                        }
                    }
                    if (i == 2){
                        Toast.makeText(Scanner.this,  C, Toast.LENGTH_LONG).show();
                        if (C.equals("Green tea $10")) {
                            String y = "0";
                            messgae.setText(y);
                            Intent intent = new Intent(Scanner.this, ProductInfo.class);
                            String keytext = y;
                            intent.putExtra("keytext", keytext);
                            startActivity(intent);
                        }else if (C.equals("Milk tea $12")) {
                            String y = "1";
                            messgae.setText(y);
                            Intent intent = new Intent(Scanner.this, ProductInfo.class);
                            String keytext = y;
                            intent.putExtra("keytext", keytext);
                            startActivity(intent);
                        }else if (C.equals("Oolong tea $13")) {
                            String y = "2";
                            messgae.setText(y);
                            Intent intent = new Intent(Scanner.this, ProductInfo.class);
                            String keytext = y;
                            intent.putExtra("keytext", keytext);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            if(hasAllPermissions(grantResults)) {
                openCamera();
            }else{
                requestPermission();
            }
        }
    }
    private boolean hasAllPermissions(int[] grantResults) {
        for(int result: grantResults){
            if (result == PackageManager.PERMISSION_DENIED)
                return false;
        }
        return true;
    }
    private void requestPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }
    private boolean hasPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
    public void ClickMenu(View view){
        //Open drawer
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view ){
        //Close drawer
        MainActivity.closeDrawer(drawerLayout);
    }



    public void ClickHome(View view){
        //Redirect activity to home
        MainActivity.redirectActivity(this,MainActivity.class);

    }

    public void ClickScanner(View view){
        //Redirect activity to home

        recreate();
    }


    public void ClickVtube(View view){
        //Redirect activity to Vtube
        MainActivity.redirectActivity(this,Vtube.class);

    }

    public void ClickVtube_Customer_service(View view){
        //Redirect activity to Vtube
        MainActivity.redirectActivity(this,Vtube_Customer_service.class);

    }

    public void ClickAboutUs(View view){
        //Recreate activity
        MainActivity.redirectActivity(this,AboutUs.class);

    }

    public void ClickShoppingcart(View view){
        //Redirect activity to Vtube service
        MainActivity.redirectActivity(this,Shopping_cart_MainActivity.class);
    }

    public void ClickLogout(View view){
        //Logout
        logout();
        intent = new Intent(context,LoginActivity.class);
        //remove all previous stack activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        MainActivity.closeDrawer(drawerLayout);
    }

}