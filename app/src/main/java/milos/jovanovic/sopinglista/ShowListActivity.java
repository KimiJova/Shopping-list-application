package milos.jovanovic.sopinglista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.UUID;

public class ShowListActivity extends AppCompatActivity {

    private String title_SLA;
    private Button button_add_SLA;
    private EditText editText_SLA;
    private ListView list_SLA;
    private TextView textView_SLA;

    private DBHelper db;
    ShopListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        button_add_SLA = findViewById(R.id.button_SLA);
        editText_SLA = findViewById(R.id.editText_SLA);
        textView_SLA = findViewById(R.id.name_SLA);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            title_SLA = getIntent().getStringExtra("naslov");
            textView_SLA.setText(title_SLA);
        }
        list_SLA = findViewById(R.id.lista_SLA);
        adapter = new ShopListAdapter(this);
        list_SLA.setAdapter(adapter);

        db = new DBHelper(this, DBHelper.DB_NAME, null, 1);

        button_add_SLA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_SLA.getText().toString().equals("")) {
                    Toast.makeText(ShowListActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                } else {
                    Zadatak z = new Zadatak(editText_SLA.getText().toString(), false, null);
                    String uID = UUID.randomUUID().toString();
                    db.insertItem(z, uID, title_SLA);
                    adapter.addItem(new Zadatak(editText_SLA.getText().toString(), false, uID));
                }
            }
        });

    }

    protected void onResume() {
        super.onResume();

        Zadatak[] items = db.readItems(title_SLA);
        adapter.update(items);
    }
}