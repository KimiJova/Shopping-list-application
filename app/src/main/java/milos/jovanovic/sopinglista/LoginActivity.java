package milos.jovanovic.sopinglista;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    Button b1;
    Button b2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login login = new Login();
        Register register = new Register();
        b1 = findViewById(R.id.dugme1);
        b2 = findViewById(R.id.dugme2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentLayout, login).addToBackStack(null).commit();
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.INVISIBLE);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentLayout, register).addToBackStack(null).commit();
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}