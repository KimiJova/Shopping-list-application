package milos.jovanovic.sopinglista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class NewListActivity extends AppCompatActivity implements View.OnClickListener {

    Button save;
    Button ok;
    Button home;
    EditText editText_title;
    TextView textView_title;
    DBHelper db;
    ListViewAdapter adapter;

    RadioButton yes, no;
    Lists l;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        save = findViewById(R.id.save_dugme);
        save.setOnClickListener(this);

        ok = findViewById(R.id.okdug);
        ok.setOnClickListener(this);

        home = findViewById(R.id.home1);
        home.setOnClickListener(this);

        editText_title = findViewById(R.id.editText_naslov);
        textView_title = findViewById(R.id.naslovliste);

        yes = findViewById(R.id.yes);
        yes = findViewById(R.id.no);

        db = new DBHelper(this, DBHelper.DB_NAME, null, 1);
        adapter = new ListViewAdapter(this);

        Bundle bundle = getIntent().getExtras();
        user = bundle.getString("user", "Default");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.okdug && !editText_title.getText().toString().isEmpty()) {
            textView_title.setText(editText_title.getText().toString());
        } else if (view.getId() == R.id.save_dugme) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("username", user);
            intent.putExtras(bundle);

            if (yes.isChecked()) {
                l = new Lists(editText_title.getText().toString(), "No");
            } else {
                l = new Lists(editText_title.getText().toString(), "Yes");
            }

            if (db.doesListExist(textView_title.getText().toString())) {
                Toast.makeText(this, "Saving failed!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Saving successful!", Toast.LENGTH_SHORT).show();
                db.insertList(l, user);
                adapter.addItem(l);
                startActivity(intent);
            }

        } else if (view.getId() == R.id.okdug) {
            textView_title = findViewById(R.id.naslovliste);
            textView_title.setText(editText_title.getText().toString());
            editText_title.setText("");
        } else if (view.getId() == R.id.home1) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
    }
}