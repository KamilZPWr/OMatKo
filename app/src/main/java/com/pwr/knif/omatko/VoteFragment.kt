package com.pwr.knif.omatko

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.pwr.knif.omatko.LoginFragment.Companion.mAuth
import kotlinx.android.synthetic.main.fragment_vote.view.*
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.doAsyncResult
import android.support.v7.app.AlertDialog


class VoteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_vote, container, false)
        val user = mAuth!!.currentUser!!.uid
        val voteButtons = listOf<Button>(view.buttonVoteOne, view.buttonVoteTwo,
                view.buttonVoteThree, view.buttonVoteFour, view.buttonVoteFive)
        val builder = AlertDialog.Builder(activity)

        view.buttonLogOut.setOnClickListener {
            logOut()
        }

        for (i in 0..4) {
            voteButtons[i].setOnClickListener {

                val eventId = getEventId(view)
                if (checkIfUserCanVote(eventId.toString(), user)) {

                    builder.setTitle("Potwierdź ocenę")
                    builder.setMessage("Jesteś pewny, że chcesz ocenić ten wykład na ${i + 1}? Oceny możesz dokonać tylko raz.")

                    builder.setPositiveButton("TAK", { dialog, _ ->
                        sendVoteToFirebase(user, i + 1, eventId.toString())
                        dialog.dismiss()
                    })

                    builder.setNegativeButton("NIE", { dialog, _ ->
                        dialog.dismiss()
                    })

                    val alert = builder.create()
                    alert.show()
                }
            }
        }

        return view
    }

    private fun checkIfUserCanVote(eventId: String, user: String): Boolean {

        val votes = doAsyncResult { DatabaseManager.getResultsById(eventId) }.get()
        return if (votes == null) {
            Toast.makeText(activity, "Wprowadź poprawny kod wykładu!", Toast.LENGTH_LONG).show()
            false
        } else {
            when (votes.votes.contains(user)) {
                true -> {
                    Toast.makeText(activity, "Wykład został już oceniony!", Toast.LENGTH_LONG).show()
                    false
                }
                false -> {
                    true
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }

    private fun getEventId(view: View): String? {

        val eventId = view.editTextEvendId.text.toString()

        return if (eventId != "") {
            eventId
        } else {
            Toast.makeText(activity, "Wprowadź kod wykładu!", Toast.LENGTH_LONG).show()
            null
        }

    }

    private fun sendVoteToFirebase(user: String, vote: Int, eventId: String) {

        val fbDatabase = FirebaseDatabase.getInstance()
        val reference = fbDatabase.getReference("results/$eventId/$user")
        reference.setValue(vote)

    }

    fun logOut() {
        FirebaseAuth.getInstance().signOut()
        val fragmentManager = activity.supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.voteFragment, LoginFragment()).commit()
        Toast.makeText(activity, "Użytkownik wylogowany!", Toast.LENGTH_LONG).show()
    }


}