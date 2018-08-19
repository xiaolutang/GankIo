package com.example.txl.gankio.change.mvp.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.txl.gankio.App;
import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseActivity;
import com.example.txl.gankio.change.mvp.data.User;
import com.example.txl.gankio.change.mvp.data.source.RepositoryFactory;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoginContract.View{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        initView();
    }

    protected void initView() {
        mEmailView = findViewById( R.id.email );
        mPasswordView = findViewById( R.id.password );
        mPasswordView.setOnEditorActionListener( new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        } );
        Button mEmailSignInButton = findViewById( R.id.email_sign_in_button );
        mEmailSignInButton.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.login( mEmailView.getText().toString().trim(), mPasswordView.getText().toString().trim());
            }
        } );
        Button mRegisterButton = findViewById( R.id.email_register_button );
        mRegisterButton.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.registerUser( mEmailView.getText().toString().trim(), mPasswordView.getText().toString().trim() );
            }
        } );
        mLoginFormView = findViewById( R.id.login_form );
        mProgressView = findViewById( R.id.login_progress );
        mLoginPresenter = new LoginPresenter( RepositoryFactory.provideUserRepository( this ),this,false);
    }


    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission( READ_CONTACTS ) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale( READ_CONTACTS )) {
            Snackbar.make( mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE )
                    .setAction( android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions( new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS );
                        }
                    } );
        } else {
            requestPermissions( new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS );
        }
        return false;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError( null );
        mPasswordView.setError( null );

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty( password ) && !isPasswordValid( password )) {
            mPasswordView.setError( getString( R.string.error_invalid_password ) );
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty( email )) {
            mEmailView.setError( getString( R.string.error_field_required ) );
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid( email )) {
            mEmailView.setError( getString( R.string.error_invalid_email ) );
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress( true );
            mAuthTask = new UserLoginTask( email, password );
            mAuthTask.execute( (Void) null );
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains( "@" );
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger( android.R.integer.config_shortAnimTime );

            mLoginFormView.setVisibility( show ? View.GONE : View.VISIBLE );
            mLoginFormView.animate().setDuration( shortAnimTime ).alpha(
                    show ? 0 : 1 ).setListener( new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility( show ? View.GONE : View.VISIBLE );
                }
            } );

            mProgressView.setVisibility( show ? View.VISIBLE : View.GONE );
            mProgressView.animate().setDuration( shortAnimTime ).alpha(
                    show ? 1 : 0 ).setListener( new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility( show ? View.VISIBLE : View.GONE );
                }
            } );
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility( show ? View.VISIBLE : View.GONE );
            mLoginFormView.setVisibility( show ? View.GONE : View.VISIBLE );
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        if(presenter == null) {
            throw new NullPointerException();
        } else {
        }
    }

    @Override
    public void registerSuccess() {
        Toast.makeText( this,"用户注册成功",Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void showUserExit(User user) {
        Toast.makeText( this,"用户已经存在，请直接登录",Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void loginSuccess(User user) {
        Toast.makeText( this,"登录成功",Toast.LENGTH_SHORT ).show();
        App.setLoginUser( user );
        finish();
    }

    @Override
    public void loginFailed() {
        Toast.makeText( this,"登录失败，请检查用户名和密码！",Toast.LENGTH_SHORT ).show();
        App.destoryLoginUser();
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep( 2000 );
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split( ":" );
                if (pieces[0].equals( mEmail )) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals( mPassword );
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress( false );

            if (success) {
                finish();
            } else {
                mPasswordView.setError( getString( R.string.error_incorrect_password ) );
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress( false );
        }
    }
}

