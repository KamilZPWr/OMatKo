package com.pwr.knif.omatko

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.messaging.FirebaseMessaging
import com.pwr.knif.omatko.R.id.*
import com.pwr.knif.omatko.contact.PersonContact
import com.pwr.knif.omatko.contact.PersonContactFragment
import com.pwr.knif.omatko.database.DatabaseManager
import com.pwr.knif.omatko.database.FbManager
import com.pwr.knif.omatko.map.AddressBookFragment
import com.pwr.knif.omatko.map.MapOpener
import com.pwr.knif.omatko.notifications.MyFirebaseMessagingService.NotificationType
import com.pwr.knif.omatko.schedule.Event
import com.pwr.knif.omatko.votes.LoginFragment.Companion.mAuth
import com.pwr.knif.omatko.schedule.EventsRecyclerViewAdapter
import com.pwr.knif.omatko.schedule.ScheduleFragment
import com.pwr.knif.omatko.schedule.TypeOfSchedule
import com.pwr.knif.omatko.votes.LoginFragment
import com.pwr.knif.omatko.votes.VoteFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main.*
import org.jetbrains.anko.doAsync
import java.lang.ref.WeakReference

enum class DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
}

class MainActivity :
        AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        MapOpener,
        PersonContactFragment.OnPersonContactListFragmentInteractionListener {

    private lateinit var scheduleFragments: List<Fragment>
    val swapManager = SwapManager(this)
    var temporaryHolder: WeakReference<EventsRecyclerViewAdapter.ViewHolder>? = null
    var temporaryId: Long? = null
    var temporaryEvent: Event? = null
    var calendarUsed: Boolean = false

    override fun onListFragmentInteraction(item: PersonContact) {
        Toast.makeText(this, "Contact clicked: ${item.name}", Toast.LENGTH_SHORT).show()
    }

    override fun openMap(location: LatLng, title: String) {
        val latLng = "${location.latitude},${location.longitude}"
        val uri = Uri.parse("geo:$latLng?q=$latLng($title)")

        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupNavigatorDrawer()
        FbManager.activateValueListener()
        DatabaseManager.databaseConnection(this)
        FirebaseMessaging.getInstance().subscribeToTopic("all")

        val bundles = Array(2) { Bundle() }
        bundles[0].putString(TypeOfSchedule.KEY, TypeOfSchedule.THEORETICAL.toString())
        bundles[1].putString(TypeOfSchedule.KEY, TypeOfSchedule.POPULARSCIENCE.toString())

        scheduleFragments = bundles.map { ScheduleFragment().apply { arguments = it } }

        val typeOfNotification = intent.action



        when (typeOfNotification) {
            NotificationType.THEORETICAL_SCHEDULE.toString() -> {
                swapManager.changeFragments(scheduleFragments[0], false)
                nav_view.setCheckedItem(nav_schedule_theoretical)
            }
            NotificationType.POPULARSCIENCE_SCHEDULE.toString() -> {
                swapManager.changeFragments(scheduleFragments[1], false)
                nav_view.setCheckedItem(nav_schedule_popular_science)
            }
            NotificationType.VOTE.toString() -> {
                tab_layout.visibility = View.GONE
                swapManager.changeFragments(LoginFragment(), false)
                nav_view.setCheckedItem(nav_assessment)
            }
            else -> {
                swapManager.changeFragments(scheduleFragments[0], false)
                nav_view.setCheckedItem(nav_schedule_theoretical)
            }
        }
    }

    override fun onStart() {
        if (calendarUsed) {
            val id = temporaryId
            val event = temporaryEvent

            val currentLastId = DatabaseManager.getMaxExistingEventId(contentResolver)

            if (currentLastId >= id ?: Long.MAX_VALUE && event != null) {

                event.isChecked = true
                event.eventCalendarID = currentLastId
                doAsync { DatabaseManager.updateEvent(event) }

                temporaryHolder?.get()?.update()
            }
            temporaryHolder = null
            temporaryId = null
            temporaryEvent = null
        }
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

        val swapManager = SwapManager(this)
        when (item.itemId) {
            R.id.nav_instagram -> {
                val intent = swapManager.getInstagramIntent()
                startActivity(intent)
                return true
            }

            R.id.nav_fb -> {
                val intent = swapManager.getFacebookIntent()
                startActivity(intent)
                return true
            }

            R.id.nav_snap -> {
                val intent = swapManager.getSnapchatIntent()
                startActivity(intent)
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
                swapManager.changeFragments(scheduleFragments[0], false)
                tab_layout.visibility = View.VISIBLE
            }
            R.id.nav_schedule_popular_science -> {
                swapManager.changeFragments(scheduleFragments[1], false)
                tab_layout.visibility = View.VISIBLE
            }
            R.id.nav_assessment -> {
                tab_layout.visibility = View.GONE
                if (mAuth == null || mAuth!!.currentUser == null) {
                    swapManager.changeFragments(LoginFragment(), false)
                } else {
                    swapManager.changeFragments(VoteFragment(), false)
                }
            }
            R.id.nav_map -> {
                swapManager.changeFragments(AddressBookFragment(), false)
                tab_layout.visibility = View.VISIBLE
            }
            R.id.nav_history -> {

            }
            R.id.nav_sponsors -> {

            }
            R.id.nav_contact -> {
                swapManager.changeFragments(PersonContactFragment(), false)
                tab_layout.visibility = View.GONE
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setupNavigatorDrawer() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

}
