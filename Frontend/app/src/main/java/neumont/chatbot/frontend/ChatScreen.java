package neumont.chatbot.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ChatScreen extends AppCompatActivity {
    private EditText message;
    private TextView response, aiResponse;
    private ScrollView messages;
    private String typing = "typing...";
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
        setContentView(R.layout.activity_chat_screen);

        //Finding the View Elements by it's Id for reference later in code
        message = findViewById(R.id.etMessage);
        response = findViewById(R.id.tvResponse);
        messages = findViewById(R.id.svMessages);

        //Checking for a Guest User
        if (getIntent().getStringExtra("name") == null || getIntent().getStringExtra("password") == null) {
            testName = "guest";
            password = "guest";
        } else {

            testName = getIntent().getStringExtra("name");
            password = getIntent().getStringExtra("password");
        }
//


//        testConnection("hi");
    }


    public void testConnection(String userMessage) {
        //Creating a JsonObject connection with the bot
        RequestQueue rq = Volley.newRequestQueue(this);
        String URL = "http://10.0.2.2:8080/";
        JSONObject jb = new JSONObject();
        try {
            //Populating JSONObject with a key and the user's message
            jb.put("message", userMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Creating a JsonObjectRequest object to connect to the AI
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, URL, jb, response -> {
            try {
                System.out.println(response);

                //Getting the message and the user's name
                String ok = (String) response.get("bot");
                if (ok.contains("%s")) {
                    ok = String.format(ok, testName);
                }

                //Timer to simulate the Bot thinking of the best response
                TimeUnit.SECONDS.sleep(2);
                this.response.append("Bot: " + ok + "\n\n");

            } catch (JSONException | InterruptedException e) {
                e.printStackTrace();
            }

        }, error -> Log.e("error: ", error.toString())) {

            //Providing authorization of users
            @Override
            public Map<String, String> getHeaders() {
                String creds = testName + ":" + password;
                String encode = Base64.encodeToString(creds.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + encode);
                return headers;
            }
        };
        rq.add(jr);

    }

    public void submitMessage(View view) {
        //Getting the user's input from an EditText
        String messageText = message.getText().toString();

        //Calling the testConnection method to connect the bot with the user's text
        testConnection(messageText);

        response.append("(" + testName + ")" + ": " + messageText + "\n" + typing + "\n");

        message.setText("");
    }

    public void clearMessages(View view) {
        response.setText("");
    }

    public void backToChatHistoryScreen(View view) {
        //Creating Intent object to switch to the Chat History Screen
        Intent intent = new Intent(this, ChatHistory.class);
        startActivity(intent);
    }

    public void backToHomeScreen(View view) {
        //Creating Intent object to switch to the Main Login Screen
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}