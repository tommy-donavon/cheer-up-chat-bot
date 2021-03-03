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
    private String testName = getIntent().getStringExtra("name");
    private String password = getIntent().getStringExtra("password");


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
        setContentView(R.layout.activity_chat_screen);

        //Finding the View Elements by it's Id for reference later in code
        message = findViewById(R.id.etMessage);
        response = findViewById(R.id.tvResponse);
        messages = findViewById(R.id.svMessages);



//        testConnection("hi");
    }

    public void testConnection(String userMessage) {

        RequestQueue rq = Volley.newRequestQueue(this); //Creating a RequestQueue Object
        String URL = "http://10.0.2.2:8080/"; //Storing the URL needed to hit the endpoint
        JSONObject jb = new JSONObject(); //Creating a JSONObject object to store messages
        try {
            jb.put("message", userMessage); //Populating JSONObject with a key and the user's message
        } catch (JSONException e) { //Catching the possibility of a JSONException
            e.printStackTrace();
        }
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, URL, jb, response -> { //Creating a JsonObjectRequest object to connect to the AI
            try {
                System.out.println(response); //Printing the message in console for testing
                String ok = (String) response.get("bot");//Getting the message
                if(ok.contains("%s"))
                {
                    ok = String.format(ok, testName);
                }
                TimeUnit.SECONDS.sleep(2); //Timer to simulate the Bot thinking of the best response
                this.response.append("Bot: " + ok + "\n\n"); //Appending to the TextView to show the conversations

            } catch (JSONException | InterruptedException e) { //Catching the possibility of a JSON or Interrupted Exception
                e.printStackTrace();
            }

        }, error -> Log.e("error: ", error.toString())){
            @Override
            public Map<String, String> getHeaders() {
                String creds = testName + ":" + password;
                String encode = Base64.encodeToString(creds.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + encode);
                return headers;
            }
        }; //Printing the error in case there is one
        rq.add(jr); //Adding the JSON request to the RequestQueue

    }

    public void submitMessage(View view) {
        String messageText = message.getText().toString(); //Getting the user's input from an EditText
        testConnection(messageText); //Calling the testConnection method to connect the bot with the user's text

        response.append("User: " + messageText + "\n" + typing + "\n"); //Appending to the TextView to show the conversations

        message.setText(""); //Resetting the the EditText to empty
    }

    public void clearMessages(View view) {
        response.setText(""); //Resetting the TextView that shows the conversations to empty
    }

    public void backToChatHistoryScreen(View view) {
        Intent intent = new Intent(this, ChatHistory.class); //Creating Intent object to switch to the Chat History Screen
        startActivity(intent); //Executing the event
    }

    public void backToHomeScreen(View view) {
        Intent intent = new Intent(this, MainActivity.class); //Creating Intent object to switch to the Main Login Screen
        startActivity(intent); //Executing the event
    }
}