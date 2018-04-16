package com.pwr.knif.omatko.votes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.pwr.knif.omatko.MainActivity
import com.pwr.knif.omatko.R
import kotlinx.android.synthetic.main.fragment_login.view.*
import java.lang.Exception

class LoginFragment : Fragment() {

    companion object {
        var mAuth: FirebaseAuth? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.buttonLogIn.setOnClickListener {
            val email = view.ediTextUserMail.text.trim()
            val password = view.editTextUserPassword.text.trim()

            when {
                email.isNullOrEmpty() -> Toast.makeText(activity,
                        "Wprowadź adres email!",
                        Toast.LENGTH_LONG).show()
                password.isNullOrEmpty() -> Toast.makeText(activity,
                        "Wprowadź hasło!",
                        Toast.LENGTH_LONG).show()
                else ->
                    logIn(email.toString(), password.toString())
            }
        }

        return view
    }

    fun logIn(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(activity, "Możesz przystąpić do oceny wykładu!",
                                Toast.LENGTH_LONG).show()

                        (activity as MainActivity).swapManager.changeFragments(VoteFragment(), false)
                    }
                }.addOnFailureListener {
                    exception: Exception -> Toast.makeText(activity, exception.message + "Wpisany adres to: " + email, Toast.LENGTH_LONG).show() }


    }

}


