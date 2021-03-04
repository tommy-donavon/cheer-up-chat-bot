package neumont.chatbot.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private Button confirmSignUp;
    private String blankErrorMessage = "Can't leave blank!";
    private String passwordMismatchMessage = "Passwords don't match!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Hides the action bar to optimize more space
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen2);

        //Finding UI elements by their Id
        username = findViewById(R.id.etSignUpUsername);
        email = findViewById(R.id.etSignUpEmail);
        password = findViewById(R.id.etSignUpPassword);
        reEnterPassword = findViewById(R.id.etSignUpPasswordCheck);

        confirmSignUp = findViewById(R.id.btnConfirmSignUp);

    }


    public void createUserOnClick(View view) {
        //Creating Intent object to switch to the Main Login Screen
        Intent intent = new Intent(this, MainActivity.class);

        //Getting the text from EditTexts
        String userNameTxt = username.getText().toString();
        String emailTxt = email.getText().toString();
        String passwordTxt = password.getText().toString();
        String reEnterPasswordTxt = reEnterPassword.getText().toString();

        //Checking for blank EditTexts
        if (userNameTxt.equals("") || emailTxt.equals("") || passwordTxt.equals("") || reEnterPasswordTxt.equals("")) {

            username.setHintTextColor(Color.RED);
            username.setHint(blankErrorMessage);

            password.setHintTextColor(Color.RED);
            password.setHint(blankErrorMessage);

            email.setHintTextColor(Color.RED);
            email.setHint(blankErrorMessage);

            reEnterPassword.setHintTextColor(Color.RED);
            reEnterPassword.setHint(blankErrorMessage);
        } else {

            //Checking to see if both passwords match
            if (!passwordTxt.equals(reEnterPasswordTxt)) {
                password.setHintTextColor(Color.RED);
                password.setHint(passwordMismatchMessage);
                password.setText("");

                reEnterPassword.setHintTextColor(Color.RED);
                reEnterPassword.setHint(passwordMismatchMessage);
                reEnterPassword.setText("");
            } else {

                //Creating the user
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

    public void backToLogin(View view) {
        //Creating Intent object to switch back to Login screen
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}