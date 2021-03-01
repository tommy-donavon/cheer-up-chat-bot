package neumont.chatbot.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpScreen extends AppCompatActivity {

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
    }

    public void createUserOnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class); //Creating Intent object to switch to the Main Login Screen
        startActivity(intent); //Executing the event
    }
}