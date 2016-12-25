package com.example.philipp.asvzroulette.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.philipp.asvzroulette.authenticator.HashedPassword;
import com.example.philipp.asvzroulette.R;
import com.example.philipp.asvzroulette.javaClassFiles.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

public class SignupActivity extends AppCompatActivity {

    private DatabaseReference dbReference;
    private TextView textView;
    private TextView errorForename;
    private TextView errorSurname;
    private TextView errorPassword1;
    private TextView errorPassword2;
    private EditText forename;
    private EditText surname;
    private EditText email;
    private EditText password;
    private EditText checkPassword;
    private Button submit;
    public Boolean noErrors;
    private Long userCount;
    private User user;





    //Dave do your thing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //setup of all components
        textView = (TextView) findViewById(R.id.new_account);

        forename = (EditText) findViewById(R.id.editVorname);

        surname = (EditText) findViewById(R.id.editName);

        password = (EditText) findViewById(R.id.editPassword);

        checkPassword = (EditText) findViewById(R.id.editCheckPassword);

        email = (EditText) findViewById(R.id.editEmail);

        errorForename = (TextView) findViewById(R.id.errorForename);

        errorSurname = (TextView) findViewById(R.id.errorSurname);

        errorPassword1 = (TextView) findViewById(R.id.errorPassword);

        errorPassword2 = (TextView) findViewById(R.id.errorPassword2);


        submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //set all error messages invisible
                errorForename.setVisibility(View.INVISIBLE);
                errorPassword1.setVisibility(View.INVISIBLE);
                errorPassword2.setVisibility(View.INVISIBLE);
                errorSurname.setVisibility(View.INVISIBLE);
                noErrors = true;

                //get user input
                String forenameString = forename.getText().toString();
                String surnameString = surname.getText().toString();
                String passwordString = password.getText().toString();
                String checkPasswordString = checkPassword.getText().toString();
                final String emailString = email.getText().toString();

                //input verification and error message displaying
                if(passwordString.length() > 20){
                    errorPassword2.setVisibility(View.VISIBLE);
                    noErrors = false;
                }

                if(isNameTooLong(forenameString)){
                    errorForename.setVisibility(View.VISIBLE);
                    noErrors = false;
                }

                if(isNameTooLong(surnameString)){
                    errorSurname.setVisibility(View.VISIBLE);
                    noErrors = false;
                }

                if(!arePasswordsEqual(passwordString, checkPasswordString)){
                    errorPassword1.setVisibility(View.VISIBLE);
                    noErrors = false;
                }

                //Load the data to the database.
                if(noErrors){
                    HashedPassword passwordHash = new HashedPassword(passwordString); //generate the password hash
                    user = new User(forenameString, surnameString, emailString, passwordHash); //new User
                    dbReference  = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference countRef = dbReference.child("userCount");

                    countRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        //get the current number of users
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            userCount=(snapshot.getValue(Long.class));


                        }
                        @Override public void onCancelled(DatabaseError error) {}
                    });
                    dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            user.setUserID((userCount)+1);



                            dbReference.child("userCount").setValue(userCount+1);    //increase user count in database
                            dbReference.child("Users").child(Long.toString(getAscii(emailString))).setValue(user);//load new user information to database.
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    startActivity(new Intent(v.getContext(), LoginActivity.class));

                }



            }
        });





    }

    //Check if inputs aren't too long
    private boolean isNameTooLong(String s){
        return s.length() > 30;
    }


    //Check if password inputs are equal.
    private boolean arePasswordsEqual(String p1, String p2){
        if (p1.length() != p2.length()){
            return false;
        }
        for (int i = 0; i < p1.length(); i++){
            if (p1.charAt(i) != p2.charAt(i)){
                return false;
            }
        }
        return true;
    }

    private long getAscii(String s){
        long sum=0;
        for (int i=0; i<s.length(); i++){
            sum += (long) s.charAt(i);
        }
        return sum;
    }


}
