package com.publicarea.course;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.publicarea.course.Fragments.CourseFragment;
import com.publicarea.course.Fragments.SettingsFragment;
import com.publicarea.course.Fragments.addCourseFragment;
import com.publicarea.course.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        final EditText password = new EditText(getApplicationContext());
        CourseFragment homeFragment = new CourseFragment();
        loadFragments(homeFragment);
        drawerLayout.closeDrawer(GravityCompat.START);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.course:
                        CourseFragment homeFragment = new CourseFragment();
                        loadFragments(homeFragment);
                        break;
                    case R.id.add_course:
                        alertDialog.setTitle("PASSWORD");
                        alertDialog.setMessage("Enter Password");


                        final EditText input = new EditText(MainActivity.this);

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        alertDialog.setView(input);
                        alertDialog.setIcon(R.drawable.book);

                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String value = input.getText().toString();
                                String password = "alampur";
                                if (value.equals("")) {
                                    Toast.makeText(MainActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
                                } else if (value.equals(password)) {
                                    addCourseFragment addCourseFragmen = new addCourseFragment();
                                    loadFragments(addCourseFragmen);

                                } else {
                                    Toast.makeText(MainActivity.this, "You are not admin", Toast.LENGTH_SHORT).show();
                                    drawerLayout.closeDrawer(GravityCompat.START);

                                }
                            }
                        });
                        alertDialog.setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        drawerLayout.openDrawer(GravityCompat.START);
                                    }
                                });

                        alertDialog.show();
                        drawerLayout.closeDrawer(GravityCompat.START);

                        ////////////////////////////////////////////////////////////////////////////////

                        break;
//                    case R.id.settings:
//                        SettingsFragment settingsFragmen = new SettingsFragment();
//                        loadFragments(settingsFragmen);
//                        break;

                }
                return true;
            }
        });

    }

    private void loadFragments(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            finish();
        }
    }
}