package com.example.hmqcoffee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    ImageButton btnbacklogin;
    EditText edtemaillogin;
    EditText edtpasswordlogin;
    TextInputLayout emailloginwrapper, passwordloginwrapper;
    TextView txtforgotpass;
    Button btnlogin;
    CheckBox cbremember;

    public static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 1 ;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASSWORD = "passwordKey";
    public static final String REMEMBER = "remember";
    public static String gmail = "a";
    public static String image = "";

    SharedPreferences sharedpreferences;
    String TAG = "LoginActivity";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();

        loginButton = (LoginButton) findViewById(R.id.btnFB);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });

//signIn của GG
        signInButton = (SignInButton) findViewById(R.id.btnGG);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
//-----------------------------------------

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    gmail = user.getDisplayName();
                    image = String.valueOf(user.getPhotoUrl());
                } else {

                }
            }
        };

//Lấy keyhash của fb
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.hmqcoffee",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
//---------------------------------

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        initWidgets();
        loadData();

        AnhXa();

        edtemaillogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    emailloginwrapper.setError("Enter your email.");
                } else {
                    emailloginwrapper.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtpasswordlogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    passwordloginwrapper.setError("Enter your password.");
                } else {
                    passwordloginwrapper.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnbacklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HelloActivity.class);
                startActivity(intent);
            }
        });

        txtforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emaillogin = edtemaillogin.getText().toString();
                String passwordlogin = edtpasswordlogin.getText().toString();
                if (cbremember.isChecked()) {
                    saveData(emaillogin,passwordlogin);
                } else {
                    clearData();
                }
                Dangnhap(emaillogin,passwordlogin);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            mAuth.removeAuthStateListener(authStateListener);
            LoginManager.getInstance().logOut();
        }
    }

    //Đăng nhập fb
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
//------------------------------------

//signIn của GG
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
//-------------------------------------

    private void clearData() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    private void saveData(String username, String Pass) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, Pass);
        editor.putBoolean(REMEMBER, cbremember.isChecked());
        editor.commit();
    }

    private void loadData() {
        if (sharedpreferences.getBoolean(REMEMBER, false)) {
            edtemaillogin.setText(sharedpreferences.getString(USERNAME, ""));
            edtpasswordlogin.setText(sharedpreferences.getString(PASSWORD, ""));
            cbremember.setChecked(true);
        } else
            cbremember.setChecked(false);
    }

    private void initWidgets() {
        edtemaillogin = (EditText) findViewById(R.id.edtEmailLogin);
        edtpasswordlogin = (EditText) findViewById(R.id.edtPasswordLogin);
        cbremember = (CheckBox) findViewById(R.id.ckbRememberMe);
        btnlogin = (Button) findViewById(R.id.btnLogin);
    }

    private void Dangnhap(final String email, String pass) {
        mAuth.signInWithEmailAndPassword(email+"@gmail.com", pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_LONG).show();
                            gmail = email;
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //----------------------------------------------------------------------------------------------------//
    public void AnhXa() {
        btnbacklogin = (ImageButton) findViewById(R.id.btnBackLogin);
        btnlogin = (Button) findViewById(R.id.btnLogin);
        edtemaillogin = findViewById(R.id.edtEmailLogin);
        emailloginwrapper = findViewById(R.id.EmailLoginWrapper);
        edtpasswordlogin = findViewById(R.id.edtPasswordLogin);
        passwordloginwrapper = findViewById(R.id.PasswordLoginWrapper);
        txtforgotpass = (TextView) findViewById(R.id.txtForgotPass);

    }
}