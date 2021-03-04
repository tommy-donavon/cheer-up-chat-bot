package neumont.chatbot.frontend;


import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            //Hides the action bar to optimize more space
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Finding the EditText object by it's Id for reference later in code
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);


    }

    public void continueAsGuestOnClick(View view) {
        //Creating Intent object to switch to the Chat Screen
        Intent intent = new Intent(this, ChatScreen.class);
        startActivity(intent);
    }

    public void signInOnClick(View view) {
        //Creating Intent object to switch to the Chat History Screen
        Intent intent = new Intent(this, ChatHistory.class);

        //Getting username and password from the SignUp Screen
        String userNameTxt = username.getText().toString();
        String passwordTxt = password.getText().toString();
        RequestQueue rq = Volley.newRequestQueue(this);
        String URL = "http://10.0.2.2:8080/login";
        JSONObject jsonBody = new JSONObject();
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, response -> {
            try {
                intent.putExtra("name", (String) response.get("username"));
                intent.putExtra("password", passwordTxt);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(intent);
        }, error -> {
            username.setHintTextColor(Color.RED);
            password.setHintTextColor(Color.RED);

            username.setHint("Invalid Username");
            password.setHint("Invalid Password");

            username.setText("");
            password.setText("");
        }) {

            //Providing authorization of users
            @Override
            public Map<String, String> getHeaders() {
                String creds = userNameTxt + ":" + passwordTxt;
                String encode = Base64.encodeToString(creds.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + encode);
                return headers;
            }
        };
        rq.add(jr);

    }

    public void signUpOnClick(View view) {
        //Creating Intent object to switch to the SignUp Screen
        Intent intent = new Intent(this, SignUpScreen.class);
        startActivity(intent);

    }


}