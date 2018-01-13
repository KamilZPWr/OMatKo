package com.pwr.knif.omatko

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class PersonContactFragment : Fragment() {

    private var listener: OnPersonContactListFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_personcontact_list, container, false)

        if (view is RecyclerView) {
            val context = view.context
            view.layoutManager = LinearLayoutManager(context)

            //TODO: get real data
            val list = listOf(
                    PersonContact("Kamil Zawistowski", "Programista",
                            "+48 668-213-793", "zawistowski96kamil@gmail.com",
                            "Jestem studentem III roku Matematyki Stosowanej na Politechnice Wrocławskiej. " +
                                    "Wszystkich zainteresowanych zapraszam do kontaktu!", R.drawable.ic_contact_person),
                    PersonContact("Jakub Dąbek", "Programista",
                            "+48 782-592-297", "jakub.dabek@gmail.com",
                            "Jestem studentem I roku Informatyki na Politechnice Wrocławskiej. " +
                                    "Wszystkich zainteresowanych zapraszam do kontaktu!", R.drawable.ic_contact_person),
                    PersonContact("Kamil Zawistowski", "Programista",
                            "+48 668-213-793", "zawistowski96kamil@gmail.com",
                            "Jestem studentem III roku Matematyki Stosowanej na Politechnice Wrocławskiej. " +
                                    "Wszystkich zainteresowanych zapraszam do kontaktu!", R.drawable.ic_contact_person),
                    PersonContact("Jakub Dąbek", "Programista",
                            "+48 782-592-297", "jakub.dabek@gmail.com",
                            "Jestem studentem I roku Informatyki na Politechnice Wrocławskiej. " +
                                    "Wszystkich zainteresowanych zapraszam do kontaktu!", R.drawable.ic_contact_person))

            view.adapter = PersonContactRecyclerViewAdapter(list, listener)
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnPersonContactListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnPersonContactListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnPersonContactListFragmentInteractionListener {
        fun onListFragmentInteraction(item: PersonContact)
    }
}
