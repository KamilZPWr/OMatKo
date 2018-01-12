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
import org.jetbrains.anko.doAsync
import java.lang.ref.WeakReference
import android.provider.CalendarContract.Instances
import android.content.ContentUris
import android.database.Cursor

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

    fun chuj(event: Event, id: Long) {
        val INSTANCE_PROJECTION = arrayOf(
                Instances.EVENT_ID, // 0
                Instances.BEGIN,    // 1
                Instances.TITLE     // 2
        )
        // The indices for the projection array above.
        val PROJECTION_ID_INDEX = 0
        val PROJECTION_BEGIN_INDEX = 1
        val PROJECTION_TITLE_INDEX = 2

        var cur: Cursor? = null
        val cr = contentResolver

        // The ID of the recurring event whose instances you are searching
        // for in the Instances table
        val selection = Instances.EVENT_ID + " = ?"
        val selectionArgs = arrayOf(id.toString())

        // Construct the query with the desired date range.
        val builder = Instances.CONTENT_URI.buildUpon()
        ContentUris.appendId(builder, event.beginTime)
        ContentUris.appendId(builder, event.endTime)

        // Submit the query
        cur = cr.query(builder.build(),
                INSTANCE_PROJECTION,
                selection,
                selectionArgs,
                null)
        if(cur.moveToNext()) {
            var title: String? = null
            var eventID: Long = 0
            var beginVal: Long = 0

            // Get the field values
            eventID = cur.getLong(PROJECTION_ID_INDEX)
            beginVal = cur.getLong(PROJECTION_BEGIN_INDEX)
            title = cur.getString(PROJECTION_TITLE_INDEX)

            // Do something with the values.
            //Log.i(DEBUG_TAG, "Event:  " + title!!)
            //val calendar = Calendar.getInstance()
            //calendar.setTimeInMillis(beginVal)
            //val formatter = SimpleDateFormat("MM/dd/yyyy")
            //Log.i(DEBUG_TAG, "Date: " + formatter.format(calendar.getTime()))
        }
        cur.close()
    }

    override fun onStart() {
        val id = temporaryId
        val event = temporaryEvent

        if(id != null && event != null) {
            val INSTANCE_PROJECTION = arrayOf(
                    Instances.EVENT_ID, // 0
                    Instances.BEGIN,    // 1
                    Instances.TITLE     // 2
            )
            // The indices for the projection array above.
            val PROJECTION_ID_INDEX = 0
            val PROJECTION_BEGIN_INDEX = 1
            val PROJECTION_TITLE_INDEX = 2

            var cur: Cursor? = null
            val cr = contentResolver

            // The ID of the recurring event whose instances you are searching
            // for in the Instances table
            val selection = Instances.EVENT_ID + " = ?"
            val selectionArgs = arrayOf(id.toString())

            // Construct the query with the desired date range.
            val builder = Instances.CONTENT_URI.buildUpon()
            ContentUris.appendId(builder, event.beginTime)
            ContentUris.appendId(builder, event.endTime)

            // Submit the query
            cur = cr.query(builder.build(),
                    INSTANCE_PROJECTION,
                    selection,
                    selectionArgs,
                    null)
            if(cur.moveToNext()) {
                var title: String? = null
                var eventID: Long = 0
                var beginVal: Long = 0

                // Get the field values
                eventID = cur.getLong(PROJECTION_ID_INDEX)
                beginVal = cur.getLong(PROJECTION_BEGIN_INDEX)
                title = cur.getString(PROJECTION_TITLE_INDEX)

                // Do something with the values.
                //Log.i(DEBUG_TAG, "Event:  " + title!!)
                //val calendar = Calendar.getInstance()
                //calendar.setTimeInMillis(beginVal)
                //val formatter = SimpleDateFormat("MM/dd/yyyy")
                //Log.i(DEBUG_TAG, "Date: " + formatter.format(calendar.getTime()))
                if(title == event.title) {
                    event.isChecked = true
                    event.eventCalendarID = id
                    doAsync { DatabaseManager.updateEvent(event) }
                }
            }
            cur.close()
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
