package neumont.chatbot.frontend;


import android.content.Intent;
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
    }

    public void signUpOnClick(View view) {
        Intent intent = new Intent(this, ChatHistory.class);
        startActivity(intent);

    }



}