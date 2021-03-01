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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide(); //Hides the action bar to optimize more space
        }
        catch (NullPointerException e){ //Catching the possibility of a Null value
            e.printStackTrace();
        }
        setContentView(R.layout.activity_chat_history);

        chatLayout = findViewById(R.id.chatLinearLayout); //Finding the LinearLayout object by it's Id for reference later in code

    }

    public void backToHomeScreen(View view) {
        Intent intent = new Intent(this, MainActivity.class); //Creating Intent object to switch to the Main Login Screen
        startActivity(intent); //Executing the event
    }


    public void backToChatScreen(View view) {
        try {
            TimeUnit.MILLISECONDS.sleep(1000); //Adding sleeper to avoid spamming the create chat button
        } catch (InterruptedException e) { //Catching the possibility of the timer being interrupted
            e.printStackTrace();
        }
        Intent intent = new Intent(this, ChatScreen.class); //Creating Intent object to switch to the Chat Screen
        String btnText = "New Chat Here!"; //Text to be placed in button


        Button newChat = new Button(this); // Creating a new Button Object
        newChat.setText(btnText); // Setting the text of the Button
        newChat.setTextColor(Color.WHITE); // Setting the color of the text
        

        Space space = new Space(this); //Creating a new Space Object to act as a divider
        space.setMinimumHeight(100); // Setting the height of that divider

        newChat.setBackgroundResource(R.drawable.custom_circle); // Setting up the style for the buttons
        newChat.setPadding(15,15,15,15);

        // Adding an Onclick for each button created to go to the Chat Screen
        newChat.setOnClickListener(view1 -> {

            startActivity(intent); //Executing the event
        });

        chatLayout.addView(space); //Adding the Space object into the ScrollView
        chatLayout.addView(newChat); //Adding the Button object into the ScrollView




    }
}