package com.example.firstexercise.Activities;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.firstexercise.Classes.State;
import com.example.firstexercise.Fragments.FirstFragment;
import com.example.firstexercise.Fragments.SecondFragment;
import com.example.firstexercise.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements FirstFragment.OnFirstFragmentInteractionListener,
        SecondFragment.OnSecondFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Launcher);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        if (fragment == null) // if its the first time to call the first fragment
        {
            fragment = new FirstFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_container, fragment, "0").commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    /**
     * Replace the first fragment with the second fragment and send the picked state
     **/
    public void LoadSecFragment(State state) {

        SecondFragment secondFragment = new SecondFragment();

        getIntent().putExtra("StateObj", state);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fragment_container, secondFragment, (getSupportFragmentManager().getBackStackEntryCount() - 1) + "").addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() { // override the back button on android phones
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit the best app ever!?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }


    @Override
    public void onFirstFragmentInteraction(Uri uri) {
    }

    @Override
    public void onSecondFragmentInteraction(Uri uri) {
    }
}
