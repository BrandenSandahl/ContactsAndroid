package com.theironyard.contactsandroid;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.Map;


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


        /* DB stuff */
        ContactDbHelper myDbHelper = new ContactDbHelper(getBaseContext());

        //gets DB in Write mode
        SQLiteDatabase db = myDbHelper.getWritableDatabase();

        //create map of values
        ContentValues values = new ContentValues();
        values.put(ContactContract.ContactEntry.COLUMN_NAME_NAME, nameField.getText().toString());
        values.put(ContactContract.ContactEntry.COLUMN_NAME_PHONE, phoneField.getText().toString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                ContactContract.ContactEntry.TABLE_NAME,
                null,
                values);


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


    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (int i = 0; i < contacts.getCount(); i++) {   //this is how to loop through ArrayAdapters i guess
            editor.putString(contacts.getItem(i).getName(), contacts.getItem(i).getPhone());
        }
        editor.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();


        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        Map<String, ?> prefMap = sharedPreferences.getAll();

        for(Map.Entry<String, ?> entry: prefMap.entrySet()) {
            Contact contact = new Contact(entry.getKey(), (String) entry.getValue());
            contacts.add(contact);
        }
    }
}
