package com.pwr.knif.omatko

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.view_pager.*


class MainActivity :
        AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        PersonContactFragment.OnPersonContactListFragmentInteractionListener,
        ScheduleEventFragmentFriday.OnScheduleEventListFragmentInteractionListener{

    val swapManager: SwapManager = SwapManager(this)
    lateinit var viewPager: ViewPager
    lateinit var pagerAdapter: SchedulePagerAdapter
    val scheduleFragments:ArrayList<Fragment> = arrayListOf(ScheduleEventFragmentFriday())

    override fun onListFragmentInteraction(item: PersonContact) {
        Toast.makeText(this, "Contact clicked: ${item.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onListFragmentInteraction(item: ScheduleEvent) {
        if (item.isTicked == false){
            Toast.makeText(this,"Obserwujesz ${item.title}!",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: switch to main fragment
        try{
            viewPager = findViewById(R.id.viewPager)!!
            pagerAdapter = SchedulePagerAdapter(supportFragmentManager, scheduleFragments)
            viewPager.adapter = pagerAdapter
        }catch (ex:Exception){
            Toast.makeText(this,ex.message,Toast.LENGTH_LONG).show()
        }


        setSupportActionBar(toolbar)
        setupNavigatorDrawer()
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
