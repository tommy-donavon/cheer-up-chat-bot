package neumont.chatbot.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;

public class ChatHistory extends AppCompatActivity {
   private ScrollView chatHistory;
   private LinearLayout chatLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        setContentView(R.layout.activity_chat_history);
        chatHistory = findViewById(R.id.svChatHistory);
        chatLayout = findViewById(R.id.chatLinearLayout);

    }

    public void backToHomeScreen(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void backToChatScreen(View view) {
        Intent intent = new Intent(this, ChatScreen.class);
        String btnText = "New Chat Here!";


        Button newChat = new Button(this); // Creating a new Button Object
        newChat.setText(btnText); // Setting the text of the Button
        newChat.setTextColor(Color.WHITE); // Setting the color of the text

        Space space = new Space(this); //Creating a new Space Object to act as a divider
        space.setMinimumHeight(100); // Setting the height of that divider

        newChat.setBackgroundResource(R.drawable.custom_circle); // Setting up the style for the buttons
        newChat.setPadding(15,15,15,15);

        newChat.setOnClickListener(new View.OnClickListener() { // Adding an Onclick for each button created to go to the Chat Screen
            @Override
            public void onClick(View view) {

                startActivity(intent);
            }
        });

        chatLayout.addView(space); //Adding the Space object into the ScrollView
        chatLayout.addView(newChat); //Adding the Button object into the ScrollView




    }
}