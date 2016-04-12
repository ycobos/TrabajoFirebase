package andoresu.listview;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private static final String TAG = "AS_ListView";
    public String BaseDatos= "listview00";
    private EditText mBaseDatos;


    public Firebase miFirebaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);



        miFirebaseRef = new Firebase("https://" +BaseDatos+ ".firebaseio.com/");
        Toast.makeText(this, (String) BaseDatos,
                Toast.LENGTH_LONG).show();


        listView = (ListView) findViewById(R.id.listView);

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, getResources().getStringArray(R.array.fi)); */

        Button btncrear = (Button) findViewById(R.id.crearBtn);
        btncrear.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.show();

                final EditText mMessageEdit = (EditText) dialog.findViewById(R.id.DialogEditText);


                Button AgregarBtn = (Button) dialog.findViewById(R.id.CrearBtn);
                AgregarBtn.setOnClickListener(new View.OnClickListener(){


                    @Override
                    public void onClick(View v) {
                        String message = mMessageEdit.getText().toString();
                        Map<String,String> values = new HashMap<>();
                        values.put("name", message);

                        Firebase mFirebaseRef= new Firebase("https://" +BaseDatos+ ".firebaseio.com/");
                        mFirebaseRef.push().setValue(values);
                        mMessageEdit.setText("");

                    }
                });

                Button CancelarBtn = (Button) dialog.findViewById(R.id.CambiarBtn);
                CancelarBtn.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });


            }
        });


        miFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<String> names = new ArrayList<String>();
                ArrayList<String> keys = new ArrayList<String>();

                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    names.add(postSnapshot.child("name").getValue().toString());
                    keys.add(postSnapshot.getRef().getKey().toString());

                }
                String[] nombres= new String[names.size()];
                nombres= names.toArray(nombres);
                String [] llaves = new String[keys.size()];
                llaves = keys.toArray(llaves);
                CustomAdapter adapter = new CustomAdapter(MainActivity.this, nombres,llaves, miFirebaseRef);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        Button CambiarDB = (Button) findViewById(R.id.CambiarBtn);
        CambiarDB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.show();

                final EditText mMessageEdit = (EditText) dialog.findViewById(R.id.DialogEditText);


                Button AgregarBtn = (Button) dialog.findViewById(R.id.CrearBtn);
                AgregarBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nombreBD= mMessageEdit.getText().toString();
                        BaseDatos =nombreBD;
                        miFirebaseRef = new Firebase("https://" +BaseDatos + ".firebaseio.com/");
                        miFirebaseRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                ArrayList<String> names = new ArrayList<String>();
                                ArrayList<String> keys = new ArrayList<String>();


                                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                                    names.add(postSnapshot.child("name").getValue().toString());
                                    keys.add(postSnapshot.getRef().getKey().toString());
                                }
                                String[] nombres= new String[names.size()];
                                nombres= names.toArray(nombres);
                                String[] llaves = new String[keys.size()];
                                llaves= keys.toArray(llaves);
                                CustomAdapter adapter = new CustomAdapter(MainActivity.this, nombres, llaves,miFirebaseRef);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                        dialog.dismiss();

                    }
                });

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Texto " + (String) view.getTag());
            }
        });
    }

    public void onClickBoton(View view){
        Log.d(TAG, "Button Text " + (String) view.getTag());
    }



}
