package neumont.chatbot.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        test = findViewById(R.id.tvTestOutput);
    }

    public void continueAsGuestOnClick(View view) {
    }

    public void signInOnClick(View view) {
    }

    public void signUpOnClick(View view) {
    }
}