package com.makhabatusen.noteapp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AppBarConfiguration appBarConfiguration;
    NavController navController;

    /* HW#7
1. Добавить Firestore
2. При добавлении записи в Room, также добавлять в Firestore
3. При удалении удалять с Firestore
4. Удаление записи через Console */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        initNavController(navView);
        Prefs prefs = new Prefs(this);

        if (!prefs.isShown())
            navController.navigate(R.id.boardFragment);
            // checking user verification
//        else if (FirebaseAuth.getInstance().getCurrentUser() == null)
//            navController.navigate(R.id.phoneFragment);




    }

    private void initNavController(BottomNavigationView navView) {
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(R.id.navigation_home);
                list.add(R.id.navigation_dashboard);
                list.add(R.id.navigation_notifications);
                list.add(R.id.navigation_profile);
                if (list.contains(destination.getId()))
                    navView.setVisibility(View.VISIBLE);
                else navView.setVisibility(View.GONE);

                // Hiding the tool bar for board fragment
                if (destination.getId() == R.id.boardFragment)
                    getSupportActionBar().hide();

                else getSupportActionBar().show();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


}

