package com.example.philipp.asvzroulette;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Button signup,skip,login;
    private TextView loginText, errorEmail, errorPassword;
    private EditText email, password;
    private String stringEmail, stringPassword;
    private DatabaseReference dbReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylogin);

        loginText = (TextView) findViewById(R.id.loginText);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        errorEmail = (TextView) findViewById(R.id.errorEmailLogin);
        errorPassword = (TextView) findViewById(R.id.errorPasswordLogin);

        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        skip = (Button) findViewById(R.id.skip);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SignupActivity.class));
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MainActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View x) {
                //get user input
                stringEmail = email.getText().toString();
                stringPassword = password.getText().toString();
                errorEmail.setVisibility(View.INVISIBLE);
                errorPassword.setVisibility(View.INVISIBLE);

                //database update
                dbReference  = FirebaseDatabase.getInstance().getReference();
                try{

                    dbReference.child("Users").child(Long.toString(getAscii(stringEmail))).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            int i = 0;
                           String [] passwordInfo = new String[2];
                            for (DataSnapshot snap : snapshot.getChildren()){
                                String s = snap.getValue(String.class);
                                passwordInfo[i] = s;
                                i++;
                            }
                            try{
                            HashedPassword stringPasswordHash = new HashedPassword(stringPassword, passwordInfo[1]);


                                if (passwordInfo[0].equals(stringPasswordHash.getHashedPassword())) {
                                    startActivity(new Intent(x.getContext(), MainActivity.class));

                                } else {
                                    errorPassword.setVisibility(View.VISIBLE);
                                }
                            }
                            catch(java.lang.NullPointerException e){
                                errorEmail.setVisibility(View.VISIBLE);

                            }


                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                }

                catch (com.google.firebase.database.DatabaseException e){
                    errorEmail.setVisibility(View.VISIBLE);
                }


            }
        });
    }
    private long getAscii(String s){
        long sum=0;
        for (int i=0; i<s.length(); i++){
            sum += (long) s.charAt(i);
        }
        System.out.println(sum);
        return sum;
    }

}
