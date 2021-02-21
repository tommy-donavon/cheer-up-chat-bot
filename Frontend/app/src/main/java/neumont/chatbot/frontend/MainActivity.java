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
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
//        test = findViewById(R.id.tvTestOutput);

    }

    public void continueAsGuestOnClick(View view) {
        Intent intent = new Intent(this, ChatScreen.class);
        startActivity(intent);
    }

    public void signInOnClick(View view) {
        //Creating Intent Object to access other screens
        Intent intent = new Intent(this, ChatHistory.class);

        //Getting the inputs as strings
        String userNameTxt = username.getText().toString();
        String passwordTxt = password.getText().toString();

        //Checking based authentication using hardcoded values
        if (userNameTxt.equals("Admin") && passwordTxt.equals("test")) {
            startActivity(intent);
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