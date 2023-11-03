package nm.vamk.assignment6;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText phoneNumberEditText;

    Button submitButton;

    String firstName;
    String lastName;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNameEditText = findViewById(R.id.editText_firstname);
        lastNameEditText = findViewById(R.id.editText_lastname);
        phoneNumberEditText = findViewById(R.id.editText_phonenumber);

        submitButton = findViewById(R.id.button_submit);
        submitButton.setOnClickListener(ButtonClickListener);

        //Here we read the content of users array from array.xml file
        //String[] users = getResources().getStringArray(R.array.users);

        //Here we define an array adapter with a style and a content list
        ArrayAdapter<User> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, UserDB.getUsersList());

        //Here we define the AutoCompleteTextView object
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.auto_complete_tw_user);
        //Here we define the required number of letters to be typed in the AutoCompleteTextView
        autoCompleteTextView.setThreshold(1);

        //Here we set the array adapter for the AutoCompleteTextView
        autoCompleteTextView.setAdapter(arrayAdapter);

        //Here we define ItemClickListener for the AutoCompleteTextView instance
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Here we get a copy of the text searched from the AutocompleteTextView and then modify it
                String text = autoCompleteTextView.getText().toString().replace(";", getResources().getString(R.string.test_txt));

                //Here we set the text of the AutocompleteTextView to the modified text
                autoCompleteTextView.setText(text);
            }
        });
    }

    private View.OnClickListener ButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button clickedButton = (Button) v;

            //Submit button event
            if (clickedButton.equals(submitButton)) {
                firstName = "";
                lastName = "";
                phoneNumber = "";

                if (firstNameEditText.getText().length() != 0) {
                    firstName = firstNameEditText.getText().toString();
                    firstNameEditText.setText("");
                } else {
                    firstNameEditText.setBackgroundColor(Color.rgb(255, 0, 0));
                }

                if (lastNameEditText.getText().length() != 0) {
                    lastName = lastNameEditText.getText().toString();
                    lastNameEditText.setText("");
                } else {
                    lastNameEditText.setBackgroundColor(Color.rgb(255, 0, 0));
                }

                if (phoneNumberEditText.getText().length() != 0) {
                    phoneNumber = phoneNumberEditText.getText().toString();
                    phoneNumberEditText.setText("");
                } else {
                    phoneNumberEditText.setBackgroundColor(Color.rgb(255, 0, 0));
                }

                if (!firstName.isEmpty() && !lastName.isEmpty() && !phoneNumber.isEmpty()) {
                    UserDB.addNewUserToDatabase(firstName, lastName, phoneNumber);
                }


            }


        }
    };
}