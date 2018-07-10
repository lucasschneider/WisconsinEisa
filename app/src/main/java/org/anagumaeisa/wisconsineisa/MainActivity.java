package org.anagumaeisa.wisconsineisa;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.anagumaeisa.wisconsineisa.music_ui.MusicPlayerActivity;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * This is the MainActivity for the Wisconsin Eisa org.anagumaeisa.wisconsineisa.App. It is divided into three tabs, using a
 * BottomNavigationView. The first tab populates mainListView with Anaguma Eisa menu options,
 * the second populates mainListView with OTDW menu options, and the third, after verifying
 * user credentials with Google Sign-In, shows members-only options.
 */
public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavView;
    ArrayAdapter mainListAdapter;
    ListView mainListView;
    LinearLayout signInButtonWrapper;

    // Variables for Google Sign-in
    SignInButton signInButton;
    GoogleSignInClient gsic;
    private static GoogleSignInAccount account;
    private static int RC_SIGN_IN = 100;

    // Variables for the user, if signed in
    private String userName;
    private String userEmail;

    private static String LOG_TAG = "MainActivityDbg";
    private static int selectedNavTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        gsic = GoogleSignIn.getClient(this, gso);

        // detect changes to a user's auth state that happen outside your app, such as
        // access token or ID token revocation, or perform cross-device sign-in
        gsic.silentSignIn();

        // Initialize default view
        setContentView(R.layout.activity_main);
        signInButtonWrapper = findViewById(R.id.sign_in_button_wrapper);
        signInButton = findViewById(R.id.sign_in_button);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });
        mainListView = findViewById(R.id.main_menu);
        initNavView();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("SelectedItemId", bottomNavView.getSelectedItemId());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int selectedItemId = savedInstanceState.getInt("SelectedItemId");
        bottomNavView.setSelectedItemId(selectedItemId);
    }

    @Override
    protected void onStart() {
        super.onStart();

        refreshLogin();
        // Restore the tab position of the main menu
        selectTab(bottomNavView.getMenu().getItem(selectedNavTab));
    }

    private void initNavView() {
        bottomNavView = findViewById(R.id.navigation);

        if (bottomNavView != null) {
            // Select first menu item by default and show Fragment accodingly
            Menu menu = bottomNavView.getMenu();
            selectTab(menu.getItem(0));

            // Set action to perform when any menu item is selected
            bottomNavView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        for (int position = 0; position < bottomNavView.getMenu().size(); position++) {
                            if (bottomNavView.getMenu().getItem(position).getItemId() == menuItem.getItemId()) {
                                selectedNavTab = position;
                            }
                        }
                        selectTab(menuItem);
                        return false;
                }
            });
        }
    }

    protected void selectTab(MenuItem item) {
        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.navigation_anaguma:
                setTitle("Anaguma Eisa");
                mainListView.setVisibility(View.VISIBLE);
                signInButtonWrapper.setVisibility(View.GONE);

                mainListAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.main_menu_anaguma));

                mainListView.setAdapter(mainListAdapter);

                mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View v, int p, long id) {
                        Uri uri;
                        Intent intent;

                        switch (p) {
                            // About
                            case 0:
                                Intent aboutAE = new Intent(getApplicationContext(), AboutAE.class);
                                startActivity(aboutAE);
                                break;
                            // History
                            case 1:
                                Intent historyAE = new Intent(getApplicationContext(), HistoryAE.class);
                                startActivity(historyAE);
                                break;
                            // Events
                            case 2:
                                Toast.makeText(getApplicationContext(), "Coming soon! Go to anagumaeisa.org for more info.", Toast.LENGTH_SHORT).show();
                                break;
                            // Photos
                            case 3:
                                uri = Uri.parse("https://www.flickr.com/photos/anagumaeisa/sets");
                                intent = new Intent(Intent.ACTION_VIEW, uri);
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                                break;
                            // Videos
                            case 4:
                                uri = Uri.parse("https://www.youtube.com/user/anagumaeisa");
                                intent = new Intent(Intent.ACTION_VIEW, uri);
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                                break;
                            // Contact
                            case 5:
                                intent = new Intent(Intent.ACTION_SENDTO);
                                intent.setData(Uri.parse("mailto:"));
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"contact@anagumaeisa.org"});
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry via WI Eisa org.anagumaeisa.wisconsineisa.App");
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
                break;
            case R.id.navigation_otdw:
                setTitle("OTDW");
                mainListView.setVisibility(View.VISIBLE);
                signInButtonWrapper.setVisibility(View.GONE);

                mainListAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.main_menu_otdw));

                mainListView.setAdapter(mainListAdapter);

                mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View v, int p, long id) {
                        Uri uri;
                        Intent intent;

                        switch (p) {
                            // About
                            case 0:
                                Intent aboutOTDW = new Intent(getApplicationContext(), AboutOTDW.class);
                                startActivity(aboutOTDW);
                                break;
                            // History
                            case 1:
                                Intent historyOTDW = new Intent(getApplicationContext(), HistoryOTDW.class);
                                startActivity(historyOTDW);
                                break;
                            // Practices
                            case 2:
                                Toast.makeText(getApplicationContext(), "Coming soon! Go to anagumaeisa.org for more info.", Toast.LENGTH_SHORT).show();
                                break;
                            // Performances
                            case 3:
                                Toast.makeText(getApplicationContext(), "Coming soon! Go to anagumaeisa.org for more info.", Toast.LENGTH_SHORT).show();
                                break;
                            // Photos
                            case 4:
                                uri = Uri.parse("https://www.flickr.com/photos/otdw/sets");
                                intent = new Intent(Intent.ACTION_VIEW, uri);
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                                break;
                            // Videos
                            case 5:
                                uri = Uri.parse("https://www.youtube.com/user/otdw");
                                intent = new Intent(Intent.ACTION_VIEW, uri);
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                                break;
                            // Contact
                            case 6:
                                intent = new Intent(Intent.ACTION_SENDTO);
                                intent.setData(Uri.parse("mailto:"));
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hhastings@madison.k12.wi.us", "lucas@anagumaeisa.org"});
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry via WI Eisa org.anagumaeisa.wisconsineisa.App");
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
                break;
            case R.id.navigation_members:

                if (account == null) {
                    setTitle("Member Login");
                    // User is not signed in

                    mainListView.setVisibility(View.GONE);
                    signInButton.setSize(SignInButton.SIZE_WIDE);
                    signInButtonWrapper.setVisibility(View.VISIBLE);
                } else {
                    setTitle("Members");

                    mainListView.setVisibility(View.VISIBLE);
                    signInButtonWrapper.setVisibility(View.GONE);

                    mainListAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.main_menu_members)) {
                        @NonNull
                        @Override
                        public View getView(int pos, View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(pos, convertView, parent);

                            if (pos == 3) {
                                view.setBackgroundColor(Color.parseColor("#FFD02323"));
                            }
                            return view;
                        }
                    };

                    mainListView.setAdapter(mainListAdapter);

                    mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, final View v, int p, long id) {
                            switch (p) {
                                // Music
                                case 0:
                                    Intent historyOTDW = new Intent(getApplicationContext(), MusicPlayerActivity.class);
                                    startActivity(historyOTDW);
                                    break;
                                // Practice Videos
                                case 1:
                                    Toast.makeText(getApplicationContext(), "Coming soon! Go to anagumaeisa.org for more info.", Toast.LENGTH_SHORT).show();
                                    break;
                                // Logout
                                case 2:
                                    Toast.makeText(getApplicationContext(), "Long-click to Logout", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });

                    mainListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int p, long id) {
                            if (p == 2) {
                                signOut();
                            }
                            return true;
                        }
                    });
                }
                break;
        }
    }

    private void refreshLogin() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            userName = account.getGivenName();
            userEmail = account.getEmail();
        } else {
            userName = null;
            userEmail = null;
        }

        if (bottomNavView.getSelectedItemId() == bottomNavView.getMenu().getItem(2).getItemId()) {
            selectTab(bottomNavView.getMenu().getItem(2));
        }
    }

    protected static boolean isLoggedIn() {
        return account != null;
    }

    /**
     * Start Intent for Google Sign-In
     */
    private void signIn() {
        Intent signInIntent = gsic.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Sing out of the members section/Google Sign-In
     */
    private void signOut() {
        gsic.signOut()
            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    refreshLogin();
                }
            });
    }

    /**
     * Listener for Activity results
     *
     * @param requestCode The ID code for a particular request
     * @param resultCode The ID code of the result received
     * @param data The Intent data received
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            if (account != null) {
                userName = account.getGivenName();
                userEmail = account.getEmail();

                // Send JSON request to compare to list of member emails

                RequestQueue queue = Volley.newRequestQueue(this);
                String url = "http://anagumaeisa.org/json/members?_format=json";

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
                                if (response.length() > 0) {
                                    ArrayList<String> userEmails = new ArrayList<>();

                                    for (int i = 0; i < response.length(); i++) {
                                        try {
                                            Log.d(LOG_TAG, response.getJSONObject(i).getString("gmail"));
                                            String memberEmail = response.getJSONObject(i).getString("gmail");

                                            if (!memberEmail.isEmpty()) {
                                                userEmails.add(response.getJSONObject(i).getString("gmail"));
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    if (userEmails.contains(userEmail)) {
                                        // Signed in successfully, show authenticated UI.
                                        refreshLogin();
                                        Toast.makeText(getApplicationContext(), "Welcome, " + userName + "!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Email not found.", Toast.LENGTH_LONG).show();
                                        gsic.revokeAccess();
                                        refreshLogin();
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error

                            }
                        });

                queue.add(jsonArrayRequest);
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

        }
    }
}
