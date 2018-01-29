package com.pwr.knif.omatko

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_login.view.*


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
            val email = view.ediTextUserMail.text.toString()
            val password = view.editTextUserPassword.text.toString()

            if (email != "" && password != "") {
                logIn(email, password)
            } else {
                when {

                    email == "" -> Toast.makeText(activity,
                            "Wprowadź adres email!",
                            Toast.LENGTH_LONG).show()

                    password == "" -> Toast.makeText(activity,
                            "Wprowadź hasło!",
                            Toast.LENGTH_LONG).show()
                }
            }
        }

        return view
    }

    fun logIn(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(activity, "Możesz przystąpić do oceny wykładu!",
                                Toast.LENGTH_LONG).show()

                        val fragmentManager = activity.supportFragmentManager
                        fragmentManager.beginTransaction().replace(R.id.loginFragment, VoteFragment()).commit()

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(activity, "Logowanie nie powiodło się, spróbuj jeszcze raz!",
                                Toast.LENGTH_LONG).show()
                    }
                })

    }

}


