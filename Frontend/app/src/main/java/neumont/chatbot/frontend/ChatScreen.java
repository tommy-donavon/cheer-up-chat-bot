package neumont.chatbot.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatScreen extends AppCompatActivity {
    private EditText message;
    private TextView response, aiResponse;
    private ScrollView messages;
    private SpannableStringBuilder ssb = new SpannableStringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        message = findViewById(R.id.etMessage);

        response = findViewById(R.id.tvResponse);
//        aiResponse = findViewById(R.id.tvAIResponse);

        messages = findViewById(R.id.svMessages);

//        testConnection("hi");
    }

    public void testConnection(String userMessage) {

        RequestQueue rq = Volley.newRequestQueue(this);
        String URL = "http://10.0.2.2:8080/";
        JSONObject jb = new JSONObject();
        try {
            jb.put("message", userMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, URL, jb, response -> {
            try {
                System.out.println(response);
                String ok = (String) response.get("bot");
                this.response.append("Bot: " + ok + "\n\n");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> Log.e("error: ", error.toString()));
        rq.add(jr);

    }

    public void submitMessage(View view) {
        String messageText = message.getText().toString();
        testConnection(messageText);

        response.append("User: " + messageText + "\n");


    }

}