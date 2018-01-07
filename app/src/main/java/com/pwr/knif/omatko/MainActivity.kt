package com.pwr.knif.omatko

import android.content.res.Resources
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
import kotlinx.android.synthetic.main.layout_main.*
import android.view.View

enum class DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    //override fun toString(): String = "day_of_week_${ this.name.toLowerCase() }"

    fun getResourceString(res: Resources): String {
        val id = res.getIdentifier(this.toString(), "string", this::class.java.`package`.name)

        return res.getString(id)
    }
}

class MainActivity :
        AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        PersonContactFragment.OnPersonContactListFragmentInteractionListener {

    lateinit var scheduleFragments: List<Fragment>
    val swapManager = SwapManager(this)

    override fun onListFragmentInteraction(item: PersonContact) {
        Toast.makeText(this, "Contact clicked: ${item.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupNavigatorDrawer()

        val bundles = Array(2) { Bundle() }
        bundles[0].putString(TypeOfSchedule.KEY, TypeOfSchedule.THEORETICAL.toString())
        bundles[1].putString(TypeOfSchedule.KEY, TypeOfSchedule.POPULARSCIENCE.toString())

        scheduleFragments = bundles.map { ScheduleFragment().apply { arguments = it } }

        swapManager.changeFragments(scheduleFragments[0], false)

        val databaseManager = DatabaseManager(this)
        databaseManager.databaseConnection()

        //TODO("Make class to get data from FB and update them in roomDB")

        // DB test
        val timeStart = java.util.Calendar.getInstance().apply {
            set(2017,11,20,20,0)
        }
        val timeEnd = java.util.Calendar.getInstance().apply {
            set(2017,11,20,21,0)
        }

        val list = listOf(Event("eventId","Dzień ","Rodzaj ","miejsce",
                "Krótki opis"
                , "Jest to wykład o niczym, serdecznie nie zapraszam nikogo. Pozdrawiam",
                timeStart.timeInMillis, timeEnd.timeInMillis,"THEORETICAL","SATURDAY"))

        DatabaseManager.AddEvents().execute(*list.toTypedArray())

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
            R.id.nav_schedule_theoretical -> {
                swapManager.changeFragments(scheduleFragments[0],false)
                tab_layout.visibility = View.VISIBLE
            }
            R.id.nav_schedule_popular_science -> {
                swapManager.changeFragments(scheduleFragments[1],false)
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
                swapManager.changeFragments(PersonContactFragment(),false)
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
