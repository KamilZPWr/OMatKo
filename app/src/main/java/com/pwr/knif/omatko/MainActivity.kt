package com.pwr.knif.omatko

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main.*
import android.view.View

//import com.pwr.knif.omatko.R.id.viewPager


class MainActivity :
        AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        PersonContactFragment.OnPersonContactListFragmentInteractionListener {

    val swapManager = SwapManager(this)

    override fun onListFragmentInteraction(item: PersonContact) {
        Toast.makeText(this, "Contact clicked: ${item.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        setupNavigatorDrawer()

        swapManager.changeFragments(ScheduleFragment(), false)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.nav_instagram -> {
                return true
            }

            R.id.nav_fb ->{
                return true
            }

            R.id.nav_snap ->{
                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_schedule -> {
                swapManager.changeFragments(ScheduleFragment(),true)
                tab_layout.visibility = View.VISIBLE
            }
            R.id.nav_assessment -> {

            }
            R.id.nav_map -> {

            }
            R.id.nav_history -> {

            }
            R.id.nav_sponsors -> {

            }
            R.id.nav_contact -> {
                swapManager.changeFragments(PersonContactFragment(),true)
                tab_layout.visibility = View.GONE
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun setupNavigatorDrawer() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }


}
