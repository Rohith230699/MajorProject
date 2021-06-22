package com.example.registerloginui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.StatusBarManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.view.WindowManager.*;

public class SelectionActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    MaterialCardView cardView1, cardView2;
    TextView selection;
    int flag = 0;
    Button next;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        Window window = SelectionActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(SelectionActivity.this, R.color.transparent));


        mAuth = FirebaseAuth.getInstance();
        cardView1 = findViewById(R.id.cardView);
        cardView2 = findViewById(R.id.cardView2);
        selection = findViewById(R.id.textView2);
        next = findViewById(R.id.nextStep);
        createRequest();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0)
                {
                    Toast.makeText(SelectionActivity.this, "Please select an option to proceed", Toast.LENGTH_SHORT).show();
                }
                else {
                    signIn();
                }
            }
        });
    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(flag == 1)
                                startActivity(new Intent(getApplicationContext(), FacultyActivity.class));
                            else
                                startActivity(new Intent(getApplicationContext(), StudentActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SelectionActivity.this, "Google Sign In failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void colorChange(View view) {
        flag = 1;
        cardView2.setCardBackgroundColor(ColorStateList.valueOf((Color.parseColor("#D8E1E1"))));
        cardView1.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#F0F8F5")));
        cardView2.setChecked(true);
        cardView1.setChecked(false);
        cardView1.setStrokeColor(ColorStateList.valueOf((Color.parseColor("#F0F8F1"))));
        cardView2.setStrokeColor(ColorStateList.valueOf((Color.parseColor("#171F63"))));
    }

    public void colorChanged(View view) {
        flag = 2;
        cardView2.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#F0F8F5")));
        cardView1.setCardBackgroundColor(ColorStateList.valueOf((Color.parseColor("#D8E1E1"))));
        cardView1.setChecked(true);
        cardView2.setChecked(false);
        cardView2.setStrokeColor(ColorStateList.valueOf((Color.parseColor("#F0F8F1"))));
        cardView1.setStrokeColor(ColorStateList.valueOf((Color.parseColor("#171F63"))));
    }

}