package neumont.chatbot.frontend;


import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide(); //Hides the action bar to optimize more space
        }
        catch (NullPointerException e){ //Catching the possibility of a Null value
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Finding the EditText object by it's Id for reference later in code
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);


    }

    public void continueAsGuestOnClick(View view) {
        Intent intent = new Intent(this, ChatScreen.class); //Creating Intent object to switch to the Chat Screen
        startActivity(intent); //Executing the event
    }

    public void signInOnClick(View view) {

        Intent intent = new Intent(this, ChatHistory.class); //Creating Intent object to switch to the Chat History Screen

        //Getting the inputs as strings
        String userNameTxt = username.getText().toString();
        String passwordTxt = password.getText().toString();

        //Checking based on authentication using hardcoded values
        if (userNameTxt.equals("Admin") && passwordTxt.equals("test")) {
            startActivity(intent); //Executing the event if condition is true
        } else {
            username.setHintTextColor(Color.RED);
            password.setHintTextColor(Color.RED);

            username.setHint("Invalid Username");
            password.setHint("Invalid Password");

            username.setText("");
            password.setText("");

        }


    }

    public void signUpOnClick(View view) {


    }


}