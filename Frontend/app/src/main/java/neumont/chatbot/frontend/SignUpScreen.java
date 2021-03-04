package neumont.chatbot.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpScreen extends AppCompatActivity {
    private EditText username, email, password, reEnterPassword;

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
        setContentView(R.layout.activity_sign_up_screen2);

        username = findViewById(R.id.etSignUpUsername);
        email = findViewById(R.id.etSignUpEmail);
        password = findViewById(R.id.etSignUpPassword);
        reEnterPassword = findViewById(R.id.etSignUpPasswordCheck);
    }


    public void createUserOnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class); //Creating Intent object to switch to the Main Login Screen
        String userNameTxt = username.getText().toString();
        String emailTxt = email.getText().toString();
        String passwordTxt = password.getText().toString();
        String reEnterPasswordTxt = reEnterPassword.getText().toString();

        if(!passwordTxt.equals(reEnterPasswordTxt)){
            password.setHintTextColor(Color.RED);
            password.setHint("Passwords don't match");
            password.setText("");

            reEnterPassword.setHintTextColor(Color.RED);
            reEnterPassword.setHint("Passwords don't match");
            reEnterPassword.setText("");
        }else{
            RequestQueue rq = Volley.newRequestQueue(this);
            String URL = "http://10.0.2.2:8080/create-user";
            JSONObject jBody = new JSONObject();
            try {
                jBody.put("username", userNameTxt);
                jBody.put("password", passwordTxt);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, URL, jBody, response ->
            {

                intent.putExtra("name", userNameTxt);
                intent.putExtra("password", passwordTxt);

                startActivity(intent);
            }, error -> {
                Log.e("Error", error.toString());

            }) {
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    if (response.data == null || response.data.length == 0) {
                        return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
                    } else {

                        return super.parseNetworkResponse(response);
                    }

                }
            };

            rq.add(jr);
        }



    }
}