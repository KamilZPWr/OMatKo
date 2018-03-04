package com.pwr.knif.omatko.contact

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pwr.knif.omatko.R
import com.pwr.knif.omatko.XmlPraser

class PersonContactFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_personcontact_list, container, false)

        if (view is RecyclerView) {
            val context = view.context
            val contacts = XmlPraser().getContactsFromFile(activity)

            view.layoutManager = LinearLayoutManager(context)
            view.adapter = PersonContactRecyclerViewAdapter(contacts)
        }
        return view
    }
}


