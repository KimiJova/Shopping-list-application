package milos.jovanovic.sopinglista;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button b1_welcome;
    private Button b2_welcome;
    private TextView username;
    private String nasl;
    private ListView lista;
    private String saved_user;

    private DBHelper db;

    private ListViewAdapter adapter;
    private Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        username = findViewById(R.id.user_welcome);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            saved_user = bundle.getString("username");
            username.setText(saved_user);
        }

        b1_welcome = findViewById(R.id.dugme1_welcome);
        b2_welcome = findViewById(R.id.dugme2_welcome);
        b1_welcome.setOnClickListener(this);
        b2_welcome.setOnClickListener(this);
        adapter = new ListViewAdapter(this);

        db = new DBHelper(this, DBHelper.DB_NAME, null, 1);

        lista = findViewById(R.id.lista);
        lista.setAdapter(adapter);

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Lists list = (Lists) adapter.getItem(position);
                adapter.removeItem((Lists) adapter.getItem(position));
                db.deleteList(list.getName());
                return true;
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Lists lv = (Lists) adapter.getItem(i);
                nasl = lv.getName();
                Bundle bundle = new Bundle();
                bundle.putString("naslov", nasl);
                Intent ii = new Intent(WelcomeActivity.this, ShowListActivity.class);
                ii.putExtras(bundle);
                startActivity(ii);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dugme1_welcome) {
            Intent intent = new Intent(this, NewListActivity.class);
            AlertDialog.Builder build = new AlertDialog.Builder(this);
            build.setTitle("New List Dialog");
            build.setMessage("Are you sure you want to create new list?");
            build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Bundle bundle = new Bundle();
                    bundle.putString("user", username.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = build.create();
            dialog.show();
        } else if (view.getId() == R.id.dugme2_welcome) {
            Lists[] lists = db.readMyLists(username.getText().toString());
            adapter.update(lists);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Lists[] lists = db.readLists(username.getText().toString());
        adapter.update(lists);
    }
}