package com.liza.ejemplo.sensores;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

  private DrawerLayout drawerLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    drawerLayout = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.navigation_view);
    navigationView.setNavigationItemSelectedListener(this);

    MenuItem menuItem = navigationView.getMenu().getItem(0);
    onNavigationItemSelected(menuItem);
  }

  @Override
  public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    int titulo= 0;
    FragmentManager fragmentManager = getSupportFragmentManager();
    switch (menuItem.getItemId()) {
      case R.id.nav_sensores:
        titulo = R.string.menu_sensores;
        Fragment fragment = SensoresFragment.newInstance(getString(titulo));
        fragmentManager.beginTransaction().replace(R.id.home_content, fragment).commit();
        break;
      case R.id.nav_historial:
        titulo = R.string.menu_historialsensores;
        Fragment historialFragment = new HistorialFragment();
        fragmentManager.beginTransaction().replace(R.id.home_content, historialFragment).commit();
        break;
      default:
        break;
    }
    setTitle(getString(titulo));

    drawerLayout.closeDrawer(GravityCompat.START);

    return true;
  }
}
