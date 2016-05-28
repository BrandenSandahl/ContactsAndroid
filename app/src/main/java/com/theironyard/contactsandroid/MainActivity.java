package com.theironyard.contactsandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    ArrayAdapter<Contact> contacts;
    EditText nameField, phoneField;
    Button addButton;
    ListView contactView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //link up my stuffs
        nameField = (EditText) findViewById(R.id.nameField);
        phoneField = (EditText) findViewById(R.id.phoneField);
        addButton = (Button) findViewById(R.id.addButton);
        contactView = (ListView) findViewById(R.id.contactView);

        //link the ArrayAdapter with the ListView
        contacts = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        contactView.setAdapter(contacts);


        addButton.setOnClickListener(this);
        contactView.setOnItemLongClickListener(this);  //man..i forgot about this! Also note that it's setting the LONG CLICK. looooong click.
        contactView.setOnItemClickListener(this);

    }



    @Override
    public void onClick(View v) {

        contacts.add(new Contact(nameField.getText().toString(), phoneField.getText().toString()));  //have to toString() here!!
        nameField.setText("");
        phoneField.setText("");

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Contact contact = contacts.getItem(position);
        contacts.remove(contact);
        System.out.println(contact);
        System.out.println(position);
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, SingleContactActivity.class);
        Contact contact = contacts.getItem(position);
        intent.putExtra("CONTACTNAME", contact.getName());
        intent.putExtra("CONTACTPHONE", contact.getPhone());
        startActivity(intent);
        
    }
}
