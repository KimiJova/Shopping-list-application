package milos.jovanovic.sopinglista;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    EditText username;
    EditText password;
    Button button_login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View n = inflater.inflate(R.layout.fragment_login, container, false);
        button_login = n.findViewById(R.id.dugme_login);
        username = n.findViewById(R.id.user_login);
        password = n.findViewById(R.id.pass_login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(getActivity(), DBHelper.DB_NAME, null, 1);

                if ((username.getText().toString().equals("admin") && password.getText().toString().equals("admin"))||
                        (db.checkUser(username.getText().toString(), password.getText().toString()))) {
                    Toast.makeText(getActivity(), "Login succesfull!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), WelcomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username.getText().toString());
                    i.putExtras(bundle);
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Login failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return n;
    }
}