package com.pwr.knif.omatko

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [PersonContactFragment.OnListFragmentInteractionListener]
 * interface.
 */
class PersonContactFragment : Fragment() {
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_personcontact_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.context
            view.layoutManager = LinearLayoutManager(context)

            //TODO: get real data
            val list = listOf(
                    PersonContact("Kamil Zawistowski","Programista",
                            "668-213-793","zawistowski96kamil@gmail.com",
                            "Jestem studentem III roku Matematyki Stosowanej na Politechnice Wrocławskiej. " +
                                    "Wszystkich zainteresowanych zapraszam do kontaktu!",R.drawable.ic_contact_person),
                    PersonContact("Jakub Dąbek","Programista",
                            "782-592-297","jakub.dabek@gmail.com",
                            "Jestem studentem I roku Informatyki na Politechnice Wrocławskiej. " +
                                    "Wszystkich zainteresowanych zapraszam do kontaktu!",R.drawable.ic_contact_person))

            view.adapter = PersonContactRecyclerViewAdapter(list, listener)
        }
        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: PersonContact)
    }
}
