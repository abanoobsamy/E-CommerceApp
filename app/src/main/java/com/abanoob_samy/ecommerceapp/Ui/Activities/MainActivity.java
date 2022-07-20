package com.abanoob_samy.ecommerceapp.Ui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.abanoob_samy.ecommerceapp.R;
import com.abanoob_samy.ecommerceapp.Ui.Fragments.HomeFragment;
import com.abanoob_samy.ecommerceapp.Ui.SignUser.GoSignActivity;
import com.abanoob_samy.ecommerceapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        HomeFragment homeFragment = new HomeFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.homeContainer, homeFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_logout:

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, GoSignActivity.class));
                finish();
                break;

            case R.id.menu_my_cart:

                startActivity(new Intent(MainActivity.this, CartActivity.class));
                break;
        }

        return false;
    }
}