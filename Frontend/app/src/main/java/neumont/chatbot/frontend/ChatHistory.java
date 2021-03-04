package neumont.chatbot.frontend;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class ChatHistory extends AppCompatActivity {
    private LinearLayout chatLayout;
    private String testName;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            //Hides the action bar to optimize more space
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_chat_history);

        //Finding the LinearLayout object by it's Id for reference later in code
        chatLayout = findViewById(R.id.chatLinearLayout);

        //Getting info from previous Login screen
        testName = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("password");
    }

    public void backToHomeScreen(View view) {
        //Creating Intent object to switch to the Main Login Screen
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void backToChatScreen(View view) {
        try {
            //Adding sleeper to avoid spamming the create chat button
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, ChatScreen.class);

        //Passing info through screens
        intent.putExtra("name", testName);
        intent.putExtra("password", password);

        //Text to be placed in button
        String btnText = "New Chat Here!";

        // Creating a new Button Object
        Button newChat = new Button(this);
        newChat.setText(btnText);
        newChat.setTextColor(Color.WHITE);

        //Creating a new Space Object to act as a divider
        Space space = new Space(this);
        space.setMinimumHeight(100);

        // Setting up the style for the buttons
        newChat.setBackgroundResource(R.drawable.custom_circle);
        newChat.setPadding(15, 15, 15, 15);

        // Adding an Onclick for each button created to go to the Chat Screen
        newChat.setOnClickListener(view1 -> {

            startActivity(intent);
        });

        //Adding objects into the ScrollView
        chatLayout.addView(space);
        chatLayout.addView(newChat);


    }
}