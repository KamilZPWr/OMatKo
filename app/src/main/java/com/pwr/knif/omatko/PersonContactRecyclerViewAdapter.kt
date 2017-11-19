package com.pwr.knif.omatko

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.pwr.knif.omatko.PersonContactFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_personcontact.view.*

/**
 * [RecyclerView.Adapter] that can display a [PersonContact] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class PersonContactRecyclerViewAdapter(
        private val values: List<PersonContact>,
        private val listener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<PersonContactRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_personcontact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = values[position]
        
        holder.fill(contact)

        holder.view.setOnClickListener {
            listener?.onListFragmentInteraction(holder.personContact!!)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var personContact: PersonContact? = null
        
        fun fill(contact: PersonContact) {
            personContact = contact
            view.apply {
                tv_personcontact_name.text = contact.name
                tv_personcontact_position.text = contact.jobTitle
                tv_personcontact_telephone.text = contact.telephoneNumber
                tv_personcontact_mail.text = contact.mailAddress
                tv_personcontact_description.text = contact.description
                img_personcontact.setImageResource(contact.imageId)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + view.tv_personcontact_name.text + "'"
        }
    }
}
