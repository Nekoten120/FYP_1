package com.example.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.drawerlayout.widget.DrawerLayout;

public class Vtube extends Login_MainActivity {
    //Initialize variable
    DrawerLayout drawerLayout;

    //Login
    TextView nameTv;
    TextView emailTv;
    TextView logoutbtn;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vtube);





        //logout

        context = this;
        init();

        //Show the user name and email
        nameTv = findViewById(R.id.nameTv);
        nameTv.setText(sharedPreferences.getString("name",""));
        emailTv = findViewById(R.id.emailTv);
        emailTv.setText(sharedPreferences.getString("email",""));

        //Vtube video side
        VideoView videoView=(VideoView) findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://com.example.fyp/"+R.raw.vtube_demo);
        videoView.start();

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Vtube.this, Vtube_Customer_service.class);
                startActivity(intent);
            }
        });
    }

    public void ClickMenu(View view){
        //Open drawer
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view ){
        //Close drawer
        MainActivity.closeDrawer(drawerLayout);
    }


    public void ClickBack(View view){
        //Open drawer
        finish();
    }
    public void ClickHome(View view){
        //Redirect activity to home
        MainActivity.redirectActivity(this,MainActivity.class);

    }

    public void ClickScanner(View view){
        //Redirect activity to home
        MainActivity.redirectActivity(this,Scanner.class);

    }


    public void ClickVtube(View view){
        //Redirect activity to Vtube
        MainActivity.redirectActivity(this,Vtube.class);


    }

    public void ClickVtube_Customer_service(View view){
        //Redirect activity to Vtube
        MainActivity.redirectActivity(this,Vtube_Customer_service.class);

    }

    public void ClickShoppingcart(View view){
        //Redirect activity to Vtube service
        MainActivity.redirectActivity(this,SummaryActivity.class);
    }

    public void ClickAboutUs(View view){
        //Recreate activity
        MainActivity.redirectActivity(this,AboutUs.class);

    }

    public void ClickLogout(View view){
        //Close app
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