package com.pwr.knif.omatko

import android.annotation.SuppressLint
import android.content.ContentResolver
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
import org.jetbrains.anko.doAsync
import java.lang.ref.WeakReference
import android.provider.CalendarContract.Instances
import android.content.ContentUris
import android.database.Cursor
import android.provider.CalendarContract
import android.util.Log

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

        DatabaseManager.databaseConnection(this)

        //TODO("Make class to get data from FB and update them in roomDB")

        doAsync { DatabaseManager.addEvents(testEvents()) }.get()
    }

    fun testEvents(): List<Event> {
        // DB test
        val timeStart = java.util.Calendar.getInstance().apply {
            set(2018,3,20,20,0)
        }
        val timeEnd = java.util.Calendar.getInstance().apply {
            set(2018,3,20,21,0)
        }

        val exampleEvent = Event("eventId","Tytuł wykładu 1","Rodzaj ","miejsce",
                "Krótki opis",
                "Jest to wykład o niczym, serdecznie nie zapraszam nikogo. Pozdrawiam",
                timeStart.timeInMillis, timeEnd.timeInMillis,"THEORETICAL","SATURDAY")
        return listOf(
                exampleEvent.copy(eventId = "event1", title = "Tytuł Wykłady Sob/1"),
                exampleEvent.copy(eventId = "event2", title = "Tytuł Wykładu Sob/2"),
                exampleEvent.copy(eventId = "event3", day = "SUNDAY", title = "Tytuł Wykłady Niedz/1"),
                exampleEvent.copy(eventId = "event4", day = "SUNDAY", title = "Tytuł Wykładu Niedz/2"))
    }

    var temporaryHolder: WeakReference<EventsRecyclerViewAdapter.ViewHolder>? = null
    var temporaryId: Long? = null
    var temporaryEvent: Event? = null

    @SuppressLint("MissingPermission")
    fun getCurrentEventId(cr: ContentResolver): Long {
        with(cr.query(
                CalendarContract.Events.CONTENT_URI,
                arrayOf("MAX(${CalendarContract.Events._ID}) as max_id"),
                null,
                null,
                CalendarContract.Events._ID)) {
            moveToFirst()
            val maxVal = getLong(getColumnIndex("max_id"))
            close()
            return maxVal
        }
    }

    override fun onStart() {
        val id = temporaryId
        val event = temporaryEvent

        val currentLastId = getCurrentEventId(contentResolver)

        if(currentLastId >= id ?: Long.MAX_VALUE && event != null) {

            event.isChecked = true
            event.eventCalendarID = currentLastId
            doAsync { DatabaseManager.updateEvent(event) }

            temporaryHolder?.get()?.update()
        }
        temporaryHolder = null
        temporaryId = null
        temporaryEvent = null
        super.onStart()
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
                // delete database for testing
                doAsync { DatabaseManager.nukeDatabase() }
                return true
            }

            R.id.nav_snap ->{
                // repopulate database with test data
                doAsync { DatabaseManager.addEvents(testEvents()) }
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
