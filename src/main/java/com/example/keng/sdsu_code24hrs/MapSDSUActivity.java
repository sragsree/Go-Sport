package com.example.keng.sdsu_code24hrs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Sreerag on 11/18/2016.
 */

public class MapSDSUActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private Button btn_start_main, btn_start_google_map, btn_send;

    private DatabaseReference mDatabase, root;

    Spinner spinner_sports;
    EditText phone_number;

    String token = FirebaseAuth.getInstance().getCurrentUser().getUid();


    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_map);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        spinner_sports = (Spinner) findViewById(R.id.spinner_sports);
        phone_number = (EditText) findViewById(R.id.phone_number);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Sports, android.R.layout.simple_spinner_item);
        spinner_sports.setAdapter(adapter);
        spinner_sports.setOnItemSelectedListener(this);

















        btn_start_google_map = (Button) findViewById(R.id.btn_start_google_map);
        btn_start_main = (Button) findViewById(R.id.btn_start_main);
        btn_send =(Button) findViewById(R.id.btn_send);

        btn_start_google_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapSDSUActivity.this, MyLocationDemoActivity.class));
            }
        });

        btn_start_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapSDSUActivity.this, MainActivity.class));
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myPhone;
                myPhone = phone_number.getText().toString();
                mDatabase.child("users").child(token).child("Phone number").setValue(myPhone);
            }
        });



    }


















    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView myText = (TextView) view;
        Toast.makeText(this, "You selected " + myText.getText(), Toast.LENGTH_SHORT ).show();
        mDatabase.child("users").child(token).child("Sport").setValue(myText.getText());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
