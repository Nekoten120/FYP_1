package com.example.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.adapters.ChatAdapter;
import com.example.fyp.helpers.SendMessageInBg;
import com.example.fyp.interfaces.BotReply;
import com.example.fyp.models.Message;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.common.collect.Lists;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Vtube_Customer_service extends Login_MainActivity implements BotReply {
    //Initialize variable
    DrawerLayout drawerLayout;

    //Login
    TextView nameTv;
    TextView emailTv;
    TextView logoutbtn;

    //In put the ask question UI
    RecyclerView chatView;
    ChatAdapter chatAdapter;
    List<Message> messageList = new ArrayList<>();
    EditText editMessage;
    ImageButton btnSend;

    //dialogFlow
    private SessionsClient sessionsClient;
    private SessionName sessionName;
    private String uuid = UUID.randomUUID().toString();
    private String TAG = "Vtube_Customer_service";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vtube_customer_service);
        chatView = findViewById(R.id.chatView);
        editMessage = findViewById(R.id.editMessage);
        btnSend = findViewById(R.id.btnSend);
        chatAdapter = new ChatAdapter(messageList, this);
        chatView.setAdapter(chatAdapter);

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

        //Chat bot function
        chatAdapter = new ChatAdapter(messageList, this);
        chatView.setAdapter(chatAdapter);

        btnSend.setOnClickListener(view -> {
            String message = editMessage.getText().toString();
            if (!message.isEmpty()) {
                messageList.add(new Message(message, false));
                editMessage.setText("");
                sendMessageToBot(message);
                Objects.requireNonNull(chatView.getAdapter()).notifyDataSetChanged();
                Objects.requireNonNull(chatView.getLayoutManager())
                        .scrollToPosition(messageList.size() - 1);
            } else {
                Toast.makeText(Vtube_Customer_service.this, "Please enter text!", Toast.LENGTH_SHORT).show();
            }
        });

        setUpBot();
    }

    private void setUpBot() {
        try {
            InputStream stream = this.getResources().openRawResource(R.raw.fypchatbotocnp15052d26801d);
            GoogleCredentials fypchatbotocnp15052d26801d = GoogleCredentials.fromStream(stream)
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            String projectId = ((ServiceAccountCredentials) fypchatbotocnp15052d26801d).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(
                    FixedCredentialsProvider.create(fypchatbotocnp15052d26801d)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            sessionName = SessionName.of(projectId, uuid);

            Log.d(TAG, "projectId : " + projectId);
        } catch (Exception e) {
            Log.d(TAG, "setUpBot: " + e.getMessage());
        }
    }

    private void sendMessageToBot(String message) {
        QueryInput input = QueryInput.newBuilder()
                .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build();
        new SendMessageInBg(this, sessionName, sessionsClient, input).execute();
    }

    @Override
    public void callback(DetectIntentResponse returnResponse) {
        if(returnResponse!=null) {
            String botReply = returnResponse.getQueryResult().getFulfillmentText();
            if(!botReply.isEmpty()){
                messageList.add(new Message(botReply, true));
                chatAdapter.notifyDataSetChanged();
                Objects.requireNonNull(chatView.getLayoutManager()).scrollToPosition(messageList.size() - 1);
            }else {
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "failed to connect!", Toast.LENGTH_SHORT).show();
        }
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