package com.pika.knct;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    private final int RC_SIGN_IN = 0;
    private final int RC_SIGN_OUT = 1;
    private GoogleSignInOptions gsOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestProfile()
            .requestEmail()
            .build();

    private GoogleSignInAccount gsAccount;
    private GoogleSignInClient gsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Connect with database
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        Intent loginIntent = getIntent();
        gsClient = GoogleSignIn.getClient(this, gsOptions);

        if (loginIntent.getIntExtra("requestCode", 0) == RC_SIGN_OUT) {
            signOut();
        }
        updateUI(gsAccount);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void updateUI(GoogleSignInAccount gsAccount) {
        if (gsAccount != null) {
            Log.i("GoogleSignInSuccessful", gsAccount.getEmail());

            // Local Database
            updateDatabase();

            // Server
            register(gsAccount);

            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            mainIntent.putExtra("fromCode", "LoginActivity");
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
            finish();
        } else {
            findViewById(R.id.sign_in_button).setOnClickListener(v -> {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            });
        }
    }

    // SignIn
    public void signIn() {
        Intent googleSignInIntent = gsClient.getSignInIntent();
        startActivityForResult(googleSignInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_SIGN_IN:
                Task<GoogleSignInAccount> gsAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignIn(gsAccountTask);
        }
    }

    private void handleSignIn(Task<GoogleSignInAccount> gsAccountTask) {
        try {
            gsAccount = gsAccountTask.getResult(ApiException.class);
            updateUI(gsAccount);
        } catch (ApiException e) {
            Log.w("GoogleSignInFailed", e);
        }
    }

    private void signOut() {
        gsClient.signOut().addOnCompleteListener(this, task -> {
            Log.i("GoogleSignOutSuccessful", "OK!");
        });
    }

    void register(GoogleSignInAccount gsAccount) {
        String displayName = gsAccount.getDisplayName();
        String id = gsAccount.getId();
        String email = gsAccount.getEmail();
        String photoURL = "null";
        if (gsAccount.getPhotoUrl() != null) {
            photoURL = gsAccount.getPhotoUrl().toString();
        }
        String[] args = {"id", "displayName", "email", "photoURI"};
        String[] params = {id, displayName, email, photoURL};
        String query = Server.queryBuilder(args, params);
        String url = Server.urlBuilder("register", query);

        // Update on server
        Log.i("URL", url);
        new registerTask().execute(url);
    }


    void updateDatabase () {
        User user = userViewModel.getSignedInUser();
        if (user != null) {
            userViewModel.signOutAllUsers();
        }
        insertDatabase();
    }

    void insertDatabase () {
        // Insert User
        User user = new User();
        user.setDisplayName(gsAccount.getDisplayName());
        user.setId(gsAccount.getId());
        user.setEmail(gsAccount.getEmail());
        if (gsAccount.getPhotoUrl() != null) {
            user.setPhotoURI(gsAccount.getPhotoUrl().toString());
        }
        user.setSignedIn("1");
        userViewModel.insertUser(user);
    }
}
