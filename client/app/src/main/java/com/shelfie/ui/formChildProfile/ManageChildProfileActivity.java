package com.shelfie.ui.formChildProfile;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.shelfie.R;
import com.shelfie.model.GuardianUser;

public class ManageChildProfileActivity extends FragmentActivity {

    private Bundle prevBundle;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ChildProfileListFragment childProfileListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_child_profile);

        init();
    }

    private void init() {
        prevBundle = getIntent().getExtras();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        childProfileListFragment = new ChildProfileListFragment();
        childProfileListFragment.setArguments(prevBundle);
        fragmentTransaction.add(R.id.frag_child_profiles_list, childProfileListFragment);
        fragmentTransaction.commit();
    }


}