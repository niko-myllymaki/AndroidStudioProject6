package nm.vamk.assignment6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    TextView searchResultTextView;
    EditText firstNameEditText, lastNameEditText, phoneNumberEditText;

    AutoCompleteTextView autoCompleteTextViewFirstName, autoCompleteTextViewLastName, autoCompleteTextViewPhoneNumber;

    ArrayAdapter<String> firstNameArrayAdapter, lastNameArrayAdapter, phoneNumberArrayAdapter;

    Button submitButton;

    String firstName, lastName, phoneNumber;

    StringBuilder stringBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchResultTextView = findViewById(R.id.tw_search_result);

        firstNameEditText = findViewById(R.id.editText_firstname);
        lastNameEditText = findViewById(R.id.editText_lastname);
        phoneNumberEditText = findViewById(R.id.editText_phonenumber);

        firstNameEditText.setOnTouchListener(onTouchListener);
        lastNameEditText.setOnTouchListener(onTouchListener);
        phoneNumberEditText.setOnTouchListener(onTouchListener);

        submitButton = findViewById(R.id.button_submit);
        submitButton.setOnClickListener(ButtonClickListener);

        //Here we define the AutoCompleteTextView object
        autoCompleteTextViewFirstName = findViewById(R.id.auto_complete_tw_firstName);
        autoCompleteTextViewLastName = findViewById(R.id.auto_complete_tw_lastName);
        autoCompleteTextViewPhoneNumber = findViewById(R.id.auto_complete_tw_phoneNumber);

        autoCompleteTextViewFirstName.setOnItemClickListener(ItemClickListener);
        autoCompleteTextViewLastName.setOnItemClickListener(ItemClickListener);
        autoCompleteTextViewPhoneNumber.setOnItemClickListener(ItemClickListener);

        //Here we define the required number of letters to be typed in the AutoCompleteTextView
        autoCompleteTextViewFirstName.setThreshold(1);
        autoCompleteTextViewLastName.setThreshold(1);
        autoCompleteTextViewPhoneNumber.setThreshold(1);

        stringBuilder = new StringBuilder();
        updateArrayAdapter();
    }

    private AdapterView.OnItemClickListener ItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String attributeValueToSearch = "";
            attributeValueToSearch = getAttributeValueToSearch(adapterView);
            searchResultTextView.setText("");

            stringBuilder = appendAllMatchedUsersToStringBuilder(attributeValueToSearch);

            //Display the text
            searchResultTextView.setText(stringBuilder);
            stringBuilder.setLength(0);
        }
    };

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
                } else {
                    firstNameEditText.setBackgroundColor(Color.rgb(255, 0, 0));
                }

                if (lastNameEditText.getText().length() != 0) {
                    lastName = lastNameEditText.getText().toString();
                } else {
                    lastNameEditText.setBackgroundColor(Color.rgb(255, 0, 0));
                }

                if (phoneNumberEditText.getText().length() != 0) {
                    phoneNumber = phoneNumberEditText.getText().toString();
                } else {
                    phoneNumberEditText.setBackgroundColor(Color.rgb(255, 0, 0));
                }

                if (!firstName.isEmpty() && !lastName.isEmpty() && !phoneNumber.isEmpty()) {
                    UserDB.addNewUserToDatabase(firstName, lastName, phoneNumber);
                    firstNameEditText.setText("");
                    lastNameEditText.setText("");
                    phoneNumberEditText.setText("");
                    updateArrayAdapter();
                }
            }
        }
    };

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            EditText clickedEditText = (EditText) v;
            if(clickedEditText.equals(firstNameEditText)) {
                firstNameEditText.setBackgroundColor(0);
            }

            if(clickedEditText.equals(lastNameEditText)) {
                lastNameEditText.setBackgroundColor(0);
            }

            if(clickedEditText.equals(phoneNumberEditText)) {
                phoneNumberEditText.setBackgroundColor(0);
            }

            return false;
        }
    };

    public void updateArrayAdapter() {

        //List of users
        List<User> users = UserDB.getUsersList();

        //Here we define an array adapters with a style and a content list
        firstNameArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                users.stream().map(User::getFirstName).collect(Collectors.toList()));

        lastNameArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                users.stream().map(User::getLastName).collect(Collectors.toList()));

        phoneNumberArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                users.stream().map(User::getPhoneNumber).collect(Collectors.toList()));

        //Here we set the array adapter for the AutoCompleteTextView
        autoCompleteTextViewFirstName.setAdapter(firstNameArrayAdapter);
        autoCompleteTextViewLastName.setAdapter(lastNameArrayAdapter);
        autoCompleteTextViewPhoneNumber.setAdapter(phoneNumberArrayAdapter);
    }

    public String getAttributeValueToSearch(AdapterView<?> adapterView) {
        //Checking which autoCompleteTextView was pressed and what value to search by
        String attributeValueToSearch = "";
        if(adapterView.getAdapter().toString().equals(autoCompleteTextViewFirstName.getAdapter().toString())) {
            attributeValueToSearch = autoCompleteTextViewFirstName.getText().toString();
        }

        if(adapterView.getAdapter().toString().equals(autoCompleteTextViewLastName.getAdapter().toString())) {
            attributeValueToSearch = autoCompleteTextViewLastName.getText().toString();
        }

        if(adapterView.getAdapter().toString().equals(autoCompleteTextViewPhoneNumber.getAdapter().toString())) {
            attributeValueToSearch = autoCompleteTextViewPhoneNumber.getText().toString();
        }

        return attributeValueToSearch;
    }

    public StringBuilder appendAllMatchedUsersToStringBuilder(String attributeValueToSearch) {
        String textToDisplay = "";
        //Getting all the users that match the given attribute value and putting them in a stringbuilder object to display on screen
        for(User user: UserDB.getUsersByAttribute(attributeValueToSearch)) {
            textToDisplay = user.toString().replace(";", getString(R.string.test_txt));
            stringBuilder.append(textToDisplay).append("\n");
        }

        return stringBuilder;
    }



}