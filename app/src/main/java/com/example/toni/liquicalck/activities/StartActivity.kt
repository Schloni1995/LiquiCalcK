package com.example.toni.liquicalck.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.toni.liquicalck.firebaseOperations.FirebaseOperations
import com.example.toni.liquicalck.fragments.CalcFragment
import com.example.toni.liquicalck.fragments.NoticeFragment
import com.example.toni.liquidcalccompatible.R

class StartActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val menuItem = navigationView.menu.findItem(R.id.nav_calc)
        onNavigationItemSelected(menuItem)

        FirebaseOperations().test()
    }




    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //TODO Handle navigation view item clicks here.
        item.isChecked = true
        title = item.title
        val id = item.itemId

        if (id == R.id.nav_calc) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_main_layout, CalcFragment())
            ft.commit()
        } else if (id == R.id.nav_notices) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_main_layout, NoticeFragment())
            ft.commit()

        }
        //        else if (id == R.id.nav_aboutme)
        //        {
        //
        //        }
        // else if (id == R.id.nav_share)
        //        {
        //
        //        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
