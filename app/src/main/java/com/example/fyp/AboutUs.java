package com.example.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

public class AboutUs extends Login_MainActivity {
    Button buttonSend;
    EditText textTo;
    EditText textSubject;
    EditText textMessage;

    //Initialize variable
    DrawerLayout drawerLayout;

    //Login
    TextView nameTv;
    TextView emailTv;
    TextView logoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //logout

        context = this;
        init();


        //Show the user name and email
        nameTv = findViewById(R.id.nameTv);
        nameTv.setText(sharedPreferences.getString("name",""));
        emailTv = findViewById(R.id.emailTv);
        emailTv.setText(sharedPreferences.getString("email",""));

        //Send the email
        buttonSend = (Button)findViewById(R.id.buttonSend);
        textTo = (EditText)findViewById(R.id.editTextTo);
        textSubject = (EditText)findViewById(R.id.editTextSubject);
        textMessage = (EditText)findViewById(R.id.editTextMessage);

            buttonSend.setOnClickListener(new Button.OnClickListener() {

                public  void onClick(View v) {

                    String to = textTo.getText().toString();
                    String subject = textSubject.getText().toString();
                    String massage = textMessage.getText().toString();

                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_TEXT, new String[]{to});

                    email.putExtra(Intent.EXTRA_SUBJECT, subject);
                    email.putExtra(Intent.EXTRA_TEXT, massage);

                    email.setType("message/rfo882");
                    startActivity(Intent.createChooser(email, "Choose an Email Client"));
            }
        } );


        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
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
        MainActivity.redirectActivity(this,Shopping_cart_MainActivity.class);
    }

    public void ClickAboutUs(View view){
        //Recreate activity
        recreate();

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